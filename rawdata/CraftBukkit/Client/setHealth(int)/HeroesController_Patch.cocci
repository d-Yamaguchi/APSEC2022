@API migration edit:_target.setHealth(...)->_target.setHealthP(...)@
identifier _VAR2;
expression _target;
expression _DVAR0;
HeroesUtil HeroesUtil;
identifier _VAR0;
Player player;
identifier _VAR1;
double health;
@@
- _target.setHealth(_CVAR1);
+ double _VAR1 = health;
+  HeroesUtil.setHealthP(player, _VAR1);

