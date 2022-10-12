package at.ac.univie.mminf.luceneSKOS.analysis;
import at.ac.univie.mminf.luceneSKOS.analysis.SKOSAnalyzer.ExpansionType;
import at.ac.univie.mminf.luceneSKOS.util.AnalyzerUtils;
import at.ac.univie.mminf.luceneSKOS.util.TestUtil;
import org.apache.lucene.document.TextField;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
/**
 * Testing the SKOS Label Filter
 *
 * @author Bernhard Haslhofer <bernhard.haslhofer@univie.ac.at>
 * @author Martin Kysel <martin.kysel@univie.ac.at>
 */
public class SKOSLabelFilterTest extends at.ac.univie.mminf.luceneSKOS.analysis.AbstractFilterTest {
    @org.junit.Before
    @java.lang.Override
    public void setUp() throws java.lang.Exception {
        super.setUp();
        skosAnalyzer = new at.ac.univie.mminf.luceneSKOS.analysis.SKOSAnalyzer(matchVersion, skosEngine, at.ac.univie.mminf.luceneSKOS.analysis.SKOSAnalyzer.ExpansionType.LABEL);
        writer = new org.apache.lucene.index.IndexWriter(directory, new org.apache.lucene.index.IndexWriterConfig(matchVersion, skosAnalyzer));
    }

    @org.junit.Test
    public void termQuerySearch() throws org.apache.lucene.index.CorruptIndexException, java.io.IOException {
        org.apache.lucene.document.Document doc = new org.apache.lucene.document.Document();
        doc.add(new org.apache.lucene.document.Field("content", "The quick brown fox jumps over the lazy dog", org.apache.lucene.document.TextField.TYPE_STORED));
        writer.addDocument(doc);
        searcher = new org.apache.lucene.search.IndexSearcher(org.apache.lucene.index.DirectoryReader.open(writer, false));
        org.apache.lucene.search.TermQuery tq = new org.apache.lucene.search.TermQuery(new org.apache.lucene.index.Term("content", "hops"));
        org.junit.Assert.assertEquals(1, at.ac.univie.mminf.luceneSKOS.util.TestUtil.hitCount(searcher, tq));
    }

    @org.junit.Test
    public void phraseQuerySearch() throws org.apache.lucene.index.CorruptIndexException, java.io.IOException {
        org.apache.lucene.document.Document doc = new org.apache.lucene.document.Document();
        doc.add(new org.apache.lucene.document.Field("content", "The quick brown fox jumps over the lazy dog", org.apache.lucene.document.TextField.TYPE_STORED));
        writer.addDocument(doc);
        searcher = new org.apache.lucene.search.IndexSearcher(org.apache.lucene.index.DirectoryReader.open(writer, false));
        org.apache.lucene.search.PhraseQuery pq = new org.apache.lucene.search.PhraseQuery();
        pq.add(new org.apache.lucene.index.Term("content", "fox"));
        pq.add(new org.apache.lucene.index.Term("content", "hops"));
        org.junit.Assert.assertEquals(1, at.ac.univie.mminf.luceneSKOS.util.TestUtil.hitCount(searcher, pq));
    }

    @org.junit.Test
    public void queryParserSearch() throws java.io.IOException, org.apache.lucene.queryparser.flexible.core.QueryNodeException {
        org.apache.lucene.document.Document doc = new org.apache.lucene.document.Document();
        doc.add(new org.apache.lucene.document.Field("content", "The quick brown fox jumps over the lazy dog", org.apache.lucene.document.TextField.TYPE_STORED));
        writer.addDocument(doc);
        searcher = new org.apache.lucene.search.IndexSearcher(org.apache.lucene.index.DirectoryReader.open(writer, false));
        org.apache.lucene.search.Query query = new org.apache.lucene.queryParser.QueryParser(org.apache.lucene.util.Version.LUCENE_36, "content", skosAnalyzer).parse("\"fox jumps\"", "content");
        org.junit.Assert.assertEquals(1, at.ac.univie.mminf.luceneSKOS.util.TestUtil.hitCount(searcher, query));
        org.junit.Assert.assertEquals("content:\"fox (jumps hops leaps)\"", query.toString());
        org.junit.Assert.assertEquals("org.apache.lucene.search.MultiPhraseQuery", query.getClass().getName());
        query = new org.apache.lucene.queryParser.QueryParser(org.apache.lucene.util.Version.LUCENE_36, "content", skosAnalyzer).parse("\"fox jumps\"", "content");
        org.junit.Assert.assertEquals(1, at.ac.univie.mminf.luceneSKOS.util.TestUtil.hitCount(searcher, query));
        org.junit.Assert.assertEquals("content:\"fox jumps\"", query.toString());
        org.junit.Assert.assertEquals("org.apache.lucene.search.PhraseQuery", query.getClass().getName());
    }

    @org.junit.Test
    public void testTermQuery() throws org.apache.lucene.index.CorruptIndexException, java.io.IOException, org.apache.lucene.queryparser.flexible.core.QueryNodeException {
        org.apache.lucene.document.Document doc = new org.apache.lucene.document.Document();
        doc.add(new org.apache.lucene.document.Field("content", "I work for the united nations", org.apache.lucene.document.TextField.TYPE_STORED));
        writer.addDocument(doc);
        searcher = new org.apache.lucene.search.IndexSearcher(org.apache.lucene.index.DirectoryReader.open(writer, false));
        org.apache.lucene.queryparser.flexible.standard.StandardQueryParser parser = new org.apache.lucene.queryparser.flexible.standard.StandardQueryParser(new org.apache.lucene.analysis.core.SimpleAnalyzer(matchVersion));
        org.apache.lucene.search.Query query = parser.parse("united nations", "content");
        org.junit.Assert.assertEquals(1, at.ac.univie.mminf.luceneSKOS.util.TestUtil.hitCount(searcher, query));
    }

    // @Test
    public void displayTokensWithLabelExpansion() throws java.io.IOException {
        java.lang.String text = "The quick brown fox jumps over the lazy dog";
        at.ac.univie.mminf.luceneSKOS.util.AnalyzerUtils.displayTokensWithFullDetails(skosAnalyzer, text);
        // AnalyzerUtils.displayTokensWithPositions(synonymAnalyzer, text);
    }
}