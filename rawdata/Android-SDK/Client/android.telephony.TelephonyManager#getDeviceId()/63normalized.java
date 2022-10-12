public static java.lang.String getDeviceId(android.content.Context context) {
    android.content.Context _CVAR0 = context;
    java.lang.String _CVAR1 = android.content.Context.TELEPHONY_SERVICE;
    android.telephony.TelephonyManager telephonyManager = ((android.telephony.TelephonyManager) (_CVAR0.getSystemService(_CVAR1)));
    android.telephony.TelephonyManager _CVAR2 = telephonyManager;
    java.lang.String imei = _CVAR2.getDeviceId();
    return imei;
}