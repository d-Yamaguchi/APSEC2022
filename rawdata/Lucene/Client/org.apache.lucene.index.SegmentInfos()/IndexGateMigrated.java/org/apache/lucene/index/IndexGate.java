package org.apache.lucene.index;
import org.apache.lucene.codecs.Codec;
import org.apache.lucene.codecs.CodecUtil;
import org.apache.lucene.store.IOContext;
import org.getopt.luke.KeepAllIndexDeletionPolicy;
import org.getopt.luke.Luke;
/**
 * This class allows us to peek at various Lucene internals, not available
 * through public APIs (for good reasons, but inquiring minds want to know ...).
 *
 * @author ab
 */
public class IndexGate {
    static java.util.HashMap<java.lang.String, java.lang.String> knownExtensions = new java.util.HashMap<java.lang.String, java.lang.String>();

    // old version constants
    public static final int OLD_FORMAT = -1;

    /**
     * This format adds details used for lockless commits.  It differs
     * slightly from the previous format in that file names
     * are never re-used (write once).  Instead, each file is
     * written to the next generation.  For example,
     * segments_1, segments_2, etc.  This allows us to not use
     * a commit lock.  See <a
     * href="http://lucene.apache.org/java/docs/fileformats.html">file
     * formats</a> for details.
     */
    public static final int FORMAT_LOCKLESS = -2;

    /**
     * This format adds a "hasSingleNormFile" flag into each segment info.
     * See <a href="http://issues.apache.org/jira/browse/LUCENE-756">LUCENE-756</a>
     * for details.
     */
    public static final int FORMAT_SINGLE_NORM_FILE = -3;

    /**
     * This format allows multiple segments to share a single
     * vectors and stored fields file.
     */
    public static final int FORMAT_SHARED_DOC_STORE = -4;

    /**
     * This format adds a checksum at the end of the file to
     *  ensure all bytes were successfully written.
     */
    public static final int FORMAT_CHECKSUM = -5;

    /**
     * This format adds the deletion count for each segment.
     *  This way IndexWriter can efficiently report numDocs().
     */
    public static final int FORMAT_DEL_COUNT = -6;

    /**
     * This format adds the boolean hasProx to record if any
     *  fields in the segment store prox information (ie, have
     *  omitTermFreqAndPositions==false)
     */
    public static final int FORMAT_HAS_PROX = -7;

    /**
     * This format adds optional commit userData (String) storage.
     */
    public static final int FORMAT_USER_DATA = -8;

    /**
     * This format adds optional per-segment String
     *  diagnostics storage, and switches userData to Map
     */
    public static final int FORMAT_DIAGNOSTICS = -9;

    /**
     * Each segment records whether it has term vectors
     */
    public static final int FORMAT_HAS_VECTORS = -10;

    /**
     * Each segment records the Lucene version that created it.
     */
    public static final int FORMAT_3_1 = -11;

    /**
     * Some early 4.0 pre-alpha
     */
    public static final int FORMAT_PRE_4 = -12;

    static {
        knownExtensions.put(org.apache.lucene.index.IndexFileNames.COMPOUND_FILE_EXTENSION, "compound file with various index data");
        knownExtensions.put(org.apache.lucene.index.IndexFileNames.COMPOUND_FILE_ENTRIES_EXTENSION, "compound file entries list");
        knownExtensions.put(org.apache.lucene.index.IndexFileNames.GEN_EXTENSION, "generation number - global file");
        knownExtensions.put(org.apache.lucene.index.IndexFileNames.SEGMENTS, "per-commit list of segments and user data");
    }

    public static java.lang.String getFileFunction(java.lang.String file) {
        if ((file == null) || (file.trim().length() == 0))
            return file;

        java.lang.String res = null;
        file = file.trim();
        int idx = file.indexOf('.');
        java.lang.String suffix = null;
        if (idx != (-1)) {
            suffix = file.substring(idx + 1);
        }
        if (suffix == null) {
            if (file.startsWith("segments_")) {
                return org.apache.lucene.index.IndexGate.knownExtensions.get(org.apache.lucene.index.IndexFileNames.SEGMENTS);
            }
        } else {
            res = org.apache.lucene.index.IndexGate.knownExtensions.get(suffix);
            if (res != null) {
                return res;
            }
            // perhaps per-field norms?
            if (suffix.length() == 2) {
                res = org.apache.lucene.index.IndexGate.knownExtensions.get(suffix.substring(0, 1));
            }
        }
        return res;
    }

