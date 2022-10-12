@API migration edit:_target.setAudioStreamType(...)->_target.setAudioAttributes(...)@
identifier _VAR3;
expression _target;
expression _DVAR0;
identifier _VAR0;
MediaPlayer mMediaPlayer;
identifier _VAR2;
identifier attributes;
identifier _VAR1;
identifier builder;
@@
- _target.setAudioStreamType(_CVAR1);
+ MediaPlayer _VAR0 = mMediaPlayer;
+ Builder builder = new android.media.AudioAttributes.Builder();
+ Builder _VAR1 = builder;
+ AudioAttributes attributes = _VAR1.build();
+ AudioAttributes _VAR2 = attributes;
+  _VAR0.setAudioAttributes(_VAR2);

