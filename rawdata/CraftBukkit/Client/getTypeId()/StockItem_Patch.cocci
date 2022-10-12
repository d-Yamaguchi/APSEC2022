@API migration edit:_target.getTypeId(...)->_target.getType(...)@
identifier _VAR3;
expression _target;
identifier _VAR2;
identifier nameSign;
identifier _VAR0;
identifier _VAR1;
@@
- _target.getTypeId();
+ Block _VAR0 = getGateNameBlockHolder();
+ BlockFace _VAR1 = getGateFacing();
+ Block nameSign = _VAR0.getRelative(_VAR1);
+ Block _VAR2 = nameSign;
+  _VAR2.getType();

