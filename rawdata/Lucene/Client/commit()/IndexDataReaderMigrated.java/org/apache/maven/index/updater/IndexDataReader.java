package org.apache.maven.index.updater;
import java.io.IOException;
import org.apache.maven.index.ArtifactInfo;
import org.apache.maven.index.context.IndexUtils;
import org.apache.maven.index.context.IndexingContext;
/**
 * An index data reader used to parse transfer index format.
 *
 * @author Eugene Kuleshov
 */
public class IndexDataReader {
    private final java.io.DataInputStream dis;

    public IndexDataReader(final java.io.InputStream is) throws java.io.IOException {
        java.io.BufferedInputStream bis = new java.io.BufferedInputStream(is, 1024 * 8);
        // MINDEXER-13
        // LightweightHttpWagon may have performed automatic decompression
        // Handle it transparently
        bis.mark(2);
        java.io.InputStream data;
        // GZIPInputStream.GZIP_MAGIC
        if ((bis.read() == 0x1f) && (bis.read() == 0x8b)) {
            bis.reset();
            data = new java.util.zip.GZIPInputStream(bis, 1024 * 8);
        } else {
            bis.reset();
            data = bis;
        }
        this.dis = new java.io.DataInputStream(data);
    }

    public org.apache.maven.index.updater.IndexDataReader.IndexDataReadResult readIndex(org.apache.lucene.index.IndexWriter w, org.apache.maven.index.context.IndexingContext context) throws java.io.IOException {
        long timestamp = readHeader();
        java.util.Date date = null;
        if (timestamp != (-1)) {
            date = new java.util.Date(timestamp);
            org.apache.maven.index.context.IndexUtils.updateTimestamp(w.getDirectory(), date);
        }
        int n = 0;
        org.apache.lucene.document.Document doc;
        __SmPLUnsupported__(0);
        IndexDataReadResult result = new org.apache.maven.index.updater.IndexDataReader.IndexDataReadResult();
        Set rootGroups = new java.util.LinkedHashSet<>();
        result.setRootGroups(rootGroups);
        w.forceMerge(1);
        IndexDataReadResult result = new org.apache.maven.index.updater.IndexDataReader.IndexDataReadResult();
        Set rootGroups = new java.util.LinkedHashSet<>();
        result.setRootGroups(rootGroups);
        org.apache.maven.index.updater.IndexDataReader.IndexDataReadResult result = new org.apache.maven.index.updater.IndexDataReader.IndexDataReadResult();
        result.setDocumentCount(n);
        result.setTimestamp(date);
        return result;
    }

    public long readHeader() throws java.io.IOException {
        final byte HDRBYTE = ((byte) ((IndexDataWriter.VERSION << 24) >> 24));
        if (HDRBYTE != dis.readByte()) {
            // data format version mismatch
            throw new java.io.IOException("Provided input contains unexpected data (0x01 expected as 1st byte)!");
        }
        return dis.readLong();
    }

    public org.apache.lucene.document.Document readDocument() throws java.io.IOException {
        int fieldCount;
        try {
            fieldCount = dis.readInt();
        } catch (java.io.EOFException ex) {
            return null;// no more documents

        }
        org.apache.lucene.document.Document doc = new org.apache.lucene.document.Document();
        for (int i = 0; i < fieldCount; i++) {
            doc.add(readField());
        }
        // Fix up UINFO field wrt MINDEXER-41
        final org.apache.lucene.document.Field uinfoField = ((org.apache.lucene.document.Field) (doc.getField(ArtifactInfo.UINFO)));
        final java.lang.String info = doc.get(ArtifactInfo.INFO);
        if ((uinfoField != null) && (!com.google.common.base.Strings.isNullOrEmpty(info))) {
            final java.lang.String[] splitInfo = ArtifactInfo.FS_PATTERN.split(info);
            if (splitInfo.length > 6) {
                final java.lang.String extension = splitInfo[6];
                final java.lang.String uinfoString = uinfoField.stringValue();
                if (uinfoString.endsWith(org.apache.maven.index.ArtifactInfo.FS + org.apache.maven.index.ArtifactInfo.NA)) {
                    uinfoField.setStringValue((uinfoString + org.apache.maven.index.ArtifactInfo.FS) + org.apache.maven.index.ArtifactInfo.nvl(extension));
                }
            }
        }
        return doc;
    }

    private org.apache.lucene.document.Field readField() throws java.io.IOException {
        int flags = dis.read();
        org.apache.lucene.document.Field.Index index = org.apache.lucene.document.Field.Index.NO;
        if ((flags & IndexDataWriter.F_INDEXED) > 0) {
            boolean isTokenized = (flags & IndexDataWriter.F_TOKENIZED) > 0;
            index = (isTokenized) ? org.apache.lucene.document.Field.Index.ANALYZED : org.apache.lucene.document.Field.Index.NOT_ANALYZED;
        }
        org.apache.lucene.document.Field.Store store = org.apache.lucene.document.Field.Store.NO;
        if ((flags & IndexDataWriter.F_STORED) > 0) {
            store = org.apache.lucene.document.Field.Store.YES;
        }
        java.lang.String name = dis.readUTF();
        java.lang.String value = org.apache.maven.index.updater.IndexDataReader.readUTF(dis);
        return new org.apache.lucene.document.Field(name, value, store, index);
    }

