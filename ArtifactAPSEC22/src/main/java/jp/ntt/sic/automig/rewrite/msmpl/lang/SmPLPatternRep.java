package jp.ntt.sic.automig.rewrite.msmpl.lang;

import jp.ntt.sic.automig.rewrite.msmpl.visitor.MetaSmPLVisitor;

public abstract class SmPLPatternRep extends SmPLPattern {
    private SmPLPatternRepeatable repE;

    SmPLPatternRep(SmPLPatternRepeatable repe) {
        repE = repe;
    }

    public SmPLPatternRepeatable getRepE() {
        return repE;
    }

    public void setRepE(SmPLPatternRepeatable repE) {
        this.repE = repE;
    }

    @Override
    public void accept(MetaSmPLVisitor v) {
        v.visit(this);
    }
}
