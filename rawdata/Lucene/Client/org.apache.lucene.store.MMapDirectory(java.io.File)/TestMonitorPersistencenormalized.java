@org.junit.Test
public void testCacheIsRepopulated() throws java.io.IOException {
    java.lang.String _CVAR0 = "f";
    java.nio.file.Path _CVAR3 = indexDirectory;
    java.io.File _CVAR4 = _CVAR3.toFile();
    uk.co.flax.luwak.queryparsers.LuceneQueryParser _CVAR1 = new uk.co.flax.luwak.queryparsers.LuceneQueryParser(_CVAR0);
    uk.co.flax.luwak.presearcher.TermFilteredPresearcher _CVAR2 = new uk.co.flax.luwak.presearcher.TermFilteredPresearcher();
    org.apache.lucene.store.MMapDirectory _CVAR5 = new org.apache.lucene.store.MMapDirectory(_CVAR4);
    uk.co.flax.luwak.Monitor monitor = new uk.co.flax.luwak.Monitor(_CVAR1, _CVAR2, _CVAR5);
    monitor.update(new uk.co.flax.luwak.MonitorQuery("1", "test"), new uk.co.flax.luwak.MonitorQuery("2", "test", "test"), new uk.co.flax.luwak.MonitorQuery("3", "test", com.google.common.collect.ImmutableMap.of("language", "en")), new uk.co.flax.luwak.MonitorQuery("4", "test", "test", com.google.common.collect.ImmutableMap.of("language", "en", "wibble", "quack")));
    uk.co.flax.luwak.InputDocument doc = uk.co.flax.luwak.InputDocument.builder("doc1").addField("f", "test", new org.apache.lucene.analysis.core.KeywordAnalyzer()).build();
    MatchesAssert.assertThat(monitor.match(doc, SimpleMatcher.FACTORY)).hasMatchCount(4);
    monitor.close();
    java.lang.String _CVAR6 = "f";
    java.nio.file.Path _CVAR9 = indexDirectory;
    java.io.File _CVAR10 = _CVAR9.toFile();
    uk.co.flax.luwak.queryparsers.LuceneQueryParser _CVAR7 = new uk.co.flax.luwak.queryparsers.LuceneQueryParser(_CVAR6);
    uk.co.flax.luwak.presearcher.TermFilteredPresearcher _CVAR8 = new uk.co.flax.luwak.presearcher.TermFilteredPresearcher();
    org.apache.lucene.store.MMapDirectory _CVAR11 = new org.apache.lucene.store.MMapDirectory(_CVAR10);
    uk.co.flax.luwak.Monitor monitor2 = new uk.co.flax.luwak.Monitor(_CVAR7, _CVAR8, _CVAR11);
    org.assertj.core.api.Assertions.assertThat(monitor2.getQueryCount()).isEqualTo(4);
    MatchesAssert.assertThat(monitor2.match(doc, SimpleMatcher.FACTORY)).hasMatchCount(4);
}