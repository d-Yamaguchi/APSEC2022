@java.lang.Override
public org.apache.lucene.search.DocIdSet getDocIdSet(org.apache.lucene.index.LeafReaderContext context, org.apache.lucene.util.Bits acceptDocs) throws java.io.IOException {
    org.apache.lucene.search.DocIdSet _CVAR0 = new org.apache.lucene.search.DocIdSet() {
        @java.lang.Override
        public long ramBytesUsed() {
            // todo this is not correct but is required for concrete class
            return 0;
        }

        @java.lang.Override
        public org.apache.lucene.search.DocIdSetIterator iterator() throws java.io.IOException {
            return new com.senseidb.clue.util.IntArrayDocIdSetIterator(docidsToDelete);
        }
    };
    return _CVAR0;
}