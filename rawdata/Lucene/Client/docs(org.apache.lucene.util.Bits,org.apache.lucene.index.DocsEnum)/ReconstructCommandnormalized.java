public java.lang.String reconstructNoPositions(org.apache.lucene.index.TermsEnum te, int docid, org.apache.lucene.util.Bits liveDocs) throws java.io.IOException {
    java.util.List<java.lang.String> textList = new java.util.ArrayList<java.lang.String>();
    org.apache.lucene.util.BytesRef text;
    org.apache.lucene.index.DocsEnum dpe = null;
    while ((text = te.next()) != null) {
        dpe = te.docs(liveDocs, dpe);
        int iterDoc = dpe.advance(docid);
        if (iterDoc == docid) {
            textList.add(text.utf8ToString());
        }
    } 
    java.lang.StringBuilder buf = new java.lang.StringBuilder();
    for (java.lang.String s : textList) {
        buf.append(s + " ");
    }
    return buf.toString();
}