@API migration edit:_target.optimize(...)->_target.forceMerge(...)@
identifier _VAR2;
expression _target;
identifier _VAR0;
IndexWriter w;
identifier _VAR1;
@@
- _target.optimize();
+ int _VAR1 = 1;
+  w.forceMerge(_VAR1);

