@org.junit.Test
public void shouldTransliterateCyrillicTokens() throws java.io.IOException {
    org.apache.lucene.analysis.Token igraCyrillic = new org.apache.lucene.analysis.Token();
    igraCyrillic.setTermBuffer("игра");
    igraCyrillic.setStartOffset(1);
    igraCyrillic.setEndOffset(4);
    igraLatin.setTermBuffer("igra");
    igraLatin.setStartOffset(1);
    igraLatin.setEndOffset(4);
    org.apache.lucene.analysis.Token igraLatin = new org.apache.lucene.analysis.Token();
    org.apache.lucene.analysis.Token _CVAR0 = igraLatin;
    int _CVAR1 = 0;
    _CVAR0.setPositionIncrement(_CVAR1);
    Assert.assertThat(filter(java.util.Arrays.asList(igraCyrillic)), CoreMatchers.equalTo(java.util.Arrays.asList(igraCyrillic, igraLatin)));
}