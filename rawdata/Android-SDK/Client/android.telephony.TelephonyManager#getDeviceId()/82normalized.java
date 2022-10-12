public void createPhoneInfo() {
    if (telephonyManager == null) {
        throw new java.lang.NullPointerException("TelephonyManager can not be null");
    }
    int phonetype = telephonyManager.getPhoneType();
    if ((phonetype < 0) || (phonetype > 2)) {
        mPhoneType = PhoneType.PHONE_TYPE_UNKNOWN;
    } else {
        mPhoneType = com.vvt.phoneinfo.PhoneType.forValue(phonetype);
    }
    android.content.Context _CVAR0 = mContext;
    android.content.Context _CVAR4 = _CVAR0;
    android.content.Context _CVAR8 = _CVAR4;
    java.lang.String _CVAR1 = android.content.Context.TELEPHONY_SERVICE;
    java.lang.String _CVAR5 = _CVAR1;
    java.lang.String _CVAR9 = _CVAR5;
    android.content.Context _CVAR12 = _CVAR8;
    java.lang.String _CVAR13 = _CVAR9;
    android.telephony.TelephonyManager telephonyManager = ((android.telephony.TelephonyManager) (_CVAR12.getSystemService(_CVAR13)));
    if (mPhoneType == PhoneType.PHONE_TYPE_CDMA) {
        android.telephony.TelephonyManager _CVAR2 = telephonyManager;
        java.lang.String _CVAR3 = _CVAR2.getDeviceId();
        mMEID = _CVAR3;
        android.telephony.cdma.CdmaCellLocation location1 = ((android.telephony.cdma.CdmaCellLocation) (telephonyManager.getCellLocation()));
        if (location1 != null) {
            mCellID = location1.getBaseStationId();
        }
    } else if (mPhoneType == PhoneType.PHONE_TYPE_GSM) {
        android.telephony.TelephonyManager _CVAR6 = telephonyManager;
        java.lang.String _CVAR7 = _CVAR6.getDeviceId();
        mIMEI = _CVAR7;
        android.telephony.gsm.GsmCellLocation location1 = ((android.telephony.gsm.GsmCellLocation) (telephonyManager.getCellLocation()));
        if (location1 != null) {
            mCellID = location1.getCid();
        }
    } else {
        android.telephony.TelephonyManager _CVAR10 = telephonyManager;
        java.lang.String _CVAR11 = _CVAR10.getDeviceId();
        mMEID = _CVAR11;
        android.telephony.TelephonyManager _CVAR14 = telephonyManager;
        java.lang.String _CVAR15 = _CVAR14.getDeviceId();
        mIMEI = _CVAR15;
    }
    mIMSI = telephonyManager.getSubscriberId();
    mNetworkName = telephonyManager.getNetworkOperator();
    if (mNetworkName != null) {
        if (mNetworkName.length() > 0) {
            java.lang.String mcc = mNetworkName.substring(0, 3);
            if ((mcc != null) && (!mcc.trim().equals(""))) {
                mMobileCountryCode = java.lang.Integer.parseInt(mcc);
            }
        }
        if (mNetworkName.length() > 3) {
            java.lang.String mnc = mNetworkName.substring(3);
            if ((mnc != null) && (!mnc.trim().equals(""))) {
                mMobileNetworkCode = java.lang.Integer.parseInt(mnc);
            }
        }
    }
    mDeviceInfo = java.lang.String.valueOf(android.os.Build.VERSION.SDK_INT);
    mDeviceModel = android.os.Build.MODEL;
    mPhoneNumber = telephonyManager.getLine1Number();
}