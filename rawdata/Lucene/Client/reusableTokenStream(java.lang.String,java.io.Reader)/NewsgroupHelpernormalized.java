public static void countWords(org.apache.lucene.analysis.Analyzer analyzer, java.util.Collection<java.lang.String> words, java.io.Reader in, com.google.common.collect.Multiset<java.lang.String> overallCounts) throws java.io.IOException {
    org.apache.lucene.analysis.Analyzer _CVAR0 = analyzer;
    java.lang.String _CVAR1 = "text";
    java.io.Reader _CVAR2 = in;
    org.apache.lucene.analysis.TokenStream ts = _CVAR0.reusableTokenStream(_CVAR1, _CVAR2);
    ts.addAttribute(org.apache.lucene.analysis.tokenattributes.CharTermAttribute.class);
    ts.reset();
    while (ts.incrementToken()) {
        java.lang.String s = ts.getAttribute(org.apache.lucene.analysis.tokenattributes.CharTermAttribute.class).toString();
        words.add(s);
    } 
    overallCounts.addAll(words);
}