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
+ Player _VAR0 = player;
+ double _VAR1 = health;
+  HeroesUtil.setHealthP(_VAR0, _VAR1);

