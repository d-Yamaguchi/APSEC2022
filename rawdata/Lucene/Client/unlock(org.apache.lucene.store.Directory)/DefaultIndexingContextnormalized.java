private void prepareIndex(boolean reclaimIndex) throws java.io.IOException, org.apache.maven.index.context.ExistingLuceneIndexMismatchException {
    if (org.apache.lucene.index.DirectoryReader.indexExists(indexDirectory)) {
        try {
            // unlock the dir forcibly
            if (org.apache.lucene.index.IndexWriter.isLocked(indexDirectory)) {
                org.apache.lucene.store.Directory _CVAR0 = indexDirectory;
                org.apache.lucene.index.IndexWriter.unlock(_CVAR0);
            }
            openAndWarmup();
            checkAndUpdateIndexDescriptor(reclaimIndex);
        } catch (java.io.IOException e) {
            if (reclaimIndex) {
                prepareCleanIndex(true);
            } else {
                throw e;
            }
        }
    } else {
        prepareCleanIndex(false);
    }
    timestamp = org.apache.maven.index.context.IndexUtils.getTimestamp(indexDirectory);
}