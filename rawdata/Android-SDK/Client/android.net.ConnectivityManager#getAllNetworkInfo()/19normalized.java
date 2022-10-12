public static boolean IsConnected(android.content.Context context) {
    int type = android.net.ConnectivityManager.TYPE_WIFI;
    android.content.Context _CVAR0 = context;
    java.lang.String _CVAR1 = android.content.Context.CONNECTIVITY_SERVICE;
    android.net.ConnectivityManager connectivity = ((android.net.ConnectivityManager) (_CVAR0.getSystemService(_CVAR1)));
    android.net.ConnectivityManager _CVAR2 = connectivity;
    android.net.NetworkInfo[] info = _CVAR2.getAllNetworkInfo();
    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
        android.net.NetworkRequest.Builder request = new android.net.NetworkRequest.Builder();
        request.addCapability(android.net.NetworkCapabilities.NET_CAPABILITY_INTERNET);
        // request.addTransportType(NetworkCapabilities.TRANSPORT_WIFI);
        connectivity.registerNetworkCallback(request.build(), new android.net.ConnectivityManager.NetworkCallback() {
            @java.lang.Override
            public void onAvailable(android.net.Network network) {
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                    android.net.ConnectivityManager.setProcessDefaultNetwork(network);
                }
            }
        });
    }
    // ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
    if (connectivity != null) {
        // NetworkInfo[] info = connectivity.getAllNetworkInfo();
        if (info != null) {
            for (int i = 0; i < info.length; i++) {
                // Log.v("MyTag",info[i].toString());
                if (info[i].getState() == android.net.NetworkInfo.State.CONNECTED) {
                    return true;
                }
            }
        }
    }
    return false;
}