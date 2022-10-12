@API migration edit:_target.setCurrentMinute(...)->_target.setMinute(...)@
identifier _VAR2;
expression _target;
expression _DVAR0;
identifier _VAR0;
identifier picker;
identifier _VAR1;
identifier lastMinute;
@@
- _target.setCurrentMinute(_CVAR2);
+ TimePicker picker = null;
+ TimePicker _VAR0 = picker;
+  _VAR0.setMinute(mHour);

