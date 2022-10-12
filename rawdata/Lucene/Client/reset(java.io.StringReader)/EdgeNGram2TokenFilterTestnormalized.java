public void testReset() throws java.lang.Exception {
    org.apache.lucene.analysis.ngram.EdgeNGram2TokenFilter filter = new org.apache.lucene.analysis.ngram.EdgeNGram2TokenFilter(tokenizer, EdgeNGram2TokenFilter.Side.FRONT, 1, 3);
    assertTokenStreamContents(filter, new java.lang.String[]{ "a", "ab", "abc" }, new int[]{ 0, 0, 0 }, new int[]{ 1, 2, 3 }, new int[]{ 1, 1, 1 });
    java.lang.String _CVAR1 = "abcde";
    org.apache.lucene.analysis.ngram.EdgeNGram2TokenFilterTest _CVAR0 = TEST_VERSION_CURRENT;
    java.io.StringReader _CVAR2 = new java.io.StringReader(_CVAR1);
    org.apache.lucene.analysis.WhitespaceTokenizer tokenizer = new org.apache.lucene.analysis.WhitespaceTokenizer(_CVAR0, _CVAR2);
    java.lang.String _CVAR4 = "ABCDE";
    org.apache.lucene.analysis.WhitespaceTokenizer _CVAR3 = tokenizer;
    java.io.StringReader _CVAR5 = new java.io.StringReader(_CVAR4);
    _CVAR3.reset(_CVAR5);
    assertTokenStreamContents(filter, new java.lang.String[]{ "A", "AB", "ABC" }, new int[]{ 0, 0, 0 }, new int[]{ 1, 2, 3 }, new int[]{ 1, 1, 1 });
}