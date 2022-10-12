@java.lang.Override
public org.apache.lucene.search.Query parse(java.lang.String queryString, java.util.Map<java.lang.String, java.lang.String> metadata) throws java.lang.Exception {
    if (queryString.equals("slow")) {
        org.apache.lucene.search.MatchAllDocsQuery _CVAR0 = new org.apache.lucene.search.MatchAllDocsQuery() {
            @java.lang.Override
            public org.apache.lucene.search.Weight createWeight(org.apache.lucene.search.IndexSearcher searcher, boolean needsScores, int flags) {
                try {
                    java.lang.Thread.sleep(delay);
                } catch (java.lang.InterruptedException e) {
                    throw new java.lang.RuntimeException(e);
                }
                return super.createWeight(searcher, needsScores, flags);
            }
        };
        return _CVAR0;
    }
    return new org.apache.lucene.search.MatchAllDocsQuery();
}