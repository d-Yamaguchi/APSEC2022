protected void runAndCompare(org.apache.maven.index.IndexerField indexerField, java.lang.String text, java.lang.String[] expected) throws java.io.IOException {
    org.apache.lucene.analysis.Tokenizer ts = ((org.apache.lucene.analysis.Tokenizer) (nexusAnalyzer.reusableTokenStream(indexerField.getKey(), new java.io.StringReader(text))));
    java.util.ArrayList<java.lang.String> tokenList = new java.util.ArrayList<java.lang.String>();
    if (!indexerField.isKeyword()) {
        while (ts.incrementToken()) {
            org.apache.lucene.analysis.tokenattributes.TermAttribute term = ts.getAttribute(org.apache.lucene.analysis.tokenattributes.TermAttribute.class);
            tokenList.add(term.term());
        } 
    } else {
        tokenList.add(text);
    }
    assertEquals("The result does not meet the expectations.", java.util.Arrays.asList(expected), tokenList);
}