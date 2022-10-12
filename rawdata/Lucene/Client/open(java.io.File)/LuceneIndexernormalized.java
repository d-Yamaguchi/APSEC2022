public void setDirectoryName(java.io.File directoryName) throws java.io.IOException {
    this.segmentFile = new java.io.File(directoryName, "segments");
    java.io.File _CVAR0 = directoryName;
    org.apache.lucene.store.FSDirectory _CVAR1 = org.apache.lucene.store.FSDirectory.open(_CVAR0);
    this.directory = _CVAR1;
}