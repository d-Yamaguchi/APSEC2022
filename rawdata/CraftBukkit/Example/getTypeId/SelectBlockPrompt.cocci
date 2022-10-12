@API migration edit:_target.getTypeId(...)->_target.getType(...)@
identifier _VAR4;
expression _target;
@@
- int _VAR4 =_target.getTypeId();
+ int _VAR4 = getType();

