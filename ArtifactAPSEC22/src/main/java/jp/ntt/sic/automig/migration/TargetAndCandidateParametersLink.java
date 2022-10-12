package jp.ntt.sic.automig.migration;

import jp.ntt.sic.automig.util.Pair;
import spoon.reflect.code.CtConstructorCall;
import spoon.reflect.code.CtInvocation;
import spoon.reflect.code.CtLambda;
import spoon.reflect.declaration.CtAnonymousExecutable;
import spoon.reflect.declaration.CtConstructor;
import spoon.reflect.declaration.CtElement;
import spoon.reflect.declaration.CtExecutable;
import spoon.reflect.declaration.CtMethod;
import spoon.reflect.reference.CtExecutableReference;
import spoon.reflect.reference.CtTypeReference;
import spoon.reflect.visitor.CtAbstractVisitor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class TargetAndCandidateParametersLink extends CtAbstractVisitor {
    /*
    linkage
    a b c -> v a c then expression is [-1, 0, 2]
    a b c d e -> v w a then expression is [-1, -1, 0]
     */

    //store parameter linkages
    public Map<Pair<CtElement,CtElement>, Optional<List<Integer>>> argsLink = new HashMap<>();

    private final List<CtElement> candidate;

    private void setArgsLink(CtElement target, List<CtElement> candidate) {
        CtExecutable<?> targetExecutable;
        if (target instanceof CtExecutable<?>) {
            targetExecutable = (CtExecutable<?>) target;
        } else if (target instanceof CtExecutableReference<?>) {
            targetExecutable = ((CtExecutableReference<?>) target).getExecutableDeclaration();
        } else {
            return;
        }
        
        List<Map.Entry<String, ? extends CtTypeReference<?>>> argTarget = targetExecutable.getParameters().stream().map(t -> Map.entry(t.getSimpleName(), t.getType())).collect(Collectors.toList());
        candidate.forEach(c -> {
            CtExecutable<?> exec = c instanceof CtExecutable<?> ? (CtExecutable<?>)c
                : c instanceof CtExecutableReference<?> ? ((CtExecutableReference<?>) c).getExecutableDeclaration()
                : null;
            if (exec != null) {
                argsLink.put(
                        Pair.of(target, c),
                        Optional.of(
                                exec.getParameters()
                                    .stream()
                                    .map(t -> Map.entry(t.getSimpleName(), t.getType()))
                                    .map(argTarget::indexOf)
                                    .collect(Collectors.toList())
                                    )
                        );
            } else {
                argsLink.put(Pair.of(target, c), Optional.empty());
            }
        });
    }

    public TargetAndCandidateParametersLink(CtElement target, List<CtElement> candidate){
        this.candidate = candidate;
        target.accept(this);
    }

    @Override
    public <T> void visitCtExecutableReference(CtExecutableReference<T> reference) {
        this.setArgsLink(reference, candidate);
    }

    public <T> void visitCtExecutable(CtExecutable<T> executable) {
        this.setArgsLink(executable, candidate);
    }

    @Override
    public <T> void visitCtConstructor(CtConstructor<T> c) {
        visitCtExecutable(c);
    }

    @Override
    public void visitCtAnonymousExecutable(CtAnonymousExecutable anonymousExec) {
        visitCtExecutable(anonymousExec);
    }

    @Override
    public <T> void visitCtLambda(CtLambda<T> lambda) {
        visitCtExecutable(lambda);
    }

    @Override
    public <T> void visitCtMethod(CtMethod<T> m) {
        visitCtExecutable(m);
    }

    @Override
    public <T> void visitCtInvocation(CtInvocation<T> invocation) {
        visitCtExecutableReference(invocation.getExecutable());
    }

    @Override
    public <T> void visitCtConstructorCall(CtConstructorCall<T> ctConstructorCall) {
        visitCtExecutableReference(ctConstructorCall.getExecutable());
    }
}
