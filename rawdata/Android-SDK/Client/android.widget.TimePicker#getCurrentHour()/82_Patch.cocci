@API migration edit:_target.getCurrentHour(...)->_target.getHour(...)@
identifier _VAR2;
expression _target;
identifier _VAR1;
identifier picker;
identifier _VAR0;
timePicker timePicker;
@@
- Integer _VAR2 =_target.getCurrentHour();
+ Integer _VAR2 = _CVAR7.getHour();

