package jp.ntt.sic.automig.rewrite.msmpl.lang;

import jp.ntt.sic.automig.rewrite.msmpl.visitor.MetaSmPLVisitor;

public class SmPLDeletionCode extends SmPLCodeMatcher {

    public SmPLDeletionCode(String code) {
        setCode(code);
    }

    @Override
    public void accept(MetaSmPLVisitor v) {
        v.visit(this);
    }
    
    @Override
    public SmPLLine clone() {
        return new SmPLDeletionCode(getCode());
    }
}
