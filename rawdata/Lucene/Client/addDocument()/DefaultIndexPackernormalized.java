static void copyLegacyDocuments(org.apache.lucene.index.IndexReader r, org.apache.lucene.store.Directory targetdir, org.apache.maven.index.context.IndexingContext context) throws org.apache.lucene.index.CorruptIndexException, org.apache.lucene.store.LockObtainFailedException, java.io.IOException {
    org.apache.lucene.index.IndexWriter w = null;
    try {
        w = new org.apache.maven.index.context.NexusIndexWriter(targetdir, new org.apache.maven.index.context.NexusLegacyAnalyzer(), true);
        int i = 0;
        for (int i = 0; i < r.maxDoc(); i++) {
            if (!r.isDeleted(i)) {
                org.apache.lucene.index.IndexReader _CVAR1 = r;
                int _CVAR2 = i;
                org.apache.lucene.document.Document _CVAR3 = _CVAR1.document(_CVAR2);
                org.apache.maven.index.context.IndexingContext _CVAR4 = context;
                org.apache.lucene.index.IndexWriter _CVAR0 = w;
                org.apache.lucene.document.Document _CVAR5 = org.apache.maven.index.packer.DefaultIndexPacker.updateLegacyDocument(_CVAR3, _CVAR4);
                _CVAR0.addDocument(_CVAR5);
            }
        }
        w.optimize();
        w.commit();
    } finally {
        org.apache.maven.index.context.IndexUtils.close(w);
    }
}