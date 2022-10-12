/**
 * 获取设备ID
 *
 * @param context
 * 		
 * @return 机器码
 */
public static java.lang.String getDeviceID(android.content.Context context) {
    android.content.Context _CVAR0 = context;
    java.lang.String _CVAR1 = android.content.Context.TELEPHONY_SERVICE;
    android.telephony.TelephonyManager manager = ((android.telephony.TelephonyManager) (_CVAR0.getSystemService(_CVAR1)));
    android.telephony.TelephonyManager _CVAR2 = manager;
    java.lang.String _CVAR3 = _CVAR2.getDeviceId();
    return _CVAR3;
}