@API migration edit:_target.removeGpsStatusListener(...)->_target.unregisterGnssStatusCallback(...)@
identifier _VAR2;
expression _target;
expression _DVAR0;
identifier _VAR0;
LocationManager mLocationManager;
identifier _VAR1;
Callback mGpsUpdate;
@@
- _target.removeGpsStatusListener(_CVAR1);
+ Callback _VAR1 = mGpsUpdate;
+  mLocationManager.unregisterGnssStatusCallback(_VAR1);

