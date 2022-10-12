@java.lang.Override
protected org.apache.lucene.analysis.ReusableAnalyzerBase.TokenStreamComponents createComponents(java.lang.String fileName, java.io.Reader reader) {
    if (expansionType.equals(at.ac.univie.mminf.luceneSKOS.analysis.SKOSAnalyzer.ExpansionType.URI)) {
        java.io.Reader _CVAR0 = reader;
        final org.apache.lucene.analysis.KeywordTokenizer src = new org.apache.lucene.analysis.KeywordTokenizer(_CVAR0);
        org.apache.lucene.analysis.TokenStream tok = new at.ac.univie.mminf.luceneSKOS.analysis.SKOSURIFilter(src, skosEngine, new org.apache.lucene.analysis.standard.StandardAnalyzer(matchVersion), types);
        tok = new org.apache.lucene.analysis.LowerCaseFilter(matchVersion, tok);
        return new org.apache.lucene.analysis.ReusableAnalyzerBase.TokenStreamComponents(src, tok);
    } else {
        final org.apache.lucene.analysis.standard.StandardTokenizer src = new org.apache.lucene.analysis.standard.StandardTokenizer(matchVersion, reader);
        src.setMaxTokenLength(maxTokenLength);
        org.apache.lucene.analysis.TokenStream tok = new org.apache.lucene.analysis.standard.StandardFilter(matchVersion, src);
        // prior to this we get the classic behavior, standardfilter does it for
        // us.
        tok = new at.ac.univie.mminf.luceneSKOS.analysis.SKOSLabelFilter(tok, skosEngine, new org.apache.lucene.analysis.standard.StandardAnalyzer(matchVersion), bufferSize, types);
        tok = new org.apache.lucene.analysis.LowerCaseFilter(matchVersion, tok);
        tok = new org.apache.lucene.analysis.StopFilter(matchVersion, tok, stopwords);
        tok = new org.apache.solr.analysis.RemoveDuplicatesTokenFilter(tok);
        return new org.apache.lucene.analysis.ReusableAnalyzerBase.TokenStreamComponents(src, tok) {
            @java.lang.Override
            protected boolean reset(final java.io.Reader reader) throws java.io.IOException {
                src.setMaxTokenLength(maxTokenLength);
                return super.reset(reader);
            }
        };
    }
}