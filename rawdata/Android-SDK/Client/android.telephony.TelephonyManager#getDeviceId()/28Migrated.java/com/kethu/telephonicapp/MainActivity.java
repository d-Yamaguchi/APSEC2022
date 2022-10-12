package com.kethu.telephonicapp;
import android.support.v7.app.AppCompatActivity;
public class MainActivity extends android.support.v7.app.AppCompatActivity {
    android.widget.TextView textView;

    private static final java.lang.String TAG = "TelephnicActivity";

    java.lang.String[] READ_PERMISSIONS = new java.lang.String[]{ android.Manifest.permission.READ_PHONE_STATE, android.Manifest.permission.ACCESS_COARSE_LOCATION };

    @android.annotation.SuppressLint("HardwareIds")
    @java.lang.Override
    protected void onCreate(android.os.Bundle savedInstanceState) {
        __SmPLUnsupported__(0).onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.textview);
        if (!com.kethu.telephonicapp.PermissionChecker.checkPermission(this, READ_PERMISSIONS))
            com.kethu.telephonicapp.PermissionChecker.reqPermissions(this, READ_PERMISSIONS);

        android.telephony.TelephonyManager telephonyManager = ((android.telephony.TelephonyManager) (getSystemService(android.content.Context.TELEPHONY_SERVICE)));
        if (telephonyManager != null) {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                // Call some material design APIs here
                android.util.Log.e(com.kethu.telephonicapp.MainActivity.TAG, "Build.VERSION.SDK_INT " + android.os.Build.VERSION.SDK_INT);
                android.util.Log.e(com.kethu.telephonicapp.MainActivity.TAG, "Single or Dula Sim " + telephonyManager.getPhoneCount());
                android.util.Log.e(com.kethu.telephonicapp.MainActivity.TAG, "Default device ID " + telephonyManager.getImei());
                android.util.Log.e(com.kethu.telephonicapp.MainActivity.TAG, "Single 1 " + telephonyManager.getDeviceId(0));
                android.util.Log.e(com.kethu.telephonicapp.MainActivity.TAG, "Single 2 " + telephonyManager.getDeviceId(1));
                java.lang.String str = (((((("Serial Number " + telephonyManager.getSimSerialNumber()) + "\n SimCount ") + telephonyManager.getPhoneCount()) + "\n Device Id of Sim 1 ") + telephonyManager.getDeviceId(0)) + "\n Device Id of Sim2 ") + telephonyManager.getDeviceId(1);
                textView.setText(str);
            } else if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                android.util.Log.e(com.kethu.telephonicapp.MainActivity.TAG, "Build.VERSION.SDK_INT " + android.os.Build.VERSION.SDK_INT);
                android.util.Log.e(com.kethu.telephonicapp.MainActivity.TAG, "Device  " + telephonyManager.getImei());
                android.util.Log.e(com.kethu.telephonicapp.MainActivity.TAG, "slot 1 " + telephonyManager.getImei(0));
                android.util.Log.e(com.kethu.telephonicapp.MainActivity.TAG, "slot 2 " + telephonyManager.getImei(1));
                android.util.Log.e(com.kethu.telephonicapp.MainActivity.TAG, "Single or Dula Sim " + telephonyManager.getPhoneCount());
                textView.setText(telephonyManager.getImei());
            } else {
                android.util.Log.e(com.kethu.telephonicapp.MainActivity.TAG, "Build.VERSION.SDK_INT " + android.os.Build.VERSION.SDK_INT);
                android.util.Log.e(com.kethu.telephonicapp.MainActivity.TAG, "Serial Number " + telephonyManager.getSimSerialNumber());
                android.util.Log.e(com.kethu.telephonicapp.MainActivity.TAG, "Device ID " + telephonyManager.getImei());
            }
        }
    }
}