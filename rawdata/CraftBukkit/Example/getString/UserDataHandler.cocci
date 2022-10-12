@API migration edit:_target.getString(...)->_target.getConfigurationSection(...)@
identifier is;
expression _target;
expression _DVAR0;
expression _DVAR1;
identifier _VAR4;
@@
- String is =_target.getString(_DVAR0,_DVAR1);
+? YamlConfiguration _VAR4 = config;
+ String is = _VAR4.getConfigurationSection(_DVAR0);

