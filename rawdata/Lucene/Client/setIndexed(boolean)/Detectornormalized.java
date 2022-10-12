/**
 * Indexes the given file using the given writer, or if a directory is
 * given, recurses over files and directories found under the given
 * directory.
 *
 * @param writer
 * 		Writer to the index where the given file/dir info will be
 * 		stored
 * @param file
 * 		The file to index, or the directory to recurse into to find
 * 		files to index
 * @throws IOException
 * 		If there is a low-level I/O error
 * @see org.apache.lucene.demo.IndexFiles#indexDocs(IndexWriter, File)
 */
private void indexDocs(org.apache.lucene.index.IndexWriter writer, java.io.File file) throws java.io.IOException {
    // do not try to index files that cannot be read
    if (file.canRead()) {
        if (file.isDirectory()) {
            java.lang.String[] tmp = file.list();
            java.util.List<java.lang.String> l = new java.util.ArrayList<java.lang.String>();
            for (java.lang.String s : tmp) {
                if (!isIgnored(s)) {
                    l.add(s);
                }
            }
            java.lang.String[] files = l.toArray(new java.lang.String[l.size()]);
            // an IO error could occur
            if (files != null) {
                for (int i = 0; i < files.length; i++) {
                    indexDocs(writer, new java.io.File(file, files[i]));
                }
            }
        } else {
            java.io.FileInputStream fis;
            try {
                fis = new java.io.FileInputStream(file);
            } catch (java.io.FileNotFoundException fnfe) {
                return;
            }
            try {
                // make a new, empty document
                org.apache.lucene.document.Document doc = new org.apache.lucene.document.Document();
                // Add the path of the file as a field named "path". Use a
                // field that is indexed (i.e. searchable), but don't
                // tokenize
                // the field into separate words and don't index term
                // frequency
                // or positional information:
                org.apache.lucene.document.Field pathField = new org.apache.lucene.document.StringField(it.uliana.cpd.Detector.FIELD_PATH, file.getPath(), org.apache.lucene.document.Field.Store.YES);
                doc.add(pathField);
                // custom field type
                org.apache.lucene.document.FieldType type = new org.apache.lucene.document.FieldType();
                org.apache.lucene.document.FieldType _CVAR0 = type;
                boolean _CVAR1 = true;
                _CVAR0.setIndexed(_CVAR1);
                type.setTokenized(true);
                type.setStored(true);
                type.setStoreTermVectors(true);
                org.apache.lucene.document.Field f = new org.apache.lucene.document.Field(it.uliana.cpd.Detector.FIELD_CONTENTS, org.apache.commons.io.IOUtils.toString(fis), type);
                doc.add(f);
                it.uliana.cpd.Detector.LOGGER.debug("adding " + file);
                writer.addDocument(doc);
            } finally {
                fis.close();
            }
        }
    }
}