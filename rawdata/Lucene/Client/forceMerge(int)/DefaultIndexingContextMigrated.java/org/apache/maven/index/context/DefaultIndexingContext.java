package org.apache.maven.index.context;
import org.apache.lucene.index.MultiFields;
import org.apache.maven.index.ArtifactInfo;
import org.apache.maven.index.artifact.GavCalculator;
import org.apache.maven.index.artifact.M2GavCalculator;
/**
 * The default {@link IndexingContext} implementation.
 *
 * @author Jason van Zyl
 * @author Tamas Cservenak
 */
public class DefaultIndexingContext extends org.apache.maven.index.context.AbstractIndexingContext {
    /**
     * A standard location for indices served up by a webserver.
     */
    private static final java.lang.String INDEX_DIRECTORY = ".index";

    public static final java.lang.String FLD_DESCRIPTOR = "DESCRIPTOR";

    public static final java.lang.String FLD_DESCRIPTOR_CONTENTS = "NexusIndex";

    public static final java.lang.String FLD_IDXINFO = "IDXINFO";

    public static final java.lang.String VERSION = "1.0";

    private static final org.apache.lucene.index.Term DESCRIPTOR_TERM = new org.apache.lucene.index.Term(org.apache.maven.index.context.DefaultIndexingContext.FLD_DESCRIPTOR, org.apache.maven.index.context.DefaultIndexingContext.FLD_DESCRIPTOR_CONTENTS);

    private org.apache.lucene.store.Directory indexDirectory;

    private java.io.File indexDirectoryFile;

    private java.lang.String id;

    private boolean searchable;

    private java.lang.String repositoryId;

    private java.io.File repository;

    private java.lang.String repositoryUrl;

    private java.lang.String indexUpdateUrl;

    private org.apache.maven.index.context.NexusIndexWriter indexWriter;

    private org.apache.lucene.search.SearcherManager searcherManager;

    private java.util.Date timestamp;

    private java.util.List<? extends org.apache.maven.index.context.IndexCreator> indexCreators;

    /**
     * Currently nexus-indexer knows only M2 reposes
     * <p>
     * XXX move this into a concrete Scanner implementation
     */
    private org.apache.maven.index.artifact.GavCalculator gavCalculator;

    private DefaultIndexingContext(java.lang.String id, java.lang.String repositoryId, java.io.File repository, // 
    java.lang.String repositoryUrl, java.lang.String indexUpdateUrl, java.util.List<? extends org.apache.maven.index.context.IndexCreator> indexCreators, org.apache.lucene.store.Directory indexDirectory, boolean reclaimIndex) throws org.apache.maven.index.context.ExistingLuceneIndexMismatchException, java.io.IOException {
        this.id = id;
        this.searchable = true;
        this.repositoryId = repositoryId;
        this.repository = repository;
        this.repositoryUrl = repositoryUrl;
        this.indexUpdateUrl = indexUpdateUrl;
        this.indexWriter = null;
        this.searcherManager = null;
        this.indexCreators = indexCreators;
        this.indexDirectory = indexDirectory;
        // eh?
        // Guice does NOT initialize these, and we have to do manually?
        // While in Plexus, all is well, but when in guice-shim,
        // these objects are still LazyHintedBeans or what not and IndexerFields are NOT registered!
        for (org.apache.maven.index.context.IndexCreator indexCreator : indexCreators) {
            indexCreator.getIndexerFields();
        }
        this.gavCalculator = new org.apache.maven.index.artifact.M2GavCalculator();
        prepareIndex(reclaimIndex);
        setIndexDirectoryFile(null);
    }

    public DefaultIndexingContext(java.lang.String id, java.lang.String repositoryId, java.io.File repository, java.io.File indexDirectoryFile, java.lang.String repositoryUrl, java.lang.String indexUpdateUrl, java.util.List<? extends org.apache.maven.index.context.IndexCreator> indexCreators, boolean reclaimIndex) throws java.io.IOException, org.apache.maven.index.context.ExistingLuceneIndexMismatchException {
        this(id, repositoryId, repository, repositoryUrl, indexUpdateUrl, indexCreators, org.apache.lucene.store.FSDirectory.open(indexDirectoryFile), reclaimIndex);
        setIndexDirectoryFile(indexDirectoryFile);
    }

