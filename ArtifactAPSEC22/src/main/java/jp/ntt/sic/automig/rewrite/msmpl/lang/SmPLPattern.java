package jp.ntt.sic.automig.rewrite.msmpl.lang;

import jp.ntt.sic.automig.rewrite.msmpl.visitor.MetaSmPLVisitor;

public abstract class SmPLPattern implements SmPLBodyAST {

    @Override
    public void accept(MetaSmPLVisitor v) {
     // rep / SmPLPattern
    }

    @Override
    public abstract SmPLPattern clone();
}
