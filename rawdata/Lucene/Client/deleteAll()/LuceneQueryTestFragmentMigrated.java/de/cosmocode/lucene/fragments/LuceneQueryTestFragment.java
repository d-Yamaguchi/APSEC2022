package de.cosmocode.lucene.fragments;
import java.io.IOException;
import de.cosmocode.junit.UnitProvider;
import de.cosmocode.lucene.LuceneQuery;
import de.cosmocode.lucene.LuceneQueryTest;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
/**
 * <p> This is an abstract Test class that implements no test itself.
 * It can only be executed by an {@link LuceneQueryTest}.
 * It sets up a dummy Lucene search directory in which the resulting queries
 * can be tested with the method {@link #assertEquals(String, LuceneQuery)}.
 * </p>
 *
 * @author Oliver Lorenz
 */
public abstract class LuceneQueryTestFragment implements de.cosmocode.junit.UnitProvider<de.cosmocode.lucene.LuceneQuery> {
    public static final org.apache.lucene.store.Directory DIRECTORY = new org.apache.lucene.store.RAMDirectory();

    public static final org.apache.lucene.analysis.Analyzer ANALYZER = new org.apache.lucene.analysis.KeywordAnalyzer();

    public static final org.apache.lucene.util.Version USED_VERSION = org.apache.lucene.util.Version.LUCENE_CURRENT;

    /**
     * A helper for wildcard queries, different then WILDCARD2.
     */
    public static final java.lang.String WILDCARD1 = "arg";

    /**
     * A helper for wildcard queries, different then WILDCARD1.
     */
    public static final java.lang.String WILDCARD2 = "hex";

    /**
     * A helper for wildcard queries. yields different results then WILDCARD1 and WILDCARD2.
     */
    public static final java.lang.String WILDCARD3 = "foo";

    /**
     * A helper for fuzzy queries.
     * Also yields a result for wildcard queries, so it can be used for wildcard + fuzzy.
     */
    public static final java.lang.String FUZZY1 = "truf";

    /**
     * A helper for fuzzy queries. yields different results then FUZZY1 and FUZZY3.
     */
    public static final java.lang.String FUZZY2 = "arf1";

    /**
     * A helper for fuzzy queries. yields different results then FUZZY1 AND FUZZY2.
     */
    public static final java.lang.String FUZZY3 = "fooba";

    public static final java.lang.String ARG1 = "arg1";

    public static final boolean ARG2 = true;

    public static final java.lang.String ARG3 = "arg3";

    public static final int ARG4 = 20;

    public static final java.lang.String DEFAULT_FIELD = "default_field";

    public static final java.lang.String FIELD1 = "field1";

    public static final java.lang.String FIELD2 = "field2";

    private static final org.slf4j.Logger LOG = org.slf4j.LoggerFactory.getLogger(de.cosmocode.lucene.fragments.LuceneQueryTestFragment.class);

    private static final java.lang.Object[] ARGS = new java.lang.Object[]{ de.cosmocode.lucene.fragments.LuceneQueryTestFragment.ARG1, de.cosmocode.lucene.fragments.LuceneQueryTestFragment.ARG2, de.cosmocode.lucene.fragments.LuceneQueryTestFragment.ARG3, de.cosmocode.lucene.fragments.LuceneQueryTestFragment.ARG4, "hexff00aa", "hex559911", "foobar", "truffel", de.cosmocode.lucene.fragments.LuceneQueryTestFragment.WILDCARD1, de.cosmocode.lucene.fragments.LuceneQueryTestFragment.WILDCARD2, de.cosmocode.lucene.fragments.LuceneQueryTestFragment.WILDCARD3, de.cosmocode.lucene.fragments.LuceneQueryTestFragment.FUZZY1, de.cosmocode.lucene.fragments.LuceneQueryTestFragment.FUZZY2, de.cosmocode.lucene.fragments.LuceneQueryTestFragment.FUZZY3 };

    @java.lang.Override
    public de.cosmocode.lucene.LuceneQuery unit() {
        return de.cosmocode.lucene.LuceneQueryTest.unitProvider().unit();
    }

