/**
 * Fetch an unique document from the index.
 *
 * @param indexReader
 * 		reader.
 * @param idTerm
 * 		term.
 * @param searchCRID
 * 		crid.
 * @return document.
 */
private org.apache.lucene.document.Document getUniqueDocument(final org.apache.lucene.index.IndexReader indexReader, final org.apache.lucene.index.Term idTerm, final java.lang.String searchCRID) {
    try {
        org.apache.lucene.index.TermDocs docs = indexReader.termDocs(idTerm);
        while (docs.next()) {
            org.apache.lucene.document.Document doc = indexReader.document(docs.doc());
            java.lang.String crID = doc.get(com.gentics.cr.lucene.indexer.index.CRLuceneIndexJob.CR_FIELD_KEY);
            if ((crID != null) && crID.equals(searchCRID)) {
                return doc;
            }
        } 
    } catch (java.io.IOException e) {
        log.error("An error happend while fetching the document in the index.", e);
    }
    return null;
}