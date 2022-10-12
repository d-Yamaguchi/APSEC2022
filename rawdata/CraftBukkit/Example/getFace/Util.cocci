@API migration edit:_target.getFace(...)->_target.getRelative(...)@
identifier _VAR3;
expression _target;
expression _DVAR0;
identifier _VAR1;
Block block;
identifier _VAR2;
identifier face;
@@
- BlockFace _VAR3 =_target.getFace(_DVAR0);
+? Block _VAR1 = block;
+? BlockFace face = block;
+? BlockFace _VAR2 = face;
+ BlockFace _VAR3 = _VAR1.getRelative(_VAR2);

