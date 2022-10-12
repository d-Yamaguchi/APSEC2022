@java.lang.Override
protected org.apache.lucene.analysis.ReusableAnalyzerBase.TokenStreamComponents createComponents(java.lang.String fieldName, java.io.Reader reader) {
    java.io.Reader _CVAR0 = reader;
    org.apache.lucene.analysis.Tokenizer tokenizer = new org.apache.lucene.analysis.KeywordTokenizer(_CVAR0);
    return new org.apache.lucene.analysis.ReusableAnalyzerBase.TokenStreamComponents(tokenizer, new org.apache.lucene.analysis.miscellaneous.WordDelimiterFilter2(tokenizer, flags, protectedWords));
}