@API migration edit:_target.getPlayer(...)->_target.getKnown(...)@
identifier id;
expression _target;
expression _DVAR0;
UUIDHelper UUIDHelper;
identifier _VAR0;
identifier name;
String[] args;
@@
- Player id =_target.getPlayer(_DVAR0);
+? String name = args[0];
+? String _VAR0 = name;
+ Player id = UUIDHelper.getKnown(_VAR0);

