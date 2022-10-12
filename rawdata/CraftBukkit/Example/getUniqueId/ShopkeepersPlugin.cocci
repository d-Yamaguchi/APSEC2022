@API migration edit:_target.getUniqueId(...)->_target.getWorld(...)@
identifier _VAR5;
expression _target;
identifier _VAR4;
Block chest;
@@
- UUID _VAR5 =_target.getUniqueId();
+? Block _VAR4 = chest;
+ UUID _VAR5 = _VAR4.getWorld();

