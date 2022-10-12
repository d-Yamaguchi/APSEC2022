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
+ Class _VAR2 = org.bukkit.entity.Egg.class;
+ Egg _VAR3 = _CVAR0.launchProjectile(_VAR2);

