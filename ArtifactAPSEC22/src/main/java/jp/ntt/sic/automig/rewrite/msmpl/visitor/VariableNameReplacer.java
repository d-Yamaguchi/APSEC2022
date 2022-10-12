package jp.ntt.sic.automig.rewrite.msmpl.visitor;

import jp.ntt.sic.automig.rewrite.msmpl.lang.SmPLConditionalAdditionCode;
import jp.ntt.sic.automig.rewrite.msmpl.lang.SmPLAdditionCode;
import jp.ntt.sic.automig.rewrite.msmpl.lang.SmPLDeletionCode;
import jp.ntt.sic.automig.rewrite.msmpl.lang.SmPLDotMatcher;
import jp.ntt.sic.automig.rewrite.msmpl.lang.SmPLJustMatchCode;
import jp.ntt.sic.automig.rewrite.msmpl.lang.SmPLPatternChunk;
import jp.ntt.sic.automig.rewrite.msmpl.normalize.LineVisitor;

public class VariableNameReplacer extends LineVisitor {

    private String from, to;

    public VariableNameReplacer(String from, String to) {
        this.from = from;
        this.to = to;
    }

    public void inlineExpansion(SmPLPatternChunk chunk) {
        chunk.getLines().forEach(c -> c.accept(this));
    }

    @Override
    public void visit(SmPLDotMatcher dotMatcher) {
        //nothing to do
    }

    @Override
    public void visit(SmPLDeletionCode deletionCode) {
        String[] toReplaceLR = deletionCode.getCode().split("=");
        deletionCode.setCode(toReplaceLR[0] + "=" + toReplaceLR[1].replaceAll(from, to));
    }

    @Override
    public void visit(SmPLAdditionCode additionCode) {
        String[] toReplaceLR = additionCode.getCode().split("=");
        additionCode.setCode(toReplaceLR[0] + "=" + toReplaceLR[1].replaceAll(from, to));
    }

    @Override
    public void visit(SmPLJustMatchCode justMatchCode) {
        String[] toReplaceLR = justMatchCode.getCode().split("=");
        justMatchCode.setCode(toReplaceLR[0] + "=" + toReplaceLR[1].replaceAll(from, to));
    }
    @Override
    public void visit(SmPLConditionalAdditionCode conditionalAdditionCode) {
        String[] toReplaceLR = conditionalAdditionCode.getCode().split("=");
        conditionalAdditionCode.setCode(toReplaceLR[0] + "=" + toReplaceLR[1].replaceAll(from, to));
    }
}
