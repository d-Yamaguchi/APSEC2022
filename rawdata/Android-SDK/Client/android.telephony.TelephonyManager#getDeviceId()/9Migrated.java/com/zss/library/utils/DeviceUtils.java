package com.zss.library.utils;
/**
 * 设备工具类
 *
 * @author zm
 */
public class DeviceUtils {
    /**
     * 获取设备ID
     *
     * @param context
     * 		
     * @return 机器码
     */
    public static java.lang.String getDeviceID(android.content.Context context) {
        android.telephony.TelephonyManager manager = ((android.telephony.TelephonyManager) (context.getSystemService(android.content.Context.TELEPHONY_SERVICE)));
        return manager.getImei();
    }

    /**
     * 获取设备唯一UUID
     *
     * @param mContext
     * 		
     * @return UUID
     */
    public static java.lang.String getUUID(android.content.Context mContext) {
        final android.telephony.TelephonyManager tm = ((android.telephony.TelephonyManager) (mContext.getSystemService(android.content.Context.TELEPHONY_SERVICE)));
        final java.lang.String tmDevice;
        final java.lang.String tmSerial;
        final java.lang.String androidId;
        tmDevice = "" + tm.getDeviceId();
        tmSerial = "" + tm.getSimSerialNumber();
        androidId = "" + android.provider.Settings.Secure.getString(mContext.getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);
        java.util.UUID deviceUuid = new java.util.UUID(androidId.hashCode(), (((long) (tmDevice.hashCode())) << 32) | tmSerial.hashCode());
        return deviceUuid.toString().replaceAll("-", "");
    }

    /**
     * 获取IMEI号，可能为空
     *
     * @param mContext
     * 		
     * @return String
     */
    public static java.lang.String getIMEI(android.content.Context mContext) {
        if (mContext == null) {
            return "";
        }
        android.telephony.TelephonyManager telephonyManager = ((android.telephony.TelephonyManager) (mContext.getSystemService(android.content.Context.TELEPHONY_SERVICE)));
        if (telephonyManager == null) {
            return "";
        }
        java.lang.String imei = telephonyManager.getDeviceId();
        if (android.text.TextUtils.isEmpty(imei)) {
            imei = "";
        }
        return imei;
    }
}