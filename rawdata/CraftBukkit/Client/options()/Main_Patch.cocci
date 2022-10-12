@API migration edit:_target.options(...)->_target.isSet(...)@
identifier _VAR2;
expression _target;
identifier _VAR0;
identifier multiverseConfig;
identifier _VAR1;
@@
- FileConfigurationOptions _VAR2 =_target.options();
+ FileConfigurationOptions _VAR2 = _CVAR0.isSet(_prefix);

