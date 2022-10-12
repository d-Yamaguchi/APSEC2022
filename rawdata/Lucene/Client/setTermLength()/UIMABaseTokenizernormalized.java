@java.lang.Override
public boolean incrementToken() throws java.io.IOException {
    if (iterator == null) {
        try {
            analyzeText(input, descriptorPath);
        } catch (java.lang.Exception e) {
            throw new java.io.IOException(e);
        }
    }
    if (iterator.hasNext()) {
        termAttr.setTermBuffer(next.getCoveredText());
        org.apache.uima.cas.FSIterator<org.apache.uima.cas.text.AnnotationFS> _CVAR1 = iterator;
        org.apache.uima.cas.text.AnnotationFS next = _CVAR1.next();
        org.apache.uima.cas.text.AnnotationFS _CVAR2 = next;
         _CVAR3 = _CVAR2.getCoveredText();
        org.apache.lucene.analysis.tokenattributes.TermAttribute _CVAR0 = termAttr;
         _CVAR4 = _CVAR3.length();
        _CVAR0.setTermLength(_CVAR4);
        offsetAttr.setOffset(next.getBegin(), next.getEnd());
        return true;
    } else {
        iterator = null;
        return false;
    }
}