    /**
     * <p> Asserts that two queries return the same result from lucene.
     * The first query is a hand-made control query, the second is the
     * LuceneQuery that should be tested.
     * </p>
     *
     * @param expected
     * 		the control Query
     * @param actual
     * 		the generated LuceneQuery
     */
    protected void assertEquals(final java.lang.String expected, final de.cosmocode.lucene.LuceneQuery actual) {
        final java.lang.String expectedString = expected;
        final java.lang.String actualString = actual.getQuery();
        final org.apache.lucene.queryParser.QueryParser parser = new org.apache.lucene.queryParser.QueryParser(de.cosmocode.lucene.fragments.LuceneQueryTestFragment.USED_VERSION, de.cosmocode.lucene.fragments.LuceneQueryTestFragment.DEFAULT_FIELD, de.cosmocode.lucene.fragments.LuceneQueryTestFragment.ANALYZER);
        final org.apache.lucene.search.Query queryExpected;
        final org.apache.lucene.search.Query queryActual;
        try {
            queryExpected = parser.parse(expectedString);
        } catch (org.apache.lucene.queryParser.ParseException e) {
            throw new java.lang.IllegalArgumentException("Expected query is illegal", e);
        }
        try {
            queryActual = parser.parse(actualString);
        } catch (org.apache.lucene.queryParser.ParseException e) {
            throw new java.lang.IllegalArgumentException("Actual query is illegal", e);
        }
        final java.util.List<?> docExpected;
        final java.util.List<?> docActual;
        try {
            docExpected = search(queryExpected, 100);
            docActual = search(queryActual, 100);
        } catch (java.io.IOException e) {
            throw new java.lang.IllegalStateException("low level IOException", e);
        }
        if (!docExpected.equals(docActual)) {
            de.cosmocode.lucene.fragments.LuceneQueryTestFragment.LOG.debug("Original queries:");
            de.cosmocode.lucene.fragments.LuceneQueryTestFragment.LOG.debug("\texpected={}", expectedString);
            de.cosmocode.lucene.fragments.LuceneQueryTestFragment.LOG.debug("\t  actual={}", actualString);
            de.cosmocode.lucene.fragments.LuceneQueryTestFragment.LOG.debug("Parsed queries:");
            de.cosmocode.lucene.fragments.LuceneQueryTestFragment.LOG.debug("\texpected={}", queryExpected);
            de.cosmocode.lucene.fragments.LuceneQueryTestFragment.LOG.debug("\t  actual={}", queryActual);
            de.cosmocode.lucene.fragments.LuceneQueryTestFragment.LOG.debug("Expected result: {}", docExpected);
            de.cosmocode.lucene.fragments.LuceneQueryTestFragment.LOG.debug("  Actual result: {}", docActual);
            de.cosmocode.lucene.fragments.LuceneQueryTestFragment.LOG.debug("End");
        }
        final java.lang.String errorMsg = (("Expected query: " + expectedString) + ", but is: ") + actualString;
        org.junit.Assert.assertTrue(errorMsg, docExpected.equals(docActual));
    }

    /**
     * Searches for the given query and returns a list that has at most "max" items.
     *
     * @param query
     * 		the query to search for
     * @param max
     * 		the maximum number of items in the returned list
     * @return a list with the found Documents
     * @throws IOException
     * 		if lucene throws an exception
     */
    protected java.util.List<java.lang.String> search(final org.apache.lucene.search.Query query, final int max) throws java.io.IOException {
        final java.util.List<java.lang.String> docList = new java.util.LinkedList<java.lang.String>();
        final org.apache.lucene.search.IndexSearcher searcher = new org.apache.lucene.search.IndexSearcher(de.cosmocode.lucene.fragments.LuceneQueryTestFragment.DIRECTORY);
        final org.apache.lucene.search.TopDocs docs = searcher.search(query, max);
        if (docs.totalHits > 0) {
            for (final org.apache.lucene.search.ScoreDoc doc : docs.scoreDocs) {
                docList.add(searcher.doc(doc.doc).get("name"));
            }
        }
        return docList;
    }

