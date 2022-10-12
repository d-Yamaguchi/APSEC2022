/**
 * Get the list of field names from an index reader.
 *
 * @param reader
 * 		
 * @return 
 */
public static java.util.List<java.lang.String> getFieldNames(org.apache.lucene.index.IndexReader reader) {
    java.util.List<java.lang.String> nameList = new java.util.Vector<java.lang.String>();
    org.apache.lucene.index.IndexReader _CVAR0 = reader;
    org.apache.lucene.index.FieldInfos _CVAR1 = org.apache.lucene.util.ReaderUtil.getMergedFieldInfos(_CVAR0);
    for (org.apache.lucene.index.FieldInfo info : ) {
        nameList.add(info.name);
    }
    return nameList;
}