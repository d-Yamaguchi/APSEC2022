@API migration edit:_target.vibrate(...)->_target.vibrate(...)@
identifier _VAR6;
expression _target;
expression _DVAR0;
identifier _VAR2;
identifier vibrator;
identifier _VAR0;
Context context;
identifier _VAR1;
Context Context;
identifier _VAR5;
identifier effect;
VibrationEffect VibrationEffect;
identifier _VAR3;
identifier milliseconds;
AppVibrator AppVibrator;
identifier _VAR4;
identifier amplitude;
AppVibrator AppVibrator;
@@
- _target.vibrate(_CVAR1);
+ int amplitude = com.ankhrom.coinmarketcap.common.AppVibrator.AMPLITUDE;
+ int _VAR4 = amplitude;
+ VibrationEffect effect = VibrationEffect.createOneShot(time, _VAR4);
+ VibrationEffect _VAR5 = effect;
+  vibrator.vibrate(_VAR5);

