@org.junit.Test
public void shouldPassOnTokensWithoutCyrillicUntouched() throws java.io.IOException {
    asdf.setTermBuffer("asdf");
    org.apache.lucene.analysis.Token asdf = new org.apache.lucene.analysis.Token();
    org.apache.lucene.analysis.Token _CVAR0 = asdf;
    int _CVAR1 = 1;
    _CVAR0.setStartOffset(_CVAR1);
    asdf.setEndOffset(4);
    Assert.assertThat(filter(java.util.Arrays.asList(asdf)), CoreMatchers.equalTo(java.util.Arrays.asList(asdf)));
}