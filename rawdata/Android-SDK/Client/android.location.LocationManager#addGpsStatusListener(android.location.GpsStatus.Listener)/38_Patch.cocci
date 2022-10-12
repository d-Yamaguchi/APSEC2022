@API migration edit:_target.addGpsStatusListener(...)->_target.registerGnssStatusCallback(...)@
identifier _VAR2;
expression _target;
expression _DVAR0;
identifier _VAR0;
identifier _VAR1;
Callback callback;
@@
- _target.addGpsStatusListener(_CVAR1);
+ MixedPositionProvider _VAR0 = locationManager;
+ Callback _VAR1 = callback;
+  _VAR0.registerGnssStatusCallback(_VAR1);

