@java.lang.Override
protected void doMatch(int doc, java.lang.String id, org.apache.lucene.util.BytesRef hash) {
    /* Note on implementation

    The purge works by scanning the query index and creating a new query cache populated
    for each query in the index.  When the scan is complete, the old query cache is swapped
    for the new, allowing it to be garbage-collected.

    In order to not drop cached queries that have been added while a purge is ongoing,
    we use a ReadWriteLock to guard the creation and removal of an update log.  Commits take
    the read lock.  If the update log has been created, then a purge is ongoing, and queries
    are added to the update log within the read lock guard.

    The purge takes the write lock when creating the update log, and then when swapping out
    the old query cache.  Within the second write lock guard, the contents of the update log
    are added to the new query cache, and the update log itself is removed.
     */
    final java.util.Map<org.apache.lucene.util.BytesRef, uk.co.flax.luwak.Monitor.CacheEntry> newCache = new java.util.concurrent.ConcurrentHashMap<>();
    org.apache.lucene.util.BytesRef _CVAR1 = hash;
    java.util.Map<org.apache.lucene.util.BytesRef, uk.co.flax.luwak.Monitor.CacheEntry> _CVAR3 = queries;
    org.apache.lucene.util.BytesRef _CVAR4 = hash;
    java.util.Map<org.apache.lucene.util.BytesRef, uk.co.flax.luwak.Monitor.CacheEntry> _CVAR0 = newCache;
    org.apache.lucene.util.BytesRef _CVAR2 = _CVAR1.clone();
    uk.co.flax.luwak.Monitor.CacheEntry _CVAR5 = _CVAR3.get(_CVAR4);
    _CVAR0.put(_CVAR2, _CVAR5);
}