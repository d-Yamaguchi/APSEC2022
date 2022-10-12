public synchronized void optimize() throws org.apache.lucene.index.CorruptIndexException, java.io.IOException {
    org.apache.lucene.index.IndexWriter _CVAR0 = getIndexWriter();
    int _CVAR1 = 1;
    _CVAR0.forceMerge(_CVAR1);
    commit();
}