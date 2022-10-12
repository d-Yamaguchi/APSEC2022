@API migration edit:_target.getPlayer(...)->_target.getKnown(...)@
identifier id;
expression _target;
expression _DVAR0;
UUIDHelper UUIDHelper;
identifier _VAR0;
identifier name;
String[] args;
@@
- Player id =_target.getPlayer(_CVAR1);
+ Player id = UUIDHelper.getKnown(_CVAR1);

