@android.annotation.SuppressLint("HardwareIds")
@java.lang.Override
protected void onCreate(android.os.Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    textView = findViewById(R.id.textview);
    if (!com.kethu.telephonicapp.PermissionChecker.checkPermission(this, READ_PERMISSIONS)) {
        com.kethu.telephonicapp.PermissionChecker.reqPermissions(this, READ_PERMISSIONS);
    }
    java.lang.String _CVAR1 = android.content.Context.TELEPHONY_SERVICE;
    java.lang.String _CVAR6 = _CVAR1;
    android.telephony.TelephonyManager telephonyManager = ((android.telephony.TelephonyManager) (getSystemService(_CVAR6)));
    if (telephonyManager != null) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            // Call some material design APIs here
            android.util.Log.e(com.kethu.telephonicapp.MainActivity.TAG, "Build.VERSION.SDK_INT " + android.os.Build.VERSION.SDK_INT);
            android.util.Log.e(com.kethu.telephonicapp.MainActivity.TAG, "Single or Dula Sim " + telephonyManager.getPhoneCount());
            android.telephony.TelephonyManager _CVAR2 = telephonyManager;
            java.lang.String _CVAR3 = _CVAR2.getDeviceId();
            java.lang.String _CVAR0 = com.kethu.telephonicapp.MainActivity.TAG;
            java.lang.String _CVAR4 = "Default device ID " + _CVAR3;
            android.util.Log.e(_CVAR0, _CVAR4);
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
            android.telephony.TelephonyManager _CVAR7 = telephonyManager;
            java.lang.String _CVAR8 = _CVAR7.getDeviceId();
            java.lang.String _CVAR5 = com.kethu.telephonicapp.MainActivity.TAG;
            java.lang.String _CVAR9 = "Device ID " + _CVAR8;
            android.util.Log.e(_CVAR5, _CVAR9);
        }
    }
}