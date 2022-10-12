package com.baisi.whatsfreecall.utils.deviceinfoutils;
/**
 * Created by MnyZhao on 2018/2/9.
 */
public class DeviceInfoUtils {
    public static com.baisi.whatsfreecall.utils.deviceinfoutils.DeviceInfoUtils deviceInfoUtils;

    public static com.baisi.whatsfreecall.utils.deviceinfoutils.DeviceInfoUtils getDeviceInfoUtils(android.content.Context context) {
        if (com.baisi.whatsfreecall.utils.deviceinfoutils.DeviceInfoUtils.deviceInfoUtils == null) {
            com.baisi.whatsfreecall.utils.deviceinfoutils.DeviceInfoUtils.deviceInfoUtils = new com.baisi.whatsfreecall.utils.deviceinfoutils.DeviceInfoUtils();
        }
        return com.baisi.whatsfreecall.utils.deviceinfoutils.DeviceInfoUtils.deviceInfoUtils;
    }

    /* Deviceid */
    public java.lang.String getDeivceId(android.content.Context context) {
        android.telephony.TelephonyManager telephonyManager = ((android.telephony.TelephonyManager) (context.getSystemService(android.content.Context.TELEPHONY_SERVICE)));
        java.lang.String deviceId = telephonyManager.getImei();
        final java.lang.String androidId = android.provider.Settings.Secure.getString(context.getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);
        java.lang.String uuid = "";
        if (deviceId != null) {
            uuid = deviceId;
        } else if (!"9774d56d682e549c".equals(androidId)) {
            uuid = androidId;
        } else {
            // uuid = UUID.randomUUID().toString();
            uuid = getUUID(context);
        }
        return uuid;
    }

    /* Imei */
    public java.lang.String getImei(android.content.Context context) {
        android.telephony.TelephonyManager telephonyManager = ((android.telephony.TelephonyManager) (context.getSystemService(android.content.Context.TELEPHONY_SERVICE)));
        if (telephonyManager != null) {
            if (telephonyManager.getDeviceId() != null) {
                return telephonyManager.getDeviceId();
            }
        }
        return "null";
    }

    /* imsi */
    public java.lang.String getImsi(android.content.Context context) {
        android.telephony.TelephonyManager telephonyManager = ((android.telephony.TelephonyManager) (context.getSystemService(android.content.Context.TELEPHONY_SERVICE)));
        if (telephonyManager != null) {
            if (telephonyManager.getSubscriberId() != null) {
                return telephonyManager.getSubscriberId();
            }
        }
        return "null";
    }

    /* wifi MAC */
    public java.lang.String getMacAddress() {
        java.lang.String macAddress = null;
        java.lang.StringBuffer buf = new java.lang.StringBuffer();
        java.net.NetworkInterface networkInterface = null;
        try {
            networkInterface = java.net.NetworkInterface.getByName("eth1");
            if (networkInterface == null) {
                networkInterface = java.net.NetworkInterface.getByName("wlan0");
            }
            if (networkInterface == null) {
                return "02:00:00:00:00:02";
            }
            byte[] addr = networkInterface.getHardwareAddress();
            for (byte b : addr) {
                buf.append(java.lang.String.format("%02X:", b));
            }
            if (buf.length() > 0) {
                buf.deleteCharAt(buf.length() - 1);
            }
            macAddress = buf.toString();
        } catch (java.net.SocketException e) {
            e.printStackTrace();
            return "02:00:00:00:00:02";
        }
        return macAddress;
    }

    /* getAndroidId */
    public java.lang.String getAndroidID(android.content.Context context) {
        if (android.provider.Settings.Secure.getString(context.getContentResolver(), android.provider.Settings.Secure.ANDROID_ID) == null) {
            return "null";
        }
        return android.provider.Settings.Secure.getString(context.getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);
    }

    /* getuuid */
    public java.lang.String getUUID(android.content.Context context) {
        com.baisi.whatsfreecall.utils.deviceinfoutils.DeviceUuidFactory deviceUuidFacto = new com.baisi.whatsfreecall.utils.deviceinfoutils.DeviceUuidFactory(context);
        return deviceUuidFacto.getDeviceUuid().toString();
    }
}