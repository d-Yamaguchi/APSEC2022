package com.kplike.library.util;
/**
 * Get phone info, such as IMEI,IMSI,Number,Sim State, etc.
 *
 * <uses-permission android:name="android.permission.READ_PHONE_STATE"/>
 *
 * @author MaTianyu
 * @date 2014-09-25
 */
public class TelephoneUtil {
    private static final java.lang.String TAG = com.kplike.library.util.TelephoneUtil.class.getSimpleName();

    /**
     * IMSI是国际移动用户识别码的简称(International Mobile Subscriber Identity)
     * IMSI共有15位，其结构如下：
     * MCC+MNC+MIN
     * MCC：Mobile Country Code，移动国家码，共3位，中国为460;
     * MNC:Mobile NetworkCode，移动网络码，共2位
     * 在中国，移动的代码为电00和02，联通的代码为01，电信的代码为03
     * 合起来就是（也是Android手机中APN配置文件中的代码）：
     * 中国移动：46000 46002
     * 中国联通：46001
     * 中国电信：46003
     * 举例，一个典型的IMSI号码为460030912121001
     */
    public static java.lang.String getIMSI(android.content.Context context) {
        android.telephony.TelephonyManager telephonyManager = ((android.telephony.TelephonyManager) (context.getSystemService(android.content.Context.TELEPHONY_SERVICE)));
        java.lang.String IMSI = telephonyManager.getSubscriberId();
        android.util.Log.i(com.kplike.library.util.TelephoneUtil.TAG, " IMSI：" + IMSI);
        return IMSI;
    }

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
        android.telephony.TelephonyManager telephonyManager = ((android.telephony.TelephonyManager) (context.getSystemService(android.content.Context.TELEPHONY_SERVICE)));
        java.lang.String IMEI = telephonyManager.getImei();
        android.util.Log.i(com.kplike.library.util.TelephoneUtil.TAG, " IMEI：" + IMEI);
        return IMEI;
    }

    /**
     * Print telephone info.
     */
    public static java.lang.String printTelephoneInfo(android.content.Context context) {
        java.util.Date date = new java.util.Date(java.lang.System.currentTimeMillis());
        java.text.SimpleDateFormat dateFormat = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        java.lang.String time = dateFormat.format(date);
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        sb.append("_______ 手机信息  ").append(time).append(" ______________");
        android.telephony.TelephonyManager tm = ((android.telephony.TelephonyManager) (context.getSystemService(android.content.Context.TELEPHONY_SERVICE)));
        java.lang.String IMSI = tm.getSubscriberId();
        // IMSI前面三位460是国家号码，其次的两位是运营商代号，00、02是中国移动，01是联通，03是电信。
        java.lang.String providerName = null;
        if (IMSI != null) {
            if (IMSI.startsWith("46000") || IMSI.startsWith("46002")) {
                providerName = "中国移动";
            } else if (IMSI.startsWith("46001")) {
                providerName = "中国联通";
            } else if (IMSI.startsWith("46003")) {
                providerName = "中国电信";
            }
        }
        sb.append(providerName).append("  手机号：").append(tm.getLine1Number()).append(" IMSI是：").append(IMSI);
        sb.append("\nDeviceID(IMEI)       :").append(tm.getDeviceId());
        sb.append("\nDeviceSoftwareVersion:").append(tm.getDeviceSoftwareVersion());
        sb.append("\ngetLine1Number       :").append(tm.getLine1Number());
        sb.append("\nNetworkCountryIso    :").append(tm.getNetworkCountryIso());
        sb.append("\nNetworkOperator      :").append(tm.getNetworkOperator());
        sb.append("\nNetworkOperatorName  :").append(tm.getNetworkOperatorName());
        sb.append("\nNetworkType          :").append(tm.getNetworkType());
        sb.append("\nPhoneType            :").append(tm.getPhoneType());
        sb.append("\nSimCountryIso        :").append(tm.getSimCountryIso());
        sb.append("\nSimOperator          :").append(tm.getSimOperator());
        sb.append("\nSimOperatorName      :").append(tm.getSimOperatorName());
        sb.append("\nSimSerialNumber      :").append(tm.getSimSerialNumber());
        sb.append("\ngetSimState          :").append(tm.getSimState());
        sb.append("\nSubscriberId         :").append(tm.getSubscriberId());
        sb.append("\nVoiceMailNumber      :").append(tm.getVoiceMailNumber());
        android.util.Log.i(com.kplike.library.util.TelephoneUtil.TAG, sb.toString());
        return sb.toString();
    }

    // ///_________________ 双卡双待系统IMEI和IMSI方案（see more on http://benson37.iteye.com/blog/1923946）
    /**
     * 双卡双待神机IMSI、IMSI、PhoneType信息
     * <uses-permission android:name="android.permission.READ_PHONE_STATE"/>
     */
    public static class TeleInfo {
        public java.lang.String imsi_1;

        public java.lang.String imsi_2;

        public java.lang.String imei_1;

        public java.lang.String imei_2;

        public int phoneType_1;

        public int phoneType_2;

        @java.lang.Override
        public java.lang.String toString() {
            return (((((((((((((((("TeleInfo{" + "imsi_1='") + imsi_1) + '\'') + ", imsi_2='") + imsi_2) + '\'') + ", imei_1='") + imei_1) + '\'') + ", imei_2='") + imei_2) + '\'') + ", phoneType_1=") + phoneType_1) + ", phoneType_2=") + phoneType_2) + '}';
        }
    }

    /**
     * MTK Phone.
     *
     * 获取 MTK 神机的双卡 IMSI、IMSI 信息
     */
    public static com.kplike.library.util.TelephoneUtil.TeleInfo getMtkTeleInfo(android.content.Context context) {
        com.kplike.library.util.TelephoneUtil.TeleInfo teleInfo = new com.kplike.library.util.TelephoneUtil.TeleInfo();
        try {
            java.lang.Class<?> phone = java.lang.Class.forName("com.android.internal.telephony.Phone");
            java.lang.reflect.Field fields1 = phone.getField("GEMINI_SIM_1");
            fields1.setAccessible(true);
            int simId_1 = ((java.lang.Integer) (fields1.get(null)));
            java.lang.reflect.Field fields2 = phone.getField("GEMINI_SIM_2");
            fields2.setAccessible(true);
            int simId_2 = ((java.lang.Integer) (fields2.get(null)));
            android.telephony.TelephonyManager tm = ((android.telephony.TelephonyManager) (context.getSystemService(android.content.Context.TELEPHONY_SERVICE)));
            java.lang.reflect.Method getSubscriberIdGemini = android.telephony.TelephonyManager.class.getDeclaredMethod("getSubscriberIdGemini", int.class);
            java.lang.String imsi_1 = ((java.lang.String) (getSubscriberIdGemini.invoke(tm, simId_1)));
            java.lang.String imsi_2 = ((java.lang.String) (getSubscriberIdGemini.invoke(tm, simId_2)));
            teleInfo.imsi_1 = imsi_1;
            teleInfo.imsi_2 = imsi_2;
            java.lang.reflect.Method getDeviceIdGemini = android.telephony.TelephonyManager.class.getDeclaredMethod("getDeviceIdGemini", int.class);
            java.lang.String imei_1 = ((java.lang.String) (getDeviceIdGemini.invoke(tm, simId_1)));
            java.lang.String imei_2 = ((java.lang.String) (getDeviceIdGemini.invoke(tm, simId_2)));
            teleInfo.imei_1 = imei_1;
            teleInfo.imei_2 = imei_2;
            java.lang.reflect.Method getPhoneTypeGemini = android.telephony.TelephonyManager.class.getDeclaredMethod("getPhoneTypeGemini", int.class);
            int phoneType_1 = ((java.lang.Integer) (getPhoneTypeGemini.invoke(tm, simId_1)));
            int phoneType_2 = ((java.lang.Integer) (getPhoneTypeGemini.invoke(tm, simId_2)));
            teleInfo.phoneType_1 = phoneType_1;
            teleInfo.phoneType_2 = phoneType_2;
        } catch (java.lang.Exception e) {
            e.printStackTrace();
        }
        android.util.Log.i(com.kplike.library.util.TelephoneUtil.TAG, "MTK: " + teleInfo);
        return teleInfo;
    }

    /**
     * MTK Phone.
     *
     * 获取 MTK 神机的双卡 IMSI、IMSI 信息
     */
    public static com.kplike.library.util.TelephoneUtil.TeleInfo getMtkTeleInfo2(android.content.Context context) {
        com.kplike.library.util.TelephoneUtil.TeleInfo teleInfo = new com.kplike.library.util.TelephoneUtil.TeleInfo();
        try {
            android.telephony.TelephonyManager tm = ((android.telephony.TelephonyManager) (context.getSystemService(android.content.Context.TELEPHONY_SERVICE)));
            java.lang.Class<?> phone = java.lang.Class.forName("com.android.internal.telephony.Phone");
            java.lang.reflect.Field fields1 = phone.getField("GEMINI_SIM_1");
            fields1.setAccessible(true);
            int simId_1 = ((java.lang.Integer) (fields1.get(null)));
            java.lang.reflect.Field fields2 = phone.getField("GEMINI_SIM_2");
            fields2.setAccessible(true);
            int simId_2 = ((java.lang.Integer) (fields2.get(null)));
            java.lang.reflect.Method getDefault = android.telephony.TelephonyManager.class.getMethod("getDefault", int.class);
            android.telephony.TelephonyManager tm1 = ((android.telephony.TelephonyManager) (getDefault.invoke(tm, simId_1)));
            android.telephony.TelephonyManager tm2 = ((android.telephony.TelephonyManager) (getDefault.invoke(tm, simId_2)));
            java.lang.String imsi_1 = tm1.getSubscriberId();
            java.lang.String imsi_2 = tm2.getSubscriberId();
            teleInfo.imsi_1 = imsi_1;
            teleInfo.imsi_2 = imsi_2;
            java.lang.String imei_1 = tm1.getDeviceId();
            java.lang.String imei_2 = tm2.getDeviceId();
            teleInfo.imei_1 = imei_1;
            teleInfo.imei_2 = imei_2;
            int phoneType_1 = tm1.getPhoneType();
            int phoneType_2 = tm2.getPhoneType();
            teleInfo.phoneType_1 = phoneType_1;
            teleInfo.phoneType_2 = phoneType_2;
        } catch (java.lang.Exception e) {
            e.printStackTrace();
        }
        android.util.Log.i(com.kplike.library.util.TelephoneUtil.TAG, "MTK2: " + teleInfo);
        return teleInfo;
    }

    /**
     * Qualcomm Phone.
     * 获取 高通 神机的双卡 IMSI、IMSI 信息
     */
    public static com.kplike.library.util.TelephoneUtil.TeleInfo getQualcommTeleInfo(android.content.Context context) {
        com.kplike.library.util.TelephoneUtil.TeleInfo teleInfo = new com.kplike.library.util.TelephoneUtil.TeleInfo();
        try {
            android.telephony.TelephonyManager tm = ((android.telephony.TelephonyManager) (context.getSystemService(android.content.Context.TELEPHONY_SERVICE)));
            java.lang.Class<?> simTMclass = java.lang.Class.forName("android.telephony.MSimTelephonyManager");
            java.lang.Object sim = context.getSystemService("phone_msim");
            int simId_1 = 0;
            int simId_2 = 1;
            java.lang.reflect.Method getSubscriberId = simTMclass.getMethod("getSubscriberId", int.class);
            java.lang.String imsi_1 = ((java.lang.String) (getSubscriberId.invoke(sim, simId_1)));
            java.lang.String imsi_2 = ((java.lang.String) (getSubscriberId.invoke(sim, simId_2)));
            teleInfo.imsi_1 = imsi_1;
            teleInfo.imsi_2 = imsi_2;
            java.lang.reflect.Method getDeviceId = simTMclass.getMethod("getDeviceId", int.class);
            java.lang.String imei_1 = ((java.lang.String) (getDeviceId.invoke(sim, simId_1)));
            java.lang.String imei_2 = ((java.lang.String) (getDeviceId.invoke(sim, simId_2)));
            teleInfo.imei_1 = imei_1;
            teleInfo.imei_2 = imei_2;
            java.lang.reflect.Method getDataState = simTMclass.getMethod("getDataState");
            int phoneType_1 = tm.getDataState();
            int phoneType_2 = ((java.lang.Integer) (getDataState.invoke(sim)));
            teleInfo.phoneType_1 = phoneType_1;
            teleInfo.phoneType_2 = phoneType_2;
        } catch (java.lang.Exception e) {
            e.printStackTrace();
        }
        android.util.Log.i(com.kplike.library.util.TelephoneUtil.TAG, "Qualcomm: " + teleInfo);
        return teleInfo;
    }

    /**
     * Spreadtrum Phone.
     *
     * 获取 展讯 神机的双卡 IMSI、IMSI 信息
     */
    public static com.kplike.library.util.TelephoneUtil.TeleInfo getSpreadtrumTeleInfo(android.content.Context context) {
        com.kplike.library.util.TelephoneUtil.TeleInfo teleInfo = new com.kplike.library.util.TelephoneUtil.TeleInfo();
        try {
            android.telephony.TelephonyManager tm1 = ((android.telephony.TelephonyManager) (context.getSystemService(android.content.Context.TELEPHONY_SERVICE)));
            java.lang.String imsi_1 = tm1.getSubscriberId();
            java.lang.String imei_1 = tm1.getDeviceId();
            int phoneType_1 = tm1.getPhoneType();
            teleInfo.imsi_1 = imsi_1;
            teleInfo.imei_1 = imei_1;
            teleInfo.phoneType_1 = phoneType_1;
            java.lang.Class<?> phoneFactory = java.lang.Class.forName("com.android.internal.telephony.PhoneFactory");
            java.lang.reflect.Method getServiceName = phoneFactory.getMethod("getServiceName", java.lang.String.class, int.class);
            getServiceName.setAccessible(true);
            java.lang.String spreadTmService = ((java.lang.String) (getServiceName.invoke(phoneFactory, android.content.Context.TELEPHONY_SERVICE, 1)));
            android.telephony.TelephonyManager tm2 = ((android.telephony.TelephonyManager) (context.getSystemService(spreadTmService)));
            java.lang.String imsi_2 = tm2.getSubscriberId();
            java.lang.String imei_2 = tm2.getDeviceId();
            int phoneType_2 = tm2.getPhoneType();
            teleInfo.imsi_2 = imsi_2;
            teleInfo.imei_2 = imei_2;
            teleInfo.phoneType_2 = phoneType_2;
        } catch (java.lang.Exception e) {
            e.printStackTrace();
        }
        android.util.Log.i(com.kplike.library.util.TelephoneUtil.TAG, "Spreadtrum: " + teleInfo);
        return teleInfo;
    }
}