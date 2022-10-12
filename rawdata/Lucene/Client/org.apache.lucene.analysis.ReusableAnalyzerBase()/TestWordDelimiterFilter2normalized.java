@org.junit.Test
public void testPositionIncrements() throws java.lang.Exception {
    final int flags = ((((org.apache.lucene.analysis.miscellaneous.WordDelimiterFilter2.GENERATE_WORD_PARTS | org.apache.lucene.analysis.miscellaneous.WordDelimiterFilter2.GENERATE_NUMBER_PARTS) | org.apache.lucene.analysis.miscellaneous.WordDelimiterFilter2.CATENATE_ALL) | org.apache.lucene.analysis.miscellaneous.WordDelimiterFilter2.SPLIT_ON_CASE_CHANGE) | org.apache.lucene.analysis.miscellaneous.WordDelimiterFilter2.SPLIT_ON_NUMERICS) | org.apache.lucene.analysis.miscellaneous.WordDelimiterFilter2.STEM_ENGLISH_POSSESSIVE;
    final org.apache.lucene.analysis.CharArraySet protWords = new org.apache.lucene.analysis.CharArraySet(TEST_VERSION_CURRENT, new java.util.HashSet<java.lang.String>(java.util.Arrays.asList("NUTCH")), false);
    /* analyzer that uses whitespace + wdf */
    org.apache.lucene.analysis.Analyzer a = new org.apache.lucene.analysis.ReusableAnalyzerBase() {
        @java.lang.Override
        public org.apache.lucene.analysis.ReusableAnalyzerBase.TokenStreamComponents createComponents(java.lang.String field, java.io.Reader reader) {
            org.apache.lucene.analysis.Tokenizer tokenizer = new org.apache.lucene.analysis.MockTokenizer(reader, org.apache.lucene.analysis.MockTokenizer.WHITESPACE, false);
            return new org.apache.lucene.analysis.ReusableAnalyzerBase.TokenStreamComponents(tokenizer, new org.apache.lucene.analysis.miscellaneous.WordDelimiterFilter2(tokenizer, flags, protWords));
        }
    };
    /* in this case, works as expected. */
    assertAnalyzesTo(a, "LUCENE / SOLR", new java.lang.String[]{ "LUCENE", "SOLR" }, new int[]{ 0, 9 }, new int[]{ 6, 13 }, null, new int[]{ 1, 1 });
    /* only in this case, posInc of 2 ?! */
    assertAnalyzesTo(a, "LUCENE / solR", new java.lang.String[]{ "LUCENE", "sol", "R", "solR" }, new int[]{ 0, 9, 12, 9 }, new int[]{ 6, 12, 13, 13 }, null, new int[]{ 1, 1, 1, 0 });
    assertAnalyzesTo(a, "LUCENE / NUTCH SOLR", new java.lang.String[]{ "LUCENE", "NUTCH", "SOLR" }, new int[]{ 0, 9, 15 }, new int[]{ 6, 14, 19 }, null, new int[]{ 1, 1, 1 });
    assertAnalyzesTo(a, "LUCENE4.0.0", new java.lang.String[]{ "LUCENE", "4", "0", "0", "LUCENE400" }, new int[]{ 0, 6, 8, 10, 0 }, new int[]{ 6, 7, 9, 11, 11 }, null, new int[]{ 1, 1, 1, 1, 0 });
    /* analyzer that will consume tokens with large position increments */
    org.apache.lucene.analysis.Analyzer a2 = new org.apache.lucene.analysis.ReusableAnalyzerBase() {
        @java.lang.Override
        public org.apache.lucene.analysis.ReusableAnalyzerBase.TokenStreamComponents createComponents(java.lang.String field, java.io.Reader reader) {
            org.apache.lucene.analysis.Tokenizer tokenizer = new org.apache.lucene.analysis.MockTokenizer(reader, org.apache.lucene.analysis.MockTokenizer.WHITESPACE, false);
            return new org.apache.lucene.analysis.ReusableAnalyzerBase.TokenStreamComponents(tokenizer, new org.apache.lucene.analysis.miscellaneous.WordDelimiterFilter2(new org.apache.lucene.analysis.miscellaneous.TestWordDelimiterFilter2.LargePosIncTokenFilter(tokenizer), flags, protWords));
        }
    };
    /* increment of "largegap" is preserved */
    assertAnalyzesTo(a2, "LUCENE largegap SOLR", new java.lang.String[]{ "LUCENE", "largegap", "SOLR" }, new int[]{ 0, 7, 16 }, new int[]{ 6, 15, 20 }, null, new int[]{ 1, 10, 1 });
    /* the "/" had a position increment of 10, where did it go?!?!! */
    assertAnalyzesTo(a2, "LUCENE / SOLR", new java.lang.String[]{ "LUCENE", "SOLR" }, new int[]{ 0, 9 }, new int[]{ 6, 13 }, null, new int[]{ 1, 11 });
    /* in this case, the increment of 10 from the "/" is carried over */
    assertAnalyzesTo(a2, "LUCENE / solR", new java.lang.String[]{ "LUCENE", "sol", "R", "solR" }, new int[]{ 0, 9, 12, 9 }, new int[]{ 6, 12, 13, 13 }, null, new int[]{ 1, 11, 1, 0 });
    assertAnalyzesTo(a2, "LUCENE / NUTCH SOLR", new java.lang.String[]{ "LUCENE", "NUTCH", "SOLR" }, new int[]{ 0, 9, 15 }, new int[]{ 6, 14, 19 }, null, new int[]{ 1, 11, 1 });
    org.apache.lucene.analysis.Analyzer a3 = new org.apache.lucene.analysis.ReusableAnalyzerBase() {
        @java.lang.Override
        public org.apache.lucene.analysis.ReusableAnalyzerBase.TokenStreamComponents createComponents(java.lang.String field, java.io.Reader reader) {
            org.apache.lucene.analysis.Tokenizer tokenizer = new org.apache.lucene.analysis.MockTokenizer(reader, org.apache.lucene.analysis.MockTokenizer.WHITESPACE, false);
            org.apache.lucene.analysis.StopFilter filter = new org.apache.lucene.analysis.StopFilter(TEST_VERSION_CURRENT, tokenizer, org.apache.lucene.analysis.standard.StandardAnalyzer.STOP_WORDS_SET);
            filter.setEnablePositionIncrements(true);
            return new org.apache.lucene.analysis.ReusableAnalyzerBase.TokenStreamComponents(tokenizer, new org.apache.lucene.analysis.miscellaneous.WordDelimiterFilter2(filter, flags, protWords));
        }
    };
    assertAnalyzesTo(a3, "lucene.solr", new java.lang.String[]{ "lucene", "solr", "lucenesolr" }, new int[]{ 0, 7, 0 }, new int[]{ 6, 11, 11 }, null, new int[]{ 1, 1, 0 });
    /* the stopword should add a gap here */
    assertAnalyzesTo(a3, "the lucene.solr", new java.lang.String[]{ "lucene", "solr", "lucenesolr" }, new int[]{ 4, 11, 4 }, new int[]{ 10, 15, 15 }, null, new int[]{ 2, 1, 0 });
    final int flags4 = flags | org.apache.lucene.analysis.miscellaneous.WordDelimiterFilter2.CATENATE_WORDS;
    org.apache.lucene.analysis.Analyzer a4 = new org.apache.lucene.analysis.ReusableAnalyzerBase() {
        @java.lang.Override
        public org.apache.lucene.analysis.ReusableAnalyzerBase.TokenStreamComponents createComponents(java.lang.String field, java.io.Reader reader) {
            org.apache.lucene.analysis.Tokenizer tokenizer = new org.apache.lucene.analysis.MockTokenizer(reader, org.apache.lucene.analysis.MockTokenizer.WHITESPACE, false);
            org.apache.lucene.analysis.StopFilter filter = new org.apache.lucene.analysis.StopFilter(TEST_VERSION_CURRENT, tokenizer, org.apache.lucene.analysis.standard.StandardAnalyzer.STOP_WORDS_SET);
            filter.setEnablePositionIncrements(true);
            return new org.apache.lucene.analysis.ReusableAnalyzerBase.TokenStreamComponents(tokenizer, new org.apache.lucene.analysis.miscellaneous.WordDelimiterFilter2(filter, flags4, protWords));
        }
    };
    assertAnalyzesTo(a4, "LUCENE4.0.0", new java.lang.String[]{ "LUCENE", "4", "0", "0", "LUCENE400" }, new int[]{ 0, 6, 8, 10, 0 }, new int[]{ 6, 7, 9, 11, 11 }, null, new int[]{ 1, 1, 1, 1, 0 });
}