@API migration edit:_target.getUniqueId(...)->_target.getWorld(...)@
identifier _VAR4;
expression _target;
identifier _VAR3;
identifier _VAR2;
identifier player;
CommandSender sender;
@@
- UUID _VAR4 =_target.getUniqueId();
+ UUID _VAR4 = _CVAR2.getWorld();

