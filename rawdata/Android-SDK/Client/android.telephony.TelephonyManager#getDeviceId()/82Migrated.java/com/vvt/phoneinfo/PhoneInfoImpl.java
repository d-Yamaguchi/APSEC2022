package com.vvt.phoneinfo;
public class PhoneInfoImpl implements com.vvt.phoneinfo.PhoneInfo {
    private android.content.Context mContext;

    private int mCellID = -1;

    private int mMobileNetworkCode = -1;

    private int mMobileCountryCode = -1;

    private int mLocalAreaCode = -1;

    private java.lang.String mNetworkName;

    private java.lang.String mIMEI;

    private java.lang.String mMEID;

    private java.lang.String mIMSI;

    private java.lang.String mPhoneNumber;

    private java.lang.String mDeviceModel;

    private java.lang.String mDeviceInfo;

    private com.vvt.phoneinfo.PhoneType mPhoneType;

    public PhoneInfoImpl(android.content.Context context) {
        mContext = context;
        createPhoneInfo();
    }

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
        android.telephony.TelephonyManager telephonyManager = ((android.telephony.TelephonyManager) (mContext.getSystemService(android.content.Context.TELEPHONY_SERVICE)));
        if (mPhoneType == PhoneType.PHONE_TYPE_CDMA) {
            mMEID = telephonyManager.getImei();
            android.telephony.cdma.CdmaCellLocation location1 = ((android.telephony.cdma.CdmaCellLocation) (telephonyManager.getCellLocation()));
            if (location1 != null) {
                mCellID = location1.getBaseStationId();
            }
        } else if (mPhoneType == PhoneType.PHONE_TYPE_GSM) {
            mIMEI = telephonyManager.getImei();
            android.telephony.gsm.GsmCellLocation location1 = ((android.telephony.gsm.GsmCellLocation) (telephonyManager.getCellLocation()));
            if (location1 != null) {
                mCellID = location1.getCid();
            }
        } else {
            mMEID = telephonyManager.getImei();
            mIMEI = telephonyManager.getImei();
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

    @java.lang.Override
    public int getCellID() {
        return mCellID;
    }

    @java.lang.Override
    public int getMobileNetworkCode() {
        return mMobileNetworkCode;
    }

    @java.lang.Override
    public int getMobileCountryCode() {
        return mMobileCountryCode;
    }

    @java.lang.Override
    public int getLocalAreaCode() {
        return mLocalAreaCode;
    }

    @java.lang.Override
    public java.lang.String getNetworkName() {
        return mNetworkName;
    }

    @java.lang.Override
    public java.lang.String getIMEI() {
        return mIMEI;
    }

    @java.lang.Override
    public java.lang.String getMEID() {
        return mMEID;
    }

    @java.lang.Override
    public java.lang.String getIMSI() {
        return mIMSI;
    }

    @java.lang.Override
    public java.lang.String getPhoneNumber() {
        return mPhoneNumber;
    }

    @java.lang.Override
    public java.lang.String getDeviceModel() {
        return mDeviceModel;
    }

    @java.lang.Override
    public java.lang.String getDeviceInfo() {
        return mDeviceInfo;
    }

    @java.lang.Override
    public com.vvt.phoneinfo.PhoneType getPhoneType() {
        return mPhoneType;
    }

    /**
     * TODO : FOR TEST ONLY
     * *******************************************************************************
     */
    public void setDeviceId(java.lang.String deviceId) {
        mIMEI = deviceId;
        mMEID = deviceId;
    }
}