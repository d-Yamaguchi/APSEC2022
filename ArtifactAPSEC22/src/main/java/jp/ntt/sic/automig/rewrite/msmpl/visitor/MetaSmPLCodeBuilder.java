package jp.ntt.sic.automig.rewrite.msmpl.visitor;

import jp.ntt.sic.automig.rewrite.msmpl.lang.SmPLBody;
import jp.ntt.sic.automig.rewrite.msmpl.lang.SmPLConditionalAdditionCode;
import jp.ntt.sic.automig.rewrite.msmpl.lang.SmPLDotMatcher;
import jp.ntt.sic.automig.rewrite.msmpl.lang.SmPLLine;
import jp.ntt.sic.automig.rewrite.msmpl.lang.SmPLPatternOr;
import jp.ntt.sic.automig.rewrite.msmpl.lang.SmPLPatternRep;
import jp.ntt.sic.automig.rewrite.msmpl.lang.SmPLAdditionCode;
import jp.ntt.sic.automig.rewrite.msmpl.lang.SmPLDeletionCode;
import jp.ntt.sic.automig.rewrite.msmpl.lang.SmPLJustMatchCode;
import jp.ntt.sic.automig.rewrite.msmpl.lang.SmPLPatternChunk;

public class MetaSmPLCodeBuilder implements MetaSmPLVisitor {
    public final StringBuilder sb = new StringBuilder();

    public String getCode() {
        return sb.toString();
    }

    @Override
    public void visit(SmPLBody body) {
        body.getPatterns().forEach(p -> {
            p.accept(this);
            sb.append("\n");
        });
    }

    @Override
    public void visit(SmPLPatternRep patternRep) {
        sb.append("(\n");
        patternRep.getRepE().accept(this);
        sb.append(")\n");
    }

    @Override
    public void visit(SmPLPatternOr patternOr) {
        sb.append("(\n");
        for (SmPLPatternChunk c : patternOr.getChunks()) {
            c.accept(this);
            sb.append("|\n");
        }
        sb.append(")\n");
    }

    @Override
    public void visit(SmPLPatternChunk patternChunk) {
        for (SmPLLine l : patternChunk.getLines()) {
            l.accept(this);
        }
    }

    @Override
    public void visit(SmPLDotMatcher dotMatcher) {
        sb.append("...");
        dotMatcher.getCondition().ifPresent(c -> sb.append(" when ").append(c));
        sb.append("\n");
    }

    @Override
    public void visit(SmPLDeletionCode deletionCode) {
        sb.append("- ").append(deletionCode.getCode()).append("\n");
    }

    @Override
    public void visit(SmPLAdditionCode additionCode) {
        sb.append("+ ").append(additionCode.getCode()).append("\n");
    }

    @Override
    public void visit(SmPLJustMatchCode justMatchCode) {
        sb.append("  ").append(justMatchCode.getCode()).append("\n");
    }

    @Override
    public void visit(SmPLConditionalAdditionCode conditionalAdditionCode) {
        sb.append("+? ").append(conditionalAdditionCode.getCode()).append("\n");
    }

}
