@java.lang.Override
public final boolean incrementToken() throws java.io.IOException {
    // return the first alpha-numeric token between 2 and 40 length
    while (input.incrementToken()) {
        int length = termAtt.termLength();
        if ((length >= 2) && (length <= 28)) {
            char[] buf = termAtt.termBuffer();
            int at = 0;
            for (int c = 0; c < length; c++) {
                char ch = buf[c];
                if (ch != '\'') {
                    output[at++] = ch;
                }
            }
            java.lang.String term = new java.lang.String(output, 0, at);
            matcher.reset(term);
            if (matcher.matches() && (!term.startsWith("a0"))) {
                termAtt.setTermBuffer(term);
                return true;
            }
        }
    } 
    return false;
}