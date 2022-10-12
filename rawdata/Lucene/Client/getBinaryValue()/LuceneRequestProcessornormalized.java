private void processDocuments(final java.util.LinkedHashMap<org.apache.lucene.document.Document, java.lang.Float> docs, final java.util.ArrayList<com.gentics.cr.CRResolvableBean> result, final org.apache.lucene.index.IndexReader reader, final org.apache.lucene.search.Query parsedQuery) {
    java.lang.String scoreAttribute = ((java.lang.String) (config.get(com.gentics.cr.lucene.search.LuceneRequestProcessor.SCORE_ATTRIBUTE_KEY)));
    // PROCESS RESULT
    if (docs != null) {
        com.gentics.cr.lucene.search.LuceneRequestProcessor _CVAR2 = config;
        java.lang.String _CVAR3 = com.gentics.cr.lucene.search.LuceneRequestProcessor.ID_ATTRIBUTE_KEY;
        java.lang.String idAttribute = ((java.lang.String) (_CVAR2.get(_CVAR3)));
        for (java.util.Map.Entry<org.apache.lucene.document.Document, java.lang.Float> entry : docs.entrySet()) {
            java.lang.Float score = entry.getValue();
            java.util.Map.Entry<org.apache.lucene.document.Document, java.lang.Float> _CVAR0 = entry;
            org.apache.lucene.document.Document doc = _CVAR0.getKey();
            org.apache.lucene.document.Document _CVAR1 = doc;
            java.lang.String _CVAR4 = idAttribute;
            java.lang.String _CVAR5 = _CVAR1.get(_CVAR4);
            com.gentics.cr.CRResolvableBean crBean = new com.gentics.cr.CRResolvableBean(_CVAR5);
            if (getStoredAttributes) {
                for (org.apache.lucene.document.Field field : com.gentics.cr.lucene.search.LuceneRequestProcessor.toFieldList(doc.getFields())) {
                    if (field.isStored()) {
                        if (field.isBinary()) {
                            org.apache.lucene.document.Field _CVAR7 = field;
                            org.apache.lucene.document.Field _CVAR9 = field;
                            com.gentics.cr.CRResolvableBean _CVAR6 = crBean;
                            java.lang.String _CVAR8 = _CVAR7.name();
                            byte[] _CVAR10 = _CVAR9.getBinaryValue();
                            _CVAR6.set(_CVAR8, _CVAR10);
                        } else {
                            crBean.set(field.name(), field.stringValue());
                        }
                    }
                }
            }
            if ((scoreAttribute != null) && (!"".equals(scoreAttribute))) {
                crBean.set(scoreAttribute, score);
            }
            // DO HIGHLIGHTING
            doHighlighting(crBean, doc, parsedQuery, reader);
            com.gentics.cr.lucene.search.LuceneRequestProcessor.LOGGER.debug((("Found " + crBean.getContentid()) + " with score ") + score.toString());
            result.add(crBean);
        }
    }
}