@API migration edit:_target.getBlockAt(...)->_target.getHighestBlockY(...)@
identifier y;
expression _target;
expression _DVAR0;
expression _DVAR1;
expression _DVAR2;
identifier _VAR1;
identifier world;
identifier _VAR0;
identifier _VAR6;
identifier x;
int chunkX;
identifier _VAR11;
identifier z;
int chunkZ;
@@
- Block y =_target.getBlockAt(_CVAR2,_CVAR6,_CVAR8);
+ WildGrassPopulator _VAR0 = state;
+ DirectWorld world = _VAR0.getDirectWorld();
+ DirectWorld _VAR1 = world;
+ Block y = _VAR1.getHighestBlockY(_CVAR2, _CVAR8);

