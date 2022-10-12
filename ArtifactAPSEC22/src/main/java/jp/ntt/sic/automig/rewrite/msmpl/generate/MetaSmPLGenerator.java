package jp.ntt.sic.automig.rewrite.msmpl.generate;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import jp.ntt.sic.automig.rewrite.msmpl.lang.MetaSmPLImpl;
import jp.ntt.sic.automig.rewrite.msmpl.lang.SmPLBody;
import jp.ntt.sic.automig.rewrite.msmpl.lang.SmPLLine;
import jp.ntt.sic.automig.rewrite.msmpl.lang.SmPLMetaVariable;
import jp.ntt.sic.automig.util.ReportUtil;
import jp.ntt.sic.automig.Benchmark;
import jp.ntt.sic.automig.rewrite.msmpl.lang.MetaSmPL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jp.ntt.sic.automig.rewrite.msmpl.lang.SmPLAdditionCode;
import jp.ntt.sic.automig.rewrite.msmpl.lang.SmPLDeletionCode;
import jp.ntt.sic.automig.rewrite.msmpl.lang.SmPLMetaVarTypes;
import jp.ntt.sic.automig.rewrite.msmpl.lang.SmPLPatternChunk;
import spoon.reflect.code.CtAbstractInvocation;
import spoon.reflect.code.CtAssignment;
import spoon.reflect.code.CtExpression;
import spoon.reflect.code.CtLocalVariable;
import spoon.reflect.code.CtRHSReceiver;
import spoon.reflect.declaration.CtElement;
import spoon.reflect.declaration.CtExecutable;
import spoon.reflect.declaration.CtField;
import spoon.reflect.declaration.CtVariable;
import spoon.reflect.reference.CtExecutableReference;
import spoon.reflect.reference.CtTypeReference;
import spoon.reflect.visitor.CtAbstractVisitor;
import spoon.reflect.visitor.filter.TypeFilter;

public class MetaSmPLGenerator extends CtAbstractVisitor {
    
    private static final Logger logger = LoggerFactory.getLogger(MetaSmPLGenerator.class);
    public static final String argPreFix = "_DVAR";

    private CtExecutableReference<?> deprecatedAPI;
    private CtExecutableReference<?> targetAPI;
    private CtTypeReference<?> deprecatedAPIType;

    private List<SmPLMetaVariable> metaVars;
    private List<SmPLLine> lines;

    private Optional<List<Integer>> apiParamMap;
    
    private Map<String, Set<String>>acceptableTypeChangeTable = 
            Map.of("Integer", Set.of("int"),
                    "boolean", Set.of("void"));

    public static MetaSmPLGenerator create(CtElement deprecatedAPI, CtElement targetAPI, Optional<List<Integer>> link) {
        if (deprecatedAPI instanceof CtExecutableReference<?>) {
            if (targetAPI instanceof CtExecutableReference<?>) {
                return new MetaSmPLGenerator((CtExecutableReference<?>)deprecatedAPI, (CtExecutableReference<?>)targetAPI,link);
            } else if (targetAPI instanceof CtExecutable<?>) {
                return new MetaSmPLGenerator((CtExecutableReference<?>)deprecatedAPI, ((CtExecutable<?>)targetAPI).getReference(),link);
            }
        } else if(deprecatedAPI instanceof CtExecutable<?>) {
            if (targetAPI instanceof CtExecutableReference<?>) {
                return new MetaSmPLGenerator(((CtExecutable<?>)deprecatedAPI).getReference(), (CtExecutableReference<?>)targetAPI, link);
            } else if (targetAPI instanceof CtExecutable<?>) {
                return new MetaSmPLGenerator((CtExecutable<?>)deprecatedAPI, (CtExecutable<?>)targetAPI,link);
            }
        }
        String message = ReportUtil.print(deprecatedAPI) + " or " + ReportUtil.print(targetAPI) + " is not a executable.";
        logger.error(message);
        throw new RuntimeException(message);
    }

