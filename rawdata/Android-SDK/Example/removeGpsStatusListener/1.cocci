@API migration edit:_target.removeGpsStatusListener(...)->_target.unregisterGnssStatusCallback(...)@
identifier _VAR2;
expression _target;
expression _DVAR0;
identifier _VAR0;
LocationManager mLocationManager;
identifier _VAR1;
Callback mGpsUpdate;
@@
- void _VAR2 =_target.removeGpsStatusListener(_DVAR0);
+? LocationManager _VAR0 = mLocationManager;
+? Callback _VAR1 = mGpsUpdate;
+ void _VAR2 = _VAR0.unregisterGnssStatusCallback(_VAR1);

