@API migration edit:_target.setType(...)->_target.setRawTypeIdAndData(...)@
identifier _VAR22;
expression _target;
expression _DVAR0;
identifier _VAR1;
identifier world;
identifier _VAR0;
identifier _VAR6;
identifier x;
int chunkX;
identifier _VAR16;
identifier y;
identifier _VAR8;
identifier _VAR10;
identifier _VAR15;
identifier z;
int chunkZ;
identifier _VAR18;
identifier _VAR20;
identifier _VAR19;
Material Material;
identifier _VAR21;
@@
- _target.setType(_CVAR12);
+ WildGrassPopulator _VAR0 = state;
+ DirectWorld world = _VAR0.getDirectWorld();
+ DirectWorld _VAR1 = world;
+ int x = chunkX;
+ int _VAR6 = x;
+ DirectWorld _VAR8 = world;
+ int _VAR10 = x;
+ int z = chunkZ;
+ int _VAR15 = z;
+ int y = _VAR8.getHighestBlockY(_VAR10, _VAR15);
+ int _VAR16 = y;
+  _VAR1.setRawTypeIdAndData(_VAR6, _VAR16, chance, maxSteps, minSteps);

