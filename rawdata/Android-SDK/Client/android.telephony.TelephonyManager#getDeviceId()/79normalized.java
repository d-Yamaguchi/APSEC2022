public static java.lang.String getImei(android.content.Context context) {
    java.lang.String imei = null;
    try {
        android.content.Context _CVAR0 = context;
        java.lang.String _CVAR1 = android.content.Context.TELEPHONY_SERVICE;
        java.lang.Object _CVAR2 = ((android.telephony.TelephonyManager) (_CVAR0.getSystemService(_CVAR1)));
        java.lang.String _CVAR3 = _CVAR2.getDeviceId();
        java.util.Locale _CVAR4 = java.util.Locale.getDefault();
        java.lang.String _CVAR5 = _CVAR3.toLowerCase(_CVAR4);
        imei = _CVAR5;
    } catch (java.lang.Exception e) {
        e.printStackTrace();
    }
    return imei == null ? null : imei.replaceAll("[^\\da-zA-Z]*", "");
}