    @java.lang.Deprecated
    public DefaultIndexingContext(java.lang.String id, java.lang.String repositoryId, java.io.File repository, org.apache.lucene.store.Directory indexDirectory, java.lang.String repositoryUrl, java.lang.String indexUpdateUrl, java.util.List<? extends org.apache.maven.index.context.IndexCreator> indexCreators, boolean reclaimIndex) throws java.io.IOException, org.apache.maven.index.context.ExistingLuceneIndexMismatchException {
        this(id, repositoryId, repository, repositoryUrl, indexUpdateUrl, indexCreators, indexDirectory, reclaimIndex);
        if (indexDirectory instanceof org.apache.lucene.store.FSDirectory) {
            setIndexDirectoryFile(((org.apache.lucene.store.FSDirectory) (indexDirectory)).getDirectory());
        }
    }

    public org.apache.lucene.store.Directory getIndexDirectory() {
        return indexDirectory;
    }

    /**
     * Sets index location. As usually index is persistent (is on disk), this will point to that value, but in
     * some circumstances (ie, using RAMDisk for index), this will point to an existing tmp directory.
     */
    protected void setIndexDirectoryFile(java.io.File dir) throws java.io.IOException {
        if (dir == null) {
            // best effort, to have a directory thru the life of a ctx
            java.io.File tmpFile = java.io.File.createTempFile("mindexer-ctx" + id, "tmp");
            tmpFile.delete();
            tmpFile.mkdirs();
            this.indexDirectoryFile = tmpFile;
        } else {
            this.indexDirectoryFile = dir;
        }
    }

    public java.io.File getIndexDirectoryFile() {
        return indexDirectoryFile;
    }

    private void prepareIndex(boolean reclaimIndex) throws java.io.IOException, org.apache.maven.index.context.ExistingLuceneIndexMismatchException {
        if (org.apache.lucene.index.DirectoryReader.indexExists(indexDirectory)) {
            try {
                // unlock the dir forcibly
                if (org.apache.lucene.index.IndexWriter.isLocked(indexDirectory)) {
                    org.apache.lucene.index.IndexWriter.unlock(indexDirectory);
                }
                openAndWarmup();
                checkAndUpdateIndexDescriptor(reclaimIndex);
            } catch (java.io.IOException e) {
                if (reclaimIndex) {
                    prepareCleanIndex(true);
                } else {
                    throw e;
                }
            }
        } else {
            prepareCleanIndex(false);
        }
        timestamp = org.apache.maven.index.context.IndexUtils.getTimestamp(indexDirectory);
    }

    private void prepareCleanIndex(boolean deleteExisting) throws java.io.IOException {
        if (deleteExisting) {
            closeReaders();
            // unlock the dir forcibly
            if (org.apache.lucene.index.IndexWriter.isLocked(indexDirectory)) {
                org.apache.lucene.index.IndexWriter.unlock(indexDirectory);
            }
            deleteIndexFiles(true);
        }
        openAndWarmup();
        if (org.codehaus.plexus.util.StringUtils.isEmpty(getRepositoryId())) {
            throw new java.lang.IllegalArgumentException("The repositoryId cannot be null when creating new repository!");
        }
        storeDescriptor();
    }

