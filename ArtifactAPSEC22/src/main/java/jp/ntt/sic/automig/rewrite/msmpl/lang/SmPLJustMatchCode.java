package jp.ntt.sic.automig.rewrite.msmpl.lang;

import jp.ntt.sic.automig.rewrite.msmpl.visitor.MetaSmPLVisitor;

public class SmPLJustMatchCode extends SmPLCodeMatcher {
    public SmPLJustMatchCode(String code) {
        setCode(code);
    }

    @Override
    public void accept(MetaSmPLVisitor v) {
        v.visit(this);
    }
    
    @Override
    public SmPLLine clone() {
        return new SmPLJustMatchCode(getCode());
    }
}
