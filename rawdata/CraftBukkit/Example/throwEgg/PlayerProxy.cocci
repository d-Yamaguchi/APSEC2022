@API migration edit:_target.throwEgg(...)->_target.launchProjectile(...)@
identifier _VAR2;
expression _target;
identifier _VAR0;
identifier _VAR1;
Egg Egg;
@@
- Egg _VAR2 =_target.throwEgg();
+? Player _VAR0 = getRealPlayer();
+? Class _VAR1 = org.bukkit.entity.Egg.class;
+ Egg _VAR2 = _VAR0.launchProjectile(_VAR1);