    private void checkAndUpdateIndexDescriptor(boolean reclaimIndex) throws java.io.IOException, org.apache.maven.index.context.ExistingLuceneIndexMismatchException {
        if (reclaimIndex) {
            // forcefully "reclaiming" the ownership of the index as ours
            storeDescriptor();
            return;
        }
        // check for descriptor if this is not a "virgin" index
        if (getSize() > 0) {
            final org.apache.lucene.search.TopScoreDocCollector collector = org.apache.lucene.search.TopScoreDocCollector.create(1, false);
            final org.apache.lucene.search.IndexSearcher indexSearcher = acquireIndexSearcher();
            try {
                indexSearcher.search(new org.apache.lucene.search.TermQuery(org.apache.maven.index.context.DefaultIndexingContext.DESCRIPTOR_TERM), collector);
                if (collector.getTotalHits() == 0) {
                    throw new org.apache.maven.index.context.ExistingLuceneIndexMismatchException("The existing index has no NexusIndexer descriptor");
                }
                if (collector.getTotalHits() > 1) {
                    // eh? this is buggy index it seems, just iron it out then
                    storeDescriptor();
                    return;
                } else {
                    // good, we have one descriptor as should
                    org.apache.lucene.document.Document descriptor = indexSearcher.doc(collector.topDocs().scoreDocs[0].doc);
                    java.lang.String[] h = org.codehaus.plexus.util.StringUtils.split(descriptor.get(org.apache.maven.index.context.DefaultIndexingContext.FLD_IDXINFO), ArtifactInfo.FS);
                    // String version = h[0];
                    java.lang.String repoId = h[1];
                    // // compare version
                    // if ( !VERSION.equals( version ) )
                    // {
                    // throw new UnsupportedExistingLuceneIndexException(
                    // "The existing index has version [" + version + "] and not [" + VERSION + "] version!" );
                    // }
                    if (getRepositoryId() == null) {
                        repositoryId = repoId;
                    } else if (!getRepositoryId().equals(repoId)) {
                        throw new org.apache.maven.index.context.ExistingLuceneIndexMismatchException((((("The existing index is for repository "// 
                         + "[") + repoId) + "] and not for repository [") + getRepositoryId()) + "]");
                    }
                }
            } finally {
                releaseIndexSearcher(indexSearcher);
            }
        }
    }

    private void storeDescriptor() throws java.io.IOException {
        org.apache.lucene.document.Document hdr = new org.apache.lucene.document.Document();
        hdr.add(new org.apache.lucene.document.Field(org.apache.maven.index.context.DefaultIndexingContext.FLD_DESCRIPTOR, org.apache.maven.index.context.DefaultIndexingContext.FLD_DESCRIPTOR_CONTENTS, org.apache.lucene.document.Field.Store.YES, org.apache.lucene.document.Field.Index.NOT_ANALYZED));
        hdr.add(new org.apache.lucene.document.Field(org.apache.maven.index.context.DefaultIndexingContext.FLD_IDXINFO, (org.apache.maven.index.context.DefaultIndexingContext.VERSION + org.apache.maven.index.ArtifactInfo.FS) + getRepositoryId(), org.apache.lucene.document.Field.Store.YES, org.apache.lucene.document.Field.Index.NO));
        org.apache.lucene.index.IndexWriter w = getIndexWriter();
        w.updateDocument(org.apache.maven.index.context.DefaultIndexingContext.DESCRIPTOR_TERM, hdr);
        w.commit();
    }

    private void deleteIndexFiles(boolean full) throws java.io.IOException {
        if (indexDirectory != null) {
            java.lang.String[] names = indexDirectory.listAll();
            if (names != null) {
                for (java.lang.String name : names) {
                    if (!(name.equals(org.apache.maven.index.context.INDEX_PACKER_PROPERTIES_FILE) || name.equals(org.apache.maven.index.context.INDEX_UPDATER_PROPERTIES_FILE))) {
                        indexDirectory.deleteFile(name);
                    }
                }
            }
            if (full) {
                if (indexDirectory.fileExists(org.apache.maven.index.context.INDEX_PACKER_PROPERTIES_FILE)) {
                    indexDirectory.deleteFile(org.apache.maven.index.context.INDEX_PACKER_PROPERTIES_FILE);
                }
                if (indexDirectory.fileExists(org.apache.maven.index.context.INDEX_UPDATER_PROPERTIES_FILE)) {
                    indexDirectory.deleteFile(org.apache.maven.index.context.INDEX_UPDATER_PROPERTIES_FILE);
                }
            }
            org.apache.maven.index.context.IndexUtils.deleteTimestamp(indexDirectory);
        }
    }

    // ==
    public boolean isSearchable() {
        return searchable;
    }

    public void setSearchable(boolean searchable) {
        this.searchable = searchable;
    }

    public java.lang.String getId() {
        return id;
    }

    public void updateTimestamp() throws java.io.IOException {
        updateTimestamp(false);
    }

    public void updateTimestamp(boolean save) throws java.io.IOException {
        updateTimestamp(save, new java.util.Date());
    }

    public void updateTimestamp(boolean save, java.util.Date timestamp) throws java.io.IOException {
        this.timestamp = timestamp;
        if (save) {
            org.apache.maven.index.context.IndexUtils.updateTimestamp(indexDirectory, getTimestamp());
        }
    }

