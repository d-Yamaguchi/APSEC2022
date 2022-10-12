/**
 * {@inheritDoc }
 */
public synchronized void clearTaxonomy() {
    checkClosed();
    while (writerUseCount > 0) {
        try {
            wait();
        } catch (java.lang.InterruptedException e) {
        }
    } 
    closeTaxonomyWriter();
    org.apache.lucene.util.Version _CVAR0 = org.apache.lucene.util.Version.LUCENE_30;
    org.apache.lucene.analysis.KeywordAnalyzer _CVAR1 = new org.apache.lucene.analysis.KeywordAnalyzer();
    org.apache.lucene.index.IndexWriterConfig _CVAR2 = new org.apache.lucene.index.IndexWriterConfig(_CVAR0, _CVAR1);
    org.apache.lucene.index.IndexWriterConfig.OpenMode _CVAR3 = this.writerOpenMode;
    org.apache.lucene.index.IndexWriterConfig _CVAR4 = _CVAR2.setOpenMode(_CVAR3);
    org.apache.lucene.index.LogByteSizeMergePolicy _CVAR5 = new org.apache.lucene.index.LogByteSizeMergePolicy();
    // Workaround for missing delete all method in the TaxonomyWriter
    org.apache.lucene.index.IndexWriterConfig config = _CVAR4.setMergePolicy(_CVAR5);
    try {
        org.apache.lucene.index.IndexWriter indexWriter = new org.apache.lucene.index.IndexWriter(directory, config);
        indexWriter.deleteAll();
        indexWriter.close();
        // the TaxonomyReader should be refreshed after this but only if the IndexReaders have been reopened before
    } catch (java.io.IOException e) {
        com.gentics.cr.lucene.facets.taxonomy.taxonomyaccessor.DefaultTaxonomyAccessor.LOGGER.error("Could not clear the Taxonomy", e);
    }
    notifyAll();
}