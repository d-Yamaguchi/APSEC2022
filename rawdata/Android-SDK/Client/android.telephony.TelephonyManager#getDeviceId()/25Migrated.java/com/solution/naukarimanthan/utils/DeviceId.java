package com.solution.naukarimanthan.utils;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
/**
 * Author : Arvindo Mondal
 * Email : arvindomondal@gmail.com
 * Created on : 03-Nov-18
 */
public enum DeviceId {

    INSTANCE;
    public static final int REQUEST_READ_PHONE_STATE = 10;

    public java.lang.String getDeviceId(android.content.Context context) {
        int permissionCheck = android.support.v4.content.ContextCompat.checkSelfPermission(context, android.Manifest.permission.READ_PHONE_STATE);
        android.app.Activity activity = ((android.app.Activity) (context));
        java.lang.String deviceId = "";
        if (permissionCheck != android.content.pm.PackageManager.PERMISSION_GRANTED) {
            android.support.v4.app.ActivityCompat.requestPermissions(activity, __SmPLUnsupported__(0), com.solution.naukarimanthan.utils.DeviceId.REQUEST_READ_PHONE_STATE);
        } else {
            // TODO
            android.telephony.TelephonyManager telephonyManager = ((android.telephony.TelephonyManager) (context.getSystemService(android.content.Context.TELEPHONY_SERVICE)));
            deviceId = telephonyManager.getImei();
        }
        return deviceId;
    }

    public java.lang.String getIMEI(android.content.Context context) {
        java.lang.String IMEI = "";
        int permissionCheck = android.support.v4.content.ContextCompat.checkSelfPermission(context, android.Manifest.permission.READ_PHONE_STATE);
        android.app.Activity activity = ((android.app.Activity) (context));
        if (permissionCheck != android.content.pm.PackageManager.PERMISSION_GRANTED) {
            android.support.v4.app.ActivityCompat.requestPermissions(activity, new java.lang.String[]{ android.Manifest.permission.READ_PHONE_STATE }, com.solution.naukarimanthan.utils.DeviceId.REQUEST_READ_PHONE_STATE);
        } else {
            // TODO
            android.telephony.TelephonyManager telephonyManager = ((android.telephony.TelephonyManager) (context.getSystemService(android.content.Context.TELEPHONY_SERVICE)));
            IMEI = telephonyManager.getDeviceId();
        }
        // TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        // IMEI = "IMEI:"+telephonyManager.getDeviceId();
        return IMEI;
    }

    public static java.lang.String androidId(android.app.Application context) {
        return android.provider.Settings.Secure.getString(context.getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);
    }

    public static boolean CheckPermission(android.content.Context context, java.lang.String Permission) {
        return android.support.v4.content.ContextCompat.checkSelfPermission(context, Permission) == android.content.pm.PackageManager.PERMISSION_GRANTED;
    }
}