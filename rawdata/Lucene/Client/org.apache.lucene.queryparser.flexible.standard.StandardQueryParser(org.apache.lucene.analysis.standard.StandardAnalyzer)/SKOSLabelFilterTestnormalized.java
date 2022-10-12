@org.junit.Test
public void queryParserSearch() throws java.io.IOException, org.apache.lucene.queryparser.flexible.core.QueryNodeException {
    org.apache.lucene.document.Document doc = new org.apache.lucene.document.Document();
    doc.add(new org.apache.lucene.document.Field("content", "The quick brown fox jumps over the lazy dog", org.apache.lucene.document.TextField.TYPE_STORED));
    writer.addDocument(doc);
    searcher = new org.apache.lucene.search.IndexSearcher(org.apache.lucene.index.DirectoryReader.open(writer, false));
    at.ac.univie.mminf.luceneSKOS.analysis.SKOSLabelFilterTest _CVAR0 = skosAnalyzer;
    org.apache.lucene.queryparser.flexible.standard.StandardQueryParser _CVAR1 = new org.apache.lucene.queryparser.flexible.standard.StandardQueryParser(_CVAR0);
    java.lang.String _CVAR2 = "\"fox jumps\"";
    java.lang.String _CVAR3 = "content";
    org.apache.lucene.search.Query query = _CVAR1.parse(_CVAR2, _CVAR3);
    org.junit.Assert.assertEquals(1, at.ac.univie.mminf.luceneSKOS.util.TestUtil.hitCount(searcher, query));
    org.junit.Assert.assertEquals("content:\"fox (jumps hops leaps)\"", query.toString());
    org.junit.Assert.assertEquals("org.apache.lucene.search.MultiPhraseQuery", query.getClass().getName());
    at.ac.univie.mminf.luceneSKOS.analysis.SKOSLabelFilterTest _CVAR4 = matchVersion;
    org.apache.lucene.analysis.standard.StandardAnalyzer _CVAR5 = new org.apache.lucene.analysis.standard.StandardAnalyzer(_CVAR4);
    org.apache.lucene.queryparser.flexible.standard.StandardQueryParser _CVAR6 = new org.apache.lucene.queryparser.flexible.standard.StandardQueryParser(_CVAR5);
    java.lang.String _CVAR7 = "\"fox jumps\"";
    java.lang.String _CVAR8 = "content";
    org.apache.lucene.search.Query _CVAR9 = _CVAR6.parse(_CVAR7, _CVAR8);
    query = _CVAR9;
    org.junit.Assert.assertEquals(1, at.ac.univie.mminf.luceneSKOS.util.TestUtil.hitCount(searcher, query));
    org.junit.Assert.assertEquals("content:\"fox jumps\"", query.toString());
    org.junit.Assert.assertEquals("org.apache.lucene.search.PhraseQuery", query.getClass().getName());
}