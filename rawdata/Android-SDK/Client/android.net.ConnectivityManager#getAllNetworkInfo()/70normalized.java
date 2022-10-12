public static boolean isWifi(android.content.Context context) {
    android.content.Context _CVAR0 = context;
    java.lang.String _CVAR1 = "connectivity";
    android.net.ConnectivityManager connectivityManager = ((android.net.ConnectivityManager) (_CVAR0.getSystemService(_CVAR1)));
    if (connectivityManager != null) {
        android.net.ConnectivityManager _CVAR2 = connectivityManager;
        android.net.NetworkInfo[] _CVAR3 = _CVAR2.getAllNetworkInfo();
        for (android.net.NetworkInfo info : ) {
            if ((1 == info.getType()) && info.isConnected()) {
                return true;
            }
        }
    }
    return false;
}