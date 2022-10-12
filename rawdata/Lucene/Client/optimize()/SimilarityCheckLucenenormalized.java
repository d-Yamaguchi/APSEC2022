public void closeIndex() {
    try {
        org.apache.lucene.index.IndexWriter _CVAR0 = writer;
        _CVAR0.optimize();
        writer.close();
    } catch (java.lang.Exception e) {
        java.lang.System.out.println("\n " + e.getMessage());
        java.lang.System.out.println("\n ERROR: Index Close Exception");
    }
}