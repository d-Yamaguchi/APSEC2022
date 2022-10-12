@API migration edit:_target.getOnlinePlayers(...)->_target.getPlayer(...)@
identifier player;
expression _target;
Bukkit Bukkit;
identifier _VAR0;
UUID id;
@@
- Player[] player =_target.getOnlinePlayers();
+ UUID _VAR0 = id;
+ Player[] player = Bukkit.getPlayer(_VAR0);

