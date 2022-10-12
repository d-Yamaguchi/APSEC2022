@API migration edit:_target.getDisplayName(...)->_target.getName(...)@
identifier _VAR2;
expression _target;
identifier _VAR1;
identifier _VAR0;
PlayerJoinEvent event;
@@
- String _VAR2 =_target.getDisplayName();
+? PlayerJoinEvent _VAR0 = event;
+? Player _VAR1 = _VAR0.getPlayer();
+ String _VAR2 = _VAR1.getName();

