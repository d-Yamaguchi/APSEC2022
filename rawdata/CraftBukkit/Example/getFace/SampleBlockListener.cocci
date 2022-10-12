@API migration edit:_target.getFace(...)->_target.getRelative(...)@
identifier above;
expression _target;
expression _DVAR0;
identifier _VAR1;
identifier block;
identifier _VAR0;
BlockPhysicsEvent event;
identifier _VAR2;
BlockFace BlockFace;
@@
- BlockFace above =_target.getFace(_DVAR0);
+? BlockPhysicsEvent _VAR0 = event;
+? Block block = _VAR0.getBlock();
+? Block _VAR1 = block;
+? BlockFace _VAR2 = org.bukkit.block.BlockFace.UP;
+ BlockFace above = _VAR1.getRelative(_VAR2);

