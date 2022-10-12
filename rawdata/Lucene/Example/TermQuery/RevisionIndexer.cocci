@API migration edit:new TermQuery(...)->new TermQuery(...)@
identifier repoQuery;
expression _target;
expression _DVAR0;
@@
- TermQuery repoQuery =new TermQuery(_DVAR0);
+ TermQuery repoQuery = new org.apache.lucene.search.TermQuery(_DVAR0);

