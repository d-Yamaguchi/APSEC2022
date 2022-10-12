java.lang.String getDeviceID(android.telephony.TelephonyManager phonyManager) {
    android.telephony.TelephonyManager _CVAR0 = phonyManager;
    java.lang.String id = _CVAR0.getDeviceId();
    if (id == null) {
        id = "not available";
    }
    int phoneType = phonyManager.getPhoneType();
    switch (phoneType) {
        case android.telephony.TelephonyManager.PHONE_TYPE_NONE :
            return "NONE: " + id;
        case android.telephony.TelephonyManager.PHONE_TYPE_GSM :
            return "GSM: IMEI=" + id;
        case android.telephony.TelephonyManager.PHONE_TYPE_CDMA :
            return "CDMA: MEID/ESN=" + id;
            /* for API Level 11 or above
            case TelephonyManager.PHONE_TYPE_SIP:
            return "SIP";
             */
        default :
            return "UNKNOWN: ID=" + id;
    }
}