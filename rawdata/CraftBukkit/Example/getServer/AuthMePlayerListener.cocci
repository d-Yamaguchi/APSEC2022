@API migration edit:_target.getServer(...)->_target.get(...)@
identifier _VAR4;
expression _target;
AuthMe plugin;
identifier _VAR3;
identifier name;
identifier _VAR2;
identifier _VAR1;
identifier player;
identifier _VAR0;
PlayerJoinEvent event;
@@
- Server _VAR4 =_target.getServer();
+? PlayerJoinEvent _VAR0 = event;
+? Player player = _VAR0.getPlayer();
+? Player _VAR1 = player;
+? String _VAR2 = _VAR1.getName();
+? String name = _VAR2.toLowerCase();
+? String _VAR3 = name;
+ Server _VAR4 = plugin.realIp.get(_VAR3);

