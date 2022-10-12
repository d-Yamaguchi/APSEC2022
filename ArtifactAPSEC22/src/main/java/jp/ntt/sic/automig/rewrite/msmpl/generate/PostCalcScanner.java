package jp.ntt.sic.automig.rewrite.msmpl.generate;

import java.util.Optional;

import spoon.reflect.code.CtAssignment;
import spoon.reflect.code.CtIf;
import spoon.reflect.code.CtLocalVariable;
import spoon.reflect.code.CtStatement;
import spoon.reflect.code.CtStatementList;
import spoon.reflect.declaration.CtElement;
import spoon.reflect.declaration.CtVariable;
import spoon.reflect.reference.CtVariableReference;
import spoon.reflect.visitor.CtAbstractVisitor;
import spoon.reflect.visitor.filter.AbstractFilter;


/**
 * A scanner for finding the same reference as targetReference from the source code below the targetReference
 */
public class PostCalcScanner extends CtAbstractVisitor {

    private final CtVariableReference<?> targetReference;
    private int targetLinePos;
    public Optional<CtStatement> result;

    public PostCalcScanner(CtVariable<?> var){
        this.targetReference = var.getReference();
        this.result = Optional.empty();
    }

    /**
     * find the same reference as targetReference from the source code below the targetReference
     * Normalized program is defined as follows:
     * Stmt ::= LineStmt Stmt | UnaryStmt | CondStmt
     * LineStmt ::= CtRHSReceiver | Block of if branch
     * UnaryStmt ::== ReturnStmt | JustStmt
     * CondStmt ::= IfStmt | ForStmt | etc..
     */
    public void scan() {
        CtStatement l = targetReference.getParent(CtStatement.class);
        CtStatementList ls = targetReference.getParent(CtStatementList.class);
        if (ls != null) {
            this.targetLinePos = ls.getStatements().indexOf(l);
            visitCtStatementList(ls);
        }
    }

    @Override
    public void visitCtStatementList(CtStatementList statements) {
        statements.getStatements().stream()
            .filter(stmt -> statements.getStatements().indexOf(stmt) > targetLinePos)
            .forEach(stmt ->this.result.ifPresentOrElse(x->{},() -> stmt.accept(this)));
    }

    /*
    LineStmt
     */

    @Override
    public <T> void visitCtLocalVariable(CtLocalVariable<T> localVariable) {
        if (localVariable.getAssignment() != null && holdsTargetReference(localVariable.getAssignment())) {
            result = Optional.of(localVariable);
        }
    }

    @Override
    public <T, A extends T> void visitCtAssignment(CtAssignment<T, A> assignement) {
        if (holdsTargetReference(assignement.getAssignment())) result = Optional.of(assignement);
    }

    /*
    CondStmt
     */

    @Override
    public void visitCtIf(CtIf ifElement) {
        if (holdsTargetReference(ifElement.getThenStatement())) {
            ifElement.getThenStatement().accept(this);
        } else if (holdsTargetReference(ifElement.getElseStatement())) {
            ifElement.getElseStatement().accept(this);
        } else {
            System.out.println("Successfully finished this Control flow!");
        }
    }


    /*
    UnaryStmt
     */

//    @Override
//    public <T> void visitCtInvocation(CtInvocation<T> invocation) {
//        if (targetReference.getType().equals(invocation.getType())) result = Optional.of(invocation);
//    }
//
//    @Override
//    public <T> void visitCtUnaryOperator(CtUnaryOperator<T> operator) {
//        operator.getOperand().accept(this);
//    }
//
//    @Override
//    public <T> void visitCtConstructorCall(CtConstructorCall<T> ctConstructorCall) {
//        if (targetReference.getType().equals(ctConstructorCall.getType())) result = Optional.of(ctConstructorCall);
//    }

    /**
     * @param e ctElement
     * @return For checking existence in subtree of e
     */
    private boolean holdsTargetReference(CtElement e){
        return e.getElements(new AbstractFilter<CtVariableReference<?>>() {
            @Override
            public boolean matches(CtVariableReference<?> element) {
                return element.equals(targetReference);
            }
        }).size() > 0;
    }
}