public static java.lang.String getDeviceID(android.content.Context context) {
    try {
        android.content.Context _CVAR0 = context;
        java.lang.String _CVAR1 = android.content.Context.TELEPHONY_SERVICE;
        android.telephony.TelephonyManager telephonyManager = ((android.telephony.TelephonyManager) (_CVAR0.getSystemService(_CVAR1)));
        android.telephony.TelephonyManager _CVAR2 = telephonyManager;
        java.lang.String _CVAR3 = _CVAR2.getDeviceId();
        return _CVAR3;
    } catch (java.lang.SecurityException e) {
        e.printStackTrace();
    } catch (java.lang.NullPointerException e) {
        e.printStackTrace();
    }
    return "";
}