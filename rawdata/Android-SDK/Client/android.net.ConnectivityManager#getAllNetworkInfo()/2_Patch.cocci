@API migration edit:_target.getAllNetworkInfo(...)->_target.getAllNetworks(...)@
identifier networks;
expression _target;
identifier _VAR2;
identifier connectivityManager;
identifier _VAR0;
Context _context;
identifier _VAR1;
Context Context;
@@
- NetworkInfo[] networks =_target.getAllNetworkInfo();
+ NetworkInfo[] networks = _CVAR1.getAllNetworks();

