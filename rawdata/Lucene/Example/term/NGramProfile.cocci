@API migration edit:_target.term(...)->_target.buffer(...)@
identifier _VAR4;
expression _target;
identifier _VAR3;
Token t;
@@
- String _VAR4 =_target.term();
+? Token _VAR3 = t;
+ String _VAR4 = _VAR3.buffer();