    private String getSimpleName(CtExecutableReference<?> r){
        return this.getSimpleName(r, "...");
    }

    private String getSimpleName(CtExecutableReference<?> r, String paramsR) {
        String s = r.getSimpleName();
        StringBuilder sb = new StringBuilder();
        if (s.equals("<init>")) {
            sb.append("new ")
            .append(r.getType().getSimpleName())
            .append("(");
        } else {
            sb.append("_target.")
            .append(s)
            .append("(");
        }
        sb.append(paramsR).append(")");
        return sb.toString();
    }

    private String getDeprecatedAPIinDelLine(CtExecutableReference<?> r) {
        AtomicInteger paramIdx = new AtomicInteger();
        List<String> paramsRS = r.getParameters().stream().map(p_type -> argPreFix +paramIdx.getAndIncrement()).collect(Collectors.toList());
        IntStream.range(0, paramIdx.get()).forEach(i -> this.metaVars.add(new SmPLMetaVariable(SmPLMetaVarTypes.Expression).setId(argPreFix + i)));
        return getSimpleName(r, String.join(",",paramsRS));
    }

    public MetaSmPLGenerator(CtExecutableReference<?> deprecatedAPI, CtExecutableReference<?> targetAPI, Optional<List<Integer>> paramMapAPI) {
        this.deprecatedAPI = deprecatedAPI;
        this.targetAPI = targetAPI;
        this.deprecatedAPIType = deprecatedAPI.getType();
        this.apiParamMap = paramMapAPI;
    }

    public MetaSmPLGenerator(CtExecutable<?> deprecatedAPI, CtExecutable<?> targetAPI, Optional<List<Integer>> paramMapAPI) {
        this(deprecatedAPI.getReference(), targetAPI.getReference(), paramMapAPI);
    }

    private boolean containsTargetAPIInvocation(CtRHSReceiver<?> element) {
        CtExpression<?> e = element.getAssignment();
        if (e instanceof CtAbstractInvocation<?>) {
            return ((CtAbstractInvocation<?>) e).getExecutable().equals(targetAPI);
        } else {
            return false;
        }        
    }

    private void linkArguments(CtRHSReceiver<?> element) {
        if (element.getAssignment() instanceof CtAbstractInvocation<?>) {
            CtAbstractInvocation<?> e = (CtAbstractInvocation<?>) element.getAssignment();
            this.apiParamMap
            .ifPresent(ls -> 
                e.setArguments(
                        IntStream.range(0,Math.min(ls.size(), e.getArguments().size()))
                            .mapToObj(i -> ls.get(i) < 0 
                                    ? e.getArguments().get(i)
                                    : e.getFactory().Code().createCodeSnippetExpression(argPreFix + i))
                            .collect(Collectors.toList())
                            )
                );
        }
    }
    
    public MetaSmPL generate(CtElement afterMigrationSampleClass) {
        Optional<CtElement> containInvocation =
            afterMigrationSampleClass.getElements(new TypeFilter<>(CtLocalVariable.class))
                .stream()
                .filter(this::containsTargetAPIInvocation)
                .peek(this::linkArguments)
                .map(e -> (CtElement)e)
                .findFirst()
            .or(() -> 
                afterMigrationSampleClass.getElements(new TypeFilter<>(CtAssignment.class))
                    .stream()
                    .filter(this::containsTargetAPIInvocation)
                    .peek(this::linkArguments)
                    .findFirst())
            .or(() -> 
                afterMigrationSampleClass.getElements(new TypeFilter<>(CtField.class))
                    .stream()
                    .filter(this::containsTargetAPIInvocation)
                    .peek(this::linkArguments)
                    .findFirst());
        if (containInvocation.isPresent()) {
            return generateInner(containInvocation.get());
        } else {
            String message = ReportUtil.print(afterMigrationSampleClass) + " does not contain any invocation of " + ReportUtil.print(targetAPI); 
            logger.warn(message);
            return null;
        }
    }

