@java.lang.Override
public org.apache.lucene.analysis.TokenStream tokenStream(java.lang.String field, final java.io.Reader reader) {
    if (!isTextField(field)) {
        org.apache.lucene.analysis.CharTokenizer _CVAR0 = new org.apache.lucene.analysis.CharTokenizer(reader) {
            @java.lang.Override
            protected boolean isTokenChar(char c) {
                return java.lang.Character.isLetterOrDigit(c);
            }

            @java.lang.Override
            protected char normalize(char c) {
                return java.lang.Character.toLowerCase(c);
            }
        };
        return _CVAR0;
    } else {
        return org.apache.maven.index.context.NexusLegacyAnalyzer.DEFAULT_ANALYZER.tokenStream(field, reader);
    }
}