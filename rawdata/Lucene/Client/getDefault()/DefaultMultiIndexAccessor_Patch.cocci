@API migration edit:_target.getDefault(...)->_target.getDefaultSimilarity(...)@
identifier _VAR0;
expression _target;
IndexSearcher IndexSearcher;
@@
- Similarity _VAR0 =_target.getDefault();
+ Similarity _VAR0 = IndexSearcher.getDefaultSimilarity();

