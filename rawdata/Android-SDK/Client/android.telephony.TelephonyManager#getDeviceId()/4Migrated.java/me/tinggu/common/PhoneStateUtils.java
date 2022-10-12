package me.tinggu.common;
/**
 * Description:
 * Copyright  : Copyright (c) 2015
 * Company    : 北京畅游天下网络科技有限公司
 * Author     : wangjia_bi
 * Date       : 2016/1/15 17:14
 */
// /**
// * 得到全局唯一UUID
// */
// public static String getUUID(Context context) {
// String uuid = null;
// SharedPreferences mShare = getSysShare(context, "sysCacheMap");
// if (mShare != null) {
// uuid = mShare.getString("uuid", "");
// }
// 
// if (TextUtils.isEmpty(uuid)) {
// uuid = UUID.randomUUID().toString();
// saveSysMap(context, "sysCacheMap", "uuid", uuid);
// }
// 
// LogUtils.e("getDeviceId", "getUUID : " + uuid);
// return uuid;
// }
public class PhoneStateUtils {
    private static android.telephony.TelephonyManager telephonyManager;

    public static java.lang.String getIMEI(android.content.Context context) {
        if (me.tinggu.common.PhoneStateUtils.telephonyManager == null) {
            me.tinggu.common.PhoneStateUtils.telephonyManager = ((android.telephony.TelephonyManager) (context.getSystemService(android.content.Context.TELEPHONY_SERVICE)));
        }
        return me.tinggu.common.PhoneStateUtils.telephonyManager.getImei();
    }

    public static java.lang.String getSN(android.content.Context context) {
        if (me.tinggu.common.PhoneStateUtils.telephonyManager == null) {
            me.tinggu.common.PhoneStateUtils.telephonyManager = ((android.telephony.TelephonyManager) (context.getSystemService(android.content.Context.TELEPHONY_SERVICE)));
        }
        return me.tinggu.common.PhoneStateUtils.telephonyManager.getSimSerialNumber();
    }

    public static java.lang.String getPhoneModel() {
        return (((android.os.Build.MODEL + ",") + android.os.Build.VERSION.SDK_INT) + ",") + android.os.Build.VERSION.RELEASE;
    }

    /**
     * deviceID的组成为：渠道标志+识别符来源标志+hash后的终端识别符
     * <p/>
     * 渠道标志为：
     * 1，andriod（a）
     * <p/>
     * 识别符来源标志：
     * 1， wifi mac地址（wifi）；
     * 2， IMEI（imei）；
     * 3， 序列号（sn）；
     * 4， id：随机码。若前面的都取不到时，则随机生成一个随机码，需要缓存。
     *
     * @param context
     * 		
     * @return 
     */
    public static java.lang.String getDeviceId(android.content.Context context) {
        java.lang.StringBuilder deviceId = new java.lang.StringBuilder();
        // 渠道标志
        deviceId.append("a");
        try {
            // wifi mac地址
            android.net.wifi.WifiManager wifi = ((android.net.wifi.WifiManager) (context.getSystemService(android.content.Context.WIFI_SERVICE)));
            android.net.wifi.WifiInfo info = wifi.getConnectionInfo();
            java.lang.String wifiMac = info.getMacAddress();
            if (!android.text.TextUtils.isEmpty(wifiMac)) {
                deviceId.append("wifi");
                deviceId.append(wifiMac);
                me.tinggu.common.LogUtils.e("getDeviceId : ", deviceId.toString());
                return deviceId.toString();
            }
            // IMEI（imei）
            android.telephony.TelephonyManager tm = ((android.telephony.TelephonyManager) (context.getSystemService(android.content.Context.TELEPHONY_SERVICE)));
            java.lang.String imei = tm.getDeviceId();
            if (!android.text.TextUtils.isEmpty(imei)) {
                deviceId.append("imei");
                deviceId.append(imei);
                me.tinggu.common.LogUtils.e("getDeviceId : ", deviceId.toString());
                return deviceId.toString();
            }
            // 序列号（sn）
            java.lang.String sn = tm.getSimSerialNumber();
            if (!android.text.TextUtils.isEmpty(sn)) {
                deviceId.append("sn");
                deviceId.append(sn);
                me.tinggu.common.LogUtils.e("getDeviceId : ", deviceId.toString());
                return deviceId.toString();
            }
            // //如果上面都没有， 则生成一个id：随机码
            // String uuid = getUUID(context);
            // if (!TextUtils.isEmpty(uuid)) {
            // deviceId.append("id");
            // deviceId.append(uuid);
            // LogUtils.e("getDeviceId : ", deviceId.toString());
            // return deviceId.toString();
            // }
        } catch (java.lang.Exception e) {
            e.printStackTrace();
            // deviceId.append("id").append(getUUID(context));
        }
        me.tinggu.common.LogUtils.e("getDeviceId : ", deviceId.toString());
        return deviceId.toString();
    }
}