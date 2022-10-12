@API migration edit:_target.options(...)->_target.isSet(...)@
identifier _VAR2;
expression _target;
identifier _VAR0;
identifier multiverseConfig;
identifier _VAR1;
@@
- FileConfigurationOptions _VAR2 =_target.options();
+? FileConfiguration multiverseConfig = null;
+? FileConfiguration _VAR0 = this.multiverseConfig;
+? String _VAR1 = "worldnameprefix";
+ FileConfigurationOptions _VAR2 = _VAR0.isSet(_VAR1);

