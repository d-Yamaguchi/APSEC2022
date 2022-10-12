@API migration edit:_target.getLastPlayed(...)->_target.getOfflinePlayer(...)@
identifier offlinePlayer;
expression _target;
identifier _VAR0;
Bukkit Bukkit;
identifier _VAR1;
String searchPlayer;
@@
- long offlinePlayer =_target.getLastPlayed();
+? Server _VAR0 = Bukkit.getServer();
+? String _VAR1 = searchPlayer;
+ long offlinePlayer = _VAR0.getOfflinePlayer(_VAR1);

