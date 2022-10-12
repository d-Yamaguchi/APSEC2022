@API migration edit:_target.getString(...)->_target.getConfigurationSection(...)@
identifier _VAR2;
expression _target;
expression _DVAR0;
expression _DVAR1;
identifier _VAR0;
Configuration c;
@@
- String _VAR2 =_target.getString(_CVAR1,_CVAR2);
+ Configuration _VAR0 = c;
+ String _VAR2 = _VAR0.getConfigurationSection(_CVAR1);

