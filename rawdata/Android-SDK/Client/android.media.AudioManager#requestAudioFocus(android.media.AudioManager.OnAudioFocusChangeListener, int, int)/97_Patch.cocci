@API migration edit:_target.requestAudioFocus(...)->_target.requestAudioFocus(...)@
identifier _VAR3;
expression _target;
expression _DVAR0;
expression _DVAR1;
expression _DVAR2;
identifier _VAR0;
AudioManager audioManager;
identifier _VAR2;
identifier request;
identifier _VAR1;
AudioFocusRequestOreo audioFocusRequestOreo;
@@
- int _VAR3 =_target.requestAudioFocus(_CVAR1,_CVAR2,_CVAR3);
+ int _VAR3 = _CVAR0.requestAudioFocus(mAudioFocusRequest);

