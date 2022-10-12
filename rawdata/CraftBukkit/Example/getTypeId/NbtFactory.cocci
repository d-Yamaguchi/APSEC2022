@API migration edit:_target.getTypeId(...)->_target.getType(...)@
identifier _VAR2;
expression _target;
identifier _VAR1;
Field CRAFT_HANDLE;
@@
- int _VAR2 =_target.getTypeId();
+? Field _VAR1 = CRAFT_HANDLE;
+ int _VAR2 = _VAR1.getType();

