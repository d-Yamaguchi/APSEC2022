@java.lang.Override
public org.apache.lucene.store.Lock makeLock(final java.lang.String name) {
    org.apache.lucene.store.Lock _CVAR0 = new org.apache.lucene.store.Lock() {
        public boolean obtain() {
            return true;
        }

        public void release() {
        }

        public boolean isLocked() {
            throw new java.lang.UnsupportedOperationException();
        }

        public java.lang.String toString() {
            return "Lock@" + new org.apache.hadoop.fs.Path(directory, name);
        }
    };
    return _CVAR0;
}