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
    com.gentics.cr.configuration.GenericConfiguration _CVAR0 = config;
    com.gentics.cr.configuration.GenericConfiguration _CVAR14 = _CVAR0;
    com.gentics.cr.configuration.GenericConfiguration _CVAR27 = _CVAR14;
    org.apache.lucene.analysis.Analyzer _CVAR1 = com.gentics.cr.lucene.indexer.index.LuceneAnalyzerFactory.createDefaultAnalyzer(_CVAR27);
    org.apache.lucene.analysis.Analyzer _CVAR15 = _CVAR1;
    org.apache.lucene.analysis.Analyzer _CVAR28 = _CVAR15;
    // Caching the analyzer instances is not possible as those do not implement Serializable
    // TODO: cache the config (imho caching should be implemented in the config itself)
    org.apache.lucene.analysis.PerFieldAnalyzerWrapper analyzerWrapper = new org.apache.lucene.analysis.PerFieldAnalyzerWrapper(_CVAR28);
    if (analyzerConfig != null) {
        java.util.ArrayList<java.lang.String> addedReverseAttributes = new java.util.ArrayList<java.lang.String>();
        java.util.List<java.lang.String> reverseAttributes = com.gentics.cr.lucene.indexer.index.LuceneAnalyzerFactory.getReverseAttributes(config);
        java.util.Map<java.lang.String, com.gentics.cr.configuration.GenericConfiguration> subconfigs = analyzerConfig.getSortedSubconfigs();
        if (subconfigs != null) {
            for (java.util.Map.Entry<java.lang.String, com.gentics.cr.configuration.GenericConfiguration> entry : subconfigs.entrySet()) {
                com.gentics.cr.configuration.GenericConfiguration _CVAR4 = analyzerconfig;
                java.lang.String _CVAR5 = com.gentics.cr.lucene.indexer.index.LuceneAnalyzerFactory.FIELD_NAME_KEY;
                java.lang.String fieldname = _CVAR4.getString(_CVAR5);
                java.util.Map.Entry<java.lang.String, com.gentics.cr.configuration.GenericConfiguration> _CVAR7 = _CVAR3;
                com.gentics.cr.configuration.GenericConfiguration analyzerconfig = _CVAR7.getValue();
                java.util.Map.Entry<java.lang.String, com.gentics.cr.configuration.GenericConfiguration> _CVAR11 = _CVAR7;
                com.gentics.cr.configuration.GenericConfiguration analyzerconfig = _CVAR11.getValue();
                org.apache.lucene.analysis.PerFieldAnalyzerWrapper _CVAR2 = analyzerWrapper;
                java.lang.String _CVAR6 = fieldname;
                org.apache.lucene.analysis.Analyzer _CVAR13 = analyzerInstance;
                _CVAR2.addAnalyzer(_CVAR6, _CVAR13);
                com.gentics.cr.lucene.indexer.index.LuceneAnalyzerFactory.configuredAnalyzerMap.put(fieldname, analyzerInstance.getClass().getCanonicalName());
                java.util.Map.Entry<java.lang.String, com.gentics.cr.configuration.GenericConfiguration> _CVAR3 = entry;
                java.util.Map.Entry<java.lang.String, com.gentics.cr.configuration.GenericConfiguration> _CVAR19 = _CVAR3;
                com.gentics.cr.configuration.GenericConfiguration analyzerconfig = _CVAR19.getValue();
                com.gentics.cr.configuration.GenericConfiguration _CVAR8 = analyzerconfig;
                java.lang.String _CVAR9 = com.gentics.cr.lucene.indexer.index.LuceneAnalyzerFactory.ANALYZER_CLASS_KEY;
                com.gentics.cr.configuration.GenericConfiguration _CVAR20 = _CVAR8;
                java.lang.String _CVAR21 = _CVAR9;
                java.lang.String analyzerclass = _CVAR20.getString(_CVAR21);
                java.lang.String _CVAR10 = analyzerclass;
                java.util.Map.Entry<java.lang.String, com.gentics.cr.configuration.GenericConfiguration> _CVAR23 = _CVAR19;
                com.gentics.cr.configuration.GenericConfiguration analyzerconfig = _CVAR23.getValue();
                com.gentics.cr.configuration.GenericConfiguration _CVAR12 = analyzerconfig;
                java.lang.String _CVAR22 = _CVAR10;
                com.gentics.cr.configuration.GenericConfiguration _CVAR24 = _CVAR12;
                org.apache.lucene.analysis.Analyzer analyzerInstance = com.gentics.cr.lucene.indexer.index.LuceneAnalyzerFactory.createAnalyzer(_CVAR22, _CVAR24);
                // ADD REVERSE ANALYZERS
                if ((reverseAttributes != null) && reverseAttributes.contains(fieldname)) {
                    addedReverseAttributes.add(fieldname);
                    java.lang.String _CVAR17 = com.gentics.cr.lucene.indexer.index.LuceneAnalyzerFactory.REVERSE_ATTRIBUTE_SUFFIX;
                    org.apache.lucene.analysis.Analyzer _CVAR25 = analyzerInstance;
                    com.gentics.cr.lucene.analysis.ReverseAnalyzer reverseAnalyzer = new com.gentics.cr.lucene.analysis.ReverseAnalyzer(_CVAR25);
                    org.apache.lucene.analysis.PerFieldAnalyzerWrapper _CVAR16 = analyzerWrapper;
                    java.lang.String _CVAR18 = fieldname + _CVAR17;
                    com.gentics.cr.lucene.analysis.ReverseAnalyzer _CVAR26 = reverseAnalyzer;
                    _CVAR16.addAnalyzer(_CVAR18, _CVAR26);
                    com.gentics.cr.lucene.indexer.index.LuceneAnalyzerFactory.configuredAnalyzerMap.put(fieldname + com.gentics.cr.lucene.indexer.index.LuceneAnalyzerFactory.REVERSE_ATTRIBUTE_SUFFIX, reverseAnalyzer.getClass().getCanonicalName());
                }
            }
        }
        // ADD ALL NON CONFIGURED REVERSE ANALYZERS
        if ((reverseAttributes != null) && (reverseAttributes.size() > 0)) {
            for (java.lang.String att : reverseAttributes) {
                if (!addedReverseAttributes.contains(att)) {
                    java.lang.String _CVAR30 = com.gentics.cr.lucene.indexer.index.LuceneAnalyzerFactory.REVERSE_ATTRIBUTE_SUFFIX;
                    <nulltype> _CVAR32 = null;
                    com.gentics.cr.lucene.analysis.ReverseAnalyzer reverseAnalyzer = new com.gentics.cr.lucene.analysis.ReverseAnalyzer(_CVAR32);
                    org.apache.lucene.analysis.PerFieldAnalyzerWrapper _CVAR29 = analyzerWrapper;
                    java.lang.String _CVAR31 = att + _CVAR30;
                    com.gentics.cr.lucene.analysis.ReverseAnalyzer _CVAR33 = reverseAnalyzer;
                    _CVAR29.addAnalyzer(_CVAR31, _CVAR33);
                    com.gentics.cr.lucene.indexer.index.LuceneAnalyzerFactory.configuredAnalyzerMap.put(att + com.gentics.cr.lucene.indexer.index.LuceneAnalyzerFactory.REVERSE_ATTRIBUTE_SUFFIX, reverseAnalyzer.getClass().getCanonicalName());
                }
            }
        }
    }
    return analyzerWrapper;
}