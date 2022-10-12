package com.rideeaze.services.telephone;
public class DriverTelephoneService {
    protected android.content.Context context;

    protected android.telephony.TelephonyManager telephonyManager;

    public DriverTelephoneService(android.content.Context context) {
        this.context = context;
        telephonyManager = ((android.telephony.TelephonyManager) (context.getSystemService(android.content.Context.TELEPHONY_SERVICE)));
    }

    public java.lang.String getDeviceId() {
        return telephonyManager.getImei();
    }

    public java.lang.String getSimSerialNumber() {
        return telephonyManager.getDeviceId();
    }
}