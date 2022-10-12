package jp.ntt.sic.automig.rewrite.msmpl.lang;

import jp.ntt.sic.automig.rewrite.msmpl.visitor.MetaSmPLVisitor;

abstract public class SmPLLine implements SmPLBodyAST {

    @Override
    public void accept(MetaSmPLVisitor v) {
        // TODO Auto-generated method stub

    }
    
    @Override
    public abstract SmPLLine clone();

}
