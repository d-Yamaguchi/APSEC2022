@API migration edit:_target.throwEgg(...)->_target.launchProjectile(...)@
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
- Egg _VAR3 =_target.throwEgg();
+? String _VAR0 = playerName;
+? Player player = Bukkit.getPlayer(_VAR0);
+? Player _VAR1 = player;
+? Class _VAR2 = org.bukkit.entity.Egg.class;
+ Egg _VAR3 = _VAR1.launchProjectile(_VAR2);

