package jp.ntt.sic.automig.rewrite.msmpl.normalize;

import jp.ntt.sic.automig.util.ReportUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spoon.SpoonException;
import spoon.reflect.code.CtAbstractInvocation;
import spoon.reflect.code.CtAnnotationFieldAccess;
import spoon.reflect.code.CtArrayRead;
import spoon.reflect.code.CtArrayWrite;
import spoon.reflect.code.CtAssignment;
import spoon.reflect.code.CtBinaryOperator;
import spoon.reflect.code.CtBlock;
import spoon.reflect.code.CtCase;
import spoon.reflect.code.CtCatch;
import spoon.reflect.code.CtConstructorCall;
import spoon.reflect.code.CtDo;
import spoon.reflect.code.CtExecutableReferenceExpression;
import spoon.reflect.code.CtExpression;
import spoon.reflect.code.CtFieldRead;
import spoon.reflect.code.CtFieldWrite;
import spoon.reflect.code.CtFor;
import spoon.reflect.code.CtForEach;
import spoon.reflect.code.CtIf;
import spoon.reflect.code.CtInvocation;
import spoon.reflect.code.CtLocalVariable;
import spoon.reflect.code.CtReturn;
import spoon.reflect.code.CtStatement;
import spoon.reflect.code.CtStatementList;
import spoon.reflect.code.CtSuperAccess;
import spoon.reflect.code.CtSwitch;
import spoon.reflect.code.CtTargetedExpression;
import spoon.reflect.code.CtThisAccess;
import spoon.reflect.code.CtTry;
import spoon.reflect.code.CtTypeAccess;
import spoon.reflect.code.CtVariableAccess;
import spoon.reflect.code.CtVariableRead;
import spoon.reflect.code.CtWhile;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.CtElement;
import spoon.reflect.declaration.CtExecutable;
import spoon.reflect.declaration.CtField;
import spoon.reflect.declaration.CtType;
import spoon.reflect.reference.CtExecutableReference;
import spoon.reflect.reference.CtTypeReference;
import spoon.reflect.reference.CtVariableReference;
import spoon.reflect.visitor.CtAbstractVisitor;
import spoon.reflect.visitor.filter.TypeFilter;
import spoon.support.reflect.code.CtLocalVariableImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Stack;
import java.util.stream.Collectors;

public class CodeNormalizer extends CtAbstractVisitor {
    
    private static final Logger logger = LoggerFactory.getLogger(CodeNormalizer.class);

    private FreshVariableGenerator varGenerator;
    private CtAbstractInvocation<?> apiInv;
    private CtExecutableReference<?> apiRef;
    private Stack<Stack<CtStatement>> stmtstack;
    private boolean clientMode;
    private CtBlock<?> topScope;

    public CodeNormalizer(CtExecutableReference<?> apiRef, boolean clientMode){
        this.clientMode = clientMode;
        this.varGenerator = new FreshVariableGenerator(clientMode);
        this.apiRef = apiRef;
        this.stmtstack = new Stack<>();
    }



    /**
     * Choose which ctExecutable is to normalize.
     * @param ctClass
     * @param <T>
     */
    public <T> void normalize(CtClass<T> ctClass) {
        ctClass.getElements(new TypeFilter<>(CtInvocation.class))
            .stream()
            .filter(inv -> inv.getExecutable().equals(apiRef))
            .forEach(inv -> {
                this.apiInv = inv;
                ctClass.getDeclaredExecutables()
                    .stream()
                    .map(CtExecutableReference::getExecutableDeclaration)
                    .filter(Objects::nonNull)
                    .filter(exe -> apiInv.hasParent(exe))
                    .findFirst()
                    .ifPresent(exe -> {
                        this.topScope = exe.getBody();
                        visitCtBlock(this.topScope);
                        });
            });
    }
    
    public void normalize(CtExecutableReference<?> execRef) {
        execRef.getExecutableDeclaration().getElements(new TypeFilter<>(CtAbstractInvocation.class))
            .stream()
            .filter(inv -> inv.getExecutable().equals(apiRef))
            .forEach(inv -> {
                this.apiInv = inv;
                if (apiInv.hasParent(execRef.getExecutableDeclaration())) {
                    this.topScope = execRef.getExecutableDeclaration().getBody(); 
                    visitCtBlock(topScope);
                }
            });
    }

