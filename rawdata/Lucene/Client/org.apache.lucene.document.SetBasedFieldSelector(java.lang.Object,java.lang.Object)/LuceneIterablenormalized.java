/**
 * Produce a LuceneIterable that can create the Vector plus normalize it.
 *
 * @param reader
 * 		The {@link org.apache.lucene.index.IndexReader} to read the documents from.
 * @param idField
 * 		- The Field containing the id. May be null
 * @param field
 * 		The field to use for the Vector
 * @param mapper
 * 		The {@link org.apache.mahout.utils.vectors.lucene.VectorMapper} for creating
 * 		{@link org.apache.mahout.math.Vector}s from Lucene's TermVectors.
 * @param normPower
 * 		The normalization value. Must be greater than or equal to 0 or equal to {@link #NO_NORMALIZING}
 */
public LuceneIterable(org.apache.lucene.index.IndexReader reader, java.lang.String idField, java.lang.String field, org.apache.mahout.utils.vectors.lucene.VectorMapper mapper, double normPower) {
    if ((normPower != org.apache.mahout.utils.vectors.lucene.LuceneIterable.NO_NORMALIZING) && (normPower < 0)) {
        throw new java.lang.IllegalArgumentException("normPower must either be -1 or >= 0");
    }
    java.lang.String _CVAR0 = idField;
    java.util.Set<java.lang.String> _CVAR1 = java.util.Collections.singleton(_CVAR0);
    java.util.Set<java.lang.String> _CVAR2 = java.util.Collections.emptySet();
    org.apache.lucene.document.SetBasedFieldSelector _CVAR3 = new org.apache.lucene.document.SetBasedFieldSelector(_CVAR1, _CVAR2);
    idFieldSelector = _CVAR3;
    this.indexReader = reader;
    this.idField = idField;
    this.field = field;
    this.mapper = mapper;
    this.normPower = normPower;
}