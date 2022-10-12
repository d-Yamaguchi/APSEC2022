@API migration edit:_target.broadcastCommandMessage(...)->_target.sendMessage(...)@
identifier _VAR3;
expression _target;
expression _DVAR0;
expression _DVAR1;
identifier _VAR0;
CommandSender sender;
identifier _VAR2;
ChatColor ChatColor;
@@
- _target.broadcastCommandMessage(_CVAR0,_CVAR2);
+  sender.sendMessage(currentAlias);

