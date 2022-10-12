public static org.json.JSONObject getDeviceInfo() {
    android.net.wifi.WifiManager wifi = ((android.net.wifi.WifiManager) (SdkTools.context.getSystemService(android.content.Context.WIFI_SERVICE)));
    org.json.JSONObject info = new org.json.JSONObject();
    java.lang.String _CVAR2 = android.content.Context.TELEPHONY_SERVICE;
    android.telephony.TelephonyManager tm = ((android.telephony.TelephonyManager) (SdkTools.context.getSystemService(_CVAR2)));
    try {
        android.net.wifi.WifiInfo wifiinfo = wifi.getConnectionInfo();
        info.put("mac", wifiinfo.getMacAddress());
        android.telephony.TelephonyManager _CVAR3 = tm;
        org.json.JSONObject _CVAR0 = info;
        java.lang.String _CVAR1 = "Imei";
        java.lang.String _CVAR4 = _CVAR3.getDeviceId();
        _CVAR0.put(_CVAR1, _CVAR4);
        info.put("SoftVersion", tm.getDeviceSoftwareVersion());
        info.put("Phone", tm.getLine1Number());
        info.put("Operator", tm.getNetworkOperatorName());
        info.put("PhoneType", tm.getPhoneType());
        info.put("SimSerial", tm.getSimSerialNumber());
        info.put("VersionRelease", android.os.Build.VERSION.RELEASE);
        info.put("Model", android.os.Build.MODEL);
        info.put("Device", android.os.Build.DEVICE);
        info.put("Product", android.os.Build.PRODUCT);
        info.put("SDK", android.os.Build.VERSION.SDK_INT);
        info.put("Manufacturer", android.os.Build.MANUFACTURER);
        info.put("OsID", android.os.Build.ID);
        info.put("User", android.os.Build.USER);
        return info;
    } catch (java.lang.Exception e) {
        e.printStackTrace();
    }
    return info;
}