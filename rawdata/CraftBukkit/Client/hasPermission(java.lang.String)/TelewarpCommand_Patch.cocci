@API migration edit:_target.hasPermission(...)->_target.equalsIgnoreCase(...)@
identifier _VAR5;
expression _target;
expression _DVAR0;
identifier _VAR2;
identifier playerName;
identifier _VAR1;
Player player;
@@
- boolean _VAR5 =_target.hasPermission(_CVAR1);
+ boolean _VAR5 = alias.equalsIgnoreCase(_CVAR1);

