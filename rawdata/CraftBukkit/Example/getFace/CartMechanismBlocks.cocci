@API migration edit:_target.getFace(...)->_target.getRelative(...)@
identifier _VAR3;
expression _target;
expression _DVAR0;
identifier _VAR0;
Block rail;
identifier _VAR1;
BlockFace BlockFace;
identifier _VAR2;
@@
- BlockFace _VAR3 =_target.getFace(_DVAR0);
+? Block _VAR0 = rail;
+? BlockFace _VAR1 = org.bukkit.block.BlockFace.DOWN;
+? int _VAR2 = 2;
+ BlockFace _VAR3 = _VAR0.getRelative(_VAR1, _VAR2);

