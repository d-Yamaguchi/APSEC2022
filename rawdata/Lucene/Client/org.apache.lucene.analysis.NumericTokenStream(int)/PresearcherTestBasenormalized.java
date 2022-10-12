@org.junit.Test
public void filtersOnNumericTermQueries() throws java.io.IOException {
    // Rudimentary query parser which returns numeric encoded BytesRefs
    try (Monitor numeric_monitor = new Monitor(new MonitorQueryParser() {
        @java.lang.Override
        public org.apache.lucene.search.Query parse(java.lang.String queryString, java.util.Map<java.lang.String, java.lang.String> metadata) throws java.lang.Exception {
            org.apache.lucene.util.BytesRefBuilder brb = new org.apache.lucene.util.BytesRefBuilder();
            org.apache.lucene.util.NumericUtils.intToPrefixCoded(java.lang.Integer.parseInt(queryString), 0, brb);
            org.apache.lucene.index.Term t = new org.apache.lucene.index.Term(uk.co.flax.luwak.presearcher.PresearcherTestBase.TEXTFIELD, brb.get());
            return new org.apache.lucene.search.TermQuery(t);
        }
    }, presearcher)) {
        for (int i = 8; i <= 15; i++) {
            numeric_monitor.update(new MonitorQuery("query" + i, "" + i));
        }
        for (int i = 8; i <= 15; i++) {
            org.apache.lucene.analysis.NumericTokenStream nts = new org.apache.lucene.analysis.NumericTokenStream(1);
            nts.setIntValue(i);
            InputDocument doc = InputDocument.builder("doc" + i).addField(new org.apache.lucene.document.TextField(uk.co.flax.luwak.presearcher.PresearcherTestBase.TEXTFIELD, nts)).build();
            MatchesAssert.assertThat(numeric_monitor.match(doc, SimpleMatcher.FACTORY)).matchesDoc("doc" + i).hasMatchCount("doc" + i, 1).matchesQuery("query" + i, "doc" + i);
        }
    }
}