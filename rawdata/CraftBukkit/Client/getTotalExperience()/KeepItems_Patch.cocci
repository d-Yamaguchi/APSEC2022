@API migration edit:_target.getTotalExperience(...)->_target.max(...)@
identifier _VAR3;
expression _target;
Math Math;
identifier _VAR8;
identifier _VAR1;
identifier playerLevel;
identifier player;
identifier _VAR9;
identifier _VAR2;
@@
- int _VAR3 =_target.getTotalExperience();
+ int playerLevel = _CVAR1.getLevel();
+ int _VAR8 = _CVAR1Level;
+ int _VAR9 = 0;
+ int _VAR3 = Math.max(_VAR8, _VAR9);

