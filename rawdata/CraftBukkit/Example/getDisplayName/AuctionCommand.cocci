@API migration edit:_target.getDisplayName(...)->_target.getName(...)@
identifier _VAR2;
expression _target;
identifier _VAR1;
@@
- String _VAR2 =_target.getDisplayName();
+? Player _VAR1 = player;
+ String _VAR2 = _VAR1.getName();

