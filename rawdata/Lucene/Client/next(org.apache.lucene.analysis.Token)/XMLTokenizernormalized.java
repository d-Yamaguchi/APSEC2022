@java.lang.Override
public org.apache.lucene.analysis.Token next(org.apache.lucene.analysis.Token reusableToken) throws java.io.IOException {
    // first try our current string delegate, if we have one
    if (delegate != null) {
        org.apache.lucene.analysis.TokenStream _CVAR0 = delegate;
        org.apache.lucene.analysis.Token _CVAR1 = reusableToken;
        org.apache.lucene.analysis.Token retval = _CVAR0.next(_CVAR1);
        if (retval != null) {
            return retval;
        } else {
            delegate = null;
        }
    }
    while (r.hasNext()) {
        javax.xml.stream.events.XMLEvent event;
        try {
            event = r.nextEvent();
        } catch (javax.xml.stream.XMLStreamException e) {
            throw new java.io.IOException(e);
        }
        switch (event.getEventType()) {
            case javax.xml.stream.XMLStreamConstants.START_ELEMENT :
                javax.xml.stream.events.StartElement se = event.asStartElement();
                langs.push(getLang(se));
                break;
            case javax.xml.stream.XMLStreamConstants.CHARACTERS :
                javax.xml.stream.events.Characters chars = event.asCharacters();
                if (chars.isWhiteSpace()) {
                    break;
                }// don't care

                delegate = new au.com.miskinhill.search.analysis.OffsetTokenFilter(analyzer.tokenStream(langs.getCurrent(), null, new java.io.StringReader(chars.getData())), event.getLocation().getCharacterOffset());
                org.apache.lucene.analysis.Token retval = delegate.next(reusableToken);
                if (retval != null) {
                    return retval;
                } else {
                    delegate = null;
                }
                break;
            case javax.xml.stream.XMLStreamConstants.END_ELEMENT :
                langs.pop();
                break;
        }
    } 
    return null;
}