/**
 * Cleans the index up again, to ensure an empty lucene index in the next test.
 *
 * @throws IOException
 * 		if cleaning the index failed
 */
@org.junit.AfterClass
public static void cleanLuceneIndex() throws java.io.IOException {
    org.apache.lucene.store.Directory _CVAR0 = de.cosmocode.lucene.fragments.LuceneQueryTestFragment.DIRECTORY;
    org.apache.lucene.analysis.Analyzer _CVAR1 = de.cosmocode.lucene.fragments.LuceneQueryTestFragment.ANALYZER;
    org.apache.lucene.index.IndexWriter.MaxFieldLength _CVAR2 = org.apache.lucene.index.IndexWriter.MaxFieldLength.UNLIMITED;
    final org.apache.lucene.index.IndexWriter writer = new org.apache.lucene.index.IndexWriter(_CVAR0, _CVAR1, _CVAR2);
    org.apache.lucene.index.IndexWriter _CVAR3 = writer;
    _CVAR3.deleteAll();
    writer.close();
}