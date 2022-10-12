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
    w.commit();
    org.apache.lucene.index.IndexWriter _CVAR0 = w;
    _CVAR0.optimize();
    org.apache.maven.index.updater.IndexDataReader.IndexDataReadResult result = new org.apache.maven.index.updater.IndexDataReader.IndexDataReadResult();
    result.setDocumentCount(n);
    result.setTimestamp(date);
    return result;
}