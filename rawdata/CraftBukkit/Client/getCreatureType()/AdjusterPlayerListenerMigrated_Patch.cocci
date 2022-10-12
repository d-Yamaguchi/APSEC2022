@API migration edit:_target.getCreatureType(...)->_target.getSpawnedType(...)@
identifier _VAR1;
expression _target;
identifier _VAR0;
identifier spawner;
identifier state;
@@
- CreatureType _VAR1 =_target.getCreatureType();
+ CreatureType _VAR1 = spawner.getSpawnedType();

