@API migration edit:_target.getFace(...)->_target.getRelative(...)@
identifier holdingBlock;
expression _target;
expression _DVAR0;
identifier _VAR11;
identifier _VAR4;
identifier _VAR0;
Block buttonBlock;
identifier _VAR13;
identifier _VAR6;
identifier _VAR2;
identifier opposite;
WorldUtils WorldUtils;
identifier _VAR8;
@@
- _target.getFace(org.bukkit.block.BlockFace.DOWN);
+ Block _VAR11 = buttonBlock;
+ BlockFace _VAR8 = WorldUtils;
+ BlockFace opposite = WorldUtils.getInverseDirection(_VAR8);
+ BlockFace _VAR13 = opposite;
+  _VAR11.getRelative(_VAR13);

