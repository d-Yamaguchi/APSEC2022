package at.ac.univie.mminf.luceneSKOS.analysis;
import at.ac.univie.mminf.luceneSKOS.analysis.tokenattributes.SKOSTypeAttribute.SKOSType;
import at.ac.univie.mminf.luceneSKOS.skos.SKOSEngine;
import at.ac.univie.mminf.luceneSKOS.skos.SKOSEngineFactory;
import org.apache.solr.analysis.RemoveDuplicatesTokenFilter;
/**
 * An analyzer for expanding fields that contain either (i) URI references to
 * SKOS concepts OR (ii) SKOS concept prefLabels as values.
 */
public class SKOSAnalyzer extends org.apache.lucene.analysis.StopwordAnalyzerBase {
    /**
     * The supported expansion types
     */
    public enum ExpansionType {

        URI,
        LABEL;}

    /**
     * Default expansion type
     */
    public static final at.ac.univie.mminf.luceneSKOS.analysis.SKOSAnalyzer.ExpansionType DEFAULT_EXPANSION_TYPE = at.ac.univie.mminf.luceneSKOS.analysis.SKOSAnalyzer.ExpansionType.LABEL;

    private at.ac.univie.mminf.luceneSKOS.analysis.SKOSAnalyzer.ExpansionType expansionType = at.ac.univie.mminf.luceneSKOS.analysis.SKOSAnalyzer.DEFAULT_EXPANSION_TYPE;

    /**
     * Default skos types to expand to
     */
    public static final at.ac.univie.mminf.luceneSKOS.analysis.tokenattributes.SKOSTypeAttribute.SKOSType[] DEFAULT_SKOS_TYPES = new at.ac.univie.mminf.luceneSKOS.analysis.tokenattributes.SKOSTypeAttribute.SKOSType[]{ at.ac.univie.mminf.luceneSKOS.analysis.tokenattributes.SKOSTypeAttribute.SKOSType.PREF, at.ac.univie.mminf.luceneSKOS.analysis.tokenattributes.SKOSTypeAttribute.SKOSType.ALT, at.ac.univie.mminf.luceneSKOS.analysis.tokenattributes.SKOSTypeAttribute.SKOSType.BROADER, at.ac.univie.mminf.luceneSKOS.analysis.tokenattributes.SKOSTypeAttribute.SKOSType.BROADERTRANSITIVE, at.ac.univie.mminf.luceneSKOS.analysis.tokenattributes.SKOSTypeAttribute.SKOSType.NARROWER, at.ac.univie.mminf.luceneSKOS.analysis.tokenattributes.SKOSTypeAttribute.SKOSType.NARROWERTRANSITIVE };

    private at.ac.univie.mminf.luceneSKOS.analysis.tokenattributes.SKOSTypeAttribute.SKOSType[] types = at.ac.univie.mminf.luceneSKOS.analysis.SKOSAnalyzer.DEFAULT_SKOS_TYPES;

    /**
     * A SKOS Engine instance
     */
    private at.ac.univie.mminf.luceneSKOS.skos.SKOSEngine skosEngine;

    /**
     * The size of the buffer used for multi-term prediction
     */
    private int bufferSize = SKOSLabelFilter.DEFAULT_BUFFER_SIZE;

    /**
     * Default maximum allowed token length
     */
    public static final int DEFAULT_MAX_TOKEN_LENGTH = 255;

    private int maxTokenLength = at.ac.univie.mminf.luceneSKOS.analysis.SKOSAnalyzer.DEFAULT_MAX_TOKEN_LENGTH;

    /**
     * An unmodifiable set containing some common English words that are usually
     * not useful for searching.
     */
    public static final java.util.Set<?> STOP_WORDS_SET = org.apache.lucene.analysis.StopAnalyzer.ENGLISH_STOP_WORDS_SET;

    public SKOSAnalyzer(org.apache.lucene.util.Version matchVersion, java.util.Set<?> stopWords, at.ac.univie.mminf.luceneSKOS.skos.SKOSEngine skosEngine, at.ac.univie.mminf.luceneSKOS.analysis.SKOSAnalyzer.ExpansionType expansionType) {
        super(matchVersion, stopWords);
        this.skosEngine = skosEngine;
        this.expansionType = expansionType;
    }

    public SKOSAnalyzer(org.apache.lucene.util.Version matchVersion, at.ac.univie.mminf.luceneSKOS.skos.SKOSEngine skosEngine, at.ac.univie.mminf.luceneSKOS.analysis.SKOSAnalyzer.ExpansionType expansionType) {
        this(matchVersion, at.ac.univie.mminf.luceneSKOS.analysis.SKOSAnalyzer.STOP_WORDS_SET, skosEngine, expansionType);
    }

    public SKOSAnalyzer(org.apache.lucene.util.Version matchVersion, java.io.Reader stopwords, at.ac.univie.mminf.luceneSKOS.skos.SKOSEngine skosEngine, at.ac.univie.mminf.luceneSKOS.analysis.SKOSAnalyzer.ExpansionType expansionType) throws java.io.IOException {
        this(matchVersion, org.apache.lucene.analysis.StopwordAnalyzerBase.loadStopwordSet(stopwords, matchVersion), skosEngine, expansionType);
    }

