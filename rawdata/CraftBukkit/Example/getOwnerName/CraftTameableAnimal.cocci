@API migration edit:_target.getOwnerName(...)->_target.getOwnerUUID(...)@
identifier _VAR1;
expression _target;
identifier _VAR0;
@@
- String _VAR1 =_target.getOwnerName();
+? EntityTameableAnimal _VAR0 = getHandle();
+ String _VAR1 = _VAR0.getOwnerUUID();

