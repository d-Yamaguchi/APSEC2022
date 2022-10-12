package jp.ntt.sic.automig.rewrite.msmpl.lang;

import java.util.Optional;

import jp.ntt.sic.automig.rewrite.msmpl.visitor.MetaSmPLVisitor;

public class SmPLDotMatcher extends SmPLLine {

    private String condition;

    @Override
    public void accept(MetaSmPLVisitor v) {
        v.visit(this);
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public Optional<String> getCondition() {
        return Optional.ofNullable(condition);
    }
    
    @Override
    public SmPLLine clone() {
        SmPLDotMatcher cloned = new SmPLDotMatcher();
        cloned.setCondition(condition);
        return cloned;
    }
}
