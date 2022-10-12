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
- Material mat =_target.valueOf(_CVAR2);
+ Material mat = Material.matchMaterial(_CVAR2);

