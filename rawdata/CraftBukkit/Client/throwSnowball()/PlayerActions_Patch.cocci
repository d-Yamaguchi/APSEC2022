@API migration edit:_target.throwSnowball(...)->_target.launchProjectile(...)@
identifier _VAR3;
expression _target;
identifier _VAR1;
identifier player;
Bukkit Bukkit;
identifier _VAR0;
String playerName;
identifier _VAR2;
Egg Egg;
@@
- _target.throwSnowball();
+ Player player = Bukkit.getPlayer(playerName);
+ Player _VAR1 = player;
+ Class _VAR2 = org.bukkit.entity.Egg.class;
+  _VAR1.launchProjectile(_VAR2);

