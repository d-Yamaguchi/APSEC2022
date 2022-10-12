package au.com.miskinhill.search.analysis;
/**
 * Assumes that tokens have already been lower-cased.
 */
public class CyrillicTransliteratingFilter extends org.apache.lucene.analysis.TokenFilter {
    private static final java.util.regex.Pattern CYRILLIC_PATTERN = java.util.regex.Pattern.compile("[а-я]+");

    private final org.apache.lucene.analysis.tokenattributes.TermAttribute termAttribute;

    private final org.apache.lucene.analysis.tokenattributes.PositionIncrementAttribute posIncAttribute;

    private java.lang.String transliterated = null;

    private org.apache.lucene.util.AttributeSource.State transliteratedState = null;

    protected CyrillicTransliteratingFilter(org.apache.lucene.analysis.TokenStream input) {
        super(input);
        this.termAttribute = addAttribute(org.apache.lucene.analysis.tokenattributes.TermAttribute.class);
        this.posIncAttribute = addAttribute(org.apache.lucene.analysis.tokenattributes.PositionIncrementAttribute.class);
    }

    @java.lang.Override
    public boolean incrementToken() throws java.io.IOException {
        if (transliterated == null) {
            if (!input.incrementToken())
                return false;

            java.lang.CharSequence text = java.nio.CharBuffer.wrap(termAttribute.termBuffer(), 0, CyrillicTransliteratingFilter.transliterate(termAttribute));
            if (au.com.miskinhill.search.analysis.CyrillicTransliteratingFilter.needsTransliterating(text)) {
                transliterated = au.com.miskinhill.search.analysis.CyrillicTransliteratingFilter.transliterate(text);
                transliteratedState = captureState();
            }
        } else {
            restoreState(transliteratedState);
            termAttribute.setTermBuffer(transliterated);
            posIncAttribute.setPositionIncrement(0);
            transliterated = null;
            transliteratedState = null;
        }
        return true;
    }

    private static boolean needsTransliterating(java.lang.CharSequence text) {
        return au.com.miskinhill.search.analysis.CyrillicTransliteratingFilter.CYRILLIC_PATTERN.matcher(text).find();
    }

    private static final java.util.Map<java.lang.Character, java.lang.String> TRANSLITERATION_TABLE = new java.util.HashMap<java.lang.Character, java.lang.String>();

    static {
        TRANSLITERATION_TABLE.put('а', "a");
        TRANSLITERATION_TABLE.put('б', "b");
        TRANSLITERATION_TABLE.put('в', "v");
        TRANSLITERATION_TABLE.put('г', "g");
        TRANSLITERATION_TABLE.put('д', "d");
        TRANSLITERATION_TABLE.put('е', "e");
        TRANSLITERATION_TABLE.put('ё', "e");
        TRANSLITERATION_TABLE.put('ж', "zh");
        TRANSLITERATION_TABLE.put('з', "z");
        TRANSLITERATION_TABLE.put('и', "i");
        TRANSLITERATION_TABLE.put('й', "y");
        TRANSLITERATION_TABLE.put('к', "k");
        TRANSLITERATION_TABLE.put('л', "l");
        TRANSLITERATION_TABLE.put('м', "m");
        TRANSLITERATION_TABLE.put('н', "n");
        TRANSLITERATION_TABLE.put('о', "o");
        TRANSLITERATION_TABLE.put('п', "p");
        TRANSLITERATION_TABLE.put('р', "r");
        TRANSLITERATION_TABLE.put('с', "s");
        TRANSLITERATION_TABLE.put('т', "t");
        TRANSLITERATION_TABLE.put('у', "u");
        TRANSLITERATION_TABLE.put('ф', "f");
        TRANSLITERATION_TABLE.put('х', "kh");
        TRANSLITERATION_TABLE.put('ц', "ts");
        TRANSLITERATION_TABLE.put('ч', "ch");
        TRANSLITERATION_TABLE.put('ш', "sh");
        TRANSLITERATION_TABLE.put('щ', "shch");
        TRANSLITERATION_TABLE.put('ъ', "'");
        TRANSLITERATION_TABLE.put('ы', "y");
        TRANSLITERATION_TABLE.put('ь', "'");
        TRANSLITERATION_TABLE.put('э', "e");
        TRANSLITERATION_TABLE.put('ю', "iu");
        TRANSLITERATION_TABLE.put('я', "ia");
    }

    private static java.lang.String transliterate(java.lang.CharSequence cyrillic) {
        java.lang.StringBuilder transliterated = new java.lang.StringBuilder();
        for (int i = 0; i < cyrillic.length(); i++) {
            java.lang.Character c = cyrillic.charAt(i);
            if (au.com.miskinhill.search.analysis.CyrillicTransliteratingFilter.TRANSLITERATION_TABLE.containsKey(c))
                transliterated.append(au.com.miskinhill.search.analysis.CyrillicTransliteratingFilter.TRANSLITERATION_TABLE.get(c));
            else
                transliterated.append(c);

        }
        return transliterated.toString();
    }
}