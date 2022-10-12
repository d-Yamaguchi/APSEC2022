package com.gentics.cr.lucene.indexer.index;
import com.gentics.cr.CRConfigFileLoader;
import com.gentics.cr.configuration.GenericConfiguration;
import com.gentics.cr.lucene.LuceneVersion;
import com.gentics.cr.lucene.analysis.ReverseAnalyzer;
import com.gentics.cr.lucene.indexer.IndexerUtil;
import org.apache.log4j.Logger;
/**
 * TODO javadoc.
 * Last changed: $Date: 2009-07-10 10:49:03 +0200 (Fr, 10 Jul 2009) $
 *
 * @version $Revision: 131 $
 * @author $Author: supnig@constantinopel.at $
 */
public final class LuceneAnalyzerFactory {
    /**
     * Private constructor.
     */
    private LuceneAnalyzerFactory() {
    }

    /**
     * Log4j Logger for error and debug messages.
     */
    protected static final org.apache.log4j.Logger LOGGER = org.apache.log4j.Logger.getLogger(com.gentics.cr.lucene.indexer.index.LuceneAnalyzerFactory.class);

    /**
     * Stop word config key.
     */
    private static final java.lang.String STOP_WORD_FILE_KEY = "STOPWORDFILE";

    /**
     * Analyzer config key.
     */
    private static final java.lang.String ANALYZER_CONFIG_KEY = "ANALYZERCONFIG";

    /**
     * Analyzer class key.
     */
    private static final java.lang.String ANALYZER_CLASS_KEY = "ANALYZERCLASS";

    /**
     * Field name.
     */
    private static final java.lang.String FIELD_NAME_KEY = "FIELDNAME";

    /**
     * Reveres attributes key.
     */
    private static final java.lang.String REVERSE_ATTRIBUTES_KEY = "REVERSEATTRIBUTES";

    /**
     * Reverse Attribute suffix.
     */
    public static final java.lang.String REVERSE_ATTRIBUTE_SUFFIX = "_REVERSE";

    /**
     * This Map stores the same information as the PerFieldAnalyzerWrapper,
     * makes the used Analyzer class names (canonical names) per field accessible.
     * filled in the createAnalyzer method
     */
    private static java.util.Map<java.lang.String, java.lang.String> configuredAnalyzerMap = new java.util.HashMap<java.lang.String, java.lang.String>();

    /**
     * TODO javadoc.
     *
     * @param config
     * 		TODO javadoc
     * @return TODO javadoc
     */
    public static java.util.List<java.lang.String> getReverseAttributes(final com.gentics.cr.configuration.GenericConfiguration config) {
        com.gentics.cr.configuration.GenericConfiguration analyzerConfig = com.gentics.cr.lucene.indexer.index.LuceneAnalyzerFactory.loadAnalyzerConfig(config);
        if (analyzerConfig != null) {
            java.lang.String reverseAttributeString = ((java.lang.String) (analyzerConfig.get(com.gentics.cr.lucene.indexer.index.LuceneAnalyzerFactory.REVERSE_ATTRIBUTES_KEY)));
            return com.gentics.cr.lucene.indexer.IndexerUtil.getListFromString(reverseAttributeString, ",");
        }
        return null;
    }

