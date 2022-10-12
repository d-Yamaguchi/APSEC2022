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
+? Context context = VasCogApplication.getInstance();
+? Context _VAR0 = context;
+? String _VAR1 = android.content.Context.TELEPHONY_SERVICE;
+? TelephonyManager tm = ((android.telephony.TelephonyManager) (_VAR0.getSystemService(_VAR1)));
+? TelephonyManager _VAR2 = tm;
+ String _VAR3 = _VAR2.getImei();

