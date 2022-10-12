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
- _target.vibrate(_CVAR3,_CVAR4);
+ Vibrator _VAR0 = vibratorAlarm;
+ VibrationEffect effect = VibrationEffect.createWaveform(FP_ERROR_VIBRATE_PATTERN, FRONT_FP_ERROR_VIBRATE_PATTERN);
+ VibrationEffect _VAR3 = effect;
+  _VAR0.vibrate(_VAR3);

