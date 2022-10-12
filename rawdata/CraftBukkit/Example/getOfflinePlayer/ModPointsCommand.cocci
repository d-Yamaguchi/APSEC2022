@API migration edit:_target.getOfflinePlayer(...)->_target.getPlayer(...)@
identifier player;
expression _target;
expression _DVAR0;
Bukkit Bukkit;
@@
- OfflinePlayer player =_target.getOfflinePlayer(_DVAR0);
+ OfflinePlayer player = Bukkit.getPlayer(_DVAR0);

