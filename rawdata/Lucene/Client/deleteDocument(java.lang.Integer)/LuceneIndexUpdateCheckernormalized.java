@java.lang.Override
public void deleteStaleObjects() {
    com.gentics.cr.lucene.indexer.index.LuceneIndexUpdateChecker.log.debug(((checkedDocuments.size() + " objects checked, ") + docs.size()) + " objects already in the index.");
    boolean readerNeedsWrite = true;
    com.gentics.cr.monitoring.UseCase deleteStale = com.gentics.cr.monitoring.MonitorFactory.startUseCase(("LuceneIndexUpdateChecker.deleteStaleObjects(" + indexLocation.getName()) + ")");
    org.apache.lucene.index.IndexReader writeReader = null;
    try {
        boolean objectsDeleted = false;
        for (java.lang.String contentId : docs.keySet()) {
            if (!checkedDocuments.contains(contentId)) {
                com.gentics.cr.lucene.indexer.index.LuceneIndexUpdateChecker.log.debug(("Object " + contentId) + " wasn't checked in the last run. So i will delete it.");
                if (writeReader == null) {
                    writeReader = indexAccessor.getReader(readerNeedsWrite);
                }
                java.util.LinkedHashMap<java.lang.String, java.lang.Integer> _CVAR1 = docs;
                java.lang.String _CVAR2 = contentId;
                org.apache.lucene.index.IndexReader _CVAR0 = writeReader;
                java.lang.Integer _CVAR3 = _CVAR1.get(_CVAR2);
                _CVAR0.deleteDocument(_CVAR3);
                objectsDeleted = true;
            }
        }
        if (objectsDeleted) {
            indexLocation.createReopenFile();
        }
    } catch (java.io.IOException e) {
        com.gentics.cr.lucene.indexer.index.LuceneIndexUpdateChecker.log.error("Cannot delete objects from index.", e);
    } finally {
        // always release writeReader it blocks other threads if you don't
        if (writeReader != null) {
            indexAccessor.release(writeReader, readerNeedsWrite);
        }
        com.gentics.cr.lucene.indexer.index.LuceneIndexUpdateChecker.log.debug("Finished cleaning stale documents");
        deleteStale.stop();
    }
    checkedDocuments.clear();
}