@API migration edit:_target.term(...)->_target.buffer(...)@
identifier _VAR1;
expression _target;
identifier _VAR0;
CharTermAttribute termAttr;
@@
- _target.term();
+ CharTermAttribute _VAR0 = termAttr;
+  _VAR0.buffer();

