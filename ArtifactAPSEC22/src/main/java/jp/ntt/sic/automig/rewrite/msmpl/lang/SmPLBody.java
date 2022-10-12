package jp.ntt.sic.automig.rewrite.msmpl.lang;

import java.util.List;
import java.util.stream.Collectors;

import jp.ntt.sic.automig.rewrite.msmpl.visitor.MetaSmPLVisitor;

public class SmPLBody implements SmPLBodyAST {

    private List<SmPLPattern> patterns;

    public SmPLBody(List<SmPLPattern> patterns) {
        this.patterns = patterns;
    }

    public final List<SmPLPattern> getPatterns() {
        return patterns;
    }

    public final void addPattern(SmPLPattern p) {
        patterns.add(p);
    }

    @Override
    public void accept(MetaSmPLVisitor v) {
        v.visit(this);
    }
    
    public SmPLBody clone() {
        return new SmPLBody(patterns.stream().map(SmPLPattern::clone).collect(Collectors.toList()));
    }
}
