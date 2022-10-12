package jp.ntt.sic.automig.rewrite.msmpl.normalize;

import jp.ntt.sic.automig.rewrite.msmpl.lang.SmPLBody;
import jp.ntt.sic.automig.rewrite.msmpl.lang.SmPLPatternChunk;
import jp.ntt.sic.automig.rewrite.msmpl.lang.SmPLPatternOr;
import jp.ntt.sic.automig.rewrite.msmpl.lang.SmPLPatternRep;
import jp.ntt.sic.automig.rewrite.msmpl.visitor.MetaSmPLVisitor;

public abstract class LineVisitor implements MetaSmPLVisitor {

    @Override
    public void visit(SmPLBody body) {

    }

    @Override
    public void visit(SmPLPatternRep patternRep) {
    }

    @Override
    public void visit(SmPLPatternOr patternOr) {
    }

    @Override
    public void visit(SmPLPatternChunk patternChunk) {
    }
}
