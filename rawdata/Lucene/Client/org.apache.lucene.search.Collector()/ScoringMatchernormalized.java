@java.lang.Override
public void matchQuery(java.lang.String queryId, org.apache.lucene.search.Query matchQuery, org.apache.lucene.search.Query highlightQuery) throws java.io.IOException {
    uk.co.flax.luwak.matchers.ScoringMatcher _CVAR0 = doc;
    org.apache.lucene.search.IndexSearcher searcher = _CVAR0.getSearcher();
    try {
        final float[] scores = new float[1];// inits to 0.0f (no match)

        org.apache.lucene.search.IndexSearcher _CVAR1 = searcher;
        org.apache.lucene.search.Query _CVAR2 = matchQuery;
        org.apache.lucene.search.Collector _CVAR3 = new org.apache.lucene.search.Collector() {
            private org.apache.lucene.search.Scorer scorer;

            @java.lang.Override
            public void collect(int doc) throws java.io.IOException {
                scores[0] = scorer.score();
            }

            @java.lang.Override
            public void setScorer(org.apache.lucene.search.Scorer scorer) {
                this.scorer = scorer;
            }

            @java.lang.Override
            public boolean acceptsDocsOutOfOrder() {
                return true;
            }

            @java.lang.Override
            public void setNextReader(org.apache.lucene.index.AtomicReaderContext context) {
            }
        };
        _CVAR1.search(_CVAR2, _CVAR3);
        float score = scores[0];
        if (score > 0) {
            this.scores.put(queryId, new ScoringMatch(queryId, score));
        }
    } catch (java.io.IOException e) {
        // can never happen (RAMDirectory)
        throw new java.lang.RuntimeException(e);
    }
}