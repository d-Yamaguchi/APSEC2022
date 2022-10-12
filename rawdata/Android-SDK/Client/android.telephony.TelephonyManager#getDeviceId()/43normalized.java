public static java.lang.String getDeviceId() {
     _CVAR0 = com.olaappathon.main.OlaAppathon.getContext();
    java.lang.String _CVAR1 = android.content.Context.TELEPHONY_SERVICE;
    final android.telephony.TelephonyManager telephonyManager = ((android.telephony.TelephonyManager) (_CVAR0.getSystemService(_CVAR1)));
    android.telephony.TelephonyManager _CVAR2 = telephonyManager;
    java.lang.String deviceId = _CVAR2.getDeviceId();
    if (deviceId != null) {
        return java.lang.Integer.toHexString(deviceId.hashCode());
    } else {
        return "";
    }
}