    public java.util.Date getTimestamp() {
        return timestamp;
    }

    public int getSize() throws java.io.IOException {
        final org.apache.lucene.search.IndexSearcher is = acquireIndexSearcher();
        try {
            return is.getIndexReader().numDocs();
        } finally {
            releaseIndexSearcher(is);
        }
    }

    public java.lang.String getRepositoryId() {
        return repositoryId;
    }

    public java.io.File getRepository() {
        return repository;
    }

    public java.lang.String getRepositoryUrl() {
        return repositoryUrl;
    }

    public java.lang.String getIndexUpdateUrl() {
        if (repositoryUrl != null) {
            if ((indexUpdateUrl == null) || (indexUpdateUrl.trim().length() == 0)) {
                return (repositoryUrl + (repositoryUrl.endsWith("/") ? "" : "/")) + org.apache.maven.index.context.DefaultIndexingContext.INDEX_DIRECTORY;
            }
        }
        return indexUpdateUrl;
    }

    public org.apache.lucene.analysis.Analyzer getAnalyzer() {
        return new org.apache.maven.index.context.NexusAnalyzer();
    }

    protected void openAndWarmup() throws java.io.IOException {
        // IndexWriter (close)
        if (indexWriter != null) {
            indexWriter.close();
            indexWriter = null;
        }
        if (searcherManager != null) {
            searcherManager.close();
            searcherManager = null;
        }
        this.indexWriter = new org.apache.maven.index.context.NexusIndexWriter(getIndexDirectory(), getWriterConfig());
        this.indexWriter.commit();// LUCENE-2386

        this.searcherManager = new org.apache.lucene.search.SearcherManager(indexWriter, false, new org.apache.maven.index.context.NexusIndexSearcherFactory(this));
    }

    /**
     * Returns new IndexWriterConfig instance
     *
     * @since 5.1
     */
    protected org.apache.lucene.index.IndexWriterConfig getWriterConfig() {
        return org.apache.maven.index.context.NexusIndexWriter.defaultConfig();
    }

    public org.apache.lucene.index.IndexWriter getIndexWriter() throws java.io.IOException {
        return indexWriter;
    }

    public org.apache.lucene.search.IndexSearcher acquireIndexSearcher() throws java.io.IOException {
        // TODO: move this to separate thread to not penalty next incoming searcher
        searcherManager.maybeRefresh();
        return searcherManager.acquire();
    }

    public void releaseIndexSearcher(final org.apache.lucene.search.IndexSearcher is) throws java.io.IOException {
        if (is == null) {
            return;
        }
        searcherManager.release(is);
    }

    public void commit() throws java.io.IOException {
        getIndexWriter().commit();
    }

    public void rollback() throws java.io.IOException {
        getIndexWriter().rollback();
    }

    public synchronized void optimize() throws org.apache.lucene.index.CorruptIndexException, java.io.IOException {
        <nulltype>();
        _VAR1 = null;
        <nulltype>();
        _VAR2 = null;
        replace(indexDirectory, _VAR1, _VAR2);
        commit();
    }

    public synchronized void close(boolean deleteFiles) throws java.io.IOException {
        if (indexDirectory != null) {
            org.apache.maven.index.context.IndexUtils.updateTimestamp(indexDirectory, getTimestamp());
            closeReaders();
            if (deleteFiles) {
                deleteIndexFiles(true);
            }
            indexDirectory.close();
        }
        indexDirectory = null;
    }

    public synchronized void purge() throws java.io.IOException {
        closeReaders();
        deleteIndexFiles(true);
        openAndWarmup();
        try {
            prepareIndex(true);
        } catch (org.apache.maven.index.context.ExistingLuceneIndexMismatchException e) {
            // just deleted it
        }
        rebuildGroups();
        updateTimestamp(true, null);
    }

    public synchronized void replace(org.apache.lucene.store.Directory directory) throws java.io.IOException {
        final java.util.Date ts = org.apache.maven.index.context.IndexUtils.getTimestamp(directory);
        closeReaders();
        deleteIndexFiles(false);
        org.apache.maven.index.context.IndexUtils.copyDirectory(directory, indexDirectory);
        openAndWarmup();
        // reclaim the index as mine
        storeDescriptor();
        rebuildGroups();
        updateTimestamp(true, ts);
        optimize();
    }