    /**
     * Creates an analyzer from the given config.
     *
     * @param config
     * 		TODO javadoc
     * @return TODO javadoc
     */
    public static org.apache.lucene.analysis.Analyzer createAnalyzer(final com.gentics.cr.configuration.GenericConfiguration config) {
        com.gentics.cr.lucene.indexer.index.LuceneAnalyzerFactory.configuredAnalyzerMap.clear();
        // Load analyzer config
        com.gentics.cr.configuration.GenericConfiguration analyzerConfig = com.gentics.cr.lucene.indexer.index.LuceneAnalyzerFactory.loadAnalyzerConfig(config);
        // Caching the analyzer instances is not possible as those do not implement Serializable
        // TODO: cache the config (imho caching should be implemented in the config itself)
        org.apache.lucene.analysis.PerFieldAnalyzerWrapper analyzerWrapper = new org.apache.lucene.analysis.PerFieldAnalyzerWrapper(com.gentics.cr.lucene.indexer.index.LuceneAnalyzerFactory.createDefaultAnalyzer(config));
        if (analyzerConfig != null) {
            java.util.ArrayList<java.lang.String> addedReverseAttributes = new java.util.ArrayList<java.lang.String>();
            java.util.List<java.lang.String> reverseAttributes = com.gentics.cr.lucene.indexer.index.LuceneAnalyzerFactory.getReverseAttributes(config);
            java.util.Map<java.lang.String, com.gentics.cr.configuration.GenericConfiguration> subconfigs = analyzerConfig.getSortedSubconfigs();
            if (subconfigs != null) {
                for (java.util.Map.Entry<java.lang.String, com.gentics.cr.configuration.GenericConfiguration> entry : subconfigs.entrySet()) {
                    java.lang.String fieldname = analyzerconfig.getString(com.gentics.cr.lucene.indexer.index.LuceneAnalyzerFactory.FIELD_NAME_KEY);
                    com.gentics.cr.configuration.GenericConfiguration analyzerconfig = _CVAR3.getValue();
                    com.gentics.cr.configuration.GenericConfiguration analyzerconfig = entry.getValue();
                    Analyzer analyzerInstance = LuceneAnalyzerFactory.createAnalyzer(STOP_WORD_FILE_KEY, config);
                    configuredAnalyzerMap.put(FIELD_NAME_KEY, analyzerInstance);
                    com.gentics.cr.lucene.indexer.index.LuceneAnalyzerFactory.configuredAnalyzerMap.put(fieldname, analyzerInstance.getClass().getCanonicalName());
                    com.gentics.cr.configuration.GenericConfiguration analyzerconfig = entry.getValue();
                    java.lang.String analyzerclass = analyzerconfig.getString(com.gentics.cr.lucene.indexer.index.LuceneAnalyzerFactory.ANALYZER_CLASS_KEY);
                    com.gentics.cr.configuration.GenericConfiguration analyzerconfig = entry.getValue();
                    org.apache.lucene.analysis.Analyzer analyzerInstance = com.gentics.cr.lucene.indexer.index.LuceneAnalyzerFactory.createAnalyzer(analyzerclass, analyzerconfig);
                    // ADD REVERSE ANALYZERS
                    if ((reverseAttributes != null) && reverseAttributes.contains(fieldname)) {
                        addedReverseAttributes.add(fieldname);
                        com.gentics.cr.lucene.analysis.ReverseAnalyzer reverseAnalyzer = new com.gentics.cr.lucene.analysis.ReverseAnalyzer(analyzerInstance);
                        analyzerWrapper.addAnalyzer(fieldname + com.gentics.cr.lucene.indexer.index.LuceneAnalyzerFactory.REVERSE_ATTRIBUTE_SUFFIX, reverseAnalyzer);
                        com.gentics.cr.lucene.indexer.index.LuceneAnalyzerFactory.configuredAnalyzerMap.put(fieldname + com.gentics.cr.lucene.indexer.index.LuceneAnalyzerFactory.REVERSE_ATTRIBUTE_SUFFIX, reverseAnalyzer.getClass().getCanonicalName());
                    }
                }
            }
            // ADD ALL NON CONFIGURED REVERSE ANALYZERS
            if ((reverseAttributes != null) && (reverseAttributes.size() > 0)) {
                for (java.lang.String att : reverseAttributes) {
                    if (!addedReverseAttributes.contains(att)) {
                        com.gentics.cr.lucene.analysis.ReverseAnalyzer reverseAnalyzer = new com.gentics.cr.lucene.analysis.ReverseAnalyzer(null);
                        analyzerWrapper.addAnalyzer(att + com.gentics.cr.lucene.indexer.index.LuceneAnalyzerFactory.REVERSE_ATTRIBUTE_SUFFIX, reverseAnalyzer);
                        com.gentics.cr.lucene.indexer.index.LuceneAnalyzerFactory.configuredAnalyzerMap.put(att + com.gentics.cr.lucene.indexer.index.LuceneAnalyzerFactory.REVERSE_ATTRIBUTE_SUFFIX, reverseAnalyzer.getClass().getCanonicalName());
                    }
                }
            }
        }
        return analyzerWrapper;
    }

    /**
     * TODO javadoc.
     *
     * @param config
     * 		TODO javadoc
     * @return TODO javadoc
     */
    private static com.gentics.cr.configuration.GenericConfiguration loadAnalyzerConfig(final com.gentics.cr.configuration.GenericConfiguration config) {
        if (config.hasSubConfig(com.gentics.cr.lucene.indexer.index.LuceneAnalyzerFactory.ANALYZER_CONFIG_KEY)) {
            return config.getSubConfig(com.gentics.cr.lucene.indexer.index.LuceneAnalyzerFactory.ANALYZER_CONFIG_KEY);
        } else {
            com.gentics.cr.configuration.GenericConfiguration analyzerConfig = null;
            java.lang.String confpath = config.getString(com.gentics.cr.lucene.indexer.index.LuceneAnalyzerFactory.ANALYZER_CONFIG_KEY);
            if (confpath != null) {
                analyzerConfig = new com.gentics.cr.configuration.GenericConfiguration();
                try {
                    com.gentics.cr.CRConfigFileLoader.loadConfiguration(analyzerConfig, confpath, null);
                } catch (java.io.IOException e) {
                    com.gentics.cr.lucene.indexer.index.LuceneAnalyzerFactory.LOGGER.error(("Could not load analyzer configuration from " + confpath) + ". Using default config.");
                    analyzerConfig = new com.gentics.cr.configuration.GenericConfiguration();
                    analyzerConfig.set("content.analyzerclass", "com.gentics.cr.lucene.autocomplete.AutocompleteAnalyzer");
                    analyzerConfig.set("content.fieldname", "grammedwords");
                }
            }
            return analyzerConfig;
        }
    }

