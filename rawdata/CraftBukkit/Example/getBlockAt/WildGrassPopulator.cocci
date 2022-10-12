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
- Block y =_target.getBlockAt(_DVAR0,_DVAR1,_DVAR2);
+? WildGrassPopulator _VAR0 = state;
+? DirectWorld world = _VAR0.getDirectWorld();
+? DirectWorld _VAR1 = world;
+? int x = chunkX;
+? int _VAR6 = x;
+? int z = chunkZ;
+? int _VAR11 = z;
+ Block y = _VAR1.getHighestBlockY(_VAR6, _VAR11);