    public synchronized void merge(org.apache.lucene.store.Directory directory) throws java.io.IOException {
        merge(directory, null);
    }

    public synchronized void merge(org.apache.lucene.store.Directory directory, org.apache.maven.index.context.DocumentFilter filter) throws java.io.IOException {
        final org.apache.lucene.search.IndexSearcher s = acquireIndexSearcher();
        try {
            final org.apache.lucene.index.IndexWriter w = getIndexWriter();
            final org.apache.lucene.index.IndexReader directoryReader = org.apache.lucene.index.IndexReader.open(directory);
            org.apache.lucene.search.TopScoreDocCollector collector = null;
            try {
                int numDocs = directoryReader.maxDoc();
                org.apache.lucene.util.Bits liveDocs = org.apache.lucene.index.MultiFields.getLiveDocs(directoryReader);
                for (int i = 0; i < numDocs; i++) {
                    if ((liveDocs != null) && (!liveDocs.get(i))) {
                        continue;
                    }
                    org.apache.lucene.document.Document d = directoryReader.document(i);
                    if ((filter != null) && (!filter.accept(d))) {
                        continue;
                    }
                    java.lang.String uinfo = d.get(ArtifactInfo.UINFO);
                    if (uinfo != null) {
                        collector = org.apache.lucene.search.TopScoreDocCollector.create(1, false);
                        s.search(new org.apache.lucene.search.TermQuery(new org.apache.lucene.index.Term(org.apache.maven.index.ArtifactInfo.UINFO, uinfo)), collector);
                        if (collector.getTotalHits() == 0) {
                            w.addDocument(org.apache.maven.index.context.IndexUtils.updateDocument(d, this, false));
                        }
                    } else {
                        java.lang.String deleted = d.get(ArtifactInfo.DELETED);
                        if (deleted != null) {
                            // Deleting the document loses history that it was delete,
                            // so incrementals wont work. Therefore, put the delete
                            // document in as well
                            w.deleteDocuments(new org.apache.lucene.index.Term(org.apache.maven.index.ArtifactInfo.UINFO, deleted));
                            w.addDocument(d);
                        }
                    }
                }
            } finally {
                directoryReader.close();
                commit();
            }
            rebuildGroups();
            java.util.Date mergedTimestamp = org.apache.maven.index.context.IndexUtils.getTimestamp(directory);
            if (((getTimestamp() != null) && (mergedTimestamp != null)) && mergedTimestamp.after(getTimestamp())) {
                // we have both, keep the newest
                updateTimestamp(true, mergedTimestamp);
            } else {
                updateTimestamp(true);
            }
            optimize();
        } finally {
            releaseIndexSearcher(s);
        }
    }

    private void closeReaders() throws org.apache.lucene.index.CorruptIndexException, java.io.IOException {
        if (searcherManager != null) {
            searcherManager.close();
            searcherManager = null;
        }
        if (indexWriter != null) {
            indexWriter.close();
            indexWriter = null;
        }
    }

    public org.apache.maven.index.artifact.GavCalculator getGavCalculator() {
        return gavCalculator;
    }

    public java.util.List<org.apache.maven.index.context.IndexCreator> getIndexCreators() {
        return java.util.Collections.unmodifiableList(indexCreators);
    }

    // groups
    public synchronized void rebuildGroups() throws java.io.IOException {
        final org.apache.lucene.search.IndexSearcher is = acquireIndexSearcher();
        try {
            final org.apache.lucene.index.IndexReader r = is.getIndexReader();
            java.util.Set<java.lang.String> rootGroups = new java.util.LinkedHashSet<java.lang.String>();
            java.util.Set<java.lang.String> allGroups = new java.util.LinkedHashSet<java.lang.String>();
            int numDocs = r.maxDoc();
            org.apache.lucene.util.Bits liveDocs = org.apache.lucene.index.MultiFields.getLiveDocs(r);
            for (int i = 0; i < numDocs; i++) {
                if ((liveDocs != null) && (!liveDocs.get(i))) {
                    continue;
                }
                org.apache.lucene.document.Document d = r.document(i);
                java.lang.String uinfo = d.get(ArtifactInfo.UINFO);
                if (uinfo != null) {
                    org.apache.maven.index.ArtifactInfo info = org.apache.maven.index.context.IndexUtils.constructArtifactInfo(d, this);
                    rootGroups.add(info.getRootGroup());
                    allGroups.add(info.getGroupId());
                }
            }
            setRootGroups(rootGroups);
            setAllGroups(allGroups);
            optimize();
        } finally {
            releaseIndexSearcher(is);
        }
    }

