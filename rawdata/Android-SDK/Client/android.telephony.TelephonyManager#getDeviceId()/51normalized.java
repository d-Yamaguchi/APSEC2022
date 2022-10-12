/**
 * IMEI是International Mobile Equipment Identity （国际移动设备标识）的简称
 * IMEI由15位数字组成的”电子串号”，它与每台手机一一对应，而且该码是全世界唯一的
 * 其组成为：
 * 1. 前6位数(TAC)是”型号核准号码”，一般代表机型
 * 2. 接着的2位数(FAC)是”最后装配号”，一般代表产地
 * 3. 之后的6位数(SNR)是”串号”，一般代表生产顺序号
 * 4. 最后1位数(SP)通常是”0″，为检验码，目前暂备用
 */
public static java.lang.String getIMEI(android.content.Context context) {
    android.content.Context _CVAR0 = context;
    java.lang.String _CVAR1 = android.content.Context.TELEPHONY_SERVICE;
    android.telephony.TelephonyManager telephonyManager = ((android.telephony.TelephonyManager) (_CVAR0.getSystemService(_CVAR1)));
    android.telephony.TelephonyManager _CVAR2 = telephonyManager;
    java.lang.String IMEI = _CVAR2.getDeviceId();
    android.util.Log.i(com.kplike.library.util.TelephoneUtil.TAG, " IMEI：" + IMEI);
    return IMEI;
}