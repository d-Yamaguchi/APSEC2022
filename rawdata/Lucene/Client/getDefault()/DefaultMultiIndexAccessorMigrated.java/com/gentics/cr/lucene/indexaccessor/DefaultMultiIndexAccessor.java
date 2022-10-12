package com.gentics.cr.lucene.indexaccessor;
import org.apache.log4j.Logger;
/**
 * Default MultiIndexAccessor implementation.
 *
 * Last changed: $Date: 2009-09-02 17:57:48 +0200 (Mi, 02 Sep 2009) $
 *
 * @version $Revision: 180 $
 * @author $Author: supnig@constantinopel.at $
 */
public class DefaultMultiIndexAccessor implements com.gentics.cr.lucene.indexaccessor.IndexAccessor {
    /**
     * Log4j logger for error and debug messages.
     */
    private static final org.apache.log4j.Logger LOGGER = org.apache.log4j.Logger.getLogger(com.gentics.cr.lucene.indexaccessor.DefaultMultiIndexAccessor.class);

    private final java.util.Map<org.apache.lucene.search.IndexSearcher, com.gentics.cr.lucene.indexaccessor.IndexAccessor> multiSearcherAccessors = new java.util.HashMap<org.apache.lucene.search.IndexSearcher, com.gentics.cr.lucene.indexaccessor.IndexAccessor>();

    private final java.util.Map<org.apache.lucene.index.IndexReader, com.gentics.cr.lucene.indexaccessor.IndexAccessor> multiReaderAccessors = new java.util.HashMap<org.apache.lucene.index.IndexReader, com.gentics.cr.lucene.indexaccessor.IndexAccessor>();

    private org.apache.lucene.search.Similarity similarity;

    private org.apache.lucene.store.Directory[] dirs;

    /**
     * Create new Instance.
     *
     * @param dirs
     * 		
     */
    public DefaultMultiIndexAccessor(org.apache.lucene.store.Directory[] dirs) {
        this.similarity = IndexSearcher.getDefaultSimilarity();
        this.dirs = dirs;
    }

    /**
     * Create new instance.
     *
     * @param dirs
     * 		
     * @param similarity
     * 		
     */
    public DefaultMultiIndexAccessor(org.apache.lucene.store.Directory[] dirs, org.apache.lucene.search.Similarity similarity) {
        this.similarity = similarity;
        this.dirs = dirs;
    }

    /* (non-Javadoc)
    @see com.mhs.indexaccessor.MultiIndexAccessor#release(org.apache.lucene.search.Searcher)
     */
    public synchronized void release(org.apache.lucene.search.IndexSearcher multiSearcher) {
        org.apache.lucene.index.IndexReader reader = multiSearcher.getIndexReader();
        release(reader, false);
    }

    /**
     * Closes all index accessors contained in the multi accessor.
     */
    public void close() {
        for (java.util.Map.Entry<org.apache.lucene.search.IndexSearcher, com.gentics.cr.lucene.indexaccessor.IndexAccessor> iae : this.multiSearcherAccessors.entrySet()) {
            com.gentics.cr.lucene.indexaccessor.IndexAccessor ia = iae.getValue();
            if (ia.isOpen()) {
                ia.close();
            }
        }
        for (java.util.Map.Entry<org.apache.lucene.index.IndexReader, com.gentics.cr.lucene.indexaccessor.IndexAccessor> iae : this.multiReaderAccessors.entrySet()) {
            com.gentics.cr.lucene.indexaccessor.IndexAccessor ia = iae.getValue();
            if (ia.isOpen()) {
                ia.close();
            }
        }
    }

    public org.apache.lucene.search.IndexSearcher getPrioritizedSearcher() throws java.io.IOException {
        return getSearcher();
    }

    public org.apache.lucene.index.IndexReader getReader(boolean write) throws java.io.IOException {
        if (write) {
            throw new java.lang.UnsupportedOperationException();
        }
        org.apache.lucene.index.IndexReader[] readers = new org.apache.lucene.index.IndexReader[this.dirs.length];
        com.gentics.cr.lucene.indexaccessor.IndexAccessorFactory factory = com.gentics.cr.lucene.indexaccessor.IndexAccessorFactory.getInstance();
        int i = 0;
        for (org.apache.lucene.store.Directory index : this.dirs) {
            com.gentics.cr.lucene.indexaccessor.IndexAccessor indexAccessor = factory.getAccessor(index);
            readers[i] = indexAccessor.getReader(false);
            multiReaderAccessors.put(readers[i], indexAccessor);
            i++;
        }
        org.apache.lucene.index.MultiReader multiReader = new org.apache.lucene.index.MultiReader(readers, true);
        return multiReader;
    }

    /**
     * Get directories.
     *
     * @return 
     */
    public org.apache.lucene.store.Directory[] getDirectories() {
        return dirs;
    }

    public org.apache.lucene.search.IndexSearcher getSearcher() throws java.io.IOException {
        return getSearcher(this.similarity, null);
    }

    public org.apache.lucene.search.IndexSearcher getSearcher(org.apache.lucene.index.IndexReader indexReader) throws java.io.IOException {
        return getSearcher(this.similarity, indexReader);
    }

    public org.apache.lucene.search.IndexSearcher getSearcher(org.apache.lucene.search.Similarity similarity) throws java.io.IOException {
        return getSearcher(similarity, null);
    }

