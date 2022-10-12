@API migration edit:_target.optimize(...)->_target.forceMerge(...)@
identifier _VAR2;
expression _target;
identifier _VAR0;
IndexWriter w;
identifier _VAR1;
@@
- void _VAR2 =_target.optimize();
+? IndexWriter _VAR0 = w;
+? int _VAR1 = 1;
+ void _VAR2 = _VAR0.forceMerge(_VAR1);

