public void setDirectoryName(java.io.File directoryName) throws java.io.IOException {
    this.segmentFile = new java.io.File(directoryName, "segments");
    java.io.File _CVAR0 = directoryName;
    java.lang.String _CVAR1 = _CVAR0.toString();
    boolean _CVAR2 = !this.segmentFile.exists();
    java.io.File _CVAR3 = org.apache.lucene.store.FSDirectory.getDirectory(_CVAR1, _CVAR2);
    this.directory = _CVAR3;
}