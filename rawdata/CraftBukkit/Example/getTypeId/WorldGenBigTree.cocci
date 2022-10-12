@API migration edit:_target.getTypeId(...)->_target.getType(...)@
identifier block1;
expression _target;
CraftBlockChangeDelegate world;
identifier aint1;
@@
- int block1 =_target.getTypeId();
+? int[] aint1 = new int[]{ 0, 0, 0 };
+ int block1 = this.world.getType(aint1[0], aint1[1], aint1[2]);

