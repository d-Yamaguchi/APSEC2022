@API migration edit:_target.setTermLength(...)->_target.setLength(...)@
identifier _VAR5;
expression _target;
expression _DVAR0;
identifier _VAR0;
CharTermAttribute termAttr;
@@
- _target.setTermLength(_CVAR4);
+ CharTermAttribute _VAR0 = termAttr;
+  _VAR0.setLength(_CVAR4);

