package jp.ntt.sic.automig.rewrite.msmpl.lang;

import java.util.List;
import java.util.stream.Collectors;

import jp.ntt.sic.automig.rewrite.msmpl.visitor.MetaSmPLVisitor;

public class SmPLPatternOr extends SmPLPatternRepeatable {
    private List<SmPLPatternChunk> chunks;

    SmPLPatternOr(List<SmPLPatternChunk> chs) {
        chunks = chs;
    }

    public List<SmPLPatternChunk> getChunks() {
        return chunks;
    }

    public void setChunks(List<SmPLPatternChunk> chunks) {
        this.chunks = chunks;
    }

    @Override
    public void accept(MetaSmPLVisitor v) {
        v.visit(this);
    }
    
    @Override
    public SmPLPatternOr clone(){
        return new SmPLPatternOr(chunks.stream().map(SmPLPatternChunk::clone).collect(Collectors.toList()));
    }
}
