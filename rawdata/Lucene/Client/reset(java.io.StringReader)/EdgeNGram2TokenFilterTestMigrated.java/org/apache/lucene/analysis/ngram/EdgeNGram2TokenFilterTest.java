package org.apache.lucene.analysis.ngram;
import org.apache.lucene.analysis.BaseTokenStreamTestCase;
import org.apache.lucene.analysis.MockTokenizer;
/**
 * Tests {@link EdgeNGram2TokenFilter} for correctness.
 */
public class EdgeNGram2TokenFilterTest extends org.apache.lucene.analysis.BaseTokenStreamTestCase {
    private org.apache.lucene.analysis.TokenStream input;

    /* Set a large position increment gap of 10 if the token is "largegap" or "/" */
    private final class LargePosIncTokenFilter extends org.apache.lucene.analysis.TokenFilter {
        private org.apache.lucene.analysis.tokenattributes.CharTermAttribute termAtt = addAttribute(org.apache.lucene.analysis.tokenattributes.CharTermAttribute.class);

        private org.apache.lucene.analysis.tokenattributes.PositionIncrementAttribute posIncAtt = addAttribute(org.apache.lucene.analysis.tokenattributes.PositionIncrementAttribute.class);

        protected LargePosIncTokenFilter(org.apache.lucene.analysis.TokenStream input) {
            super(input);
        }

        @java.lang.Override
        public boolean incrementToken() throws java.io.IOException {
            if (input.incrementToken()) {
                if (termAtt.toString().equals("largegap") || termAtt.toString().equals("/"))
                    posIncAtt.setPositionIncrement(10);

                return true;
            } else {
                return false;
            }
        }
    }

    @java.lang.Override
    public void setUp() throws java.lang.Exception {
        super.setUp();
        input = new org.apache.lucene.analysis.ngram.EdgeNGram2TokenFilterTest.LargePosIncTokenFilter(new org.apache.lucene.analysis.MockTokenizer(new java.io.StringReader("abcde / ABCDE"), org.apache.lucene.analysis.MockTokenizer.WHITESPACE, false));
    }

    public void testInvalidInput() throws java.lang.Exception {
        boolean gotException = false;
        try {
            new org.apache.lucene.analysis.ngram.EdgeNGram2TokenFilter(input, EdgeNGram2TokenFilter.Side.FRONT, 0, 0);
        } catch (java.lang.IllegalArgumentException e) {
            gotException = true;
        }
        assertTrue(gotException);
    }

    public void testInvalidInput2() throws java.lang.Exception {
        boolean gotException = false;
        try {
            new org.apache.lucene.analysis.ngram.EdgeNGram2TokenFilter(input, EdgeNGram2TokenFilter.Side.FRONT, 2, 1);
        } catch (java.lang.IllegalArgumentException e) {
            gotException = true;
        }
        assertTrue(gotException);
    }

    public void testInvalidInput3() throws java.lang.Exception {
        boolean gotException = false;
        try {
            new org.apache.lucene.analysis.ngram.EdgeNGram2TokenFilter(input, EdgeNGram2TokenFilter.Side.FRONT, -1, 2);
        } catch (java.lang.IllegalArgumentException e) {
            gotException = true;
        }
        assertTrue(gotException);
    }

    public void testFrontUnigram() throws java.lang.Exception {
        org.apache.lucene.analysis.ngram.EdgeNGram2TokenFilter tokenizer = new org.apache.lucene.analysis.ngram.EdgeNGram2TokenFilter(input, EdgeNGram2TokenFilter.Side.FRONT, 1, 1);
        assertTokenStreamContents(tokenizer, new java.lang.String[]{ "a", "/", "A" }, new int[]{ 0, 6, 8 }, new int[]{ 1, 7, 9 }, new int[]{ 1, 1, 1 });
    }

    public void testFrontUnigramPreserve() throws java.lang.Exception {
        org.apache.lucene.analysis.ngram.EdgeNGram2TokenFilter tokenizer = new org.apache.lucene.analysis.ngram.EdgeNGram2TokenFilter(input, EdgeNGram2TokenFilter.Side.FRONT, 1, 1, true);
        assertTokenStreamContents(tokenizer, new java.lang.String[]{ "a", "/", "A" }, new int[]{ 0, 6, 8 }, new int[]{ 1, 7, 9 }, new int[]{ 1, 10, 1 });
    }

