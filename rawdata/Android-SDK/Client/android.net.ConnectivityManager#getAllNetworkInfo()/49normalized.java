public static boolean hasInternet(android.support.v4.app.FragmentActivity activity) {
    boolean hasConnectedWifi = false;
    boolean hasConnectedMobile = false;
    try {
        android.support.v4.app.FragmentActivity _CVAR0 = activity;
        java.lang.String _CVAR1 = android.content.Context.CONNECTIVITY_SERVICE;
        android.net.ConnectivityManager cmManager = ((android.net.ConnectivityManager) (_CVAR0.getSystemService(_CVAR1)));
        android.net.ConnectivityManager _CVAR2 = cmManager;
        android.net.NetworkInfo[] netInfo = _CVAR2.getAllNetworkInfo();
        for (android.net.NetworkInfo net : netInfo) {
            if (net.getTypeName().equalsIgnoreCase("wifi")) {
                if (net.isConnected()) {
                    hasConnectedWifi = true;
                }
            }
            if (net.getTypeName().equalsIgnoreCase("mobile")) {
                if (net.isConnected()) {
                    hasConnectedMobile = true;
                }
            }
        }
    } catch (java.lang.Exception e) {
    }
    return hasConnectedWifi || hasConnectedMobile;
}