@android.annotation.SuppressLint({ "MissingPermission" })
public static java.lang.String getDeviceId(android.content.Context context) {
    java.lang.String str;
    java.lang.String str2 = null;
    if (com.sina.deviceidjnisdk.DeviceInfo.isPermissionGranted(context, "android.permission.READ_PHONE_STATE")) {
        try {
            if (telephonyManager == null) {
                return null;
            }
            android.content.Context _CVAR0 = context;
            java.lang.String _CVAR1 = "phone";
            android.telephony.TelephonyManager telephonyManager = ((android.telephony.TelephonyManager) (_CVAR0.getSystemService(_CVAR1)));
            if (android.os.Build.VERSION.SDK_INT >= 23) {
                android.telephony.TelephonyManager _CVAR2 = telephonyManager;
                int _CVAR3 = 0;
                java.lang.String _CVAR4 = _CVAR2.getDeviceId(_CVAR3);
                str = _CVAR4;
            } else {
                str = telephonyManager.getDeviceId();
            }
            if (str == null) {
                str = "";
            }
            str2 = str;
        } catch (java.lang.Exception e) {
            e.printStackTrace();
            str2 = "";
        }
    }
    return str2;
}