    public java.util.Set<java.lang.String> getAllGroups() throws java.io.IOException {
        return getGroups(ArtifactInfo.ALL_GROUPS, ArtifactInfo.ALL_GROUPS_VALUE, ArtifactInfo.ALL_GROUPS_LIST);
    }

    public synchronized void setAllGroups(java.util.Collection<java.lang.String> groups) throws java.io.IOException {
        setGroups(groups, ArtifactInfo.ALL_GROUPS, ArtifactInfo.ALL_GROUPS_VALUE, ArtifactInfo.ALL_GROUPS_LIST);
        commit();
    }

    public java.util.Set<java.lang.String> getRootGroups() throws java.io.IOException {
        return getGroups(ArtifactInfo.ROOT_GROUPS, ArtifactInfo.ROOT_GROUPS_VALUE, ArtifactInfo.ROOT_GROUPS_LIST);
    }

    public synchronized void setRootGroups(java.util.Collection<java.lang.String> groups) throws java.io.IOException {
        setGroups(groups, ArtifactInfo.ROOT_GROUPS, ArtifactInfo.ROOT_GROUPS_VALUE, ArtifactInfo.ROOT_GROUPS_LIST);
        commit();
    }

    protected java.util.Set<java.lang.String> getGroups(java.lang.String field, java.lang.String filedValue, java.lang.String listField) throws java.io.IOException, org.apache.lucene.index.CorruptIndexException {
        final org.apache.lucene.search.TopScoreDocCollector collector = org.apache.lucene.search.TopScoreDocCollector.create(1, false);
        final org.apache.lucene.search.IndexSearcher indexSearcher = acquireIndexSearcher();
        try {
            indexSearcher.search(new org.apache.lucene.search.TermQuery(new org.apache.lucene.index.Term(field, filedValue)), collector);
            org.apache.lucene.search.TopDocs topDocs = collector.topDocs();
            java.util.Set<java.lang.String> groups = new java.util.LinkedHashSet<java.lang.String>(java.lang.Math.max(10, topDocs.totalHits));
            if (topDocs.totalHits > 0) {
                org.apache.lucene.document.Document doc = indexSearcher.doc(topDocs.scoreDocs[0].doc);
                java.lang.String groupList = doc.get(listField);
                if (groupList != null) {
                    groups.addAll(java.util.Arrays.asList(groupList.split("\\|")));
                }
            }
            return groups;
        } finally {
            releaseIndexSearcher(indexSearcher);
        }
    }

    protected void setGroups(java.util.Collection<java.lang.String> groups, java.lang.String groupField, java.lang.String groupFieldValue, java.lang.String groupListField) throws java.io.IOException, org.apache.lucene.index.CorruptIndexException {
        final org.apache.lucene.index.IndexWriter w = getIndexWriter();
        w.updateDocument(new org.apache.lucene.index.Term(groupField, groupFieldValue), createGroupsDocument(groups, groupField, groupFieldValue, groupListField));
    }

    protected org.apache.lucene.document.Document createGroupsDocument(java.util.Collection<java.lang.String> groups, java.lang.String field, java.lang.String fieldValue, java.lang.String listField) {
        final org.apache.lucene.document.Document groupDoc = new org.apache.lucene.document.Document();
        groupDoc.add(// 
        new org.apache.lucene.document.Field(field, fieldValue, org.apache.lucene.document.Field.Store.YES, org.apache.lucene.document.Field.Index.NOT_ANALYZED));
        groupDoc.add(// 
        new org.apache.lucene.document.Field(listField, org.apache.maven.index.ArtifactInfo.lst2str(groups), org.apache.lucene.document.Field.Store.YES, org.apache.lucene.document.Field.Index.NO));
        return groupDoc;
    }

    @java.lang.Override
    public java.lang.String toString() {
        return (id + " : ") + timestamp;
    }
}