    public org.apache.lucene.search.IndexSearcher getSearcher(final org.apache.lucene.search.Similarity similarity, final org.apache.lucene.index.IndexReader indexReader) throws java.io.IOException {
        org.apache.lucene.index.IndexReader ir = indexReader;
        if (ir == null) {
            org.apache.lucene.index.IndexReader[] readers = new org.apache.lucene.index.IndexReader[this.dirs.length];
            com.gentics.cr.lucene.indexaccessor.IndexAccessorFactory factory = com.gentics.cr.lucene.indexaccessor.IndexAccessorFactory.getInstance();
            int i = 0;
            for (org.apache.lucene.store.Directory index : this.dirs) {
                com.gentics.cr.lucene.indexaccessor.IndexAccessor indexAccessor = factory.getAccessor(index);
                readers[i] = indexAccessor.getReader(false);
                multiReaderAccessors.put(readers[i], indexAccessor);
                i++;
            }
            ir = new org.apache.lucene.index.MultiReader(readers, false);
        }
        org.apache.lucene.search.IndexSearcher multiSearcher = new org.apache.lucene.search.IndexSearcher(ir);
        multiSearcher.setSimilarity(similarity);
        return multiSearcher;
    }

    public org.apache.lucene.index.IndexWriter getWriter() throws java.io.IOException {
        throw new java.lang.UnsupportedOperationException();
    }

    public boolean isOpen() {
        boolean open = true;
        com.gentics.cr.lucene.indexaccessor.IndexAccessorFactory factory = com.gentics.cr.lucene.indexaccessor.IndexAccessorFactory.getInstance();
        for (org.apache.lucene.store.Directory index : this.dirs) {
            com.gentics.cr.lucene.indexaccessor.IndexAccessor indexAccessor = factory.getAccessor(index);
            if (!indexAccessor.isOpen()) {
                open = false;
            }
        }
        return open;
    }

    public boolean isLocked() {
        boolean locked = false;
        for (org.apache.lucene.store.Directory d : this.dirs) {
            try {
                locked = org.apache.lucene.index.IndexWriter.isLocked(d);
            } catch (java.io.IOException e) {
                com.gentics.cr.lucene.indexaccessor.DefaultMultiIndexAccessor.LOGGER.error(e);
            }
            if (locked) {
                break;
            }
        }
        return locked;
    }

    public void open() {
        com.gentics.cr.lucene.indexaccessor.IndexAccessorFactory factory = com.gentics.cr.lucene.indexaccessor.IndexAccessorFactory.getInstance();
        for (org.apache.lucene.store.Directory index : this.dirs) {
            com.gentics.cr.lucene.indexaccessor.IndexAccessor indexAccessor = factory.getAccessor(index);
            indexAccessor.open();
        }
    }

    public int readingReadersOut() {
        int usecount = 0;
        com.gentics.cr.lucene.indexaccessor.IndexAccessorFactory factory = com.gentics.cr.lucene.indexaccessor.IndexAccessorFactory.getInstance();
        for (org.apache.lucene.store.Directory index : this.dirs) {
            com.gentics.cr.lucene.indexaccessor.IndexAccessor indexAccessor = factory.getAccessor(index);
            usecount += indexAccessor.readingReadersOut();
        }
        return usecount;
    }

    public void release(org.apache.lucene.index.IndexReader reader, boolean write) {
        if (reader instanceof org.apache.lucene.index.MultiReader) {
            org.apache.lucene.index.IndexReader[] readers = ((org.apache.lucene.index.MultiReader) (reader)).getSequentialSubReaders();
            for (org.apache.lucene.index.IndexReader r : readers) {
                com.gentics.cr.lucene.indexaccessor.IndexAccessor accessor = multiReaderAccessors.get(r);
                if (accessor != null) {
                    accessor.release(r, write);
                    multiReaderAccessors.remove(r);
                }
            }
        }
    }

    public void release(org.apache.lucene.index.IndexWriter writer) {
        throw new java.lang.UnsupportedOperationException();
    }

    public int searcherUseCount() {
        int usecount = 0;
        com.gentics.cr.lucene.indexaccessor.IndexAccessorFactory factory = com.gentics.cr.lucene.indexaccessor.IndexAccessorFactory.getInstance();
        for (org.apache.lucene.store.Directory index : this.dirs) {
            com.gentics.cr.lucene.indexaccessor.IndexAccessor indexAccessor = factory.getAccessor(index);
            usecount += indexAccessor.searcherUseCount();
        }
        return usecount;
    }

    public int writerUseCount() {
        int usecount = 0;
        com.gentics.cr.lucene.indexaccessor.IndexAccessorFactory factory = com.gentics.cr.lucene.indexaccessor.IndexAccessorFactory.getInstance();
        for (org.apache.lucene.store.Directory index : this.dirs) {
            com.gentics.cr.lucene.indexaccessor.IndexAccessor indexAccessor = factory.getAccessor(index);
            usecount += indexAccessor.writerUseCount();
        }
        return usecount;
    }

    public int writingReadersUseCount() {
        int usecount = 0;
        com.gentics.cr.lucene.indexaccessor.IndexAccessorFactory factory = com.gentics.cr.lucene.indexaccessor.IndexAccessorFactory.getInstance();
        for (org.apache.lucene.store.Directory index : this.dirs) {
            com.gentics.cr.lucene.indexaccessor.IndexAccessor indexAccessor = factory.getAccessor(index);
            usecount += indexAccessor.writingReadersUseCount();
        }
        return usecount;
    }

    public void reopen() throws java.io.IOException {
        com.gentics.cr.lucene.indexaccessor.IndexAccessorFactory factory = com.gentics.cr.lucene.indexaccessor.IndexAccessorFactory.getInstance();
        for (org.apache.lucene.store.Directory index : this.dirs) {
            com.gentics.cr.lucene.indexaccessor.IndexAccessor indexAccessor = factory.getAccessor(index);
            indexAccessor.reopen();
        }
    }
}