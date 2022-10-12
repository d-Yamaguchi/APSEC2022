@API migration edit:_target.getFace(...)->_target.getRelative(...)@
identifier _VAR2;
expression _target;
expression _DVAR0;
identifier _VAR0;
Block trigger;
identifier _VAR1;
BlockFace BlockFace;
@@
- BlockFace _VAR2 =_target.getFace(_DVAR0);
+? Block _VAR0 = trigger;
+? BlockFace _VAR1 = org.bukkit.block.BlockFace.UP;
+ BlockFace _VAR2 = _VAR0.getRelative(_VAR1);

