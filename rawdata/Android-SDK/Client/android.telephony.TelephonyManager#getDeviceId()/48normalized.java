public static java.lang.String getUniqueId(android.content.Context context) {
    java.lang.String uniqueId = null;
    java.lang.String tmDevice = "";
    java.lang.String tmSerial = "";
    java.lang.String androidId = "";
    // Get id from TelephonyManager
    // might get null values in tablet
    try {
        android.content.Context _CVAR3 = context;
        java.lang.String _CVAR4 = android.content.Context.TELEPHONY_SERVICE;
        android.content.Context _CVAR8 = _CVAR3;
        java.lang.String _CVAR9 = _CVAR4;
        android.telephony.TelephonyManager mTelephony = ((android.telephony.TelephonyManager) (_CVAR8.getSystemService(_CVAR9)));
        if (mTelephony != null) {
            <nulltype> _CVAR0 = null;
            boolean _CVAR1 = mTelephony.getDeviceId() != _CVAR0;
            if () {
                android.telephony.TelephonyManager _CVAR5 = mTelephony;
                java.lang.String _CVAR6 = _CVAR5.getDeviceId();
                java.lang.String _CVAR2 = com.project.dayworx.util.UniqueId.TAG;
                java.lang.String _CVAR7 = "mTelephony.getDeviceId() = " + _CVAR6;
                android.util.Log.e(_CVAR2, _CVAR7);
                android.telephony.TelephonyManager _CVAR10 = mTelephony;
                java.lang.String _CVAR11 = _CVAR10.getDeviceId();
                java.lang.String _CVAR12 = "" + _CVAR11;
                tmDevice = _CVAR12;
            }
            <nulltype> _CVAR13 = null;
            boolean _CVAR14 = mTelephony.getDeviceId() != _CVAR13;
            if () {
                android.util.Log.e(com.project.dayworx.util.UniqueId.TAG, "mTelephony.getSimSerialNumber() = " + mTelephony.getSimSerialNumber());
                tmSerial = "" + mTelephony.getSimSerialNumber();
            }
        }
    } catch (java.lang.Exception e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    }
    // ANDROID_ID
    // Not to be a unique
    try {
        androidId = "" + android.provider.Settings.Secure.getString(context.getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);
        android.util.Log.e(com.project.dayworx.util.UniqueId.TAG, "androidId = " + androidId);
    } catch (java.lang.Exception e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    }
    // Generate hash from above values
    // if TelephonyManager is going to return null and ANDROID_ID and also
    // then we will have hash of empty string
    try {
        java.util.UUID deviceUuid = new java.util.UUID(androidId.hashCode(), (((long) (tmDevice.hashCode())) << 32) | tmSerial.hashCode());
        uniqueId = deviceUuid.toString();
    } catch (java.lang.Exception e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    }
    android.util.Log.e(com.project.dayworx.util.UniqueId.TAG, "uniqueId = " + uniqueId);
    return uniqueId;
}