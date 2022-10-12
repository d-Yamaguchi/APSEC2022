// TODO: Work per segment. The collector should collect docs per segment instead of one big set of top level ids
private void fillBatch(org.apache.lucene.search.DocIdSetIterator iterator, org.apache.lucene.index.IndexReader indexReader, ShardUpdateByQueryRequest request, java.util.List<BulkItemRequest> bulkItemRequests) throws java.io.IOException {
    int counter = 0;
    for (int docID = iterator.nextDoc(); docID != org.apache.lucene.search.DocIdSetIterator.NO_MORE_DOCS; docID = iterator.nextDoc()) {
        indexReader.document(docID, fieldVisitor);
        java.util.Set<java.lang.String> _CVAR0 = org.elasticsearch.action.updatebyquery.TransportShardUpdateByQueryAction.fields;
        org.apache.lucene.document.DocumentStoredFieldVisitor fieldVisitor = new org.apache.lucene.document.DocumentStoredFieldVisitor(_CVAR0);
        org.apache.lucene.document.DocumentStoredFieldVisitor _CVAR1 = fieldVisitor;
        org.apache.lucene.document.Document document = _CVAR1.getDocument();
        int readerIndex = org.apache.lucene.index.ReaderUtil.subIndex(docID, indexReader.leaves());
        org.apache.lucene.index.AtomicReaderContext subReaderContext = indexReader.leaves().get(readerIndex);
        bulkItemRequests.add(new BulkItemRequest(counter, createRequest(request, document, subReaderContext)));
        if ((++counter) == batchSize) {
            break;
        }
    }
}