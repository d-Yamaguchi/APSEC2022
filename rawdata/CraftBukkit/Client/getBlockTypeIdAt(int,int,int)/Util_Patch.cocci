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
- int type =_target.getBlockTypeIdAt(_CVAR1,_CVAR2,_CVAR3);
+ int type = WorldUtil.getBlockType(_CVAR0, _CVAR1, _CVAR2, _CVAR3);