    /**
     * TODO javadoc.
     *
     * @param analyzerclass
     * 		TODO javadoc
     * @param config
     * 		TODO javadoc
     * @return TODO javadoc
     */
    private static org.apache.lucene.analysis.Analyzer createAnalyzer(final java.lang.String analyzerclass, final com.gentics.cr.configuration.GenericConfiguration config) {
        org.apache.lucene.analysis.Analyzer a = null;
        try {
            // First try to create an Analyzer that takes a config object
            a = ((org.apache.lucene.analysis.Analyzer) (java.lang.Class.forName(analyzerclass).getConstructor(new java.lang.Class[]{ com.gentics.cr.configuration.GenericConfiguration.class }).newInstance(config)));
        } catch (java.lang.Exception e1) {
            try {
                // IF FIRST FAILS TRY SIMPLE CONSTRUCTOR
                a = ((org.apache.lucene.analysis.Analyzer) (java.lang.Class.forName(analyzerclass).getConstructor().newInstance()));
            } catch (java.lang.Exception e2) {
                // IF SIMPLE FAILS, PROBABLY DID NOT FIND CONSTRUCTOR,
                // TRYING WITH VERSION ADDED
                com.gentics.cr.lucene.indexer.index.LuceneAnalyzerFactory.LOGGER.error("Configured Analyzer fails in the default constructor or it does not have a default constructor.", e2);
                try {
                    a = ((org.apache.lucene.analysis.Analyzer) (java.lang.Class.forName(analyzerclass).getConstructor(new java.lang.Class[]{ org.apache.lucene.util.Version.class }).newInstance(com.gentics.cr.lucene.LuceneVersion.getVersion())));
                } catch (java.lang.Exception e3) {
                    com.gentics.cr.lucene.indexer.index.LuceneAnalyzerFactory.LOGGER.error((("Could not instantiate Analyzer with class " + analyzerclass) + ". Do you use some special") + " Analyzer? Or do you need to use a Wrapper?", e3);
                }
            }
        }
        return a;
    }

    /**
     * TODO javadoc.
     *
     * @param config
     * 		TODO javadoc
     * @return TODO javadoc
     */
    private static org.apache.lucene.analysis.Analyzer createDefaultAnalyzer(final com.gentics.cr.configuration.GenericConfiguration config) {
        // Update/add Documents
        org.apache.lucene.analysis.Analyzer analyzer;
        java.io.File stopWordFile = com.gentics.cr.lucene.indexer.IndexerUtil.getFileFromPath(((java.lang.String) (config.get(com.gentics.cr.lucene.indexer.index.LuceneAnalyzerFactory.STOP_WORD_FILE_KEY))));
        if (stopWordFile != null) {
            // initialize Analyzer with stop words
            try {
                analyzer = new org.apache.lucene.analysis.standard.StandardAnalyzer(com.gentics.cr.lucene.LuceneVersion.getVersion(), stopWordFile);
                return analyzer;
            } catch (java.io.IOException ex) {
                com.gentics.cr.lucene.indexer.index.LuceneAnalyzerFactory.LOGGER.error("Could not open stop words file. " + ("Will create standard " + "analyzer."), ex);
            }
        }
        analyzer = new org.apache.lucene.analysis.standard.StandardAnalyzer(com.gentics.cr.lucene.LuceneVersion.getVersion(), org.apache.lucene.analysis.CharArraySet.EMPTY_SET);
        return analyzer;
    }

    /**
     * Return a map of all used analyzers (per field).
     * This method calls createAnalyzer(config) so it is quite expensive.
     * The config parameter is needed for the call to createAnalyzer as this method
     * reads the analyzer configuration everytime!
     * Key: fieldname
     * Value: canonical class name
     *
     * @param config
     * 		needed for listing all analyzers.
     * @return Map of analyzers per field.
     */
    public static java.util.Map<java.lang.String, java.lang.String> getConfiguredAnalyzers(final com.gentics.cr.configuration.GenericConfiguration config) {
        com.gentics.cr.lucene.indexer.index.LuceneAnalyzerFactory.createAnalyzer(config);
        return com.gentics.cr.lucene.indexer.index.LuceneAnalyzerFactory.configuredAnalyzerMap;
    }
}