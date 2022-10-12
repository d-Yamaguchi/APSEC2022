public final boolean incrementToken() throws java.io.IOException {
    if (input.incrementToken()) {
        org.apache.lucene.analysis.tokenattributes.TermAttribute _CVAR0 = termAttr;
        java.lang.String _CVAR1 = _CVAR0.term();
        java.lang.String _CVAR2 = "warning";
        boolean _CVAR3 = _CVAR1.equals(_CVAR2);
        if () {
            payloadAttr.setPayload(null);
        } else {
            payloadAttr.setPayload(null);
        }
        return true;
    } else {
        return false;
    }
}