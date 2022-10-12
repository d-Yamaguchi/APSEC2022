public static boolean isWifiAvailable() {
    android.content.Context _CVAR0 = com.studychen.seenews.util.NetworkUtil.context;
    java.lang.String _CVAR1 = com.studychen.seenews.util.NetworkUtil.context.CONNECTIVITY_SERVICE;
    android.net.ConnectivityManager cm = ((android.net.ConnectivityManager) (_CVAR0.getSystemService(_CVAR1)));
    android.net.ConnectivityManager _CVAR2 = cm;
    android.net.NetworkInfo[] netInfos = _CVAR2.getAllNetworkInfo();
    for (android.net.NetworkInfo net : netInfos) {
        java.lang.String type = net.getTypeName();
        if (type.equalsIgnoreCase("WIFI")) {
            if (net.isConnected()) {
                return true;
            }
        }
    }
    return false;
}