package jp.ntt.sic.automig.rewrite.msmpl.visitor;

import jp.ntt.sic.automig.rewrite.msmpl.lang.SmPLBody;
import jp.ntt.sic.automig.rewrite.msmpl.lang.SmPLCodeMatcher;
import jp.ntt.sic.automig.rewrite.msmpl.lang.SmPLConditionalAdditionCode;
import jp.ntt.sic.automig.rewrite.msmpl.lang.SmPLPatternOr;
import jp.ntt.sic.automig.rewrite.msmpl.lang.SmPLPatternRep;
import jp.ntt.sic.automig.rewrite.msmpl.normalize.VariableNameFinderOverNormalizedClient;
import jp.ntt.sic.automig.rewrite.msmpl.generate.MetaSmPLGenerator;
import jp.ntt.sic.automig.rewrite.msmpl.lang.SmPLAdditionCode;
import jp.ntt.sic.automig.rewrite.msmpl.lang.SmPLDeletionCode;
import jp.ntt.sic.automig.rewrite.msmpl.lang.SmPLDotMatcher;
import jp.ntt.sic.automig.rewrite.msmpl.lang.SmPLJustMatchCode;
import jp.ntt.sic.automig.rewrite.msmpl.lang.SmPLPatternChunk;
import jp.ntt.sic.automig.rewrite.msmpl.normalize.NormalizedLineTriplet;
import spoon.reflect.code.CtExpression;
import spoon.reflect.code.CtInvocation;
import spoon.reflect.code.CtLocalVariable;
import spoon.reflect.code.CtStatement;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SmPLCodeLocalizer<T> implements MetaSmPLVisitor {

    private CtStatement client; // a normalized statement assigned API invocation which exists in the client code and should be replaced

    public SmPLCodeLocalizer(CtStatement client){
        this.client = client;
    }

    private final List<String> usedLVs = new ArrayList<>();
//    public final Map<String,String> localizedIdentifiers = new HashMap<>();

    @Override
    public void visit(SmPLBody body) {
        body.getPatterns().forEach(p -> p.accept(this));
    }

    @Override
    public void visit(SmPLPatternRep patternRep) {
        patternRep.getRepE().accept(this);
    }

    @Override
    public void visit(SmPLPatternOr patternOr) {
        patternOr.getChunks().forEach(c -> c.accept(this));
    }

    private void setLVS(SmPLPatternChunk patternChunk, CtInvocation<?> invocation) {
        List<CtExpression<?>> arguments = invocation.getArguments();
        patternChunk.getLines()
                .stream()
                .filter(l -> l instanceof SmPLCodeMatcher)
                .forEach(l -> {
                    String replaced = ((SmPLCodeMatcher)l).getCode();
                    if (l instanceof SmPLDeletionCode){
                        for (int i = 0; i < arguments.size(); i++) {
                            replaced = replaced.replaceFirst(MetaSmPLGenerator.argPreFix +i, arguments.get(i).toString());
//                            localizedIdentifiers.put(MetaSmPLGenerator.argPreFix +i, arguments.get(i).toString());
                        }
                    } else {
                        //to memorize used client variable
                        for (int i = 0; i < arguments.size(); i++) {
                            if (replaced.contains(MetaSmPLGenerator.argPreFix+i) && !usedLVs.contains(arguments.get(i).toString())){
                                replaced = replaced.replaceFirst(MetaSmPLGenerator.argPreFix+i, arguments.get(i).toString());
                                usedLVs.add(arguments.get(i).toString());
                            }
                        }
                    }
                    ((SmPLCodeMatcher)l).setCode(replaced);
                });
    }

    @Override
    public void visit(SmPLPatternChunk patternChunk) {
        if (client instanceof CtInvocation<?>) {
            setLVS(patternChunk, (CtInvocation<?>) client);
        } else if (client instanceof CtLocalVariable<?> && ((CtLocalVariable<?>) client).getDefaultExpression() instanceof CtInvocation<?>) {
            setLVS(patternChunk, (CtInvocation<?>) ((CtLocalVariable<?>) client).getDefaultExpression());
        }

        VariableNameFinderOverNormalizedClient analyzer = new VariableNameFinderOverNormalizedClient(client);
        while (true) {
            final List<SmPLConditionalAdditionCode> cdls = patternChunk.getLines().stream()
                    .filter(l -> l instanceof SmPLConditionalAdditionCode)
                    .map(x -> (SmPLConditionalAdditionCode) x)
                    .collect(Collectors.toList());
            if (cdls.size() > 0) {
                SmPLConditionalAdditionCode objCond = cdls.get(cdls.size() - 1);
                NormalizedLineTriplet triplet = new NormalizedLineTriplet(objCond.getCode());
                analyzer.setObjective_type(triplet.type);
                usedLVs.addAll(analyzer.localize(usedLVs));
                analyzer.find().ifPresent(replacementVariable -> {
                    String from = triplet.verName;
                    VariableNameReplacer replacer = new VariableNameReplacer(from, replacementVariable);
                    replacer.inlineExpansion(patternChunk);//do side effect on pattern chunk
                });

                UnusedCondEraser eraser = new UnusedCondEraser();
                eraser.run(patternChunk);//do side effect on pattern chunk
        } else {
                break;
            }
        }
        
    }

    @Override
    public void visit(SmPLDotMatcher dotMatcher) {
        // nothingtodo
    }

    @Override
    public void visit(SmPLDeletionCode deletionCode) {
        // nothingtodo
    }

    @Override
    public void visit(SmPLAdditionCode additionCode) {
        // nothingtodo
    }

    @Override
    public void visit(SmPLJustMatchCode justMatchCode) {
        // nothingtodo
    }

    @Override
    public void visit(SmPLConditionalAdditionCode conditionalAdditionCode) {
        // nothingtodo
    }

}
