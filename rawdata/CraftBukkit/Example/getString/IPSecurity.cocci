@API migration edit:_target.getString(...)->_target.getConfigurationSection(...)@
identifier _VAR2;
expression _target;
expression _DVAR0;
expression _DVAR1;
identifier _VAR0;
YamlConfiguration config;
@@
- String _VAR2 =_target.getString(_DVAR0,_DVAR1);
+? YamlConfiguration _VAR0 = config;
+ String _VAR2 = _VAR0.getConfigurationSection(_DVAR0);

