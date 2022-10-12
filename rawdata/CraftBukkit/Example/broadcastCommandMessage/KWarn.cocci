@API migration edit:_target.broadcastCommandMessage(...)->_target.sendMessage(...)@
identifier _VAR3;
expression _target;
expression _DVAR0;
expression _DVAR1;
identifier _VAR0;
CommandSender sender;
identifier _VAR2;
identifier prefix;
ChatColor ChatColor;
@@
- void _VAR3 =_target.broadcastCommandMessage(_DVAR0,_DVAR1);
+? CommandSender _VAR0 = sender;
+? String prefix = org.bukkit.ChatColor.GREEN;
+? String _VAR2 = (prefix + org.bukkit.ChatColor.RED);
+ void _VAR3 = _VAR0.sendMessage(_VAR2);

