@API migration edit:_target.getUniqueId(...)->_target.getWorld(...)@
identifier _VAR4;
expression _target;
identifier _VAR3;
identifier _VAR2;
identifier player;
CommandSender sender;
@@
- UUID _VAR4 =_target.getUniqueId();
+ Location _VAR3 = _CVAR2.getLocation();
+ UUID _VAR4 = _VAR3.getWorld();