    private MetaSmPL generateInner(CtElement element) {
        this.metaVars = new ArrayList<>();
        this.lines = new ArrayList<>();

        if (element instanceof CtLocalVariable<?>) {
            visitCtLocalVariable((CtLocalVariable<?>) element);
        } else if(element instanceof CtAssignment<?, ?>) {
            visitCtAssignment((CtAssignment<?, ?>) element);    
        } else if(element instanceof CtField<?>) {
            visitCtField((CtField<?>) element);
        }
        
        SmPLPatternChunk chunk = new SmPLPatternChunk(lines);
        SmPLBody body = new SmPLBody(Arrays.asList(new SmPLPatternChunk[]{chunk}));

        MetaSmPL smpl = new MetaSmPLImpl();
        smpl.setRuleName("API migration edit:" + getSimpleName(deprecatedAPI) + "->" + getSimpleName(targetAPI));
        smpl.setSmPLMetaVariables(metaVars);
        smpl.setSmPLBody(body);
        return smpl;
    }

    @Override
    public <T> void visitCtLocalVariable(CtLocalVariable<T> localVariable) {
        String id = localVariable.getSimpleName();
        metaVars.add(new SmPLMetaVariable(SmPLMetaVarTypes.Identifier).setId(id));

        if (Benchmark.CANCEL_POST_CALC || this.deprecatedAPIType.equals(localVariable.getType())) {
            metaVars.add(new SmPLMetaVariable(SmPLMetaVarTypes.Expression).setId("_target"));
            lines.add(new SmPLDeletionCode(deprecatedAPIType.getSimpleName() + " " + id + " =" + this.getDeprecatedAPIinDelLine(deprecatedAPI)+ ";"));
            lines.add(new SmPLAdditionCode(deprecatedAPIType.getSimpleName() + " " + id + " = " + addPreVariables(localVariable.getAssignment()) + ";").defineForMigrationDestination());
        } else if(acceptableTypeChangeTable.containsKey(this.deprecatedAPIType.getSimpleName()) &&
                acceptableTypeChangeTable.get(this.deprecatedAPIType.getSimpleName()).contains(localVariable.getType().getSimpleName())) {
            metaVars.add(new SmPLMetaVariable(SmPLMetaVarTypes.Expression).setId("_target"));
            lines.add(new SmPLDeletionCode(deprecatedAPIType.getSimpleName() + " " + id + " =" + this.getDeprecatedAPIinDelLine(deprecatedAPI)+ ";"));
            lines.add(new SmPLAdditionCode(localVariable.getType().getSimpleName() + " " + id + " = " + addPreVariables(localVariable.getAssignment()) + ";").defineForMigrationDestination());
        } else {
            String typePart = localVariable.getType() == null ? "" : localVariable.getType().getSimpleName() + " ";
            lines.add(new SmPLAdditionCode(typePart + id + " = " + addPreVariables(localVariable.getAssignment())+";"));
            setFromPostCalc(localVariable);
        }
    }

    @Override
    public <T, A extends T> void visitCtAssignment(CtAssignment<T, A> assignment) {
        super.visitCtAssignment(assignment);
    }

    @Override
    public <T> void visitCtField(CtField<T> f) {
        super.visitCtField(f);
    }

    /**
     * @param assignment (use arguments of the assignment)
     * @return returning right-hand expression of rhs and causing side effects to SmPL
     */
    private String addPreVariables(CtExpression<?> assignment) {
        //do scan assignment metavar (Don't forget looking up lines)
        PreCalcScanner scanner = new PreCalcScanner(metaVars, lines);
        scanner.scan(assignment);
        //update
        metaVars = scanner.getMetaVars();
        lines = scanner.getLines();
        return scanner.getRhLine();
    }

    private <T> void setFromPostCalc(CtVariable<T> var) {
        PostCalcScanner scanner = new PostCalcScanner(var);
        scanner.scan();
        scanner.result.ifPresentOrElse(rhs -> rhs.accept(this), () -> logger.warn("No migration destination found in this after-migration code"));
    }
}