    private void addToSpecifiedScope(CtStatement added, CtStatement scopeSpecifier) {
        int depth = 0;
        CtStatementList parentBlock = scopeSpecifier.getParent(CtStatementList.class);
        if (parentBlock != null) {
            int i = 0;
            for (CtStatement s: parentBlock.getStatements()) {
                if (scopeSpecifier.hasParent(s)) break;
                else i++;
            }
            try {
                parentBlock.addStatement(i, added);
            } catch (SpoonException e){
                logger.warn("spoon.SpoonException: The default behavior has changed, a new check has been added! Don't worry, you can disable this check\n" +
                        "with one of the following options:\n" +
                        " - by configuring Spoon with getEnvironment().setSelfChecks(true)\n" +
                        " - by removing the node from its previous parent (element.delete())\n" +
                        " - by cloning the node before adding it here (element.clone())");
            }

        }
        while(true) {
            if (parentBlock == null) {
                depth = 0;
                break;
            } else if(parentBlock == this.topScope) {
                break;
            } else {
                depth++;
            }
            
            parentBlock = parentBlock.getParent(CtStatementList.class);
        }
        this.stmtstack.get(depth).push(added);
    }
    
    /**
     * it manipulates statements in this block (Main part of this visitor)
     * @param block
     * @param <R>
     */

    @Override
    public <R> void visitCtBlock(CtBlock<R> block) {
        visitCtStatementList(block);
    }

    @Override
    public <R> void visitCtStatementList(CtStatementList ctStatements) {
        ctStatements.getStatements()
            .stream()
            .filter(stmt -> apiInv == stmt || apiInv.hasParent(stmt))
            .findFirst()
            .ifPresent(stmt -> {
                Stack<CtStatement> normalizeds = this.visitCtStatement(stmt);
//                int i = ctStatements.getStatements().indexOf(stmt);
//                int size = normalizeds.size();
//                if (size > 0) {
//                    for (int j = 0; j < size; j++) {
//                        CtStatement normalized = normalizeds.pop();
//                        if (!normalized.equals(stmt) && toupdate.contains(normalized)) {
//                            toupdate.remove(normalized);
//                            if(i > 0) i--;
//                        }
//                        toupdate.add(i, normalized);
//                    }
//                    toupdate.remove(stmt);
//                }
            });
    }

    private Stack<CtStatement> visitCtStatement(CtStatement stmt) {
        this.stmtstack.push(new Stack<>());
        stmt.accept(this);
        if (stmt instanceof CtInvocation<?>) {
            if (this.clientMode) {
                addToSpecifiedScope(stmt, stmt);
            } else {// introduce new  local variable in example file to generate msmpl file
                @SuppressWarnings("unchecked")
                CtLocalVariable<?> newVar = stmt.getFactory()
                        .createLocalVariable()
                        .setDefaultExpression((CtExpression<Object>) stmt.clone())
                        .setType((CtTypeReference<Object>) ((CtInvocation<?>) stmt).getExecutable().getType());
                newVar.setSimpleName(varGenerator.getFreshVar());
                addToSpecifiedScope(newVar, stmt);
            }
        }
        return this.stmtstack.pop();
    }

    //An API call contained in a compound expression or statement (e.g. if, loop, return, etc) is extracted into a variable assignment.
    /*
      err in e   Δ ~ e -> Δ' ~ e'
   ---------------------------------------------------------------------(CtIf1)
    Δ ~  if e then b1 else b2; -> Δ'; B v = e' ~ if v then b1 else b2
          err in b1   Δ ~ b1 -> Δ' ~ b1'
   ---------------------------------------------------------------------(CtIf2)
    Δ ~  if e then b1 else b2; -> Δ' ~ if e then b1' else b2
          err in b2   Δ ~ b2 -> Δ' ~ b2'
   ---------------------------------------------------------------------(CtIf3)
    Δ ~  if e then b1 else b2; -> Δ'; B v = e' ~ if e then b1 else b2'
     */
    @Override
    public void visitCtIf(CtIf ifElement) {
        if (apiInv == ifElement.getCondition() || apiInv.hasParent(ifElement.getCondition())) {
            ifElement.getCondition().accept(this);

            CtLocalVariableImpl<Boolean> newVar = new CtLocalVariableImpl<>();
            newVar.setDefaultExpression(ifElement.getCondition());
            newVar.setType(ifElement.getCondition().getType());
            newVar.setSimpleName(varGenerator.getFreshVar());

            ifElement.setCondition(newVar.getFactory().createVariableRead());

            addToSpecifiedScope(newVar, ifElement);
        } else if (apiInv == ifElement.getThenStatement() || apiInv.hasParent(ifElement.getThenStatement())) {
            ifElement.getThenStatement().accept(this);
        } else {
            ifElement.getElseStatement().accept(this);
        }
        addToSpecifiedScope(ifElement, ifElement);
    }

