@API migration edit:_target.loadConfiguration(...)->_target.getMaterial(...)@
identifier _VAR3;
expression _target;
expression _DVAR0;
Material Material;
identifier _VAR2;
identifier f_str;
identifier _VAR0;
identifier _VAR1;
@@
- YamlConfiguration _VAR3 =_target.loadConfiguration(_DVAR0);
+? FileConfiguration _VAR0 = getConfig();
+? String _VAR1 = "farmer_material";
+? String f_str = _VAR0.getString(_VAR1);
+? String _VAR2 = f_str;
+ YamlConfiguration _VAR3 = Material.getMaterial(_VAR2);

