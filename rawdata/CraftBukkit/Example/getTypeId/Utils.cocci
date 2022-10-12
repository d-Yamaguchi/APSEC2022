@API migration edit:_target.getTypeId(...)->_target.getType(...)@
identifier _VAR1;
expression _target;
identifier _VAR5;
identifier _VAR0;
ItemStack is2;
@@
- int _VAR1 =_target.getTypeId();
+? ItemStack _VAR0 = is2;
+? ItemStack _VAR5 = is2;
+ int _VAR1 = _VAR5.getType();

