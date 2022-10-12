@API migration edit:_target.setCurrentMinute(...)->_target.setMinute(...)@
identifier _VAR2;
expression _target;
expression _DVAR0;
identifier _VAR0;
identifier picker;
identifier _VAR1;
identifier lastMinute;
@@
- _target.setCurrentMinute(_CVAR1);
+ TimePicker picker = null;
+ TimePicker _VAR0 = picker;
+ int lastMinute = 0;
+ int _VAR1 = lastMinute;
+  _VAR0.setMinute(_VAR1);

