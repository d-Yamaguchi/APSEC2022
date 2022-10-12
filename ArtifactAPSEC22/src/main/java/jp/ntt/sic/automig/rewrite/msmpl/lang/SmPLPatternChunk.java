package jp.ntt.sic.automig.rewrite.msmpl.lang;

import java.util.List;
import java.util.stream.Collectors;

import jp.ntt.sic.automig.rewrite.msmpl.visitor.MetaSmPLVisitor;

public class SmPLPatternChunk extends SmPLPatternRepeatable {
    private List<SmPLLine> lines;

    public SmPLPatternChunk(List<SmPLLine> ls) {
        lines = ls;
    }

    public List<SmPLLine> getLines() {
        return lines;
    }

    public void setLines(List<SmPLLine> lines) {
        this.lines = lines;
    }

    @Override
    public void accept(MetaSmPLVisitor v) {
        v.visit(this);
    }
    
    @Override
    public SmPLPatternChunk clone(){
        return new SmPLPatternChunk(lines.stream().map(SmPLLine::clone).collect(Collectors.toList()));
    }
}
