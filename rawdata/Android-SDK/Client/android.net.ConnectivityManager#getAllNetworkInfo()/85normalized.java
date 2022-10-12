// TODO cleanup this method and the next one to avoid duplication
// TODO This entire method needs revamping
// There is a bug in Android that causes isConnected to fail sometimes without toggling wifi
public static void logNetworkStatus(android.content.Context aContext) {
    boolean haveConnectedWifi = false;
    boolean haveConnectedMobile = false;
    android.content.Context _CVAR0 = aContext;
    java.lang.String _CVAR1 = android.content.Context.CONNECTIVITY_SERVICE;
    android.net.ConnectivityManager connectivityManager = ((android.net.ConnectivityManager) (_CVAR0.getSystemService(_CVAR1)));
    android.net.ConnectivityManager _CVAR2 = connectivityManager;
    android.net.NetworkInfo[] netInfo = _CVAR2.getAllNetworkInfo();
    for (android.net.NetworkInfo ni : netInfo) {
        // This can fail on some phones (e.g. yifei's phone)
        if (ni.getType() == android.net.ConnectivityManager.TYPE_WIFI) {
            if (ni.isConnected()) {
                haveConnectedWifi = true;
            }
        }
        if (ni.getType() == android.net.ConnectivityManager.TYPE_MOBILE) {
            if (ni.isConnected()) {
                haveConnectedMobile = true;
            }
        }
    }
    android.net.NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
    boolean isConnected = (activeNetwork != null) && activeNetwork.isConnectedOrConnecting();
    edu.neu.android.wocketslib.utils.Log.o(edu.neu.android.wocketslib.utils.NetworkMonitor.TAG, isConnected ? "isNetwork" : "noNetwork", haveConnectedWifi ? "WIFI" : "noWIFI", haveConnectedMobile ? "MobileData" : "noMobileData");
}