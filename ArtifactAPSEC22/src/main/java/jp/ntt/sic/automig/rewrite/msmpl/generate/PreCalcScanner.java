package jp.ntt.sic.automig.rewrite.msmpl.generate;

import java.lang.annotation.Annotation;
import java.util.List;

import jp.ntt.sic.automig.rewrite.msmpl.lang.SmPLConditionalAdditionCode;
import jp.ntt.sic.automig.rewrite.msmpl.lang.SmPLLine;
import jp.ntt.sic.automig.rewrite.msmpl.lang.SmPLMetaVarTypes;
import jp.ntt.sic.automig.rewrite.msmpl.lang.SmPLMetaVariable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spoon.reflect.code.CtAnnotationFieldAccess;
import spoon.reflect.code.CtArrayRead;
import spoon.reflect.code.CtArrayWrite;
import spoon.reflect.code.CtAssignment;
import spoon.reflect.code.CtBinaryOperator;
import spoon.reflect.code.CtConditional;
import spoon.reflect.code.CtConstructorCall;
import spoon.reflect.code.CtExecutableReferenceExpression;
import spoon.reflect.code.CtExpression;
import spoon.reflect.code.CtFieldRead;
import spoon.reflect.code.CtFieldWrite;
import spoon.reflect.code.CtInvocation;
import spoon.reflect.code.CtLambda;
import spoon.reflect.code.CtLiteral;
import spoon.reflect.code.CtLocalVariable;
import spoon.reflect.code.CtNewArray;
import spoon.reflect.code.CtNewClass;
import spoon.reflect.code.CtSuperAccess;
import spoon.reflect.code.CtSwitchExpression;
import spoon.reflect.code.CtThisAccess;
import spoon.reflect.code.CtTypeAccess;
import spoon.reflect.code.CtTypePattern;
import spoon.reflect.code.CtUnaryOperator;
import spoon.reflect.code.CtVariableRead;
import spoon.reflect.code.CtVariableWrite;
import spoon.reflect.declaration.CtAnnotation;
import spoon.reflect.declaration.CtField;
import spoon.reflect.declaration.CtParameter;
import spoon.reflect.reference.CtExecutableReference;
import spoon.reflect.reference.CtFieldReference;
import spoon.reflect.visitor.CtAbstractVisitor;
import spoon.reflect.visitor.filter.TypeFilter;

public class PreCalcScanner extends CtAbstractVisitor {

    private static final Logger logger = LoggerFactory.getLogger(PreCalcScanner.class);
    private List<SmPLMetaVariable> metaVars;
    private List<SmPLLine> lines;
    private String rhLine;

    public PreCalcScanner(List<SmPLMetaVariable> mv, List<SmPLLine> ls){
        metaVars = mv;
        lines = ls;
    }

    public List<SmPLMetaVariable> getMetaVars() {
        return metaVars;
    }

    public List<SmPLLine> getLines() {
        return lines;
    }

    public String getRhLine() {
        return rhLine;
    }

    public void scan(CtExpression<?> e) {
        e.accept(this);
        //rhLine is expected to contain the expression which is end of the control-flow here
    }

    /*
    FOR ctExpressions
     */

    @Override
    public <T> void visitCtFieldRead(CtFieldRead<T> fieldRead) {
        CtExpression<?> target = fieldRead.getTarget();
        if (target != null) {
            if (target instanceof CtThisAccess<?>) {
                CtFieldReference<T> fieldRef = fieldRead.getVariable();
                fieldRef.getDeclaration().accept(this);
            }
            target.accept(this);
        }
        this.rhLine = fieldRead.toString();
    }

    @Override
    public <T> void visitCtFieldWrite(CtFieldWrite<T> fieldWrite) {
        logger.warn("PreCalc CtFieldWrite is not implemented yet!");
        super.visitCtFieldWrite(fieldWrite);
    }

    @Override
    public <T, E extends CtExpression<?>> void visitCtExecutableReferenceExpression(CtExecutableReferenceExpression<T, E> expression) {
        this.rhLine = expression.toString();
    }

    @Override
    public <T> void visitCtSuperAccess(CtSuperAccess<T> f) {
        logger.warn("PreCalc CtSuperAccess is not implemented yet!");
    }

    @Override
    public <T> void visitCtThisAccess(CtThisAccess<T> thisAccess) {
        logger.warn("PreCalc CtThisAccess is not implemented yet!");
    }

    @Override
    public <T> void visitCtInvocation(CtInvocation<T> invocation) {
        CtExpression<?> target = invocation.getTarget();
        CtExecutableReference<T> executable = invocation.getExecutable();
        if (target instanceof CtThisAccess<?>) {
            logger.warn("You need add an method: " + executable.getSimpleName());
        } else {
            target.accept(this);
        }
        invocation.getArguments().forEach(arg -> arg.accept(this));
        
        List<CtTypeAccess<?>> typeAccesses = invocation.getElements(new TypeFilter<>(CtTypeAccess.class));
        String invStr = invocation.toString();
        for(CtTypeAccess<?> ta : typeAccesses) {
            invStr = invStr.replace(ta.getAccessedType().getQualifiedName(), ta.getAccessedType().getSimpleName());
        }
        
        this.rhLine = invStr;
    }

    @Override
    public <T> void visitCtConstructorCall(CtConstructorCall<T> ctConstructorCall) {
        //don't add new definition of constructor
        ctConstructorCall.getArguments().forEach(arg -> arg.accept(this));
        this.rhLine = ctConstructorCall.toString().replace("\n","");
    }

