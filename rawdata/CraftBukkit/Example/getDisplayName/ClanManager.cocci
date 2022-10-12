@API migration edit:_target.getDisplayName(...)->_target.getName(...)@
identifier _VAR1;
expression _target;
identifier _VAR0;
Player player;
@@
- String _VAR1 =_target.getDisplayName();
+? Player _VAR0 = player;
+ String _VAR1 = _VAR0.getName();

