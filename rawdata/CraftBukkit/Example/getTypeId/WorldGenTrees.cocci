@API migration edit:_target.getTypeId(...)->_target.getType(...)@
identifier _VAR5;
expression _target;
identifier _VAR0;
CraftBlockChangeDelegate world;
identifier _VAR2;
identifier k1;
int i;
identifier _VAR3;
identifier j1;
int j;
identifier _VAR4;
identifier i1;
@@
- int _VAR5 =_target.getTypeId();
+? CraftBlockChangeDelegate _VAR0 = world;
+? int k1 = i;
+? int _VAR2 = k1;
+? int j1 = j;
+? int _VAR3 = j1;
+? int i1 = j1;
+? int _VAR4 = i1;
+ int _VAR5 = _VAR0.getType(_VAR2, _VAR3, _VAR4);