    @Override
    public <T> void visitCtNewClass(CtNewClass<T> newClass) {
        visitCtConstructorCall(newClass);
    }

    @Override
    public <T> void visitCtAnnotationFieldAccess(CtAnnotationFieldAccess<T> annotationFieldAccess) {
        logger.warn("PreCalc CtAnnotationFieldAccess is not implemented yet!");
    }

    @Override
    public <T> void visitCtArrayRead(CtArrayRead<T> arrayRead) {
        arrayRead.getTarget().accept(this);
        arrayRead.getIndexExpression().accept(this);
        this.rhLine = arrayRead.toString();
    }

    @Override
    public <T> void visitCtArrayWrite(CtArrayWrite<T> arrayWrite) {
        logger.warn("Precalc CtArrayWrite is not implemented yet!");
    }

    @Override
    public <T> void visitCtBinaryOperator(CtBinaryOperator<T> operator) {
        //use only the left-hand operand
        operator.getLeftHandOperand().accept(this);
        this.rhLine = operator.getLeftHandOperand().toString();
    }

    @Override
    public <T, S> void visitCtSwitchExpression(CtSwitchExpression<T, S> switchExpression) {
        logger.warn("Precalc CtSwitchExpression is not implemented yet!");
    }


    @Override
    public <T> void visitCtUnaryOperator(CtUnaryOperator<T> operator) {
        operator.getOperand().accept(this);
    }

    @Override
    public <T> void visitCtConditional(CtConditional<T> conditional) {
        logger.warn("Precalc CtConditional is not implemented yet!");
    }

    @Override
    public <A extends Annotation> void visitCtAnnotation(CtAnnotation<A> annotation) {
        logger.warn("Precalc CtAnnotation is not implemented yet!");
    }

    @Override
    public void visitCtTypePattern(CtTypePattern pattern) {
        logger.warn("Precalc CtTypePattern is not implemented yet!");
    }

    @Override
    public <T> void visitCtTypeAccess(CtTypeAccess<T> typeAccess) {
        this.rhLine = typeAccess.getAccessedType().getSimpleName();
        metaVars.add(new SmPLMetaVariable(SmPLMetaVarTypes.Type).setRawType(typeAccess.getAccessedType().getSimpleName()).setId(typeAccess.getAccessedType().getSimpleName()));
    }

    @Override
    public <T> void visitCtNewArray(CtNewArray<T> newArray) {
        newArray.getElements().forEach(item -> item.accept(this));
        this.rhLine = newArray.toString();
    }

    @Override
    public <T> void visitCtVariableWrite(CtVariableWrite<T> variableWrite) {
        logger.warn("Precalc CtVariableWrite is not implemented yet!");
    }

    @Override
    public <T> void visitCtLambda(CtLambda<T> lambda) {
        logger.warn("Precalc CtLambda is not implemented yet!");
    }

    @Override
    public <T> void visitCtParameter(CtParameter<T> parameter) {
        this.rhLine = parameter.getSimpleName();
        metaVars.add(new SmPLMetaVariable(SmPLMetaVarTypes.RawType).setRawType(parameter.getType().getSimpleName()).setId(parameter.getSimpleName()));
    }

    @Override
    public <T> void visitCtLiteral(CtLiteral<T> literal) {
        this.rhLine = literal.toString();
    }

    /*
    For passing to rh receiver
     */

    @Override
    public <T> void visitCtVariableRead(CtVariableRead<T> variableRead) {
        if (this.metaVars.stream().filter(item -> item.getId().equals(variableRead.toString())).findAny().isEmpty()) {
            if (variableRead.getVariable().getDeclaration() != null)
            variableRead.getVariable().getDeclaration().accept(this);
        }
        if (!variableRead.getVariable().getSimpleName().startsWith("_")) {
            this.rhLine = variableRead.getVariable().getSimpleName();
        }
    }

    /*
    For acceptor with introducing new variables
     */

    @Override
    public <T> void visitCtLocalVariable(CtLocalVariable<T> localVariable) {
        String id = localVariable.getSimpleName();
        metaVars.add(new SmPLMetaVariable(SmPLMetaVarTypes.Identifier).setId(id));
        if (localVariable.getAssignment() != null) localVariable.getAssignment().accept(this);//fixed for addDetail, deletedocumet, GegBinary value, getMergedField..., reopen, setValue, termDocs
        if (getRhLine() != null) lines.add(new SmPLConditionalAdditionCode(localVariable.getType().getSimpleName() + " " + id + " = " + this.getRhLine()+";"));
    }


    @Override
    public <T> void visitCtField(CtField<T> f) {
        String id = f.getSimpleName();
        if (f.getAssignment() == null) {
            metaVars.add(new SmPLMetaVariable(SmPLMetaVarTypes.RawType).setRawType(f.getType().getSimpleName()).setId(id));
        } else {
            metaVars.add(new SmPLMetaVariable(SmPLMetaVarTypes.Identifier).setId(id));
            f.getAssignment().accept(this);
            lines.add(new SmPLConditionalAdditionCode(f.getType().getSimpleName() + " " + id + " = " + this.getRhLine() + ";"));
        }
    }

    @Override
    public <T, A extends T> void visitCtAssignment(CtAssignment<T, A> assignement) {
        logger.warn("Precalc CtAssignment (this case is Assignment in Assignment case) is not implemented yet!");
    }
}