    public SKOSAnalyzer(org.apache.lucene.util.Version matchVersion, java.util.Set<?> stopWords, java.lang.String skosFile, at.ac.univie.mminf.luceneSKOS.analysis.SKOSAnalyzer.ExpansionType expansionType, int bufferSize, java.lang.String... languages) throws java.io.IOException {
        super(matchVersion, stopWords);
        this.skosEngine = at.ac.univie.mminf.luceneSKOS.skos.SKOSEngineFactory.getSKOSEngine(matchVersion, skosFile, languages);
        this.expansionType = expansionType;
        this.bufferSize = bufferSize;
    }

    public SKOSAnalyzer(org.apache.lucene.util.Version matchVersion, java.lang.String skosFile, at.ac.univie.mminf.luceneSKOS.analysis.SKOSAnalyzer.ExpansionType expansionType, int bufferSize, java.lang.String... languages) throws java.io.IOException {
        this(matchVersion, at.ac.univie.mminf.luceneSKOS.analysis.SKOSAnalyzer.STOP_WORDS_SET, skosFile, expansionType, bufferSize, languages);
    }

    public SKOSAnalyzer(org.apache.lucene.util.Version matchVersion, java.lang.String skosFile, at.ac.univie.mminf.luceneSKOS.analysis.SKOSAnalyzer.ExpansionType expansionType, int bufferSize) throws java.io.IOException {
        this(matchVersion, skosFile, expansionType, bufferSize, ((java.lang.String[]) (null)));
    }

    public SKOSAnalyzer(org.apache.lucene.util.Version matchVersion, java.lang.String skosFile, at.ac.univie.mminf.luceneSKOS.analysis.SKOSAnalyzer.ExpansionType expansionType) throws java.io.IOException {
        this(matchVersion, skosFile, expansionType, SKOSLabelFilter.DEFAULT_BUFFER_SIZE);
    }

    public SKOSAnalyzer(org.apache.lucene.util.Version matchVersion, java.io.Reader stopwords, java.lang.String skosFile, at.ac.univie.mminf.luceneSKOS.analysis.SKOSAnalyzer.ExpansionType expansionType, int bufferSize, java.lang.String... languages) throws java.io.IOException {
        this(matchVersion, org.apache.lucene.analysis.StopwordAnalyzerBase.loadStopwordSet(stopwords, matchVersion), skosFile, expansionType, bufferSize, languages);
    }

    public at.ac.univie.mminf.luceneSKOS.analysis.tokenattributes.SKOSTypeAttribute.SKOSType[] getTypes() {
        return types;
    }

    public void setTypes(at.ac.univie.mminf.luceneSKOS.analysis.tokenattributes.SKOSTypeAttribute.SKOSType... types) {
        this.types = types;
    }

    /**
     * Set maximum allowed token length. If a token is seen that exceeds this
     * length then it is discarded. This setting only takes effect the next time
     * tokenStream or tokenStream is called.
     */
    public void setMaxTokenLength(int length) {
        maxTokenLength = length;
    }

    /**
     *
     *
     * @see #setMaxTokenLength
     */
    public int getMaxTokenLength() {
        return maxTokenLength;
    }

    @java.lang.Override
    protected org.apache.lucene.analysis.ReusableAnalyzerBase.TokenStreamComponents createComponents(java.lang.String fileName, java.io.Reader reader) {
        if (expansionType.equals(at.ac.univie.mminf.luceneSKOS.analysis.SKOSAnalyzer.ExpansionType.URI)) {
            KeywordTokenizer src = new org.apache.lucene.analysis.core.KeywordTokenizer(reader);
            org.apache.lucene.analysis.TokenStream tok = new at.ac.univie.mminf.luceneSKOS.analysis.SKOSURIFilter(src, skosEngine, new org.apache.lucene.analysis.standard.StandardAnalyzer(matchVersion), types);
            tok = new org.apache.lucene.analysis.LowerCaseFilter(matchVersion, tok);
            return new org.apache.lucene.analysis.ReusableAnalyzerBase.TokenStreamComponents(src, tok);
        } else {
            final org.apache.lucene.analysis.standard.StandardTokenizer src = new org.apache.lucene.analysis.standard.StandardTokenizer(matchVersion, reader);
            src.setMaxTokenLength(maxTokenLength);
            org.apache.lucene.analysis.TokenStream tok = new org.apache.lucene.analysis.standard.StandardFilter(matchVersion, src);
            // prior to this we get the classic behavior, standardfilter does it for
            // us.
            tok = new at.ac.univie.mminf.luceneSKOS.analysis.SKOSLabelFilter(tok, skosEngine, new org.apache.lucene.analysis.standard.StandardAnalyzer(matchVersion), bufferSize, types);
            tok = new org.apache.lucene.analysis.LowerCaseFilter(matchVersion, tok);
            tok = new org.apache.lucene.analysis.StopFilter(matchVersion, tok, stopwords);
            tok = new org.apache.solr.analysis.RemoveDuplicatesTokenFilter(tok);
            return __SmPLUnsupported__(0);
        }
    }
}