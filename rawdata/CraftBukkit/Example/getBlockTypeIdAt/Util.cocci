@API migration edit:_target.getBlockTypeIdAt(...)->_target.getBlockType(...)@
identifier type;
expression _target;
expression _DVAR0;
expression _DVAR1;
expression _DVAR2;
WorldUtil WorldUtil;
identifier _VAR0;
World world;
identifier _VAR1;
int x;
identifier _VAR2;
int y;
identifier _VAR3;
int z;
@@
- int type =_target.getBlockTypeIdAt(_DVAR0,_DVAR1,_DVAR2);
+? World _VAR0 = world;
+? int _VAR1 = x;
+? int _VAR2 = y;
+? int _VAR3 = z;
+ int type = WorldUtil.getBlockType(_VAR0, _VAR1, _VAR2, _VAR3);

