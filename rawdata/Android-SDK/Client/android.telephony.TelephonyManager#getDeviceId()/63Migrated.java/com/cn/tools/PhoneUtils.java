package com.cn.tools;
public class PhoneUtils {
    public static java.lang.String getDeviceId(android.content.Context context) {
        android.telephony.TelephonyManager telephonyManager = ((android.telephony.TelephonyManager) (context.getSystemService(android.content.Context.TELEPHONY_SERVICE)));
        java.lang.String imei = telephonyManager.getImei();
        return imei;
    }

    public static java.lang.String getPhoneNumber(android.content.Context context) {
        android.telephony.TelephonyManager mTelephonyMgr;
        mTelephonyMgr = ((android.telephony.TelephonyManager) (context.getSystemService(android.content.Context.TELEPHONY_SERVICE)));
        return mTelephonyMgr.getLine1Number();
    }

    public static java.lang.String getMacAddress(android.content.Context context) {
        android.net.wifi.WifiManager wifi = ((android.net.wifi.WifiManager) (context.getSystemService(android.content.Context.WIFI_SERVICE)));
        android.net.wifi.WifiInfo info = wifi.getConnectionInfo();
        return info.getMacAddress();
    }

    public static android.telephony.CellLocation getCellLocation(android.content.Context context) {
        android.telephony.TelephonyManager mTelephonyMgr;
        mTelephonyMgr = ((android.telephony.TelephonyManager) (context.getSystemService(android.content.Context.TELEPHONY_SERVICE)));
        return mTelephonyMgr.getCellLocation();
    }
}