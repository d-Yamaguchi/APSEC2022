/**
 * Work out whether a given change, for the specified repository, is already in the index or not.
 */
private boolean hasDocument(long repoId, java.lang.String branchName, org.eclipse.jgit.lib.ObjectId revisionNumber, org.apache.lucene.index.IndexReader reader) throws java.io.IOException {
    org.apache.lucene.search.IndexSearcher searcher = new org.apache.lucene.search.IndexSearcher(reader);
    try {
        org.apache.lucene.search.TermQuery repoQuery = new org.apache.lucene.search.TermQuery(new org.apache.lucene.index.Term(com.xiplink.jira.git.revisions.RevisionIndexer.FIELD_REPOSITORY, java.lang.Long.toString(repoId)));
        org.apache.lucene.search.TermQuery branchQuery = new org.apache.lucene.search.TermQuery(new org.apache.lucene.index.Term(com.xiplink.jira.git.revisions.RevisionIndexer.FIELD_BRANCH, branchName));
        org.apache.lucene.search.TermQuery revQuery = new org.apache.lucene.search.TermQuery(new org.apache.lucene.index.Term(com.xiplink.jira.git.revisions.RevisionIndexer.FIELD_REVISIONNUMBER, revisionNumber.name()));
        org.apache.lucene.search.BooleanQuery repoAndRevQuery = new org.apache.lucene.search.BooleanQuery();
        repoAndRevQuery.add(repoQuery, org.apache.lucene.search.BooleanClause.Occur.MUST);
        repoAndRevQuery.add(branchQuery, org.apache.lucene.search.BooleanClause.Occur.MUST);
        repoAndRevQuery.add(revQuery, org.apache.lucene.search.BooleanClause.Occur.MUST);
        org.apache.lucene.search.Hits hits = searcher.search(repoAndRevQuery);
        if (hits.length() == 1) {
            return true;
        } else if (hits.length() == 0) {
            return false;
        } else {
            com.xiplink.jira.git.revisions.RevisionIndexer.log.error((((("Found MORE than one document for repository=" + repoId) + "; branch=") + branchName) + "; revision=") + revisionNumber);
            return true;
        }
    } finally {
        searcher.close();
    }
}