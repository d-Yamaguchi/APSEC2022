public java.util.Collection<java.util.Map<java.lang.String, java.lang.String>> readAll(final int offset, final int limit, final java.lang.String query) throws org.apache.lucene.index.StaleReaderException, org.apache.lucene.index.CorruptIndexException, org.apache.lucene.store.LockObtainFailedException, java.io.IOException, org.apache.lucene.queryParser.ParseException {
    org.apache.lucene.util.Version _CVAR1 = org.apache.lucene.util.Version.LUCENE_CURRENT;
    java.lang.String _CVAR0 = "id";
    org.apache.lucene.analysis.standard.StandardAnalyzer _CVAR2 = new org.apache.lucene.analysis.standard.StandardAnalyzer(_CVAR1);
    final org.apache.lucene.queryParser.QueryParser parser = new org.apache.lucene.queryParser.QueryParser(_CVAR0, _CVAR2);
    if (query.startsWith("NOT ")) {
        final org.apache.lucene.search.Query query3 = new org.apache.lucene.search.WildcardQuery(new org.apache.lucene.index.Term("id", "*"));
        final org.apache.lucene.search.BooleanQuery query2 = new org.apache.lucene.search.BooleanQuery();
        query2.add(query3, org.apache.lucene.search.BooleanClause.Occur.MUST);
        query2.add(parser.parse(query.substring(4)), org.apache.lucene.search.BooleanClause.Occur.MUST_NOT);
        return readAll(offset, limit, query2);
    } else {
        return readAll(offset, limit, parser.parse(query));
    }
}