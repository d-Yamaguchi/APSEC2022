public static java.lang.String getIMEI(android.content.Context context) {
    if (me.tinggu.common.PhoneStateUtils.telephonyManager == null) {
        me.tinggu.common.PhoneStateUtils.telephonyManager = ((android.telephony.TelephonyManager) (context.getSystemService(android.content.Context.TELEPHONY_SERVICE)));
    }
    android.telephony.TelephonyManager _CVAR0 = me.tinggu.common.PhoneStateUtils.telephonyManager;
    java.lang.String _CVAR1 = _CVAR0.getDeviceId();
    return _CVAR1;
}