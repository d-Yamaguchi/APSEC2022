package jp.ntt.sic.automig.rewrite.msmpl.lang;

import jp.ntt.sic.automig.rewrite.msmpl.visitor.MetaSmPLVisitor;

public class SmPLAdditionCode extends SmPLCodeMatcher {
    private boolean isDestination;
    public SmPLAdditionCode(String code) {
        isDestination = false;
        setCode(code);
    }

    @Override
    public void accept(MetaSmPLVisitor v) {
        v.visit(this);
    }
    
    @Override
    public SmPLLine clone() { return isDestination ? new SmPLAdditionCode(getCode()).defineForMigrationDestination() : new SmPLAdditionCode(getCode()); }

    public SmPLAdditionCode defineForMigrationDestination(){ isDestination = true; return this; }
    public boolean isForMigrationDestination(){return isDestination;}
}
