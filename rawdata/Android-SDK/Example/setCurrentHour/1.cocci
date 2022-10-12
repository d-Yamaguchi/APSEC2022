@API migration edit:_target.setCurrentHour(...)->_target.setHour(...)@
identifier _VAR2;
expression _target;
expression _DVAR0;
identifier _VAR0;
identifier picker;
identifier _VAR1;
identifier lastHour;
@@
- void _VAR2 =_target.setCurrentHour(_DVAR0);
+? TimePicker picker = null;
+? TimePicker _VAR0 = picker;
+? int lastHour = 0;
+? int _VAR1 = lastHour;
+ void _VAR2 = _VAR0.setHour(_VAR1);

