@API migration edit:_target.getString(...)->_target.getConfigurationSection(...)@
identifier userSection;
expression _target;
expression _DVAR0;
expression _DVAR1;
identifier _VAR0;
BoosConfigManager BoosConfigManager;
@@
- String userSection =_target.getString(_DVAR0,_DVAR1);
+? YamlConfiguration _VAR0 = cz.boosik.boosCooldown.Managers.BoosConfigManager.confusers;
+ String userSection = _VAR0.getConfigurationSection(_DVAR0);

