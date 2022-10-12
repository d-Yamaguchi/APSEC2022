@API migration edit:_target.getEntity(...)->_target.getPlayer(...)@
identifier _VAR1;
expression _target;
identifier _VAR0;
PlayerJoinEvent event;
@@
- Player _VAR1 =_target.getEntity();
+? PlayerJoinEvent _VAR0 = event;
+ Player _VAR1 = _VAR0.getPlayer();

