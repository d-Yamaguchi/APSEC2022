/**
 * Reopens all of the Searchers in the Searcher cache. This method is invoked
 * in a synchronized context.
 */
protected void reopenCachedSearchers() {
    com.gentics.cr.lucene.indexaccessor.WarmingIndexAccessor.LOGGER.debug((("reopening cached searchers (" + cachedSearchers.size()) + "):") + java.lang.Thread.currentThread().getId());
    java.util.Set<org.apache.lucene.search.Similarity> keys = cachedSearchers.keySet();
    for (org.apache.lucene.search.Similarity key : keys) {
        com.gentics.cr.lucene.indexaccessor.WarmingIndexAccessor _CVAR0 = cachedSearchers;
        org.apache.lucene.search.Similarity _CVAR1 = key;
        org.apache.lucene.search.IndexSearcher searcher = _CVAR0.get(_CVAR1);
        try {
            org.apache.lucene.search.IndexSearcher oldSearcher = searcher;
            org.apache.lucene.search.IndexSearcher _CVAR2 = searcher;
            org.apache.lucene.index.IndexReader oldReader = _CVAR2.getIndexReader();
            org.apache.lucene.index.IndexReader _CVAR3 = oldReader;
            org.apache.lucene.index.IndexReader newReader = _CVAR3.reopen();
            if (newReader != oldReader) {
                retireSearchers();
                org.apache.lucene.search.IndexSearcher newSearcher = new org.apache.lucene.search.IndexSearcher(newReader);
                newSearcher.setSimilarity(oldSearcher.getSimilarity());
                com.gentics.cr.lucene.indexaccessor.WarmingIndexAccessor.SearcherWarmer warmer = new com.gentics.cr.lucene.indexaccessor.WarmingIndexAccessor.SearcherWarmer(oldSearcher, newSearcher);
                numSearchersForRetirment++;
                numSearchersForRetirment++;
                pool.execute(warmer);
            }
        } catch (java.io.IOException e) {
            com.gentics.cr.lucene.indexaccessor.WarmingIndexAccessor.LOGGER.error("error reopening cached Searcher", e);
        }
    }
}