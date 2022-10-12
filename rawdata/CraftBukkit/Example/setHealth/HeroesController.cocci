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
- void _VAR2 =_target.setHealth(_DVAR0);
+? Player _VAR0 = player;
+? double _VAR1 = health;
+ void _VAR2 = HeroesUtil.setHealthP(_VAR0, _VAR1);

