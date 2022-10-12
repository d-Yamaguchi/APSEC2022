/* Deviceid */
public java.lang.String getDeivceId(android.content.Context context) {
    android.content.Context _CVAR0 = context;
    java.lang.String _CVAR1 = android.content.Context.TELEPHONY_SERVICE;
    android.telephony.TelephonyManager telephonyManager = ((android.telephony.TelephonyManager) (_CVAR0.getSystemService(_CVAR1)));
    android.telephony.TelephonyManager _CVAR2 = telephonyManager;
    final java.lang.String deviceId = _CVAR2.getDeviceId();
    final java.lang.String androidId = android.provider.Settings.Secure.getString(context.getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);
    java.lang.String uuid = "";
    if (deviceId != null) {
        uuid = deviceId;
    } else if (!"9774d56d682e549c".equals(androidId)) {
        uuid = androidId;
    } else {
        // uuid = UUID.randomUUID().toString();
        uuid = getUUID(context);
    }
    return uuid;
}