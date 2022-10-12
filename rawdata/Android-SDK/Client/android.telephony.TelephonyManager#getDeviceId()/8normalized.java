/**
 * 获取设备ID
 */
@android.annotation.SuppressLint("MissingPermission")
public static java.lang.String getDeviceId() {
    // 获取IMEI
    java.lang.String getDeviceId = "";
    try {
         _CVAR0 = com.component.preject.youlong.base.BaseApplication.getContext();
        java.lang.String _CVAR1 = android.content.Context.TELEPHONY_SERVICE;
        android.telephony.TelephonyManager telephonyManager = ((android.telephony.TelephonyManager) (_CVAR0.getSystemService(_CVAR1)));
        android.telephony.TelephonyManager _CVAR2 = telephonyManager;
        java.lang.String _CVAR3 = _CVAR2.getDeviceId();
        getDeviceId = _CVAR3;
    } catch (java.lang.SecurityException e) {
        e.printStackTrace();
        com.component.preject.youlong.utils.LogUtils.i("YYYY", "getDeviceId==" + e.getMessage());
    }
    com.component.preject.youlong.utils.LogUtils.i("YYYY", "getDeviceId==" + getDeviceId);
    return getDeviceId;
}