    private static java.lang.String readUTF(java.io.DataInput in) throws java.io.IOException {
        int utflen = in.readInt();
        byte[] bytearr;
        char[] chararr;
        try {
            bytearr = new byte[utflen];
            chararr = new char[utflen];
        } catch (java.lang.OutOfMemoryError e) {
            final java.io.IOException ex = new java.io.IOException("Index data content is inappropriate (is junk?), leads to OutOfMemoryError! See MINDEXER-28 for more information!");
            ex.initCause(e);
            throw ex;
        }
        int c;
        int char2;
        int char3;
        int count = 0;
        int chararr_count = 0;
        in.readFully(bytearr, 0, utflen);
        while (count < utflen) {
            c = bytearr[count] & 0xff;
            if (c > 127) {
                break;
            }
            count++;
            chararr[chararr_count++] = ((char) (c));
        } 
        while (count < utflen) {
            c = bytearr[count] & 0xff;
            switch (c >> 4) {
                case 0 :
                case 1 :
                case 2 :
                case 3 :
                case 4 :
                case 5 :
                case 6 :
                case 7 :
                    /* 0xxxxxxx */
                    count++;
                    chararr[chararr_count++] = ((char) (c));
                    break;
                case 12 :
                case 13 :
                    /* 110x xxxx 10xx xxxx */
                    count += 2;
                    if (count > utflen) {
                        throw new java.io.UTFDataFormatException("malformed input: partial character at end");
                    }
                    char2 = bytearr[count - 1];
                    if ((char2 & 0xc0) != 0x80) {
                        throw new java.io.UTFDataFormatException("malformed input around byte " + count);
                    }
                    chararr[chararr_count++] = ((char) (((c & 0x1f) << 6) | (char2 & 0x3f)));
                    break;
                case 14 :
                    /* 1110 xxxx 10xx xxxx 10xx xxxx */
                    count += 3;
                    if (count > utflen) {
                        throw new java.io.UTFDataFormatException("malformed input: partial character at end");
                    }
                    char2 = bytearr[count - 2];
                    char3 = bytearr[count - 1];
                    if (((char2 & 0xc0) != 0x80) || ((char3 & 0xc0) != 0x80)) {
                        throw new java.io.UTFDataFormatException("malformed input around byte " + (count - 1));
                    }
                    chararr[chararr_count++] = ((char) ((((c & 0xf) << 12) | ((char2 & 0x3f) << 6)) | ((char3 & 0x3f) << 0)));
                    break;
                default :
                    /* 10xx xxxx, 1111 xxxx */
                    throw new java.io.UTFDataFormatException("malformed input around byte " + count);
            }
        } 
        // The number of chars produced may be less than utflen
        return new java.lang.String(chararr, 0, chararr_count);
    }

    /**
     * An index data read result holder
     */
    public static class IndexDataReadResult {
        private java.util.Date timestamp;

        private int documentCount;

        public void setDocumentCount(int documentCount) {
            this.documentCount = documentCount;
        }

        public int getDocumentCount() {
            return documentCount;
        }

        public void setTimestamp(java.util.Date timestamp) {
            this.timestamp = timestamp;
        }

        public java.util.Date getTimestamp() {
            return timestamp;
        }
    }

    /**
     * Reads index content by using a visitor. <br>
     * The visitor is called for each read documents after it has been populated with Lucene fields.
     *
     * @param visitor
     * 		an index data visitor
     * @param context
     * 		indexing context
     * @return statistics about read data
     * @throws IOException
     * 		in case of an IO exception during index file access
     */
    public org.apache.maven.index.updater.IndexDataReader.IndexDataReadResult readIndex(final org.apache.maven.index.updater.IndexDataReader.IndexDataReadVisitor visitor, final org.apache.maven.index.context.IndexingContext context) throws java.io.IOException {
        dis.readByte();// data format version

        long timestamp = dis.readLong();
        java.util.Date date = null;
        if (timestamp != (-1)) {
            date = new java.util.Date(timestamp);
        }
        int n = 0;
        org.apache.lucene.document.Document doc;
        while ((doc = readDocument()) != null) {
            visitor.visitDocument(org.apache.maven.index.context.IndexUtils.updateDocument(doc, context, false));
            n++;
        } 
        org.apache.maven.index.updater.IndexDataReader.IndexDataReadResult result = new org.apache.maven.index.updater.IndexDataReader.IndexDataReadResult();
        result.setDocumentCount(n);
        result.setTimestamp(date);
        return result;
    }

    /**
     * Visitor of indexed Lucene documents.
     */
    public static interface IndexDataReadVisitor {
        /**
         * Called on each read document. The document is already populated with fields.
         *
         * @param document
         * 		read document
         */
        void visitDocument(org.apache.lucene.document.Document document);
    }
}