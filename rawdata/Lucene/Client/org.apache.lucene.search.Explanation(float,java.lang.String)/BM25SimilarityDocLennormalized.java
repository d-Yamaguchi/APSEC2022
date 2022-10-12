/**
 * Computes a score factor for a simple term and returns an explanation
 * for that score factor.
 *
 * <p>
 * The default implementation uses:
 *
 * <pre class="prettyprint">
 * idf(docFreq, searcher.maxDoc());
 * </pre>
 *
 * Note that {@link CollectionStatistics#maxDoc()} is used instead of
 * {@link org.apache.lucene.index.IndexReader#numDocs() IndexReader#numDocs()} because also
 * {@link TermStatistics#docFreq()} is used, and when the latter
 * is inaccurate, so is {@link CollectionStatistics#maxDoc()}, and in the same direction.
 * In addition, {@link CollectionStatistics#maxDoc()} is more efficient to compute
 *
 * @param collectionStats
 * 		collection-level statistics
 * @param termStats
 * 		term-level statistics for the term
 * @return an Explain object that includes both an idf score factor
and an explanation for the term.
 */
public org.apache.lucene.search.Explanation idfExplain(org.apache.lucene.search.CollectionStatistics collectionStats, org.apache.lucene.search.TermStatistics termStats) {
    org.apache.lucene.search.TermStatistics _CVAR0 = termStats;
    final long df = _CVAR0.docFreq();
    org.apache.lucene.search.CollectionStatistics _CVAR2 = collectionStats;
    final long max = _CVAR2.maxDoc();
    long _CVAR1 = df;
    long _CVAR3 = max;
    final float idf = idf(_CVAR1, _CVAR3);
    java.lang.String _CVAR5 = ")";
    float _CVAR4 = idf;
    java.lang.String _CVAR6 = ((("idf(docFreq=" + df) + ", maxDocs=") + max) + _CVAR5;
    org.apache.lucene.search.Explanation _CVAR7 = new org.apache.lucene.search.Explanation(_CVAR4, _CVAR6);
    return _CVAR7;
}