@API migration edit:_target.getBlockX(...)->_target.getX(...)@
identifier _VAR1;
expression _target;
identifier _VAR0;
Block block;
@@
- int _VAR1 =_target.getBlockX();
+? Block _VAR0 = block;
+ int _VAR1 = _VAR0.getX();

