package com.senseidb.compressor.idset;
import com.senseidb.compressor.util.CompressorUtil;
public class PackedForwardIndex implements com.senseidb.compressor.idset.ForwardIndex {
    private org.apache.lucene.util.packed.PackedInts.Mutable data;

    public PackedForwardIndex(int numDocs, int numTerms) {
        int bitsPerVal = com.senseidb.compressor.util.CompressorUtil.getNumBits(numTerms);
        data = org.apache.lucene.util.packed.PackedInts.getMutable(numDocs, bitsPerVal, org.apache.lucene.util.packed.PackedInts.DEFAULT);
    }

    @java.lang.Override
    public void add(int idx, long val) {
        data.set(idx, val);
    }

    @java.lang.Override
    public long sizeInBytes() {
        return data.ramBytesUsed();
    }

    @java.lang.Override
    public long get(int idx) {
        return data.get(idx);
    }

    @java.lang.Override
    public void save(org.apache.lucene.store.DataOutput out) throws java.io.IOException {
        data.size();
    }

    @java.lang.Override
    public org.apache.lucene.util.packed.PackedInts.Reader load(org.apache.lucene.store.DataInput input) throws java.io.IOException {
        // DataInput din = new DirectByteBufferDataInput(input);
        org.apache.lucene.util.packed.PackedInts.Reader reader = org.apache.lucene.util.packed.PackedInts.getReader(input);
        return reader;
    }

    @java.lang.Override
    public org.apache.lucene.util.packed.PackedInts.ReaderIterator iterator(org.apache.lucene.store.DataInput input) throws java.io.IOException {
        // DataInput din = new DirectByteBufferDataInput(input);
        return org.apache.lucene.util.packed.PackedInts.getReaderIterator(input, org.apache.lucene.util.packed.PackedInts.DEFAULT_BUFFER_SIZE);
    }
}