@org.junit.Test
public void testNonStandardSimilarity() throws java.io.IOException {
    try (uk.co.flax.luwak.Monitor monitor = new uk.co.flax.luwak.Monitor(new uk.co.flax.luwak.queryparsers.LuceneQueryParser("field"), new uk.co.flax.luwak.presearcher.MatchAllPresearcher())) {
        monitor.update(new uk.co.flax.luwak.MonitorQuery("1", "test"));
        org.apache.lucene.search.similarities.Similarity similarity = new org.apache.lucene.search.similarities.DefaultSimilarity() {
            @java.lang.Override
            public float tf(float freq) {
                return 1000.0F;
            }
        };
        uk.co.flax.luwak.InputDocument doc = uk.co.flax.luwak.InputDocument.builder("doc").addField("field", "this is a test", new org.apache.lucene.analysis.standard.StandardAnalyzer()).build();
        uk.co.flax.luwak.DocumentBatch batch = new uk.co.flax.luwak.DocumentBatch.Builder().add(doc).setSimilarity(similarity).build();
        uk.co.flax.luwak.Matches<uk.co.flax.luwak.matchers.ScoringMatch> standard = monitor.match(doc, ScoringMatcher.FACTORY);
        uk.co.flax.luwak.Matches<uk.co.flax.luwak.matchers.ScoringMatch> withSim = monitor.match(batch, ScoringMatcher.FACTORY);
        assertThat(com.google.common.collect.Iterables.getFirst(standard.getMatches("doc"), null).getScore()).isEqualTo(com.google.common.collect.Iterables.getFirst(withSim.getMatches("doc"), null).getScore() / 1000);
    }
}