@org.junit.Test
public void testAnalysis() throws java.lang.Exception {
    org.apache.mahout.text.MailArchivesClusteringAnalyzer analyzer = new org.apache.mahout.text.MailArchivesClusteringAnalyzer();
    java.lang.String text = "A test message\n";
    text += "atokenthatistoolongtobeusefulforclustertextanalysis\n";
    text += "Mahout is a scalable, machine-learning LIBRARY\n";
    text += "we\'ve added some additional stopwords such as html, mailto, regards\t";
    text += "apache_hadoop provides the foundation for scalability\n";
    text += "www.nabble.com general-help@incubator.apache.org\n";
    text += "public void int protected package";
    java.io.StringReader reader = new java.io.StringReader(text);
    // if you change the text above, then you may need to change this as well
    // order matters too
    java.lang.String[] expectedTokens = new java.lang.String[]{ "test", "mahout", "scalabl", "machin", "learn", "librari", "weve", "ad", "stopword", "apach", "hadoop", "provid", "foundat", "scalabl" };
    org.apache.lucene.analysis.TokenStream tokenStream = analyzer.tokenStream("test", reader);
    assertNotNull(tokenStream);
    org.apache.lucene.analysis.tokenattributes.TermAttribute termAtt = tokenStream.addAttribute(org.apache.lucene.analysis.tokenattributes.TermAttribute.class);
    int e = -1;
    while (tokenStream.incrementToken()) {
        assertEquals(expectedTokens[++e], termAtt.term());
    } 
}