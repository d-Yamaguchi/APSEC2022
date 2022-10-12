public static boolean preferCompoundFormat(org.apache.lucene.store.Directory dir) throws java.lang.Exception {
    org.apache.lucene.index.SegmentInfos infos = new org.apache.lucene.index.SegmentInfos();
    infos.read(dir);
    int compound = 0;
    int nonCompound = 0;
    for (int i = 0; i < infos.size(); i++) {
        if (((org.apache.lucene.index.SegmentInfoPerCommit) (infos.info(i))).info.getUseCompoundFile()) {
            compound++;
        } else {
            nonCompound++;
        }
    }
    return compound > nonCompound;
}