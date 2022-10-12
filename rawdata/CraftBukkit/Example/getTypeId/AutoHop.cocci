@API migration edit:_target.getTypeId(...)->_target.getType(...)@
identifier _VAR5;
expression _target;
identifier _VAR4;
identifier b;
identifier _VAR2;
identifier _VAR1;
identifier to;
identifier _VAR0;
PlayerMoveEvent event;
identifier _VAR3;
identifier face;
@@
- int _VAR5 =_target.getTypeId();
+? PlayerMoveEvent _VAR0 = event;
+? Location to = _VAR0.getTo();
+? Location _VAR1 = to;
+? Block _VAR2 = _VAR1.getBlock();
+? BlockFace face = null;
+? BlockFace _VAR3 = face;
+? Block b = _VAR2.getRelative(_VAR3);
+? Block _VAR4 = b;
+ int _VAR5 = _VAR4.getType();

