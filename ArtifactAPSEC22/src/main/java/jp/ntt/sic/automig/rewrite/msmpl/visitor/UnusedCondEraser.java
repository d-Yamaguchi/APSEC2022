package jp.ntt.sic.automig.rewrite.msmpl.visitor;

import java.util.List;
import java.util.stream.Collectors;

import jp.ntt.sic.automig.rewrite.msmpl.lang.SmPLAdditionCode;
import jp.ntt.sic.automig.rewrite.msmpl.lang.SmPLConditionalAdditionCode;
import jp.ntt.sic.automig.rewrite.msmpl.lang.SmPLDotMatcher;
import jp.ntt.sic.automig.rewrite.msmpl.lang.SmPLJustMatchCode;
import jp.ntt.sic.automig.rewrite.msmpl.lang.SmPLPatternChunk;
import jp.ntt.sic.automig.rewrite.msmpl.normalize.LineVisitor;
import jp.ntt.sic.automig.rewrite.msmpl.normalize.NormalizedLineTriplet;
import jp.ntt.sic.automig.rewrite.msmpl.lang.SmPLDeletionCode;

public class UnusedCondEraser extends LineVisitor {

    private String checkIt;
    private boolean used;


    @Override
    public void visit(SmPLDotMatcher dotMatcher) {
        //nothing to do
    }

    @Override
    public void visit(SmPLDeletionCode deletionCode) {
        used = new NormalizedLineTriplet(deletionCode.getCode()).dependedVariables.contains(checkIt);
    }

    @Override
    public void visit(SmPLAdditionCode additionCode) {
        used = new NormalizedLineTriplet(additionCode.getCode()).dependedVariables.contains(checkIt);
    }

    @Override
    public void visit(SmPLJustMatchCode justMatchCode) {
        used = new NormalizedLineTriplet(justMatchCode.getCode()).dependedVariables.contains(checkIt);
    }

    @Override
    public void visit(SmPLConditionalAdditionCode conditionalAdditionCode) {
        used = new NormalizedLineTriplet(conditionalAdditionCode.getCode()).dependedVariables.contains(checkIt);
    }

    public void run(SmPLPatternChunk patternChunk) {
        List<SmPLConditionalAdditionCode> cds = patternChunk.getLines().stream()
                .filter(x -> x instanceof SmPLConditionalAdditionCode)
                .map(x -> (SmPLConditionalAdditionCode) x)
                .collect(Collectors.toList());
        if (!cds.isEmpty()) {
            SmPLConditionalAdditionCode cd = cds.get(cds.size() - 1);
            checkIt = new NormalizedLineTriplet(cd.getCode()).verName;
            used = false;
            patternChunk.getLines().forEach(x -> {
                if (!used) x.accept(this);
            });
            if (!used) {
                patternChunk.getLines().remove(cd);
            } else {
                SmPLAdditionCode ad = new SmPLAdditionCode(cd.getCode());
                patternChunk.getLines().set(patternChunk.getLines().indexOf(cd),ad);
            }
        }
    }
}
