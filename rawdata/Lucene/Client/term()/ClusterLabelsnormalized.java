/**
 * Get the list of labels, sorted by best score.
 */
protected java.util.List<org.apache.mahout.utils.vectors.lucene.TermInfoClusterInOut> getClusterLabels(java.lang.Integer integer, java.util.Collection<org.apache.mahout.clustering.classify.WeightedVectorWritable> wvws) throws java.io.IOException {
    if (wvws.size() < minNumIds) {
        org.apache.mahout.utils.vectors.lucene.ClusterLabels.log.info("Skipping small cluster {} with size: {}", integer, wvws.size());
        return null;
    }
    org.apache.mahout.utils.vectors.lucene.ClusterLabels.log.info("Processing Cluster {} with {} documents", integer, wvws.size());
    org.apache.lucene.store.Directory dir = org.apache.lucene.store.FSDirectory.open(new java.io.File(this.indexDir));
    org.apache.lucene.index.IndexReader reader = org.apache.lucene.index.IndexReader.open(dir, false);
    org.apache.mahout.utils.vectors.lucene.ClusterLabels.log.info("# of documents in the index {}", reader.numDocs());
    java.util.Collection<java.lang.String> idSet = new java.util.HashSet<java.lang.String>();
    for (org.apache.mahout.clustering.classify.WeightedVectorWritable wvw : wvws) {
        org.apache.mahout.math.Vector vector = wvw.getVector();
        if (vector instanceof org.apache.mahout.math.NamedVector) {
            idSet.add(((org.apache.mahout.math.NamedVector) (vector)).getName());
        }
    }
    int numDocs = reader.numDocs();
    org.apache.lucene.util.OpenBitSet clusterDocBitset = org.apache.mahout.utils.vectors.lucene.ClusterLabels.getClusterDocBitset(reader, idSet, this.idField);
    org.apache.mahout.utils.vectors.lucene.ClusterLabels.log.info("Populating term infos from the index");
    /**
     * This code is as that of CachedTermInfo, with one major change, which is to get the document frequency.
     *
     * Since we have deleted the documents out of the cluster, the document frequency for a term should only
     * include the in-cluster documents. The document frequency obtained from TermEnum reflects the frequency
     * in the entire index. To get the in-cluster frequency, we need to query the index to get the term
     * frequencies in each document. The number of results of this call will be the in-cluster document
     * frequency.
     */
    org.apache.lucene.index.TermEnum te = reader.terms(new org.apache.lucene.index.Term(contentField, ""));
    java.util.Map<java.lang.String, org.apache.mahout.utils.vectors.TermEntry> termEntryMap = new java.util.LinkedHashMap<java.lang.String, org.apache.mahout.utils.vectors.TermEntry>();
    try {
        int count = 0;
        do {
            org.apache.lucene.index.Term term = te.term();
            if ((term == null) || (!term.field().equals(contentField))) {
                break;
            }
            org.apache.lucene.util.OpenBitSet termBitset = new org.apache.lucene.util.OpenBitSet(reader.maxDoc());
            // Generate bitset for the term
            org.apache.lucene.index.TermDocs termDocs = reader.termDocs(term);
            while (termDocs.next()) {
                termBitset.set(termDocs.doc());
            } 
            // AND the term's bitset with cluster doc bitset to get the term's in-cluster frequency.
            // This modifies the termBitset, but that's fine as we are not using it anywhere else.
            termBitset.and(clusterDocBitset);
            int inclusterDF = ((int) (termBitset.cardinality()));
            org.apache.mahout.utils.vectors.TermEntry entry = new org.apache.mahout.utils.vectors.TermEntry(term.text(), count++, inclusterDF);
            termEntryMap.put(entry.getTerm(), entry);
        } while (te.next() );
    } finally {
        com.google.common.io.Closeables.closeQuietly(te);
    }
    java.util.List<org.apache.mahout.utils.vectors.lucene.TermInfoClusterInOut> clusteredTermInfo = new java.util.LinkedList<org.apache.mahout.utils.vectors.lucene.TermInfoClusterInOut>();
    int clusterSize = wvws.size();
    for (org.apache.mahout.utils.vectors.TermEntry termEntry : termEntryMap.values()) {
        int corpusDF = reader.terms(new org.apache.lucene.index.Term(this.contentField, termEntry.getTerm())).docFreq();
        int outDF = corpusDF - termEntry.getDocFreq();
        int inDF = termEntry.getDocFreq();
        double logLikelihoodRatio = org.apache.mahout.utils.vectors.lucene.ClusterLabels.scoreDocumentFrequencies(inDF, outDF, clusterSize, numDocs);
        org.apache.mahout.utils.vectors.lucene.TermInfoClusterInOut termInfoCluster = new org.apache.mahout.utils.vectors.lucene.TermInfoClusterInOut(termEntry.getTerm(), inDF, outDF, logLikelihoodRatio);
        clusteredTermInfo.add(termInfoCluster);
    }
    java.util.Collections.sort(clusteredTermInfo);
    // Cleanup
    com.google.common.io.Closeables.closeQuietly(reader);
    termEntryMap.clear();
    return clusteredTermInfo.subList(0, java.lang.Math.min(clusteredTermInfo.size(), maxLabels));
}