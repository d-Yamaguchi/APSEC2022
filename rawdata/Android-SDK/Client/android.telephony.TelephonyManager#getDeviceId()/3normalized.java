@android.annotation.SuppressLint("MissingPermission")
@java.lang.Override
public com.advertising_id_service.appclick.googleadvertisingidservice.GUID.GUID generateId() {
    java.lang.String devicIMEI1 = "none";
    java.lang.String devicIMEI2 = "none";
    java.lang.String devicIMEI3 = "none";
    java.lang.String androidID = "none";
    if (context != null) {
        com.advertising_id_service.appclick.googleadvertisingidservice.IdGenerators.MixIDGenerator _CVAR0 = context;
        com.advertising_id_service.appclick.googleadvertisingidservice.IdGenerators.MixIDGenerator _CVAR4 = _CVAR0;
        java.lang.String _CVAR1 = android.content.Context.TELEPHONY_SERVICE;
        java.lang.String _CVAR5 = _CVAR1;
        com.advertising_id_service.appclick.googleadvertisingidservice.IdGenerators.MixIDGenerator _CVAR8 = _CVAR4;
        java.lang.String _CVAR9 = _CVAR5;
        android.telephony.TelephonyManager telephonyManager = ((android.telephony.TelephonyManager) (_CVAR8.getSystemService(_CVAR9)));
        if (android.support.v4.app.ActivityCompat.checkSelfPermission(context, android.Manifest.permission.READ_PHONE_STATE) != android.content.pm.PackageManager.PERMISSION_GRANTED) {
        } else {
            android.telephony.TelephonyManager _CVAR2 = telephonyManager;
            java.lang.String _CVAR3 = _CVAR2.getDeviceId();
            devicIMEI1 = _CVAR3;
            android.telephony.TelephonyManager _CVAR6 = telephonyManager;
            java.lang.String _CVAR7 = _CVAR6.getDeviceId();
            devicIMEI2 = _CVAR7;
            android.telephony.TelephonyManager _CVAR10 = telephonyManager;
            java.lang.String _CVAR11 = _CVAR10.getDeviceId();
            devicIMEI3 = _CVAR11;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                devicIMEI1 = (telephonyManager.getPhoneCount() >= 1) ? telephonyManager.getDeviceId(1) : "none";
                devicIMEI2 = (telephonyManager.getPhoneCount() >= 2) ? telephonyManager.getDeviceId(2) : "none";
                devicIMEI3 = (telephonyManager.getPhoneCount() >= 3) ? telephonyManager.getDeviceId(3) : "none";
            }
        }
        androidID = android.provider.Settings.Secure.getString(context.getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);
    }
    java.lang.String pseudoID = (((((((("" + android.os.Build.BRAND) + "") + android.os.Build.DEVICE) + "") + android.os.Build.MANUFACTURER) + "") + android.os.Build.MODEL) + "") + android.os.Build.PRODUCT;
    com.advertising_id_service.appclick.googleadvertisingidservice.GUID.GUID id = new com.advertising_id_service.appclick.googleadvertisingidservice.GUID.GUID("fake_gaid_" + ((((devicIMEI1 + devicIMEI2) + devicIMEI3) + androidID) + pseudoID).hashCode());
    return id;
}