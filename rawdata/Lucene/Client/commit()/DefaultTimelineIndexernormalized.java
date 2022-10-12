private void closeIndexWriter() throws java.io.IOException {
    if (indexWriter != null) {
        org.apache.lucene.index.IndexWriter _CVAR0 = indexWriter;
        _CVAR0.commit();
        indexWriter.close();
        indexWriter = null;
    }
}