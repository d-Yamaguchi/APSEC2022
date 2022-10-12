public org.apache.maven.index.updater.IndexDataReader.IndexDataReadResult readIndex(org.apache.lucene.index.IndexWriter w, org.apache.maven.index.context.IndexingContext context) throws java.io.IOException {
    long timestamp = readHeader();
    java.util.Date date = null;
    if (timestamp != (-1)) {
        date = new java.util.Date(timestamp);
        org.apache.maven.index.context.IndexUtils.updateTimestamp(w.getDirectory(), date);
    }
    int n = 0;
    org.apache.lucene.document.Document doc;
    while ((doc = readDocument()) != null) {
        w.addDocument(org.apache.maven.index.context.IndexUtils.updateDocument(doc, context, false));
        n++;
    } 
    org.apache.lucene.index.IndexWriter _CVAR0 = w;
    _CVAR0.commit();
    w.forceMerge(1);
    org.apache.lucene.index.IndexWriter _CVAR1 = w;
    _CVAR1.commit();
    org.apache.maven.index.updater.IndexDataReader.IndexDataReadResult result = new org.apache.maven.index.updater.IndexDataReader.IndexDataReadResult();
    result.setDocumentCount(n);
    result.setTimestamp(date);
    return result;
}