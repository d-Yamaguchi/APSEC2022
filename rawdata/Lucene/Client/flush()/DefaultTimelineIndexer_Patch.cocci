@API migration edit:_target.flush(...)->_target.commit(...)@
identifier _VAR1;
expression _target;
expression _DVAR0;
expression _DVAR1;
identifier _VAR0;
IndexWriter writer;
@@
- _target.flush(_DVAR0,_DVAR1);
+  indexWriter.commit();

