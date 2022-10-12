@API migration edit:_target.document(...)->_target.search(...)@
identifier docs;
expression _target;
expression _DVAR0;
identifier _VAR0;
IndexSearcher indexSearcher;
identifier _VAR1;
identifier query;
identifier _VAR2;
@@
- Document docs =_target.document(docs.doc());
+ IndexSearcher _VAR0 = indexSearcher;
+ BooleanQuery query = new org.apache.lucene.search.BooleanQuery();
+ BooleanQuery _VAR1 = query;
+ Document docs = _VAR0.search(_VAR1, ONE_THOUSAND);

