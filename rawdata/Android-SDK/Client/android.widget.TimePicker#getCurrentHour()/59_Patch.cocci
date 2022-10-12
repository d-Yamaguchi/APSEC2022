@API migration edit:_target.getCurrentHour(...)->_target.getHour(...)@
identifier _VAR2;
expression _target;
identifier _VAR1;
identifier picker;
identifier _VAR0;
timePicker timePicker;
@@
- Integer _VAR2 =_target.getCurrentHour();
+ void _VAR0 = timePicker;
+ TimePicker picker = ((android.widget.TimePicker) (findViewById(_VAR0)));
+ TimePicker _VAR1 = picker;
+ Integer _VAR2 = _VAR1.getHour();

