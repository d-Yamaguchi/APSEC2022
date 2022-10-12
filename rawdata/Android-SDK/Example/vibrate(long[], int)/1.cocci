@API migration edit:_target.vibrate(...)->_target.vibrate(...)@
identifier _VAR4;
expression _target;
expression _DVAR0;
expression _DVAR1;
identifier _VAR0;
Vibrator vibratorAlarm;
identifier _VAR3;
identifier effect;
VibrationEffect VibrationEffect;
identifier _VAR1;
identifier pattern;
identifier _VAR2;
identifier repeat;
@@
- void _VAR4 =_target.vibrate(_DVAR0,_DVAR1);
+? Vibrator _VAR0 = vibratorAlarm;
+? long[] pattern = new long[]{ 0, 400, 800 };
+? long[] _VAR1 = pattern;
+? int repeat = 0;
+? int _VAR2 = repeat;
+? VibrationEffect effect = VibrationEffect.createWaveform(_VAR1, _VAR2);
+? VibrationEffect _VAR3 = effect;
+ void _VAR4 = _VAR0.vibrate(_VAR3);