    public void testBackUnigram() throws java.lang.Exception {
        org.apache.lucene.analysis.ngram.EdgeNGram2TokenFilter tokenizer = new org.apache.lucene.analysis.ngram.EdgeNGram2TokenFilter(input, EdgeNGram2TokenFilter.Side.BACK, 1, 1);
        assertTokenStreamContents(tokenizer, new java.lang.String[]{ "e", "/", "E" }, new int[]{ 4, 6, 12 }, new int[]{ 5, 7, 13 }, new int[]{ 1, 1, 1 });
    }

    public void testBackUnigramPreserve() throws java.lang.Exception {
        org.apache.lucene.analysis.ngram.EdgeNGram2TokenFilter tokenizer = new org.apache.lucene.analysis.ngram.EdgeNGram2TokenFilter(input, EdgeNGram2TokenFilter.Side.BACK, 1, 1, true);
        assertTokenStreamContents(tokenizer, new java.lang.String[]{ "e", "/", "E" }, new int[]{ 4, 6, 12 }, new int[]{ 5, 7, 13 }, new int[]{ 1, 10, 1 });
    }

    public void testOversizedNgrams() throws java.lang.Exception {
        org.apache.lucene.analysis.ngram.EdgeNGram2TokenFilter tokenizer = new org.apache.lucene.analysis.ngram.EdgeNGram2TokenFilter(input, EdgeNGram2TokenFilter.Side.FRONT, 6, 6);
        assertTokenStreamContents(tokenizer, new java.lang.String[0], new int[0], new int[0], new int[0]);
    }

    public void testOversizedNgramsPreserve() throws java.lang.Exception {
        org.apache.lucene.analysis.ngram.EdgeNGram2TokenFilter tokenizer = new org.apache.lucene.analysis.ngram.EdgeNGram2TokenFilter(input, EdgeNGram2TokenFilter.Side.FRONT, 6, 6, true);
        assertTokenStreamContents(tokenizer, new java.lang.String[0], new int[0], new int[0], new int[0]);
    }

    public void testFrontRangeOfNgrams() throws java.lang.Exception {
        org.apache.lucene.analysis.ngram.EdgeNGram2TokenFilter tokenizer = new org.apache.lucene.analysis.ngram.EdgeNGram2TokenFilter(input, EdgeNGram2TokenFilter.Side.FRONT, 1, 3);
        assertTokenStreamContents(tokenizer, new java.lang.String[]{ "a", "ab", "abc", "/", "A", "AB", "ABC" }, new int[]{ 0, 0, 0, 6, 8, 8, 8 }, new int[]{ 1, 2, 3, 7, 9, 10, 11 }, new int[]{ 1, 1, 1, 1, 1, 1, 1 });
    }

    public void testFrontRangeOfNgramsPreserve() throws java.lang.Exception {
        org.apache.lucene.analysis.ngram.EdgeNGram2TokenFilter tokenizer = new org.apache.lucene.analysis.ngram.EdgeNGram2TokenFilter(input, EdgeNGram2TokenFilter.Side.FRONT, 1, 3, true);
        assertTokenStreamContents(tokenizer, new java.lang.String[]{ "a", "ab", "abc", "/", "A", "AB", "ABC" }, new int[]{ 0, 0, 0, 6, 8, 8, 8 }, new int[]{ 1, 2, 3, 7, 9, 10, 11 }, new int[]{ 1, 0, 0, 10, 1, 0, 0 });
    }

    public void testBackRangeOfNgrams() throws java.lang.Exception {
        org.apache.lucene.analysis.ngram.EdgeNGram2TokenFilter tokenizer = new org.apache.lucene.analysis.ngram.EdgeNGram2TokenFilter(input, EdgeNGram2TokenFilter.Side.BACK, 1, 3);
        assertTokenStreamContents(tokenizer, new java.lang.String[]{ "e", "de", "cde", "/", "E", "DE", "CDE" }, new int[]{ 4, 3, 2, 6, 12, 11, 10 }, new int[]{ 5, 5, 5, 7, 13, 13, 13 }, new int[]{ 1, 1, 1, 1, 1, 1, 1 });
    }

