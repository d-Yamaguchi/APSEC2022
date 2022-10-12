public java.lang.String getDeviceId() {
    final java.lang.String tmDevice;
    final java.lang.String tmSerial;
    final java.lang.String androidId;
    android.content.Context _CVAR0 = getBaseContext();
    java.lang.String _CVAR1 = android.content.Context.TELEPHONY_SERVICE;
    final android.telephony.TelephonyManager tm = ((android.telephony.TelephonyManager) (_CVAR0.getSystemService(_CVAR1)));
    android.telephony.TelephonyManager _CVAR2 = tm;
    java.lang.String _CVAR3 = _CVAR2.getDeviceId();
    java.lang.String _CVAR4 = "" + _CVAR3;
    tmDevice = _CVAR4;
    tmSerial = "" + tm.getSimSerialNumber();
    androidId = "" + android.provider.Settings.Secure.getString(getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);
    java.util.UUID deviceUuid = new java.util.UUID(androidId.hashCode(), (((long) (tmDevice.hashCode())) << 32) | tmSerial.hashCode());
    return deviceUuid.toString();
}