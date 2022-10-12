@java.lang.Override
public org.apache.lucene.analysis.TokenStream create(org.apache.lucene.analysis.TokenStream tokenStream) {
    org.apache.lucene.analysis.TokenStream _CVAR0 = tokenStream;
    org.apache.lucene.analysis.util.CharArraySet _CVAR1 = exclusions;
    org.apache.lucene.analysis.miscellaneous.KeywordMarkerFilter _CVAR2 = new org.apache.lucene.analysis.miscellaneous.KeywordMarkerFilter(_CVAR0, _CVAR1);
    org.apache.lucene.analysis.de.GermanStemFilter _CVAR3 = new org.apache.lucene.analysis.de.GermanStemFilter(_CVAR2);
    return _CVAR3;
}