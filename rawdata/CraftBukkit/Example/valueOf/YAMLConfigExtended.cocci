@API migration edit:_target.valueOf(...)->_target.matchMaterial(...)@
identifier mat;
expression _target;
expression _DVAR0;
Material Material;
identifier _VAR2;
identifier _VAR0;
String path;
identifier _VAR1;
@@
- Material mat =_target.valueOf(_DVAR0);
+? String _VAR0 = path;
+? String _VAR1 = "";
+? String _VAR2 = getString(_VAR0, _VAR1);
+ Material mat = Material.matchMaterial(_VAR2);

