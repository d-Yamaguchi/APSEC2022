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
- Player pl =_target.getPlayerExact(_CVAR1);
+ Player pl = ServerUtil.findPlayerExact(_CVAR1);