    private static void detectOldFormats(org.apache.lucene.index.IndexGate.FormatDetails res, int format) {
        switch (format) {
            case org.apache.lucene.index.IndexGate.OLD_FORMAT :
                res.capabilities = "old plain";
                res.genericName = "Lucene Pre-2.1";
                res.version = "2.0?";
                break;
            case org.apache.lucene.index.IndexGate.FORMAT_LOCKLESS :
                res.capabilities = "lock-less";
                res.genericName = "Lucene 2.1";
                res.version = "2.1";
                break;
            case org.apache.lucene.index.IndexGate.FORMAT_SINGLE_NORM_FILE :
                res.capabilities = "lock-less, single norms file";
                res.genericName = "Lucene 2.2";
                res.version = "2.2";
                break;
            case org.apache.lucene.index.IndexGate.FORMAT_SHARED_DOC_STORE :
                res.capabilities = "lock-less, single norms file, shared doc store";
                res.genericName = "Lucene 2.3";
                res.version = "2.3";
                break;
            case org.apache.lucene.index.IndexGate.FORMAT_CHECKSUM :
                res.capabilities = "lock-less, single norms, shared doc store, checksum";
                res.genericName = "Lucene 2.4";
                res.version = "2.4";
                break;
            case org.apache.lucene.index.IndexGate.FORMAT_DEL_COUNT :
                res.capabilities = "lock-less, single norms, shared doc store, checksum, del count";
                res.genericName = "Lucene 2.4";
                res.version = "2.4";
                break;
            case org.apache.lucene.index.IndexGate.FORMAT_HAS_PROX :
                res.capabilities = "lock-less, single norms, shared doc store, checksum, del count, omitTf";
                res.genericName = "Lucene 2.4";
                res.version = "2.4";
                break;
            case org.apache.lucene.index.IndexGate.FORMAT_USER_DATA :
                res.capabilities = "lock-less, single norms, shared doc store, checksum, del count, omitTf, user data";
                res.genericName = "Lucene 2.9-dev";
                res.version = "2.9-dev";
                break;
            case org.apache.lucene.index.IndexGate.FORMAT_DIAGNOSTICS :
                res.capabilities = "lock-less, single norms, shared doc store, checksum, del count, omitTf, user data, diagnostics";
                res.genericName = "Lucene 2.9";
                res.version = "2.9";
                break;
            case org.apache.lucene.index.IndexGate.FORMAT_HAS_VECTORS :
                res.capabilities = "lock-less, single norms, shared doc store, checksum, del count, omitTf, user data, diagnostics, hasVectors";
                res.genericName = "Lucene 2.9";
                res.version = "2.9";
                break;
            case org.apache.lucene.index.IndexGate.FORMAT_3_1 :
                res.capabilities = "lock-less, single norms, shared doc store, checksum, del count, omitTf, user data, diagnostics, hasVectors";
                res.genericName = "Lucene 3.1";
                res.version = "3.1";
                break;
            case org.apache.lucene.index.IndexGate.FORMAT_PRE_4 :
                res.capabilities = "flexible, unreleased 4.0 pre-alpha";
                res.genericName = "Lucene 4.0-dev";
                res.version = "4.0-dev";
                break;
            default :
                if (format < org.apache.lucene.index.IndexGate.FORMAT_PRE_4) {
                    res.capabilities = "flexible, unreleased 4.0 pre-alpha";
                    res.genericName = "Lucene 4.0-dev";
                    res.version = "4.0-dev";
                } else {
                    res.capabilities = "unknown";
                    res.genericName = "Lucene 1.3 or earlier, or unreleased";
                    res.version = "1.3?";
                }
                break;
        }
        res.genericName = ((res.genericName + " (") + format) + ")";
    }

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

    public static boolean preferCompoundFormat(org.apache.lucene.store.Directory dir) throws java.lang.Exception {
        SegmentInfos infos = new org.apache.lucene.index.SegmentInfos();
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

    /* FIXME chatellier : not called and cause compile error since lucene 4.3.0
    public static void deletePendingFiles(Directory dir, IndexDeletionPolicy policy) throws Exception {
    SegmentInfos infos = new SegmentInfos();
    infos.read(dir);
    IndexWriterConfig cfg = new IndexWriterConfig(Luke.LV, new WhitespaceAnalyzer(Luke.LV));
    IndexWriter iw = new IndexWriter(dir, cfg);
    IndexFileDeleter deleter = new IndexFileDeleter(dir, policy, infos, null, iw, true);
    deleter.close();
    iw.close();
    }
     */
    public static java.util.List<java.lang.String> getDeletableFiles(org.apache.lucene.store.Directory dir) throws java.lang.Exception {
        java.util.List<java.lang.String> known = org.apache.lucene.index.IndexGate.getIndexFiles(dir);
        java.util.Set<java.lang.String> dirFiles = new java.util.HashSet<java.lang.String>(java.util.Arrays.asList(dir.listAll()));
        dirFiles.removeAll(known);
        return new java.util.ArrayList<java.lang.String>(dirFiles);
    }

    public static java.util.List<java.lang.String> getIndexFiles(org.apache.lucene.store.Directory dir) throws java.lang.Exception {
        java.util.List<org.apache.lucene.index.IndexCommit> commits = null;
        try {
            commits = org.apache.lucene.index.DirectoryReader.listCommits(dir);
        } catch (org.apache.lucene.index.IndexNotFoundException e) {
            return java.util.Collections.emptyList();
        }
        java.util.Set<java.lang.String> known = new java.util.HashSet<java.lang.String>();
        for (org.apache.lucene.index.IndexCommit ic : commits) {
            known.addAll(ic.getFileNames());
        }
        if (dir.fileExists(org.apache.lucene.index.IndexFileNames.SEGMENTS_GEN)) {
            known.add(org.apache.lucene.index.IndexFileNames.SEGMENTS_GEN);
        }
        java.util.List<java.lang.String> names = new java.util.ArrayList<java.lang.String>(known);
        java.util.Collections.sort(names);
        return names;
    }

    public static class FormatDetails {
        public java.lang.String genericName = "N/A";

        public java.lang.String capabilities = "N/A";

        public java.lang.String version = "N/A";
    }
}