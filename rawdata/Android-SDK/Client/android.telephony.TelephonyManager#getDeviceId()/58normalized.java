public static java.lang.String getImeiNew(android.content.Context context) {
    java.lang.String imei = null;
    try {
        if (context != null) {
            int state = androidx.core.content.PermissionChecker.checkSelfPermission(context, "android.permission.READ_PHONE_STATE");
            android.util.Log.i(com.example.customview.DeviceInfo.TAG, "getImeiNew: state = " + state);
            android.content.Context _CVAR0 = context;
            java.lang.String _CVAR1 = android.content.Context.TELEPHONY_SERVICE;
            android.content.Context _CVAR4 = _CVAR0;
            java.lang.String _CVAR5 = _CVAR1;
            android.telephony.TelephonyManager telephonyManager = ((android.telephony.TelephonyManager) (_CVAR4.getSystemService(_CVAR5)));
            if ((telephonyManager != null) && (state == android.content.pm.PackageManager.PERMISSION_GRANTED)) {
                if (android.os.Build.VERSION.SDK_INT >= 26) {
                    try {
                        java.lang.reflect.Method method = telephonyManager.getClass().getMethod("getImei", new java.lang.Class[0]);
                        method.setAccessible(true);
                        imei = ((java.lang.String) (method.invoke(telephonyManager, new java.lang.Object[0])));
                        android.util.Log.i(com.example.customview.DeviceInfo.TAG, "getImeiNew: 反射 获取 imei = " + imei);
                    } catch (java.lang.Exception e) {
                        android.util.Log.w(com.example.customview.DeviceInfo.TAG, "getImeiNew: 11", e);
                    }
                    if (android.text.TextUtils.isEmpty(imei)) {
                        android.telephony.TelephonyManager _CVAR2 = telephonyManager;
                        java.lang.String _CVAR3 = _CVAR2.getDeviceId();
                        imei = _CVAR3;
                        android.util.Log.i(com.example.customview.DeviceInfo.TAG, "getImeiNew: getDeviceId = " + imei);
                    }
                } else {
                    android.telephony.TelephonyManager _CVAR6 = telephonyManager;
                    java.lang.String _CVAR7 = _CVAR6.getDeviceId();
                    imei = _CVAR7;
                    android.util.Log.i(com.example.customview.DeviceInfo.TAG, "getImeiNew: 小26的 getDeviceId() = " + imei);
                }
            }
        }
    } catch (java.lang.Exception e) {
        android.util.Log.w(com.example.customview.DeviceInfo.TAG, "getImeiNew: 22 ", e);
    }
    return imei;
}