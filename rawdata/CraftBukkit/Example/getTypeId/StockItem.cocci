@API migration edit:_target.getTypeId(...)->_target.getType(...)@
identifier _VAR1;
expression _target;
identifier _VAR0;
ItemStack item;
@@
- int _VAR1 =_target.getTypeId();
+? ItemStack _VAR0 = item;
+ int _VAR1 = _VAR0.getType();

