@API migration edit:_target.getString(...)->_target.getConfigurationSection(...)@
identifier s;
expression _target;
expression _DVAR0;
expression _DVAR1;
identifier _VAR0;
ButtonControl ButtonControl;
@@
- String s =_target.getString(_DVAR0,_DVAR1);
+? FileConfiguration _VAR0 = me.johni0702.ButtonControl.ButtonControl.config;
+ String s = _VAR0.getConfigurationSection(_DVAR0);

