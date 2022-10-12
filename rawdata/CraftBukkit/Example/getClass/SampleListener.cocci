@API migration edit:_target.getClass(...)->_target.getPlayer(...)@
identifier _VAR1;
expression _target;
identifier _VAR0;
PlayerJoinEvent event;
@@
- Class _VAR1 =_target.getClass();
+? PlayerJoinEvent _VAR0 = event;
+ Class _VAR1 = _VAR0.getPlayer();

