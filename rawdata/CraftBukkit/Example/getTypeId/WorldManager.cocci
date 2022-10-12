@API migration edit:_target.getTypeId(...)->_target.getType(...)@
identifier material;
expression _target;
identifier _VAR0;
Block safeBlock;
@@
- int material =_target.getTypeId();
+? Block _VAR0 = safeBlock;
+ int material = _VAR0.getType();

