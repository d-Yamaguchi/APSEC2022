/**
 * Run a Search against the lucene index.
 *
 * @param searcher
 * 		
 * @param parsedQuery
 * 		
 * @param count
 * 		
 * @param ttcollector
 * 		
 * @param explain
 * 		
 * @param start
 * 		
 * @param facetsCollector
 * 		a {@link FacetsCollector}
 * @return ArrayList of results
 */
private java.util.HashMap<java.lang.String, java.lang.Object> executeSearcher(final org.apache.lucene.search.TopDocsCollector<?> ttcollector, final org.apache.lucene.search.IndexSearcher searcher, final org.apache.lucene.search.Query parsedQuery, final boolean explain, final int count, final int start, final org.apache.lucene.facet.search.FacetsCollector facetsCollector, final org.apache.lucene.search.Filter filter) {
    try {
        org.apache.lucene.search.Collector collector = null;
        if (facetsCollector != null) {
            // wrap the TopDocsCollector and the FacetsCollector to one
            // MultiCollector and perform the search
            collector = org.apache.lucene.search.MultiCollector.wrap(ttcollector, facetsCollector);
        } else {
            collector = ttcollector;
        }
        if (filter != null) {
            searcher.search(parsedQuery, filter, collector);
        } else {
            searcher.search(parsedQuery, collector);
        }
        org.apache.lucene.search.TopDocs tdocs = ttcollector.topDocs(start, count);
        float maxScoreReturn = tdocs.getMaxScore();
        com.gentics.cr.lucene.search.CRSearcher.log.debug("maxScoreReturn: " + maxScoreReturn);
        org.apache.lucene.search.ScoreDoc[] hits = tdocs.scoreDocs;
        com.gentics.cr.lucene.search.CRSearcher.log.debug("hits (topdocs): \n" + com.gentics.cr.util.StringUtils.getCollectionSummary(java.util.Arrays.asList(hits), "\n"));
        java.util.LinkedHashMap<org.apache.lucene.document.Document, java.lang.Float> result = new java.util.LinkedHashMap<org.apache.lucene.document.Document, java.lang.Float>(hits.length);
        // Calculate the number of documents to be fetched
        int num = java.lang.Math.min(hits.length, count);
        for (int i = 0; i < num; i++) {
            org.apache.lucene.search.ScoreDoc currentDoc = hits[i];
            if (currentDoc.doc != java.lang.Integer.MAX_VALUE) {
                com.gentics.cr.lucene.search.CRSearcher.log.debug((("currentDoc id: " + currentDoc.doc) + " ; score: ") + currentDoc.score);
                // add id field for AdvancedContentHighlighter
                doc.add(new org.apache.lucene.document.Field("id", hits[i].doc + "", org.apache.lucene.document.Field.Store.YES, org.apache.lucene.document.Field.Index.NO));
                org.apache.lucene.search.IndexSearcher _CVAR1 = searcher;
                int _CVAR2 = currentDoc.doc;
                org.apache.lucene.document.Document doc = _CVAR1.doc(_CVAR2);
                org.apache.lucene.document.Document _CVAR3 = doc;
                java.lang.String _CVAR4 = "contentid";
                org.apache.lucene.document.Fieldable _CVAR5 = _CVAR3.getFieldable(_CVAR4);
                org.apache.log4j.Logger _CVAR0 = com.gentics.cr.lucene.search.CRSearcher.log;
                java.lang.String _CVAR6 = "adding contentid: " + _CVAR5;
                _CVAR0.debug(_CVAR6);
                com.gentics.cr.lucene.search.CRSearcher.log.debug((("with hits[" + i) + "].score = ") + hits[i].score);
                result.put(doc, hits[i].score);
                if (explain) {
                    org.apache.lucene.search.Explanation ex = searcher.explain(parsedQuery, hits[i].doc);
                    com.gentics.cr.lucene.search.CRSearcher.log_explain.debug((("Explanation for " + doc.toString()) + " - ") + ex.toString());
                }
            } else {
                com.gentics.cr.lucene.search.CRSearcher.log.error("Loading search documents failed partly (document has MAX_INTEGER as document id");
            }
        }
        com.gentics.cr.lucene.search.CRSearcher.log.debug(((((("Fetched Document " + start) + " to ") + (start + num)) + " of ") + ttcollector.getTotalHits()) + " found Documents");
        java.util.HashMap<java.lang.String, java.lang.Object> ret = new java.util.HashMap<java.lang.String, java.lang.Object>(3);
        if (retrieveCollector) {
            ret.put(com.gentics.cr.lucene.search.CRSearcher.RESULT_COLLECTOR_KEY, collector);
        }
        ret.put(com.gentics.cr.lucene.search.CRSearcher.RESULT_RESULT_KEY, result);
        ret.put(com.gentics.cr.lucene.search.CRSearcher.RESULT_MAXSCORE_KEY, maxScoreReturn);
        return ret;
    } catch (java.lang.Exception e) {
        com.gentics.cr.lucene.search.CRSearcher.log.error("Error running search for query " + parsedQuery, e);
    }
    return null;
}