package com.github.sedtum.lucene;
public class UIMABasePayloadsFilter extends org.apache.lucene.analysis.TokenFilter {
    private org.apache.lucene.analysis.tokenattributes.TermAttribute termAttr;

    private org.apache.lucene.analysis.tokenattributes.PayloadAttribute payloadAttr;

    private org.apache.lucene.index.Payload uimaPayload;

    private org.apache.lucene.analysis.tokenattributes.TypeAttribute typeAttr;

    protected UIMABasePayloadsFilter(org.apache.lucene.analysis.TokenStream input) {
        super(input);
        payloadAttr = ((org.apache.lucene.analysis.tokenattributes.PayloadAttribute) (addAttribute(org.apache.lucene.analysis.tokenattributes.PayloadAttribute.class)));
        termAttr = ((org.apache.lucene.analysis.tokenattributes.TermAttribute) (addAttribute(org.apache.lucene.analysis.tokenattributes.TermAttribute.class)));
        typeAttr = ((org.apache.lucene.analysis.tokenattributes.TypeAttribute) (addAttribute(org.apache.lucene.analysis.tokenattributes.TypeAttribute.class)));
    }

    public final boolean incrementToken() throws java.io.IOException {
        if (input.incrementToken()) {
            if () {
                payloadAttr.setPayload(null);
            } else {
                payloadAttr.setPayload(null);
            }
            return true;
        } else
            return false;

    }
}