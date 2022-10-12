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
+ String _VAR3 = _CVAR2.getImei();

