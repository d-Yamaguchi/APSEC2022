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
+? Context _VAR0 = _context;
+? String _VAR1 = android.content.Context.CONNECTIVITY_SERVICE;
+? ConnectivityManager connectivityManager = ((android.net.ConnectivityManager) (_VAR0.getSystemService(_VAR1)));
+? ConnectivityManager _VAR2 = connectivityManager;
+ NetworkInfo[] networks = _VAR2.getAllNetworks();

