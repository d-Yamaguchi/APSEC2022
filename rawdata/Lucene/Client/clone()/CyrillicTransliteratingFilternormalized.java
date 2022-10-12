@java.lang.Override
public org.apache.lucene.analysis.Token next(org.apache.lucene.analysis.Token reusableToken) throws java.io.IOException {
    org.apache.lucene.analysis.Token tok;
    if (transliterated == null) {
        tok = input.next(reusableToken);
        if (tok == null) {
            return null;
        }
        if (au.com.miskinhill.search.analysis.CyrillicTransliteratingFilter.needsTransliterating(tok.term())) {
            org.apache.lucene.analysis.Token _CVAR0 = tok;
            java.lang.Object _CVAR1 = ((org.apache.lucene.analysis.Token) (_CVAR0.clone()));
            transliterated = _CVAR1;
            transliterated.setTermBuffer(au.com.miskinhill.search.analysis.CyrillicTransliteratingFilter.transliterate(transliterated.term()));
            transliterated.setPositionIncrement(0);
        }
    } else {
        tok = transliterated;
        transliterated = null;
    }
    return tok;
}