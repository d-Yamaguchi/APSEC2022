/**
 * Create new Instance.
 *
 * @param dirs
 * 		
 */
public DefaultMultiIndexAccessor(org.apache.lucene.store.Directory[] dirs) {
    org.apache.lucene.search.Similarity _CVAR0 = org.apache.lucene.search.Similarity.getDefault();
    this.similarity = _CVAR0;
    this.dirs = dirs;
}