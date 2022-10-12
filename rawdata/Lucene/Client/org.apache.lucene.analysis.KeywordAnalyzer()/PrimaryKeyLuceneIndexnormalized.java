private void initIndex() throws java.io.IOException {
    try {
        org.apache.lucene.util.Version _CVAR0 = org.apache.lucene.util.Version.LUCENE_36;
        org.apache.lucene.analysis.KeywordAnalyzer _CVAR1 = new org.apache.lucene.analysis.KeywordAnalyzer();
        final org.apache.lucene.index.IndexWriterConfig indexWriterConfig = new org.apache.lucene.index.IndexWriterConfig(_CVAR0, _CVAR1);
        indexWriterConfig.setMaxBufferedDocs(org.apache.lucene.index.IndexWriterConfig.DISABLE_AUTO_FLUSH);
        indexWriterConfig.setRAMBufferSizeMB(ramBuffer);
        indexWriterConfig.setMergeScheduler(org.apache.lucene.index.NoMergeScheduler.INSTANCE);
        final org.apache.lucene.index.IndexWriter indexWriter = new org.apache.lucene.index.IndexWriter(new org.apache.lucene.store.NIOFSDirectory(new java.io.File(indexPath)), indexWriterConfig);
        indexWriter.setInfoStream(java.lang.System.err);
        indexer = new org.culturegraph.mf.search.index.BatchIndexer(indexWriter);
        indexer.setBatchSize(batchSize);
    } catch (java.lang.NumberFormatException e) {
        throw new org.culturegraph.mf.search.index.IndexException("Error in indexer properties. Not a number: " + e.getMessage(), e);
    }
    init = true;
}