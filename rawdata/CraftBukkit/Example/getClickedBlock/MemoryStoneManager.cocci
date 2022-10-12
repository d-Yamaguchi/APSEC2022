@API migration edit:_target.getClickedBlock(...)->_target.getBlock(...)@
identifier _VAR15;
expression _target;
identifier _VAR14;
identifier _VAR4;
identifier _VAR3;
identifier sign;
identifier _VAR5;
identifier _VAR7;
identifier _VAR6;
identifier _VAR10;
identifier _VAR9;
identifier _VAR13;
identifier _VAR12;
@@
- Block _VAR15 =_target.getClickedBlock();
+? Sign sign = _VAR5.getSign();
+? Sign _VAR3 = sign;
+? World _VAR4 = _VAR3.getWorld();
+? Sign _VAR6 = sign;
+? int _VAR7 = _VAR6.getX();
+? Sign _VAR9 = sign;
+? int _VAR10 = _VAR9.getY();
+? Sign _VAR12 = sign;
+? int _VAR13 = _VAR12.getZ();
+? Location _VAR14 = new org.bukkit.Location(_VAR4, _VAR7, _VAR10, _VAR13);
+ Block _VAR15 = _VAR14.getBlock();

