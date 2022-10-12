@API migration edit:_target.getString(...)->_target.getConfigurationSection(...)@
identifier itemsSection;
expression _target;
expression _DVAR0;
expression _DVAR1;
identifier _VAR0;
@@
- String itemsSection =_target.getString(_DVAR0,_DVAR1);
+? FileConfiguration _VAR0 = config;
+ String itemsSection = _VAR0.getConfigurationSection(_DVAR0);

