public static org.apache.lucene.index.IndexGate.FormatDetails getIndexFormat(final org.apache.lucene.store.Directory dir) throws java.lang.Exception {
    org.apache.lucene.index.SegmentInfos.FindSegmentsFile fsf = new org.apache.lucene.index.SegmentInfos.FindSegmentsFile(dir) {
        protected java.lang.Object doBody(java.lang.String segmentsFile) throws org.apache.lucene.index.CorruptIndexException, java.io.IOException {
            org.apache.lucene.index.IndexGate.FormatDetails res = new org.apache.lucene.index.IndexGate.FormatDetails();
            res.capabilities = "unknown";
            res.genericName = "unknown";
            org.apache.lucene.store.IndexInput in = dir.openInput(segmentsFile, IOContext.READ);
            try {
                int indexFormat = in.readInt();
                if (indexFormat == org.apache.lucene.codecs.CodecUtil.CODEC_MAGIC) {
                    res.genericName = "Lucene 4.x";
                    res.capabilities = "flexible, codec-specific";
                    int actualVersion = org.apache.lucene.index.SegmentInfos.VERSION_40;
                    try {
                        actualVersion = org.apache.lucene.codecs.CodecUtil.checkHeaderNoMagic(in, "segments", org.apache.lucene.index.SegmentInfos.VERSION_40, java.lang.Integer.MAX_VALUE);
                        if (actualVersion > org.apache.lucene.index.SegmentInfos.VERSION_40) {
                            res.capabilities += " (WARNING: newer version of Lucene that this tool)";
                        }
                    } catch (java.lang.Exception e) {
                        e.printStackTrace();
                        res.capabilities += (" (error reading: " + e.getMessage()) + ")";
                    }
                    res.genericName = "Lucene 4." + actualVersion;
                    res.version = "4." + actualVersion;
                } else {
                    res.genericName = "Lucene 3.x or prior";
                    org.apache.lucene.index.IndexGate.detectOldFormats(res, indexFormat);
                    if (res.version.compareTo("3") < 0) {
                        res.capabilities = res.capabilities + " (UNSUPPORTED)";
                    }
                }
            } finally {
                in.close();
            }
            return res;
        }
    };
    return ((org.apache.lucene.index.IndexGate.FormatDetails) (fsf.run()));
}