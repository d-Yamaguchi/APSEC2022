@API migration edit:_target.teleport(...)->_target.initTeleport(...)@
identifier _VAR8;
expression _target;
expression _DVAR0;
identifier _VAR3;
identifier playerFrom;
identifier _VAR0;
Bukkit Bukkit;
identifier _VAR2;
identifier _VAR1;
CommandSender sender;
identifier _VAR7;
identifier _VAR6;
identifier playerTo;
identifier _VAR4;
Bukkit Bukkit;
identifier _VAR5;
String[] args;
@@
- boolean _VAR8 =_target.teleport(_DVAR0);
+? Server _VAR0 = Bukkit.getServer();
+? CommandSender _VAR1 = sender;
+? String _VAR2 = _VAR1.getName();
+? Player playerFrom = _VAR0.getPlayer(_VAR2);
+? Player _VAR3 = playerFrom;
+? Server _VAR4 = Bukkit.getServer();
+? String _VAR5 = args[0];
+? Player playerTo = _VAR4.getPlayer(_VAR5);
+? Player _VAR6 = playerTo;
+? Location _VAR7 = _VAR6.getLocation();
+ boolean _VAR8 = initTeleport(_VAR3, _VAR7);

