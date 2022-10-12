@java.lang.Override
protected void onCreate(android.os.Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    try {
        ubc.yingying.reflection2test4.BaseClass concrete1 = ((ubc.yingying.reflection2test4.BaseClass) (java.lang.Class.forName("ubc.yingying.reflection2test4.ConcreteClass1").newInstance()));
        ubc.yingying.reflection2test4.BaseClass concrete2 = ((ubc.yingying.reflection2test4.BaseClass) (java.lang.Class.forName("ubc.yingying.reflection2test4.ConcreteClass2").newInstance()));
        ubc.yingying.reflection2test4.BaseClass2 concrete3 = ((ubc.yingying.reflection2test4.BaseClass2) (java.lang.Class.forName("ubc.yingying.reflection2test4.ConcreteClass3").newInstance()));
        ubc.yingying.reflection2test4.BaseClass2 concrete4 = ((ubc.yingying.reflection2test4.BaseClass2) (java.lang.Class.forName("ubc.yingying.reflection2test4.ConcreteClass4").newInstance()));
        java.lang.String _CVAR0 = android.content.Context.TELEPHONY_SERVICE;
        android.telephony.TelephonyManager telephonyManager1 = ((android.telephony.TelephonyManager) (getSystemService(_CVAR0)));
        android.telephony.TelephonyManager _CVAR1 = telephonyManager1;
        java.lang.String _CVAR2 = _CVAR1.getDeviceId();
        concrete1.imei = _CVAR2;// source

        java.lang.String _CVAR3 = android.content.Context.TELEPHONY_SERVICE;
        android.telephony.TelephonyManager telephonyManager2 = ((android.telephony.TelephonyManager) (getSystemService(_CVAR3)));
        android.telephony.TelephonyManager _CVAR4 = telephonyManager2;
        java.lang.String _CVAR5 = _CVAR4.getDeviceId();
        concrete2.imei = _CVAR5;// source

        java.lang.String _CVAR6 = android.content.Context.TELEPHONY_SERVICE;
        android.telephony.TelephonyManager telephonyManager3 = ((android.telephony.TelephonyManager) (getSystemService(_CVAR6)));
        android.telephony.TelephonyManager _CVAR7 = telephonyManager3;
        java.lang.String _CVAR8 = _CVAR7.getDeviceId();
        concrete3.imei = _CVAR8;// source

        java.lang.String _CVAR9 = android.content.Context.TELEPHONY_SERVICE;
        android.telephony.TelephonyManager telephonyManager4 = ((android.telephony.TelephonyManager) (getSystemService(_CVAR9)));
        android.telephony.TelephonyManager _CVAR10 = telephonyManager4;
        java.lang.String _CVAR11 = _CVAR10.getDeviceId();
        concrete4.imei = _CVAR11;// source

        android.util.Log.i("concrete1,leak", concrete1.foo());// sink, leak

        android.util.Log.i("concrete2,no leak", concrete2.foo());// sink, no leak

        android.util.Log.i("concrete3,leak", concrete3.foo());// sink, leak

        android.util.Log.i("concrete4,no leak", concrete4.foo());// sink, no leak

    } catch (java.lang.InstantiationException e) {
        e.printStackTrace();
    } catch (java.lang.IllegalAccessException e) {
        e.printStackTrace();
    } catch (java.lang.ClassNotFoundException e) {
        e.printStackTrace();
    }
}