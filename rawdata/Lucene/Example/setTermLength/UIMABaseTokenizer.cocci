@API migration edit:_target.setTermLength(...)->_target.setLength(...)@
identifier _VAR5;
expression _target;
expression _DVAR0;
identifier _VAR0;
CharTermAttribute termAttr;
@@
- void _VAR5 =_target.setTermLength(_DVAR0);
+? CharTermAttribute _VAR0 = termAttr;
+ void _VAR5 = _VAR0.setLength(_DVAR0);

