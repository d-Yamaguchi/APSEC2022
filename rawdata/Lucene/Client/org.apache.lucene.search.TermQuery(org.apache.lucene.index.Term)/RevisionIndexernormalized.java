/**
 * Work out whether a given change, for the specified repository, is already in the index or not.
 */
private boolean hasDocument(long repoId, java.lang.String branchName, org.eclipse.jgit.lib.ObjectId revisionNumber, org.apache.lucene.index.IndexReader reader) throws java.io.IOException {
    org.apache.lucene.search.IndexSearcher searcher = new org.apache.lucene.search.IndexSearcher(reader);
    try {
        long _CVAR1 = repoId;
        java.lang.String _CVAR0 = com.xiplink.jira.git.revisions.RevisionIndexer.FIELD_REPOSITORY;
        java.lang.String _CVAR2 = java.lang.Long.toString(_CVAR1);
        org.apache.lucene.index.Term _CVAR3 = new org.apache.lucene.index.Term(_CVAR0, _CVAR2);
        org.apache.lucene.search.TermQuery repoQuery = new org.apache.lucene.search.TermQuery(_CVAR3);
        java.lang.String _CVAR4 = com.xiplink.jira.git.revisions.RevisionIndexer.FIELD_BRANCH;
        java.lang.String _CVAR5 = branchName;
        org.apache.lucene.index.Term _CVAR6 = new org.apache.lucene.index.Term(_CVAR4, _CVAR5);
        org.apache.lucene.search.TermQuery branchQuery = new org.apache.lucene.search.TermQuery(_CVAR6);
        org.eclipse.jgit.lib.ObjectId _CVAR8 = revisionNumber;
        java.lang.String _CVAR7 = com.xiplink.jira.git.revisions.RevisionIndexer.FIELD_REVISIONNUMBER;
         _CVAR9 = _CVAR8.name();
        org.apache.lucene.index.Term _CVAR10 = new org.apache.lucene.index.Term(_CVAR7, _CVAR9);
        org.apache.lucene.search.TermQuery revQuery = new org.apache.lucene.search.TermQuery(_CVAR10);
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