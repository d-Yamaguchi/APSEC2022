@API migration edit:_target.abandonAudioFocus(...)->_target.abandonAudioFocusRequest(...)@
identifier _VAR4;
expression _target;
expression _DVAR0;
identifier _VAR0;
AudioManager audioManager;
identifier _VAR3;
identifier request;
identifier _VAR2;
identifier builder;
identifier _VAR1;
AudioManager AudioManager;
@@
- _target.abandonAudioFocus(_CVAR1);
+ AudioManager _VAR0 = audioManager;
+ Builder builder = new android.media.AudioFocusRequest.Builder(focusChange);
+ Builder _VAR2 = builder;
+ AudioFocusRequest request = _VAR2.build();
+ AudioFocusRequest _VAR3 = request;
+  _VAR0.abandonAudioFocusRequest(_VAR3);

