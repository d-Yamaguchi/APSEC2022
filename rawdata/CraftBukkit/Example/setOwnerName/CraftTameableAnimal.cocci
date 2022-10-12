@API migration edit:_target.setOwnerName(...)->_target.setOwnerUUID(...)@
identifier _VAR2;
expression _target;
expression _DVAR0;
identifier _VAR0;
identifier _VAR1;
@@
- void _VAR2 =_target.setOwnerName(_DVAR0);
+? EntityTameableAnimal _VAR0 = getHandle();
+? String _VAR1 = "";
+ void _VAR2 = _VAR0.setOwnerUUID(_VAR1);

