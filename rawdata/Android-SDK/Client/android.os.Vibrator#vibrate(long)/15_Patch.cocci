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
- _target.vibrate(millis);
+ Vibrator vibrator = ((android.os.Vibrator) (mContext.getSystemService(TAG)));
+ Vibrator _VAR2 = vibrator;
+ VibrationEffect effect = VibrationEffect.createOneShot(millis, uid);
+ VibrationEffect _VAR5 = effect;
+  _VAR2.vibrate(_VAR5);