    @Override
    public void visitCtFor(CtFor forLoop) {
        if (apiInv == forLoop.getExpression()) {
            forLoop.getExpression().accept(this);
            
            CtLocalVariableImpl<Boolean> newVar = new CtLocalVariableImpl<>();
            newVar.setDefaultExpression(forLoop.getExpression());
            newVar.setType(forLoop.getExpression().getType());
            newVar.setSimpleName(varGenerator.getFreshVar());

            forLoop.setExpression(newVar.getFactory().createVariableRead());

            addToSpecifiedScope(newVar, forLoop);
        } else if (apiInv == forLoop.getBody() || apiInv.hasParent(forLoop.getBody())) {
            forLoop.getBody().accept(this);
        } else {
            forLoop.getForUpdate();
        }
        addToSpecifiedScope(forLoop, forLoop);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void visitCtForEach(CtForEach foreach) {
        if (apiInv == foreach.getExpression()) {
            foreach.getExpression().accept(this);
            
            @SuppressWarnings("rawtypes")
            CtLocalVariable newVar = foreach.getFactory().createLocalVariable();
            newVar.setDefaultExpression(foreach.getExpression());
            newVar.setType(foreach.getExpression().getType());
            newVar.setSimpleName(varGenerator.getFreshVar());

            foreach.setExpression(newVar.getFactory().createVariableRead());

            addToSpecifiedScope(newVar, foreach);
        } else {
            foreach.getBody().accept(this);
        }
        addToSpecifiedScope(foreach, foreach);
    }

    @Override
    public void visitCtWhile(CtWhile whileLoop) {
        super.visitCtWhile(whileLoop);
    }

    @Override
    public void visitCtDo(CtDo doLoop) {
        super.visitCtDo(doLoop);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <S> void visitCtSwitch(CtSwitch<S> switchStatement) {
        if (apiInv == switchStatement.getSelector() || apiInv.hasParent(switchStatement.getSelector())) {
            switchStatement.getSelector().accept(this);

            CtLocalVariable<?> newVar = switchStatement.getFactory()
                    .createLocalVariable()
                    .setDefaultExpression((CtExpression<Object>) switchStatement.getSelector())
                    .setType((CtTypeReference<Object>) switchStatement.getSelector().getType());
                newVar.setSimpleName(varGenerator.getFreshVar());

            switchStatement.setSelector(
                    (CtExpression<S>) switchStatement.getFactory()
                        .createVariableRead()
                        .setVariable((CtVariableReference<Object>) newVar.getReference()));

            addToSpecifiedScope(newVar, switchStatement);
        } else {
            switchStatement.getCases().stream().filter(branch -> apiInv.hasParent(branch)).forEach(branch -> branch.accept(this));
        }
        addToSpecifiedScope(switchStatement, switchStatement);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <S> void visitCtCase(CtCase<S> caseStatement) {
        if (apiInv == caseStatement.getCaseExpression() || apiInv.hasParent(caseStatement.getCaseExpression())) {
            caseStatement.getCaseExpression().accept(this);

            CtLocalVariable<?> newVar = caseStatement.getFactory()
                    .createLocalVariable()
                    .setDefaultExpression((CtExpression<Object>) caseStatement.getCaseExpression())
                    .setType((CtTypeReference<Object>) caseStatement.getCaseExpression().getType());
            newVar.setSimpleName(varGenerator.getFreshVar());

            caseStatement.setCaseExpression(
                    (CtExpression<S>) caseStatement.getFactory()
                        .createVariableRead()
                        .setVariable(
                                (CtVariableReference<Object>) newVar.getReference()));

            addToSpecifiedScope(newVar, caseStatement);
        } else {
            visitCtStatementList(caseStatement);
        }
    }

    @Override
    public void visitCtTry(CtTry tryBlock) {
        visitCtStatementList(tryBlock.getBody());
        tryBlock.getCatchers().stream().map(CtCatch::getBody).forEach(this::visitCtStatementList);
        if(tryBlock.getFinalizer() != null) visitCtStatementList(tryBlock.getFinalizer());
        addToSpecifiedScope(tryBlock, tryBlock);
    }


    /*
            Δ ~ e -> Δ' ~ e'
   --------------------------------------------------(CtRet)
    Δ ~  return e ; -> Δ'; T_e v = e' ~ return v;
     */
    @SuppressWarnings("unchecked")
    @Override
    public <R> void visitCtReturn(CtReturn<R> returnStatement) {
        returnStatement.getReturnedExpression().accept(this);
        CtLocalVariable<?> newVar = returnStatement.getFactory()
                .createLocalVariable()
                .setDefaultExpression((CtExpression<Object>) returnStatement.getReturnedExpression())
                .setType((CtTypeReference<Object>) returnStatement.getReturnedExpression().getType());
        newVar.setSimpleName(varGenerator.getFreshVar());

        returnStatement.setReturnedExpression(
                (CtExpression<R>) returnStatement.getFactory()
                    .createVariableRead()
                    .setVariable(
                            (CtVariableReference<Object>) newVar.getReference()));
        addToSpecifiedScope(newVar, returnStatement);
        addToSpecifiedScope(returnStatement, returnStatement);
    }

    @Override
    public <T> void visitCtVariableRead(CtVariableRead<T> variableRead) {
        if (variableRead.getVariable().getDeclaration() != null) variableRead.getVariable().getDeclaration().accept(this);//fixed for add Analyzer
    }

    /*
                delta ~ a -> delta' ~ a'
            ------------------------------------------(CtBinOp)
            delta ;a % b -> delta';v = a'  ~ v & b
             */
    @SuppressWarnings("unchecked")
    @Override
    public <T> void visitCtBinaryOperator(CtBinaryOperator<T> operator) {
        if (apiInv.hasParent(operator.getLeftHandOperand())) {
            operator.getLeftHandOperand().accept(this);

            CtLocalVariable<?> newVar = operator.getFactory()
                    .createLocalVariable()
                    .setDefaultExpression((CtExpression<Object>) operator.getLeftHandOperand())
                    .setType((CtTypeReference<Object>) operator.getLeftHandOperand().getType());
            newVar.setSimpleName(varGenerator.getFreshVar());

            operator.setLeftHandOperand(operator.getFactory().createVariableRead().setVariable((CtVariableReference<Object>) newVar.getReference()));

            addToSpecifiedScope(newVar, operator.getParent(CtStatement.class));
        } else {
            operator.getRightHandOperand().accept(this);

            CtLocalVariable<?> newVar = operator.getFactory()
                    .createLocalVariable()
                    .setDefaultExpression((CtExpression<Object>) operator.getRightHandOperand())
                    .setType((CtTypeReference<Object>) operator.getRightHandOperand().getType());
            newVar.setSimpleName(varGenerator.getFreshVar());

            operator.setRightHandOperand(operator.getFactory().createVariableRead().setVariable((CtVariableReference<Object>) newVar.getReference()));

            addToSpecifiedScope(newVar, operator.getParent(CtStatement.class));
        }
    }

    //Arguments of an API call are extracted into variable assignments.
    //The object receiving an API call is extracted into a variable assignment.
    ////1st, search variable binding whose rhs contains errInv
    ////2nd, if it didn't found, search statement whose expression contains
    /*
        Δ ~ e -> Δ_1' ~ e'      Δ_1' ~ t -> Δ_2' ~ t'
   ----------------------------------------------------------(CtTar)
    Δ ~  t.e -> Δ_2'; T_t v = t' ~ v.e';
     */
    public <T,E extends CtExpression<?>> void visitCtTargetedExpression(CtTargetedExpression<T,E> ctTargetedExpression){
        logger.warn("Not Implemented Yet for Normalize" + ctTargetedExpression.getClass().toString());

    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> void visitCtInvocation(CtInvocation<T> invocation) {
        final List<CtLocalVariable<?>> newVarsForArgument = new ArrayList<>();
        CtType<?> parentType = (CtType<?>) invocation.getParent(CtType.class);

//        if (!(invocation.getTarget() instanceof CtTypeAccess<?>) && parentType != null && (parentType.getQualifiedName() == null || invocation.getExecutable().getDeclaringType() == null || !parentType.getQualifiedName().equals(invocation.getExecutable().getDeclaringType().getQualifiedName()))) {
//            if (!this.fleshVariableTable.containsKey(invocation.getTarget().getShortRepresentation())) {
//                CtLocalVariable<?> newVar = invocation.getFactory()
//                        .createLocalVariable()
//                        .setDefaultExpression((CtExpression<Object>) invocation.getTarget())
//                        .setType((CtTypeReference<Object>) invocation.getTarget().getType());
//                newVar.setSimpleName(varGenerator.getFreshVar());
//                CtVariableAccess<Object> newVariableAccess = invocation.getFactory()
//                        .createVariableRead()
//                        .setVariable((CtVariableReference<Object>) newVar.getReference());
//
//                this.fleshVariableTable.put(invocation.getTarget().getShortRepresentation(), newVariableAccess);
//                invocation.getTarget().accept(this);
//                invocation.setTarget(newVariableAccess);
//                invocation.getArguments().forEach(arg -> {
//                    arg.accept(this);
//
//                    CtLocalVariable<?> newArgVar = arg.getFactory().createLocalVariable()
//                            .setDefaultExpression((CtExpression<Object>) arg)
//                            .setType((CtTypeReference<Object>) arg.getType());
//                    newArgVar.setSimpleName(varGenerator.getFreshVar());
//                    newVarsForArgument.add(newArgVar);//store LocalVariable Impl for add
//                });
//                invocation.setArguments(
//                        newVarsForArgument.stream()
//                                .map(nv -> invocation.getFactory().createVariableRead().setVariable((CtVariableReference<Object>) nv.getReference()))
//                                .map(e -> (CtVariableAccess<?>)e)
//                                .collect(Collectors.toList()));
//                stmtstack.peek().push(newVar);
//                newVarsForArgument.forEach(x -> stmtstack.peek().push(x));
//            } else {
//                invocation.setTarget(this.fleshVariableTable.get(invocation.getTarget().getShortRepresentation()));
//                invocation.getArguments().forEach(arg -> {
//                    arg.accept(this);
//                    CtLocalVariable<?> newArgVar = arg.getFactory().createLocalVariable()
//                            .setDefaultExpression((CtExpression<Object>) arg)
//                            .setType((CtTypeReference<Object>) arg.getType());
//                    newArgVar.setSimpleName(varGenerator.getFreshVar());
//                    newVarsForArgument.add(newArgVar);//store LocalVariable Impl for add
//                });
//                invocation.setArguments(
//                        newVarsForArgument.stream()
//                                .map(nv -> invocation.getFactory().createVariableRead().setVariable((CtVariableReference<Object>) nv.getReference()))
//                                .map(e -> (CtVariableAccess<?>)e)
//                                .collect(Collectors.toList()));
//                newVarsForArgument.forEach(x -> stmtstack.peek().push(x));
//            }
//        } else {
//            invocation.getArguments().forEach(arg -> {
//                arg.accept(this);
//
//                CtLocalVariable<?> newArgVar = arg.getFactory().createLocalVariable()
//                        .setDefaultExpression((CtExpression<Object>) arg)
//                        .setType((CtTypeReference<Object>) arg.getType());
//                newArgVar.setSimpleName(varGenerator.getFreshVar());
//                newVarsForArgument.add(newArgVar);//store LocalVariable Impl for add
//            });
//            invocation.setArguments(
//                    newVarsForArgument.stream()
//                            .map(nv -> invocation.getFactory().createVariableRead().setVariable((CtVariableReference<Object>) nv.getReference()))
//                            .map(e -> (CtVariableAccess<?>)e)
//                            .collect(Collectors.toList()));
//            newVarsForArgument.forEach(x -> stmtstack.peek().push(x));
//        }

        if (invocation.getTarget() != null && !(invocation.getTarget() instanceof CtThisAccess) && !(invocation.getTarget() instanceof CtTypeAccess<?>) && parentType != null && (parentType.getQualifiedName() == null || invocation.getExecutable().getDeclaringType() == null || !parentType.getQualifiedName().equals(invocation.getExecutable().getDeclaringType().getQualifiedName()))) {
            invocation.getTarget().accept(this);

            CtLocalVariable<?> newVar = invocation.getFactory()
                    .createLocalVariable()
                    .setDefaultExpression((CtExpression<Object>) invocation.getTarget())
                    .setType((CtTypeReference<Object>) invocation.getTarget().getType());
            newVar.setSimpleName(varGenerator.getFreshVar());
            
            invocation.setTarget(
                    invocation.getFactory()
                        .createVariableRead()
                        .setVariable(
                                (CtVariableReference<Object>) newVar.getReference()));
            invocation.getArguments().forEach(arg -> {
                arg.accept(this);
                
                CtLocalVariable<?> newArgVar = arg.getFactory().createLocalVariable()
                        .setDefaultExpression((CtExpression<Object>) arg)
                        .setType((CtTypeReference<Object>) arg.getType());
                newArgVar.setSimpleName(varGenerator.getFreshVar());
                newVarsForArgument.add(newArgVar);//store LocalVariable Impl for add
            });
            invocation.setArguments(
                    newVarsForArgument.stream()
                .map(nv -> invocation.getFactory().createVariableRead().setVariable((CtVariableReference<Object>) nv.getReference()))
                .map(e -> (CtVariableAccess<?>)e)
                .collect(Collectors.toList()));
            
            addToSpecifiedScope(newVar, invocation);
            newVarsForArgument.forEach(x -> addToSpecifiedScope(x, invocation));
        } else {
            invocation.getArguments().forEach(arg -> {
                arg.accept(this);

                CtLocalVariable<?> newArgVar = arg.getFactory().createLocalVariable()
                        .setDefaultExpression((CtExpression<Object>) arg)
                        .setType((CtTypeReference<Object>) arg.getType());
                newArgVar.setSimpleName(varGenerator.getFreshVar());
                newVarsForArgument.add(newArgVar);//store LocalVariable Impl for add
            });
            invocation.setArguments(
                    newVarsForArgument.stream()
                .map(nv -> invocation.getFactory().createVariableRead().setVariable((CtVariableReference<Object>) nv.getReference()))
                .map(e -> (CtVariableAccess<?>)e)
                .collect(Collectors.toList()));
            newVarsForArgument.forEach(x -> addToSpecifiedScope(x, invocation));
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> void visitCtConstructorCall(CtConstructorCall<T> ctConstructorCall) {
        final List<CtLocalVariable<?>> newVarsForArgument = new ArrayList<>();
        CtType<?> parentType = (CtType<?>) ctConstructorCall.getParent(CtType.class);

        if (parentType != null && (parentType.getQualifiedName() == null || ctConstructorCall.getExecutable().getDeclaringType() == null || !parentType.getQualifiedName().equals(ctConstructorCall.getExecutable().getDeclaringType().getQualifiedName()))) {
            ctConstructorCall.getArguments().forEach(arg -> {
                arg.accept(this);
                CtLocalVariable<?> newArgVar = arg.getFactory().createLocalVariable()
                        .setDefaultExpression((CtExpression<Object>) arg)
                        .setType((CtTypeReference<Object>) arg.getType());
                newArgVar.setSimpleName(varGenerator.getFreshVar());
                newVarsForArgument.add(newArgVar);//store LocalVariable Impl for add
            });
            ctConstructorCall.setArguments(
                    newVarsForArgument.stream()
                .map(nv -> ctConstructorCall.getFactory().createVariableRead().setVariable((CtVariableReference<Object>) nv.getReference()))
                .map(e -> (CtVariableAccess<?>)e)
                .collect(Collectors.toList()));
            
            newVarsForArgument.forEach(x -> addToSpecifiedScope(x, ctConstructorCall));
        } else {
            ctConstructorCall.getArguments().forEach(arg -> {
                arg.accept(this);
                CtLocalVariable<?> newArgVar = arg.getFactory().createLocalVariable()
                        .setDefaultExpression((CtExpression<Object>) arg)
                        .setType((CtTypeReference<Object>) arg.getType());
                newArgVar.setSimpleName(varGenerator.getFreshVar());
                newVarsForArgument.add(newArgVar);//store LocalVariable Impl for add
            });
            ctConstructorCall.setArguments(
                    newVarsForArgument.stream()
                .map(nv -> ctConstructorCall.getFactory().createVariableRead().setVariable((CtVariableReference<Object>) nv.getReference()))
                .map(e -> (CtVariableAccess<?>)e)
                .collect(Collectors.toList()));

            newVarsForArgument.forEach(x -> addToSpecifiedScope(x, ctConstructorCall));
        }
    }

    @Override
    public <T> void visitCtFieldWrite(CtFieldWrite<T> fieldWrite) {
        visitCtTargetedExpression(fieldWrite);
    }

    @Override
    public <T> void visitCtFieldRead(CtFieldRead<T> fieldRead) {
        visitCtTargetedExpression(fieldRead);
    }

    @Override
    public <T, E extends CtExpression<?>> void visitCtExecutableReferenceExpression(CtExecutableReferenceExpression<T, E> expression) {
        visitCtTargetedExpression(expression);
    }

    @Override
    public <T> void visitCtSuperAccess(CtSuperAccess<T> f) {
        visitCtTargetedExpression(f);
    }

    @Override
    public <T> void visitCtAnnotationFieldAccess(CtAnnotationFieldAccess<T> annotationFieldAccess) {
        visitCtTargetedExpression(annotationFieldAccess);
    }

    @Override
    public <T> void visitCtThisAccess(CtThisAccess<T> thisAccess) {
        visitCtTargetedExpression(thisAccess);
    }

    @Override
    public <T> void visitCtArrayWrite(CtArrayWrite<T> arrayWrite) {
        visitCtTargetedExpression(arrayWrite);
    }

    @Override
    public <T> void visitCtArrayRead(CtArrayRead<T> arrayRead) {
        visitCtTargetedExpression(arrayRead);
    }

    /*
      err in e   Δ ~ e -> Δ' ~ e'
   ----------------------------------(CtRHSReceiver)
    Δ ~ T id = e; -> Δ' ~  T id = e';
     */
    @Override
    public <T> void visitCtField(CtField<T> f) {
        super.visitCtField(f);
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public <T> void visitCtLocalVariable(CtLocalVariable<T> localVariable) {
        if (localVariable.getAssignment() != null) {
            localVariable.getAssignment().accept(this);

//            Factory factory = localVariable.getFactory();
//
//            CtLocalVariable<?> newVar = factory.createLocalVariable()
//                    .setDefaultExpression((CtExpression<Object>) localVariable.getAssignment())
//                    .setType((CtTypeReference<Object>) localVariable.getAssignment().getType());
//            newVar.setSimpleName(varGenerator.getFreshVar());
//
//            CtVariableRead<?> newVarRead = factory.createVariableRead();
//            newVarRead.setVariable((CtVariableReference) newVar.getReference());
//
//            CtLocalVariable<?> origVar = localVariable.getFactory().createLocalVariable()
//                    .setDefaultExpression((CtExpression<Object>) newVarRead)
//                    .setType((CtTypeReference<Object>) localVariable.getAssignment().getType());
//            origVar.setSimpleName(localVariable.getSimpleName());
//
//            stmtstack.peek().push(newVar);
            addToSpecifiedScope(localVariable.clone(), localVariable);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T, A extends T> void visitCtAssignment(CtAssignment<T, A> assignement) {
        assignement.getAssignment().accept(this);
        CtExpression<A> rhs = assignement.getAssignment();
        CtLocalVariable<A> newVar = assignement.getFactory().createLocalVariable().setSimpleName(varGenerator.getFreshVar());

        assignement.setAssignment(assignement.getFactory().createVariableRead().setVariable((CtVariableReference) newVar.getReference()));
        newVar.setDefaultExpression(rhs).setType(rhs.getType());
        rhs.setParent(newVar);

        addToSpecifiedScope(newVar, assignement);
        addToSpecifiedScope(assignement, assignement);
    }

    public static CodeNormalizer create(CtElement apiRef, boolean clientMode) {
        if (apiRef instanceof CtExecutable<?>) {
            return new CodeNormalizer(((CtExecutable<?>) apiRef).getReference(), clientMode);
        } else if (apiRef instanceof CtExecutableReference<?>) {
            return new CodeNormalizer((CtExecutableReference<?>) apiRef, clientMode);
        }
        String message = ReportUtil.print(apiRef) + " is not a executable.";
        logger.error(message);
        throw new RuntimeException(message);
    }
}
