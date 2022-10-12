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
+? Player player = null;
+? int playerLevel = player.getLevel();
+? int _VAR1 = playerLevel;
+? int _VAR8 = playerLevel;
+? int _VAR2 = 0;
+? int _VAR9 = 0;
+ int _VAR3 = Math.max(_VAR8, _VAR9);

