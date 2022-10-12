@API migration edit:_target.getOfflinePlayer(...)->_target.getPlayer(...)@
identifier target;
expression _target;
expression _DVAR0;
Bukkit Bukkit;
@@
- OfflinePlayer target =_target.getOfflinePlayer(_DVAR0);
+ OfflinePlayer target = Bukkit.getPlayer(_DVAR0);

