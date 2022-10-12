@API migration edit:_target.getPlayerExact(...)->_target.findPlayerExact(...)@
identifier pl;
expression _target;
expression _DVAR0;
ServerUtil ServerUtil;
identifier _VAR1;
identifier name;
identifier _VAR0;
Player p;
@@
- Player pl =_target.getPlayerExact(_DVAR0);
+? Player _VAR0 = p;
+? String name = _VAR0.getName();
+? String _VAR1 = name;
+ Player pl = ServerUtil.findPlayerExact(_VAR1);

