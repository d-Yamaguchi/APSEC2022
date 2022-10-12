@API migration edit:_target.getList(...)->_target.getMapList(...)@
identifier rawNodes;
expression _target;
expression _DVAR0;
identifier _VAR0;
YamlConfiguration config;
@@
- List rawNodes =_target.getList(_DVAR0);
+? YamlConfiguration _VAR0 = this.config;
+ List rawNodes = _VAR0.getMapList(_DVAR0);

