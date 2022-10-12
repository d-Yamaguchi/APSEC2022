/**
 * Indexes the data from the given {@link Dictionary}.
 *
 * @param dict
 * 		Dictionary to index
 * @throws IOException
 * 		in case of error
 */
public final void indexDictionary(final org.apache.lucene.search.spell.Dictionary dict) throws java.io.IOException {
    synchronized(modifyCurrentIndexLock) {
        ensureOpen();
        final com.gentics.cr.lucene.indexaccessor.IndexAccessor accessor = this.spellIndex.getAccessor();
        final org.apache.lucene.index.IndexWriter writer = accessor.getWriter();
        writer.setMergeFactor(300);
        final org.apache.lucene.search.IndexSearcher indexSearcher = ((org.apache.lucene.search.IndexSearcher) (accessor.getPrioritizedSearcher()));
        int obj_count = 0;
        try {
            org.apache.lucene.util.BytesRefIterator iter = dict.getWordsIterator();
            org.apache.lucene.util.BytesRef ref = iter.next();
            while (ref != null) {
                java.lang.String word = ref.utf8ToString();
                int len = word.length();
                if (len < org.apache.lucene.search.spell.CustomSpellChecker.THREE) {
                    ref = iter.next();
                    continue;// too short we bail but "too long" is fine...

                }
                if (indexSearcher.docFreq(org.apache.lucene.search.spell.CustomSpellChecker.F_WORD_TERM.createTerm(word)) > 0) {
                    // if the word already exist in the gramindex
                    ref = iter.next();
                    continue;
                }
                // ok index the word
                org.apache.lucene.document.Document doc = org.apache.lucene.search.spell.CustomSpellChecker.createDocument(word, org.apache.lucene.search.spell.CustomSpellChecker.getMin(len), org.apache.lucene.search.spell.CustomSpellChecker.getMax(len));
                writer.addDocument(doc);
                obj_count++;
                ref = iter.next();
            } 
        } finally {
            // if documents where added to the index create a reopen file and
            // optimize the writer
            if (obj_count > 0) {
                writer.optimize();
                this.spellIndex.createReopenFile();
            }
            accessor.release(writer);
            accessor.release(indexSearcher);
        }
    }
}