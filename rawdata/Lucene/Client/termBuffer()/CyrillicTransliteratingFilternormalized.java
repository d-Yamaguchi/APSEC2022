@java.lang.Override
public boolean incrementToken() throws java.io.IOException {
    if (transliterated == null) {
        if (!input.incrementToken()) {
            return false;
        }
        org.apache.lucene.analysis.tokenattributes.TermAttribute _CVAR0 = termAttribute;
        org.apache.lucene.analysis.tokenattributes.TermAttribute _CVAR3 = termAttribute;
        char[] _CVAR1 = _CVAR0.termBuffer();
        int _CVAR2 = 0;
        int _CVAR4 = _CVAR3.termLength();
        java.lang.CharSequence text = java.nio.CharBuffer.wrap(_CVAR1, _CVAR2, _CVAR4);
        if (au.com.miskinhill.search.analysis.CyrillicTransliteratingFilter.needsTransliterating(text)) {
            transliterated = au.com.miskinhill.search.analysis.CyrillicTransliteratingFilter.transliterate(text);
            transliteratedState = captureState();
        }
    } else {
        restoreState(transliteratedState);
        termAttribute.setTermBuffer(transliterated);
        posIncAttribute.setPositionIncrement(0);
        transliterated = null;
        transliteratedState = null;
    }
    return true;
}