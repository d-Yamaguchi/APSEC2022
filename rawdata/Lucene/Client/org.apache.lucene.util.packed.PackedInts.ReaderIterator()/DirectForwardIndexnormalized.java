@java.lang.Override
public org.apache.lucene.util.packed.PackedInts.ReaderIterator iterator(org.apache.lucene.store.DataInput in) throws java.io.IOException {
    long[] arr = com.senseidb.compressor.idset.DirectForwardIndex.readFromDataInput(in);
    final java.nio.LongBuffer buf = java.nio.LongBuffer.wrap(arr);
    org.apache.lucene.util.packed.PackedInts.ReaderIterator _CVAR0 = new org.apache.lucene.util.packed.PackedInts.ReaderIterator() {
        @java.lang.Override
        public void close() throws java.io.IOException {
        }

        @java.lang.Override
        public long next() throws java.io.IOException {
            return buf.get();
        }

        @java.lang.Override
        public org.apache.lucene.util.LongsRef next(int count) throws java.io.IOException {
            int size = java.lang.Math.min(count, buf.remaining());
            long[] retBuf = new long[size];
            buf.get(retBuf);
            return new org.apache.lucene.util.LongsRef(retBuf, 0, size);
        }

        @java.lang.Override
        public int getBitsPerValue() {
            return 64;
        }

        @java.lang.Override
        public int size() {
            return buf.capacity();
        }

        @java.lang.Override
        public int ord() {
            return buf.position();
        }
    };
    return _CVAR0;
}