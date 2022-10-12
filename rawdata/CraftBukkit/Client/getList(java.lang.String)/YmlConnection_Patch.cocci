@API migration edit:_target.getList(...)->_target.getMapList(...)@
identifier rawNodes;
expression _target;
expression _DVAR0;
identifier _VAR0;
YamlConfiguration config;
@@
- List rawNodes =_target.getList(_CVAR1);
+ List rawNodes = _CVAR0.getMapList(_CVAR1);

