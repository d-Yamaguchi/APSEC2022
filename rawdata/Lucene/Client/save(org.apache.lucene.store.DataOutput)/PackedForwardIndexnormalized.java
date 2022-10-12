@java.lang.Override
public void save(org.apache.lucene.store.DataOutput out) throws java.io.IOException {
    org.apache.lucene.util.packed.PackedInts.Mutable _CVAR0 = data;
    org.apache.lucene.store.DataOutput _CVAR1 = out;
    // DataOutput dout = new DirectByteBufferDataOutput(out);
    _CVAR0.save(_CVAR1);
}