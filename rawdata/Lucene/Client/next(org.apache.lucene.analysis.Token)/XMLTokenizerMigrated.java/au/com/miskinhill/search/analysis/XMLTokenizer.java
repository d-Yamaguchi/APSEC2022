package au.com.miskinhill.search.analysis;
public class XMLTokenizer extends org.apache.lucene.analysis.TokenStream {
    private static final javax.xml.stream.XMLInputFactory factory = javax.xml.stream.XMLInputFactory.newInstance();

    static {
        factory.setProperty("javax.xml.stream.isCoalescing", true);
    }

    public static javax.xml.stream.XMLInputFactory getXMLInputFactory() {
        return au.com.miskinhill.search.analysis.XMLTokenizer.factory;
    }

    private static final java.lang.String XHTML_NS_URI = "http://www.w3.org/1999/xhtml";

    private static class LangStack extends java.util.Stack<java.lang.String> {
        private static final long serialVersionUID = 7020093255092191463L;

        private java.lang.String current = null;

        @java.lang.Override
        public java.lang.String push(java.lang.String item) {
            if (item != null)
                current = item;

            super.push(current);
            return item;
        }

        @java.lang.Override
        public synchronized java.lang.String pop() {
            java.lang.String top = super.pop();
            current = (empty()) ? null : peek();
            return top;
        }

        public java.lang.String getCurrent() {
            return current;
        }
    }

    private javax.xml.stream.XMLEventReader r;

    private au.com.miskinhill.search.analysis.PerLanguageAnalyzerWrapper analyzer;

    private au.com.miskinhill.search.analysis.XMLTokenizer.LangStack langs = new au.com.miskinhill.search.analysis.XMLTokenizer.LangStack();

    /**
     * Current delegate in use (null if none currently)
     */
    private org.apache.lucene.analysis.TokenStream delegate = null;

    public XMLTokenizer(java.io.Reader reader, au.com.miskinhill.search.analysis.PerLanguageAnalyzerWrapper analyzer) throws javax.xml.stream.XMLStreamException {
        this.analyzer = analyzer;
        r = au.com.miskinhill.search.analysis.XMLTokenizer.factory.createXMLEventReader(reader);
    }

    public XMLTokenizer(java.io.InputStream in, au.com.miskinhill.search.analysis.PerLanguageAnalyzerWrapper analyzer) throws javax.xml.stream.XMLStreamException {
        this.analyzer = analyzer;
        r = au.com.miskinhill.search.analysis.XMLTokenizer.factory.createXMLEventReader(in);
    }

    @java.lang.Override
    public org.apache.lucene.analysis.Token next(org.apache.lucene.analysis.Token reusableToken) throws java.io.IOException {
        // first try our current string delegate, if we have one
        if (delegate != null) {
            Token retval = delegate.incrementToken();
            if (retval != null)
                return retval;
            else
                delegate = null;

        }
        __SmPLUnsupported__(0);
        return null;
    }

    private java.lang.String getLang(javax.xml.stream.events.StartElement se) {
        // xml:lang takes precedence
        javax.xml.namespace.QName xmlLangQName = new javax.xml.namespace.QName(se.getNamespaceURI("") == javax.xml.XMLConstants.XML_NS_URI ? "" : javax.xml.XMLConstants.XML_NS_URI, "lang");
        javax.xml.stream.events.Attribute xmlLang = se.getAttributeByName(xmlLangQName);
        if (xmlLang != null)
            return xmlLang.getValue();

        javax.xml.namespace.QName xhtmlLangQName = new javax.xml.namespace.QName(se.getNamespaceURI("") == au.com.miskinhill.search.analysis.XMLTokenizer.XHTML_NS_URI ? "" : au.com.miskinhill.search.analysis.XMLTokenizer.XHTML_NS_URI, "lang");
        javax.xml.stream.events.Attribute xhtmlLang = se.getAttributeByName(xhtmlLangQName);
        if (xhtmlLang != null)
            return xhtmlLang.getValue();

        return null;
    }
}