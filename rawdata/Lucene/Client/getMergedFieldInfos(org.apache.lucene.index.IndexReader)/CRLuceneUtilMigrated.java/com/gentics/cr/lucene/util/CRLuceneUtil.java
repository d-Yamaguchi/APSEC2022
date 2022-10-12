package com.gentics.cr.lucene.util;
public class CRLuceneUtil {
    /**
     * Utility class.
     */
    private CRLuceneUtil() {
    }

    /**
     * Get the list of field names from an index reader.
     *
     * @param reader
     * 		
     * @return 
     */
    public static java.util.List<java.lang.String> getFieldNames(org.apache.lucene.index.IndexReader reader) {
        java.util.List<java.lang.String> nameList = new java.util.Vector<java.lang.String>();
        AtomicReader aReader = aRC.reader();
        for (org.apache.lucene.index.FieldInfo info : ) {
            nameList.add(info.name);
        }
        return nameList;
    }
}