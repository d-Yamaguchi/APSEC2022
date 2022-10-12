package jp.ntt.sic.automig.rewrite.msmpl;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import jp.ntt.sic.automig.util.ReportUtil;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.commons.InstructionAdapter;
import org.objectweb.asm.util.TraceClassVisitor;

import fr.inria.controlflow.BranchKind;
import fr.inria.controlflow.ControlFlowGraph;
import fr.inria.controlflow.ControlFlowNode;
import spoon.Launcher;
import spoon.reflect.code.CtIf;
import spoon.reflect.code.CtOperatorAssignment;
import spoon.reflect.declaration.CtExecutable;
import spoon.smpl.CFGModel;
import spoon.smpl.Label;
import spoon.smpl.SmPLMethodCFG;
import spoon.smpl.SmPLMethodCFG.NodeTag;
import spoon.smpl.label.StatementLabel;

public class CFGFix {

    //--------------------- methods for CFGModel ------------------------------
    
    public static void fixModel(CFGModel cfg) {
        cfg.getStates()
            .stream()
            .map(cfg::getLabels)
            .forEach(ls -> {
                List<Label> newLabels = ls.stream()
                        .map(CFGFix::replaceLabel)
                        .collect(Collectors.toList());
                ls.clear();
                ls.addAll(newLabels);
            });
    }
    
    private static Label replaceLabel(Label label) {
        if (label instanceof StatementLabel) {
            return new MyStatementLabel(((StatementLabel) label).getCodeElement());
        }
        return label;
    }
    
    //--------------------- classes and method for SmPLMethodCFG ------------------------------
    /*
     * spoon-smpl 0.0.1 does not support statements containing branch except If-Statements.
     * When a method contains such statements (e.g. For-Statements), the constructor of SmPLMethodCFG throws an Exception.
     * The classes and methods defined below are solve the problem by reflection and byte-code transformation.
     * Because they depend on implementation detail of spoon-smpl, they do not work in future versions.
     */
    private static class MyClassVisitor extends ClassVisitor {
        TraceClassVisitor trace;
        
        public MyClassVisitor(int api, ClassVisitor classVisitor) {
            super(api, classVisitor);
            cv = classVisitor;
            trace = new TraceClassVisitor(cv, new PrintWriter(System.out));
        }

        @Override
        public MethodVisitor visitMethod(int access, String name, String descriptor, String signature,
                String[] exceptions) {
            if (name.equals("<init>")) {
                return new MyMethodVisitor(Opcodes.ASM9, cv.visitMethod(access, name, descriptor, signature, exceptions));
                //return trace.visitMethod(access, name, descriptor, signature, exceptions);
            }
            
            return cv.visitMethod(access, name, descriptor, signature, exceptions);
        }
        
        @Override
        public void visitEnd() {
            trace.visitEnd();
        }
    }
    
    private static class MyMethodVisitor extends InstructionAdapter {

        protected MyMethodVisitor(int api, MethodVisitor methodVisitor) {
            super(api, methodVisitor);
        }

        org.objectweb.asm.Label ignored;
        MethodVisitor tmpMv;

        @Override
        public void visitLineNumber(int line, org.objectweb.asm.Label start) {
            if (line == 416) {
                mv = tmpMv;
            } else if (line == 338){
                tmpMv = mv;
                mv = new MethodVisitor(Opcodes.ASM9) {};
            } else if (line == 413) {
                ignored = start;
            }

            super.visitLineNumber(line, start);
        }
        
        @Override
        public void visitLocalVariable(String name, String descriptor, String signature,
                org.objectweb.asm.Label start, org.objectweb.asm.Label end, int index) {
            if (!end.equals(ignored)) {
                super.visitLocalVariable(name, descriptor, signature, start, end, index);
            }
        }
    }
    
    private static class MyClassLoader extends ClassLoader{
        public MyClassLoader(ClassLoader contextClassLoader) {
            Thread.currentThread().setContextClassLoader(this);
        }

        public Class<?> defineClass(byte[] b) {
            return defineClass("spoon.smpl.SmPLMethodCFG", b, 0, b.length);
        }
    }

    private static MyClassLoader myClassLoader;
    private static Class<?> transformedClass;
    private static Constructor<?> transformedConstructor;
    private static Field cfgFieldInTransformed;
    private static Field cfgFieldInOriginal;

    private static MyClassLoader getMyClassLoader() {
        if (myClassLoader == null) {
            myClassLoader = new MyClassLoader(Thread.currentThread().getContextClassLoader());
        }
        return myClassLoader;
    }

    private static Class<?> getTransformedClass(){
        if (transformedClass == null) {
            try {
                ClassWriter cw = new ClassWriter(0);
                ClassReader cr;
                cr = new ClassReader("spoon.smpl.SmPLMethodCFG");
                cr.accept(new MyClassVisitor(Opcodes.ASM9, cw), ClassReader.EXPAND_FRAMES);
                transformedClass = getMyClassLoader().defineClass(cw.toByteArray());
            } catch (IOException e) {
                // TODO: handle exception
                e.printStackTrace();
            }
        }
        return transformedClass;
    }
    
    private static ControlFlowGraph getCFGFromTransformedInstance(Object instance) {
        if (cfgFieldInTransformed == null) {
            try {
                cfgFieldInTransformed = getTransformedClass().getDeclaredField("cfg");
                cfgFieldInTransformed.setAccessible(true);
            } catch (NoSuchFieldException | SecurityException e) {
                // TODO: handle exception
                e.printStackTrace();
            }
        }
        try {
            return (ControlFlowGraph)cfgFieldInTransformed.get(instance);
        } catch (IllegalArgumentException | IllegalAccessException e) {
            // TODO: handle exception
            e.printStackTrace();
            return null;
        }
    }
    
