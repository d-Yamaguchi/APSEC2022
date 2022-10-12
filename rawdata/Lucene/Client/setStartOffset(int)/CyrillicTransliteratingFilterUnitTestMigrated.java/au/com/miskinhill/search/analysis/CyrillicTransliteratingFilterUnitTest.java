package au.com.miskinhill.search.analysis;
import org.junit.Test;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
public class CyrillicTransliteratingFilterUnitTest {
    @org.junit.Test
    public void shouldPassOnTokensWithoutCyrillicUntouched() throws java.io.IOException {
        asdf.setTermBuffer("asdf");
        org.apache.lucene.analysis.Token asdf = new org.apache.lucene.analysis.Token();
        Token asdf = new org.apache.lucene.analysis.Token();
        TokenFilter filter = new CyrillicTransliteratingFilter(new au.com.miskinhill.search.analysis.CyrillicTransliteratingFilterUnitTest.FakeTokenStream(asdf));
        assertAttributes(filter, "asdf", 1, 4, 1);
        asdf.setEndOffset(4);
        Assert.assertThat(filter(java.util.Arrays.asList(asdf)), CoreMatchers.equalTo(java.util.Arrays.asList(asdf)));
    }

    @org.junit.Test
    public void shouldTransliterateCyrillicTokens() throws java.io.IOException {
        org.apache.lucene.analysis.Token igraCyrillic = new org.apache.lucene.analysis.Token();
        igraCyrillic.setTermBuffer("игра");
        igraCyrillic.setStartOffset(1);
        igraCyrillic.setEndOffset(4);
        org.apache.lucene.analysis.Token igraLatin = new org.apache.lucene.analysis.Token();
        igraLatin.setTermBuffer("igra");
        igraLatin.setStartOffset(1);
        igraLatin.setEndOffset(4);
        igraLatin.setPositionIncrement(0);
        Assert.assertThat(filter(java.util.Arrays.asList(igraCyrillic)), CoreMatchers.equalTo(java.util.Arrays.asList(igraCyrillic, igraLatin)));
    }

    @org.junit.Test
    public void shouldTransliterateTokensWithMixedLatinAndCyrillic() throws java.io.IOException {
        org.apache.lucene.analysis.Token mixed = new org.apache.lucene.analysis.Token();
        mixed.setTermBuffer("interнет");
        mixed.setStartOffset(1);
        mixed.setEndOffset(4);
        org.apache.lucene.analysis.Token latin = new org.apache.lucene.analysis.Token();
        latin.setTermBuffer("internet");
        latin.setStartOffset(1);
        latin.setEndOffset(4);
        latin.setPositionIncrement(0);
        Assert.assertThat(filter(java.util.Arrays.asList(mixed)), CoreMatchers.equalTo(java.util.Arrays.asList(mixed, latin)));
    }

    private java.util.List<org.apache.lucene.analysis.Token> filter(java.util.List<org.apache.lucene.analysis.Token> input) throws java.io.IOException {
        final java.util.Iterator<org.apache.lucene.analysis.Token> inputIt = input.iterator();
        org.apache.lucene.analysis.TokenStream inputStream = new org.apache.lucene.analysis.TokenStream() {
            @java.lang.Override
            public org.apache.lucene.analysis.Token next(org.apache.lucene.analysis.Token reusableToken) throws java.io.IOException {
                if (!inputIt.hasNext())
                    return null;
                else
                    return inputIt.next();

            }
        };
        au.com.miskinhill.search.analysis.CyrillicTransliteratingFilter filter = new au.com.miskinhill.search.analysis.CyrillicTransliteratingFilter(inputStream);
        java.util.List<org.apache.lucene.analysis.Token> output = new java.util.ArrayList<org.apache.lucene.analysis.Token>();
        while (true) {
            org.apache.lucene.analysis.Token next = filter.next(new org.apache.lucene.analysis.Token());
            if (next == null)
                break;

            output.add(next);
        } 
        return output;
    }
}