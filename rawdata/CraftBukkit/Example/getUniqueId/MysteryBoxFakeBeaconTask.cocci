@API migration edit:_target.getUniqueId(...)->_target.getWorld(...)@
identifier _VAR3;
expression _target;
identifier _VAR2;
identifier newLoc;
identifier _VAR4;
@@
- UUID _VAR3 =_target.getUniqueId();
+? Location newLoc = _VAR4.clone();
+? Location _VAR2 = newLoc;
+ UUID _VAR3 = _VAR2.getWorld();