    /**
     * <p> Creates a lucene document that store the given value
     * in a field named "key".
     * </p>
     * <p> This ensures that a search for "key:value"
     * returns the generated document.
     * If the key is DEFAULT_FIELD then a search for "value"
     * returns the generated document.
     * </p>
     *
     * @param keyValues
     * 		name and value of the fields, alternating (key, value, key, value, ...)
     * @return a new document with a field set to the given values
     */
    protected static org.apache.lucene.document.Document createDocument(final java.lang.String... keyValues) {
        final org.apache.lucene.document.Document doc = new org.apache.lucene.document.Document();
        java.lang.String key = null;
        for (java.lang.String keyOrValue : keyValues) {
            if (key == null) {
                key = keyOrValue;
                continue;
            }
            final org.apache.lucene.document.Field field = new org.apache.lucene.document.Field(key, keyOrValue, org.apache.lucene.document.Field.Store.YES, org.apache.lucene.document.Field.Index.ANALYZED);
            doc.add(field);
            key = null;
        }
        doc.add(new org.apache.lucene.document.Field("empty", "empty", org.apache.lucene.document.Field.Store.NO, org.apache.lucene.document.Field.Index.ANALYZED));
        return doc;
    }

    /**
     * Creates a new Lucene Index with some predefined field and arguments.
     *
     * @throws IOException
     * 		if creating the index failed
     */
    @org.junit.BeforeClass
    public static void createLuceneIndex() throws java.io.IOException {
        final org.apache.lucene.index.IndexWriter writer = new org.apache.lucene.index.IndexWriter(de.cosmocode.lucene.fragments.LuceneQueryTestFragment.DIRECTORY, de.cosmocode.lucene.fragments.LuceneQueryTestFragment.ANALYZER, org.apache.lucene.index.IndexWriter.MaxFieldLength.UNLIMITED);
        final java.lang.String[] fields = new java.lang.String[]{ de.cosmocode.lucene.fragments.LuceneQueryTestFragment.FIELD1, de.cosmocode.lucene.fragments.LuceneQueryTestFragment.FIELD2, de.cosmocode.lucene.fragments.LuceneQueryTestFragment.DEFAULT_FIELD };
        for (final java.lang.String field : fields) {
            final java.lang.String fieldName = (field == de.cosmocode.lucene.fragments.LuceneQueryTestFragment.DEFAULT_FIELD) ? "arg" : field + "_arg";
            for (int i = 0; i < de.cosmocode.lucene.fragments.LuceneQueryTestFragment.ARGS.length; ++i) {
                writer.addDocument(de.cosmocode.lucene.fragments.LuceneQueryTestFragment.createDocument("name", fieldName + (i + 1), field, de.cosmocode.lucene.fragments.LuceneQueryTestFragment.ARGS[i].toString()));
                if (i == 0)
                    continue;

                final java.lang.String[] subArgs = new java.lang.String[2 * (i + 1)];
                final java.lang.StringBuilder name = new java.lang.StringBuilder();
                name.append(fieldName);
                for (int j = 0; j <= i; ++j) {
                    subArgs[2 * j] = field;
                    subArgs[(2 * j) + 1] = de.cosmocode.lucene.fragments.LuceneQueryTestFragment.ARGS[j].toString();
                    name.append(j + 1);
                }
                subArgs[2 * i] = "name";
                subArgs[(2 * i) + 1] = name.toString();
                writer.addDocument(de.cosmocode.lucene.fragments.LuceneQueryTestFragment.createDocument(subArgs));
            }
        }
        writer.close();
    }

    /**
     * Cleans the index up again, to ensure an empty lucene index in the next test.
     *
     * @throws IOException
     * 		if cleaning the index failed
     */
    @org.junit.AfterClass
    public static void cleanLuceneIndex() throws java.io.IOException {
        final org.apache.lucene.index.IndexWriter writer = new org.apache.lucene.index.IndexWriter(de.cosmocode.lucene.fragments.LuceneQueryTestFragment.DIRECTORY, de.cosmocode.lucene.fragments.LuceneQueryTestFragment.ANALYZER, org.apache.lucene.index.IndexWriter.MaxFieldLength.UNLIMITED);
        IndexWriter writer = new org.apache.lucene.index.IndexWriter(DIRECTORY, de.cosmocode.lucene.fragments.LuceneQueryTestFragment.ANALYZER, org.apache.lucene.index.IndexWriter.MaxFieldLength.UNLIMITED);
        writer.deleteDocuments(new org.apache.lucene.queryParser.QueryParser(WILDCARD2, ANALYZER).parse(WILDCARD1));
        writer.close();
    }
}