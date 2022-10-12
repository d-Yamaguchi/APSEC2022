@java.lang.Override
protected void setUp() throws java.lang.Exception {
    super.setUp();
    nexusIndexer = lookup(org.apache.maven.index.NexusIndexer.class);
    java.io.File _CVAR0 = indexDirFile;
    org.apache.lucene.store.FSDirectory _CVAR1 = org.apache.lucene.store.FSDirectory.open(_CVAR0);
    indexDir = _CVAR1;
    context = nexusIndexer.addIndexingContext("one", "nexus-13", repo, indexDir, null, null, org.apache.maven.index.DEFAULT_CREATORS);
    nexusIndexer.scan(context);
    java.io.File _CVAR2 = otherIndexDirFile;
    org.apache.lucene.store.FSDirectory _CVAR3 = org.apache.lucene.store.FSDirectory.open(_CVAR2);
    otherIndexDir = _CVAR3;
    otherContext = nexusIndexer.addIndexingContext("other", "nexus-13", repo, otherIndexDir, null, null, org.apache.maven.index.DEFAULT_CREATORS);
    nexusIndexer.scan(otherContext);
}