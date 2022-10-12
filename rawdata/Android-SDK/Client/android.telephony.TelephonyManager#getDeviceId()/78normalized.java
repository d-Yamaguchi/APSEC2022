/**
 * MTK的芯片的判断2
 *
 * @param mContext
 * 		
 * @return 
 */
public com.gongdian.qmcb.others.IMSInfo initMtkSecondDoubleSim() {
    com.gongdian.qmcb.others.IMSInfo imsInfo = null;
    try {
        java.lang.Class<?> c = java.lang.Class.forName("com.android.internal.telephony.Phone");
        java.lang.reflect.Field fields1 = c.getField("GEMINI_SIM_1");
        fields1.setAccessible(true);
        simId_1 = ((java.lang.Integer) (fields1.get(null)));
        java.lang.reflect.Field fields2 = c.getField("GEMINI_SIM_2");
        fields2.setAccessible(true);
        simId_2 = ((java.lang.Integer) (fields2.get(null)));
        imsi_1 = tm1.getSubscriberId();
        imsi_2 = tm2.getSubscriberId();
        java.lang.Class _CVAR0 = android.telephony.TelephonyManager.class;
        java.lang.String _CVAR1 = "getDefault";
        java.lang.Class _CVAR2 = int.class;
        java.lang.reflect.Method mx = _CVAR0.getMethod(_CVAR1, _CVAR2);
        android.content.Context _CVAR4 = mContext;
        java.lang.String _CVAR5 = android.content.Context.TELEPHONY_SERVICE;
        android.telephony.TelephonyManager tm = ((android.telephony.TelephonyManager) (_CVAR4.getSystemService(_CVAR5)));
        java.lang.reflect.Method _CVAR3 = mx;
        android.telephony.TelephonyManager _CVAR6 = tm;
        java.lang.Integer _CVAR7 = simId_1;
        android.telephony.TelephonyManager tm1 = ((android.telephony.TelephonyManager) (_CVAR3.invoke(_CVAR6, _CVAR7)));
        android.telephony.TelephonyManager _CVAR8 = tm1;
        java.lang.String _CVAR9 = _CVAR8.getDeviceId();
        imei_1 = _CVAR9;
        java.lang.reflect.Method _CVAR10 = mx;
        android.telephony.TelephonyManager _CVAR11 = tm;
        java.lang.Integer _CVAR12 = simId_2;
        android.telephony.TelephonyManager tm2 = ((android.telephony.TelephonyManager) (_CVAR10.invoke(_CVAR11, _CVAR12)));
        android.telephony.TelephonyManager _CVAR13 = tm2;
        java.lang.String _CVAR14 = _CVAR13.getDeviceId();
        imei_2 = _CVAR14;
        imsInfo = new com.gongdian.qmcb.others.IMSInfo();
        imsInfo.chipName = "MTK芯片";
        imsInfo.imei_1 = imei_1;
        imsInfo.imei_2 = imei_2;
        imsInfo.imsi_1 = imsi_1;
        imsInfo.imsi_2 = imsi_2;
    } catch (java.lang.Exception e) {
        imsInfo = null;
        return imsInfo;
    }
    return imsInfo;
}