    private static void setCFGToSmPLMethodCFG(SmPLMethodCFG smPLMethodCFG, ControlFlowGraph cfg) {
        if (cfgFieldInOriginal == null) {
            try {
                cfgFieldInOriginal = SmPLMethodCFG.class.getDeclaredField("cfg");
                cfgFieldInOriginal.setAccessible(true);
            } catch (NoSuchFieldException | SecurityException e) {
                // TODO: handle exception
                e.printStackTrace();
            }
        }
        try {
            cfgFieldInOriginal.set(smPLMethodCFG, cfg);
        } catch (IllegalArgumentException | IllegalAccessException e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }
    
    private static void cfgBranchProcessor(ControlFlowGraph cfg) {
        int parentId = 0;
        for (ControlFlowNode node : cfg.findNodesOfKind(BranchKind.BRANCH)) {
            if (node.getStatement().getParent() instanceof CtIf) {
                CtIf ifStm = (CtIf) node.getStatement().getParent();
    
                int currentParentId = parentId++;
    
                NodeTag branchTag = new NodeTag("branch", ifStm);
                branchTag.setMetadata("parentId", currentParentId);
                node.setTag(branchTag);
    
                ControlFlowNode afterNode = node.getParent().findNodeById(node.getId() + 1);
    
                if (afterNode != null) {
                    NodeTag afterTag = new NodeTag("after", ifStm);
                    afterTag.setMetadata("parent", currentParentId);
                    afterNode.setTag(afterTag);
                }
    
                List<ControlFlowNode> branchNodes = new ArrayList<>();
    
                for (ControlFlowNode innerNode : node.next()) {
                    if (innerNode.getKind() != BranchKind.CATCH) {
                        branchNodes.add(innerNode);
                    }
                }
    
                if (branchNodes.size() != 2) {
                    throw new IllegalStateException("branch node with invalid successors");
                }
    
                ControlFlowNode n1 = branchNodes.get(0);
                ControlFlowNode n2 = branchNodes.get(1);
    
                NodeTag trueTag = new NodeTag("trueBranch", ifStm.getThenStatement());
                trueTag.setMetadata("parent", currentParentId);
    
                NodeTag falseTag = new NodeTag("falseBranch", ifStm.getElseStatement());
                falseTag.setMetadata("parent", currentParentId);
    
                // If only one successor is a BLOCK_BEGIN the branch is else-less.
                if (n1.getKind() == BranchKind.BLOCK_BEGIN && n2.getKind() == BranchKind.CONVERGE) {
                    n1.setTag(trueTag);
                } else if (n2.getKind() == BranchKind.BLOCK_BEGIN && n1.getKind() == BranchKind.CONVERGE) {
                    n2.setTag(trueTag);
                } else {
                    // Both successors are blocks
    
                    ControlFlowNode n1next = n1.next().get(0);
                    ControlFlowNode n2next = n2.next().get(0);
    
                    if (n1next.getKind() == BranchKind.CONVERGE && n2next.getKind() == BranchKind.CONVERGE) {
                        // Both blocks are empty, we can choose labels arbitrarily
                        n1.setTag(trueTag);
                        n2.setTag(falseTag);
                    } else {
                        // One or both blocks contains statements, must assign correct label
    
                        boolean n1HasStatements = n1next.getStatement() != null;
                        boolean n1IsTrueBranch;
    
                        if (n1HasStatements) {
                            n1IsTrueBranch = n1next.getStatement().getParent() == ifStm.getThenStatement();
                        } else {
                            n1IsTrueBranch = n2next.getStatement().getParent() == ifStm.getElseStatement();
                        }
    
                        if (n1IsTrueBranch) {
                            n1.setTag(trueTag);
                            n2.setTag(falseTag);
                        } else {
                            n1.setTag(falseTag);
                            n2.setTag(trueTag);
                        }
                    }
                }
            } else {
                node.setKind(BranchKind.STATEMENT);
                node.setTag(new NodeTag("unsupported", node.getStatement()));
            }
        }
    }
    
    private static Object createTransformedInstance(CtExecutable<?> executable) {
        if (transformedConstructor == null) {
            try {
                transformedConstructor = getTransformedClass().getConstructor(CtExecutable.class);
            } catch (NoSuchMethodException | SecurityException e) {
                // TODO handle exception
                e.printStackTrace();
            }
        }
        try {
            return transformedConstructor.newInstance(executable);
        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException
                | InvocationTargetException e) {
            // TODO handle exception
            e.printStackTrace();
            return null;
        }
    }
    
    private static void replaceTag(ControlFlowGraph cfg) {
        for (ControlFlowNode node : cfg.findNodesOfKind(BranchKind.STATEMENT)) {
            if(node.getStatement() instanceof CtOperatorAssignment) {
                node.setTag(new NodeTag("unsupported", node.getStatement()));
            }
        }
    }

    private static final CtExecutable<?> dummy = Launcher.parseClass("class Dummy{void dummy(){}}").getMethods().stream().findAny().get();
    
    public static SmPLMethodCFG createSmPLMethodCFG(CtExecutable<?> executable) {
        String path = executable.getPosition().getFile().getPath().substring(0, executable.getPosition().getFile().getPath().length() - 5) + "normalized.java";

        try {
            ReportUtil.fileOut(path, executable.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }

        SmPLMethodCFG result = new SmPLMethodCFG(dummy);
        ControlFlowGraph cfg = getCFGFromTransformedInstance(createTransformedInstance(executable));
        cfgBranchProcessor(cfg);
        replaceTag(cfg);
        setCFGToSmPLMethodCFG(result, cfg);
        return result;
    }

}
