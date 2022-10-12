@API migration edit:_target.addGpsStatusListener(...)->_target.registerGnssStatusCallback(...)@
identifier _VAR2;
expression _target;
expression _DVAR0;
identifier _VAR0;
identifier _VAR1;
Callback callback;
@@
- boolean _VAR2 =_target.addGpsStatusListener(_DVAR0);
+? MixedPositionProvider _VAR0 = locationManager;
+? Callback _VAR1 = callback;
+ boolean _VAR2 = _VAR0.registerGnssStatusCallback(_VAR1);

