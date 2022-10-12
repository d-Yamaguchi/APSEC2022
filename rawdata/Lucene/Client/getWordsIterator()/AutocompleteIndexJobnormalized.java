private synchronized void reIndex() throws java.io.IOException {
    com.gentics.cr.monitoring.UseCase ucReIndex = com.gentics.cr.monitoring.MonitorFactory.startUseCase("reIndex()");
    // build a dictionary (from the spell package)
    log.debug("Starting to reindex autocomplete index.");
    com.gentics.cr.lucene.indexer.index.LuceneIndexLocation autocompleteLocation = this.autocompleter.getAutocompleteLocation();
    com.gentics.cr.lucene.indexaccessor.IndexAccessor aia = autocompleteLocation.getAccessor();
    // IndexReader reader = aia.getReader(false);
    org.apache.lucene.index.IndexWriter writer = aia.getWriter();
    com.gentics.cr.lucene.autocomplete.AutocompleteIndexExtension _CVAR0 = this.autocompleter;
    com.gentics.cr.lucene.indexer.index.LuceneIndexLocation source = _CVAR0.getSource();
    com.gentics.cr.lucene.indexer.index.LuceneIndexLocation _CVAR1 = source;
    com.gentics.cr.lucene.indexaccessor.IndexAccessor sia = _CVAR1.getAccessor();
    com.gentics.cr.lucene.indexaccessor.IndexAccessor _CVAR2 = sia;
    boolean _CVAR3 = false;
    org.apache.lucene.index.IndexReader sourceReader = _CVAR2.getReader(_CVAR3);
    com.gentics.cr.lucene.autocomplete.AutocompleteIndexExtension _CVAR5 = this.autocompleter;
    java.lang.String autocompletefield = _CVAR5.getAutocompletefield();
    org.apache.lucene.index.IndexReader _CVAR4 = sourceReader;
    java.lang.String _CVAR6 = autocompletefield;
    org.apache.lucene.search.spell.LuceneDictionary dict = new org.apache.lucene.search.spell.LuceneDictionary(_CVAR4, _CVAR6);
    try {
        writer.setMergeFactor(300);
        writer.setMaxBufferedDocs(150);
        // go through every word, storing the original word (incl. n-grams)
        // and the number of times it occurs
        // CREATE WORD LIST FROM SOURCE INDEX
        java.util.Map<java.lang.String, java.lang.Integer> wordsMap = new java.util.HashMap<java.lang.String, java.lang.Integer>();
        org.apache.lucene.search.spell.LuceneDictionary _CVAR7 = dict;
        org.apache.lucene.util.BytesRefIterator iter = _CVAR7.getWordsIterator();
        org.apache.lucene.util.BytesRef ref = iter.next();
        while (ref != null) {
            java.lang.String word = ref.utf8ToString();
            int len = word.length();
            if (len < 3) {
                ref = iter.next();
                continue;// too short we bail but "too long" is fine...

            }
            if (wordsMap.containsKey(word)) {
                throw new java.lang.IllegalStateException("Lucene returned a bad word list");
            } else {
                // use the number of documents this word appears in
                wordsMap.put(word, sourceReader.docFreq(new org.apache.lucene.index.Term(autocompletefield, word)));
            }
            ref = iter.next();
        } 
        // DELETE OLD OBJECTS FROM INDEX
        writer.deleteAll();
        // UPDATE DOCUMENTS IN AUTOCOMPLETE INDEX
        for (java.lang.String word : wordsMap.keySet()) {
            // ok index the word
            org.apache.lucene.document.Document doc = new org.apache.lucene.document.Document();
            doc.add(new org.apache.lucene.document.Field(SOURCE_WORD_FIELD, word, org.apache.lucene.document.Field.Store.YES, org.apache.lucene.document.Field.Index.NOT_ANALYZED_NO_NORMS));// orig term

            doc.add(new org.apache.lucene.document.Field(GRAMMED_WORDS_FIELD, word, org.apache.lucene.document.Field.Store.YES, org.apache.lucene.document.Field.Index.ANALYZED));// grammed

            doc.add(new org.apache.lucene.document.Field(COUNT_FIELD, java.lang.Integer.toString(wordsMap.get(word)), org.apache.lucene.document.Field.Store.YES, org.apache.lucene.document.Field.Index.NOT_ANALYZED_NO_NORMS));// count

            writer.addDocument(doc);
        }
        writer.optimize();
        autocompleteLocation.createReopenFile();
    } finally {
        sia.release(sourceReader, false);
        // close writer
        aia.release(writer);
        // aia.release(reader,false);
    }
    log.debug("Finished reindexing autocomplete index.");
    ucReIndex.stop();
}