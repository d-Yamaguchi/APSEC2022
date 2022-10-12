@API migration edit:_target.getTypeId(...)->_target.getType(...)@
identifier block;
expression _target;
identifier _VAR0;
CraftBlockChangeDelegate world;
identifier _VAR1;
int i;
identifier _VAR3;
int j;
identifier _VAR4;
int k;
@@
- int block =_target.getTypeId();
+? CraftBlockChangeDelegate _VAR0 = world;
+? int _VAR1 = i;
+? int _VAR3 = j;
+? int _VAR4 = k;
+ int block = _VAR0.getType(_VAR1, _VAR3, _VAR4);

