package com.advertising_id_service.appclick.googleadvertisingidservice.IdGenerators;
import android.support.v4.app.ActivityCompat;
import com.advertising_id_service.appclick.googleadvertisingidservice.GUID.GUID;
public class MixIDGenerator extends com.advertising_id_service.appclick.googleadvertisingidservice.IdGenerators.IDGenerator implements com.advertising_id_service.appclick.googleadvertisingidservice.IdGenerators.IGenerator {
    @android.annotation.SuppressLint("MissingPermission")
    @java.lang.Override
    public com.advertising_id_service.appclick.googleadvertisingidservice.GUID.GUID generateId() {
        java.lang.String devicIMEI1 = "none";
        java.lang.String devicIMEI2 = "none";
        java.lang.String devicIMEI3 = "none";
        java.lang.String androidID = "none";
        if (context != null) {
            android.telephony.TelephonyManager telephonyManager = ((android.telephony.TelephonyManager) (context.getSystemService(android.content.Context.TELEPHONY_SERVICE)));
            if (android.support.v4.app.ActivityCompat.checkSelfPermission(context, android.Manifest.permission.READ_PHONE_STATE) != android.content.pm.PackageManager.PERMISSION_GRANTED) {
            } else {
                devicIMEI1 = telephonyManager.getImei();
                devicIMEI2 = telephonyManager.getImei();
                devicIMEI3 = telephonyManager.getImei();
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                    devicIMEI1 = (telephonyManager.getPhoneCount() >= 1) ? telephonyManager.getDeviceId(1) : "none";
                    devicIMEI2 = (telephonyManager.getPhoneCount() >= 2) ? telephonyManager.getDeviceId(2) : "none";
                    devicIMEI3 = (telephonyManager.getPhoneCount() >= 3) ? telephonyManager.getDeviceId(3) : "none";
                }
            }
            androidID = android.provider.Settings.Secure.getString(context.getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);
        }
        java.lang.String pseudoID = (((((((("" + android.os.Build.BRAND) + "") + android.os.Build.DEVICE) + "") + android.os.Build.MANUFACTURER) + "") + android.os.Build.MODEL) + "") + android.os.Build.PRODUCT;
        com.advertising_id_service.appclick.googleadvertisingidservice.GUID.GUID id = new com.advertising_id_service.appclick.googleadvertisingidservice.GUID.GUID("fake_gaid_" + ((((devicIMEI1 + devicIMEI2) + devicIMEI3) + androidID) + pseudoID).hashCode());
        return id;
    }
}