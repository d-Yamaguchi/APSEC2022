package jp.ntt.sic.automig.rewrite.msmpl.lang;

import jp.ntt.sic.automig.rewrite.msmpl.visitor.MetaSmPLVisitor;

public class SmPLConditionalAdditionCode extends SmPLCodeMatcher {

    public SmPLConditionalAdditionCode(String code) {
        setCode(code);
    }

    @Override
    public void accept(MetaSmPLVisitor v) {
        v.visit(this);
    }
    
    @Override
    public SmPLLine clone() {
        return new SmPLConditionalAdditionCode(getCode());
    }
}
