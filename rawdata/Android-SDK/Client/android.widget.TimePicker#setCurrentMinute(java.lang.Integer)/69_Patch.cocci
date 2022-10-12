@API migration edit:_target.setCurrentMinute(...)->_target.setMinute(...)@
identifier _VAR2;
expression _target;
expression _DVAR0;
identifier _VAR0;
identifier picker;
identifier _VAR1;
identifier lastMinute;
@@
- _target.setCurrentMinute(_CVAR3);
+ int lastMinute = 0;
+ int _VAR1 = lastMinute;
+  timePicker.setMinute(_VAR1);