    public void testBackRangeOfNgramsPreserve() throws java.lang.Exception {
        org.apache.lucene.analysis.ngram.EdgeNGram2TokenFilter tokenizer = new org.apache.lucene.analysis.ngram.EdgeNGram2TokenFilter(input, EdgeNGram2TokenFilter.Side.BACK, 1, 3, true);
        assertTokenStreamContents(tokenizer, new java.lang.String[]{ "e", "de", "cde", "/", "E", "DE", "CDE" }, new int[]{ 4, 3, 2, 6, 12, 11, 10 }, new int[]{ 5, 5, 5, 7, 13, 13, 13 }, new int[]{ 1, 0, 0, 10, 1, 0, 0 });
    }

    public void testSmallTokenInStream() throws java.lang.Exception {
        input = new org.apache.lucene.analysis.MockTokenizer(new java.io.StringReader("abc de fgh"), org.apache.lucene.analysis.MockTokenizer.WHITESPACE, false);
        org.apache.lucene.analysis.ngram.EdgeNGram2TokenFilter tokenizer = new org.apache.lucene.analysis.ngram.EdgeNGram2TokenFilter(input, EdgeNGram2TokenFilter.Side.FRONT, 3, 3);
        assertTokenStreamContents(tokenizer, new java.lang.String[]{ "abc", "fgh" }, new int[]{ 0, 7 }, new int[]{ 3, 10 }, new int[]{ 1, 1 });
    }

    public void testSmallTokenInStreamPreserve() throws java.lang.Exception {
        input = new org.apache.lucene.analysis.MockTokenizer(new java.io.StringReader("abc de fgh"), org.apache.lucene.analysis.MockTokenizer.WHITESPACE, false);
        org.apache.lucene.analysis.ngram.EdgeNGram2TokenFilter tokenizer = new org.apache.lucene.analysis.ngram.EdgeNGram2TokenFilter(input, EdgeNGram2TokenFilter.Side.FRONT, 3, 3, true);
        assertTokenStreamContents(tokenizer, new java.lang.String[]{ "abc", "fgh" }, new int[]{ 0, 7 }, new int[]{ 3, 10 }, new int[]{ 1, 2 });
    }

    public void testReset() throws java.lang.Exception {
        org.apache.lucene.analysis.ngram.EdgeNGram2TokenFilter filter = new org.apache.lucene.analysis.ngram.EdgeNGram2TokenFilter(tokenizer, EdgeNGram2TokenFilter.Side.FRONT, 1, 3);
        assertTokenStreamContents(filter, __SmPLUnsupported__(0), __SmPLUnsupported__(1), __SmPLUnsupported__(2), __SmPLUnsupported__(3));
        org.apache.lucene.analysis.WhitespaceTokenizer tokenizer = new org.apache.lucene.analysis.WhitespaceTokenizer(TEST_VERSION_CURRENT, new java.io.StringReader("abcde"));
        WhitespaceTokenizer tokenizer = new org.apache.lucene.analysis.core.WhitespaceTokenizer(TEST_VERSION_CURRENT, new java.io.StringReader("abcde"));
        tokenizer.setReader(new java.io.StringReader("ABCDE"));
        assertTokenStreamContents(filter, __SmPLUnsupported__(4), __SmPLUnsupported__(5), __SmPLUnsupported__(6), __SmPLUnsupported__(7));
    }

    public void testResetPreserve() throws java.lang.Exception {
        org.apache.lucene.analysis.WhitespaceTokenizer tokenizer = new org.apache.lucene.analysis.WhitespaceTokenizer(TEST_VERSION_CURRENT, new java.io.StringReader("abcde"));
        org.apache.lucene.analysis.ngram.EdgeNGram2TokenFilter filter = new org.apache.lucene.analysis.ngram.EdgeNGram2TokenFilter(tokenizer, EdgeNGram2TokenFilter.Side.FRONT, 1, 3, true);
        assertTokenStreamContents(filter, new java.lang.String[]{ "a", "ab", "abc" }, new int[]{ 0, 0, 0 }, new int[]{ 1, 2, 3 }, new int[]{ 1, 0, 0 });
        tokenizer.reset(new java.io.StringReader("ABCDE"));
        assertTokenStreamContents(filter, new java.lang.String[]{ "A", "AB", "ABC" }, new int[]{ 0, 0, 0 }, new int[]{ 1, 2, 3 }, new int[]{ 1, 0, 0 });
    }
}