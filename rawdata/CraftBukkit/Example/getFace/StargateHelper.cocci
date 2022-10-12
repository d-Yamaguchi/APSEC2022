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
- BlockFace holdingBlock =_target.getFace(_DVAR0);
+? Block _VAR0 = buttonBlock;
+? Block _VAR4 = buttonBlock;
+? Block _VAR11 = buttonBlock;
+? BlockFace _VAR8 = WorldUtils;
+? BlockFace opposite = WorldUtils.getInverseDirection(_VAR8);
+? BlockFace _VAR2 = opposite;
+? BlockFace _VAR6 = opposite;
+? BlockFace _VAR13 = opposite;
+ BlockFace holdingBlock = _VAR11.getRelative(_VAR13);

