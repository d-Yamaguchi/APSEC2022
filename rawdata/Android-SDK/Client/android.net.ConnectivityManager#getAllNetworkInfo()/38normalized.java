public static boolean isNetworkAvailable(android.app.Activity mActivity) {
    android.app.Activity _CVAR0 = mActivity;
    android.content.Context context = _CVAR0.getApplicationContext();
    android.content.Context _CVAR1 = context;
    java.lang.String _CVAR2 = android.content.Context.CONNECTIVITY_SERVICE;
    android.net.ConnectivityManager connectivity = ((android.net.ConnectivityManager) (_CVAR1.getSystemService(_CVAR2)));
    if (connectivity == null) {
        return false;
    } else {
        android.net.ConnectivityManager _CVAR3 = connectivity;
        android.net.NetworkInfo[] info = _CVAR3.getAllNetworkInfo();
        if (info != null) {
            for (int i = 0; i < info.length; i++) {
                if (info[i].getState() == null) {
                    continue;
                }
                if (info[i].getState() == android.net.NetworkInfo.State.CONNECTED) {
                    return true;
                }
            }
        }
    }
    return false;
}