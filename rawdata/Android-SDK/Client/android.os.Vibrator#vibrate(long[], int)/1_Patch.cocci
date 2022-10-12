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
- _target.vibrate(_CVAR1,_CVAR2);
+ long[] pattern = new long[]{ 0, 400, 800 };
+ long[] _VAR1 = pattern;
+ VibrationEffect effect = VibrationEffect.createWaveform(_VAR1, longSize);
+ VibrationEffect _VAR3 = effect;
+  vibrator.vibrate(_VAR3);

