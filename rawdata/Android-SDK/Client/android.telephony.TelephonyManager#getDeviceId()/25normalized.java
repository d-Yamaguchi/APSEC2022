public java.lang.String getDeviceId(android.content.Context context) {
    int permissionCheck = android.support.v4.content.ContextCompat.checkSelfPermission(context, android.Manifest.permission.READ_PHONE_STATE);
    android.app.Activity activity = ((android.app.Activity) (context));
    java.lang.String deviceId = "";
    if (permissionCheck != android.content.pm.PackageManager.PERMISSION_GRANTED) {
        android.support.v4.app.ActivityCompat.requestPermissions(activity, new java.lang.String[]{ android.Manifest.permission.READ_PHONE_STATE }, com.solution.naukarimanthan.utils.DeviceId.REQUEST_READ_PHONE_STATE);
    } else {
        android.content.Context _CVAR0 = context;
        java.lang.String _CVAR1 = android.content.Context.TELEPHONY_SERVICE;
        // TODO
        android.telephony.TelephonyManager telephonyManager = ((android.telephony.TelephonyManager) (_CVAR0.getSystemService(_CVAR1)));
        android.telephony.TelephonyManager _CVAR2 = telephonyManager;
        java.lang.String _CVAR3 = _CVAR2.getDeviceId();
        deviceId = _CVAR3;
    }
    return deviceId;
}