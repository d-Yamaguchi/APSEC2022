@API migration edit:_target.getDeviceId(...)->_target.getImei(...)@
identifier _VAR3;
expression _target;
identifier _VAR2;
identifier tm;
identifier _VAR0;
identifier context;
VasCogApplication VasCogApplication;
identifier _VAR1;
Context Context;
@@
- String _VAR3 =_target.getDeviceId();
+ TelephonyManager tm = ((android.telephony.TelephonyManager) (context.getSystemService(TAG)));
+ TelephonyManager _VAR2 = tm;
+ String _VAR3 = _VAR2.getImei();

