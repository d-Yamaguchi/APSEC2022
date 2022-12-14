package au.com.miskinhill.search.analysis;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;
import java.util.Stack;

import javax.xml.XMLConstants;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

import org.apache.lucene.analysis.TokenStream;

public class XMLTokenizer extends TokenStream {

    private static final XMLInputFactory factory = XMLInputFactory.newInstance();
    static {
        factory.setProperty("javax.xml.stream.isCoalescing", true);
    }
    public static XMLInputFactory getXMLInputFactory() {
        return factory;
    }

    private static final String XHTML_NS_URI = "http://www.w3.org/1999/xhtml";

    private static class LangStack extends Stack<String> {
        private static final long serialVersionUID = 7020093255092191463L;
        private String current = null;

        public LangStack() {
        }

        @Override
        public String push(String item) {
            if (item != null)
                current = item;
            super.push(current);
            return item;
        }

        @Override
        public synchronized String pop() {
            String top = super.pop();
            current = empty() ? null : peek();
            return top;
        }

        public String getCurrent() {
            return current;
        }
    }

    private XMLEventReader r;
    private PerLanguageAnalyzerMap analyzerMap;
    private LangStack langs = new LangStack();

    /** Current delegate in use (null if none currently) */
    private TokenStream delegate = null;

    public XMLTokenizer(Reader reader, PerLanguageAnalyzerMap analyzerMap) throws XMLStreamException {
        this.analyzerMap = analyzerMap;
        r = factory.createXMLEventReader(reader);
    }

    public XMLTokenizer(InputStream in, PerLanguageAnalyzerMap analyzerMap) throws XMLStreamException {
        this.analyzerMap = analyzerMap;
        r = factory.createXMLEventReader(in);
    }

    @Override
    public boolean incrementToken() throws IOException {
        clearAttributes();

        // first try our current string delegate, if we have one
        if (delegate != null) {
            if (delegate.incrementToken())
                return true;
            else
                delegate = null;
        }

        while (r.hasNext()) {
            XMLEvent event;
            try {
                event = r.nextEvent();
            } catch (XMLStreamException e) {
                throw new IOException(e);
            }
            switch (event.getEventType()) {
                case XMLStreamConstants.START_ELEMENT:
                    StartElement se = event.asStartElement();
                    langs.push(getLang(se));
                    break;
                case XMLStreamConstants.CHARACTERS:
                    Characters chars = event.asCharacters();
                    if (chars.isWhiteSpace())
                        break; // don't care
                    Analyzer analyzer = analyzerMap.getAnalyzer(langs.getCurrent());
                    delegate = new OffsetTokenFilter(analyzer.applyFilters(analyzer.tokenizer(this, new StringReader(
                            chars.getData()))), event.getLocation().getCharacterOffset());
                    if (delegate.incrementToken())
                        return true;
                    else
                        delegate = null;
                    break;
                case XMLStreamConstants.END_ELEMENT:
                    langs.pop();
                    break;
            }
        }
        return false;
    }

    private String getLang(StartElement se) {
        // xml:lang takes precedence
        QName xmlLangQName = new QName(
                se.getNamespaceURI("") == XMLConstants.XML_NS_URI ? "" : XMLConstants.XML_NS_URI, "lang");
        Attribute xmlLang = se.getAttributeByName(xmlLangQName);
        if (xmlLang != null)
            return xmlLang.getValue();

        QName xhtmlLangQName = new QName(se.getNamespaceURI("") == XHTML_NS_URI ? "" : XHTML_NS_URI, "lang");
        Attribute xhtmlLang = se.getAttributeByName(xhtmlLangQName);
        if (xhtmlLang != null)
            return xhtmlLang.getValue();

        return null;
    }

}
