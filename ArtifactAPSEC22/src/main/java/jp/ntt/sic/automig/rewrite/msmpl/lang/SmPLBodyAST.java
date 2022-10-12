package jp.ntt.sic.automig.rewrite.msmpl.lang;

import jp.ntt.sic.automig.rewrite.msmpl.visitor.MetaSmPLVisitor;

public interface SmPLBodyAST {
    void accept(MetaSmPLVisitor v);
    
    SmPLBodyAST clone();
}
