@API migration edit:_target.loadConfiguration(...)->_target.getMaterial(...)@
identifier _VAR3;
expression _target;
expression _DVAR0;
Material Material;
identifier _VAR2;
identifier f_str;
identifier _VAR0;
identifier _VAR1;
@@
- YamlConfiguration _VAR3 =_target.loadConfiguration(_CVAR2);
+ YamlConfiguration _VAR3 = Material.getMaterial(_CVAR1);

