@API migration edit:_target.setCurrentHour(...)->_target.setHour(...)@
identifier _VAR2;
expression _target;
expression _DVAR0;
identifier _VAR0;
identifier picker;
identifier _VAR1;
identifier lastHour;
@@
- _target.setCurrentHour(_CVAR9);
+ TimePicker picker = null;
+ TimePicker _VAR0 = picker;
+  _VAR0.setHour(startHr);

