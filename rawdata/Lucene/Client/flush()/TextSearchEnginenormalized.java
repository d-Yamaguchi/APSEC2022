public void add(net.schweerelos.parrot.model.NodeWrapper node) {
    org.apache.lucene.document.Document doc = new org.apache.lucene.document.Document();
    doc.add(new org.apache.lucene.document.Field(net.schweerelos.parrot.model.TextSearchEngine.LABEL_FIELD_NAME, node.toString(), org.apache.lucene.document.Field.Store.COMPRESS, org.apache.lucene.document.Field.Index.TOKENIZED));
    doc.add(new org.apache.lucene.document.Field(net.schweerelos.parrot.model.TextSearchEngine.HASH_FIELD_NAME, java.lang.String.valueOf(node.hashCode()), org.apache.lucene.document.Field.Store.YES, org.apache.lucene.document.Field.Index.NO));
    try {
        writer.addDocument(doc);
        hashToNodeWrapper.put(node.hashCode(), node);
        org.apache.lucene.index.IndexWriter _CVAR0 = writer;
        _CVAR0.flush();
    } catch (org.apache.lucene.index.CorruptIndexException e) {
        // ignore
        e.printStackTrace();
    } catch (java.io.IOException e) {
        // ignore
        e.printStackTrace();
    }
}