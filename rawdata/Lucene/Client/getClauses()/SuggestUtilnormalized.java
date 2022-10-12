public static java.util.List<org.apache.lucene.search.TermQuery> getTermQueryList(final org.apache.lucene.search.Query query, final java.lang.String[] fields) {
    if (query instanceof org.apache.lucene.search.BooleanQuery) {
        final org.apache.lucene.search.BooleanQuery booleanQuery = ((org.apache.lucene.search.BooleanQuery) (query));
        org.apache.lucene.search.BooleanQuery _CVAR0 = booleanQuery;
        final org.apache.lucene.search.BooleanClause[] clauses = _CVAR0.getClauses();
        final java.util.List<org.apache.lucene.search.TermQuery> queryList = new java.util.ArrayList<>();
        for (final org.apache.lucene.search.BooleanClause clause : clauses) {
            final org.apache.lucene.search.Query q = clause.getQuery();
            if (q instanceof org.apache.lucene.search.BooleanQuery) {
                queryList.addAll(org.codelibs.fess.suggest.util.SuggestUtil.getTermQueryList(q, fields));
            } else if (q instanceof org.apache.lucene.search.TermQuery) {
                final org.apache.lucene.search.TermQuery termQuery = ((org.apache.lucene.search.TermQuery) (q));
                for (final java.lang.String field : fields) {
                    if (field.equals(termQuery.getTerm().field())) {
                        queryList.add(termQuery);
                    }
                }
            }
        }
        return queryList;
    } else if (query instanceof org.apache.lucene.search.TermQuery) {
        final org.apache.lucene.search.TermQuery termQuery = ((org.apache.lucene.search.TermQuery) (query));
        for (final java.lang.String field : fields) {
            if (field.equals(termQuery.getTerm().field())) {
                final java.util.List<org.apache.lucene.search.TermQuery> queryList = new java.util.ArrayList<>(1);
                queryList.add(termQuery);
                return queryList;
            }
        }
    }
    return java.util.Collections.emptyList();
}