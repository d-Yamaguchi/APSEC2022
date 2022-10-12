@API migration edit:new BooleanQuery(...)->new BooleanQuery(...)@
identifier repoAndRevQuery;
expression _target;
@@
- BooleanQuery repoAndRevQuery =new BooleanQuery();
+ BooleanQuery repoAndRevQuery = new org.apache.lucene.search.BooleanQuery();

