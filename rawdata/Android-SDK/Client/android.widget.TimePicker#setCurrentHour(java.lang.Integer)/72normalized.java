@java.lang.Override
protected void onCreate(android.os.Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    android.content.SharedPreferences settings = getSharedPreferences(de.elbosso.serveravailabilityalarm.MainActivity.PREFS_NAME, 0);
    java.lang.String urlString = settings.getString("url", null);
    if (urlString != null) {
        ((android.widget.EditText) (findViewById(R.id.editText))).setText(urlString);
    }
    int perc = 25;
    if (settings.contains("perc")) {
        perc = settings.getInt("perc", perc);
    }
    ((android.widget.EditText) (findViewById(R.id.editText2))).setText("" + perc);
    int age = 3;
    if (settings.contains("age")) {
        age = settings.getInt("age", age);
    }
    ((android.widget.EditText) (findViewById(R.id.editText3))).setText("" + age);
    if (settings.contains("smsbeforeh")) {
        smsbeforeh = settings.getInt("smsbeforeh", smsbeforeh);
    }
    void _CVAR0 = R.id.timePicker;
    int smsbeforeh = 9;
    android.view.View _CVAR1 = ((android.widget.TimePicker) (findViewById(_CVAR0)));
    int _CVAR2 = smsbeforeh;
    _CVAR1.setCurrentHour(_CVAR2);
    int smsbeforem = 0;
    if (settings.contains("smsbeforem")) {
        smsbeforem = settings.getInt("smsbeforem", smsbeforem);
    }
    ((android.widget.TimePicker) (findViewById(R.id.timePicker))).setCurrentMinute(smsbeforem);
    if (settings.contains("smsafterh")) {
        smsafterh = settings.getInt("smsafterh", smsafterh);
    }
    void _CVAR3 = R.id.timePicker3;
    int smsafterh = 16;
    android.view.View _CVAR4 = ((android.widget.TimePicker) (findViewById(_CVAR3)));
    int _CVAR5 = smsafterh;
    _CVAR4.setCurrentHour(_CVAR5);
    int smsafterm = 0;
    if (settings.contains("smsafterm")) {
        smsafterm = settings.getInt("smsafterm", smsafterm);
    }
    ((android.widget.TimePicker) (findViewById(R.id.timePicker3))).setCurrentMinute(smsafterm);
    if (settings.contains("smsremindh")) {
        smsremindh = settings.getInt("smsremindh", smsremindh);
    }
    void _CVAR6 = R.id.timePicker4;
    int smsremindh = 7;
    android.view.View _CVAR7 = ((android.widget.TimePicker) (findViewById(_CVAR6)));
    int _CVAR8 = smsremindh;
    _CVAR7.setCurrentHour(_CVAR8);
    int smsremindm = 30;
    if (settings.contains("smsremindm")) {
        smsremindm = settings.getInt("smsremindm", smsremindm);
    }
    ((android.widget.TimePicker) (findViewById(R.id.timePicker4))).setCurrentMinute(smsremindm);
    java.lang.String jabber_host = settings.getString("jabber_host", null);
    if (jabber_host != null) {
        ((android.widget.EditText) (findViewById(R.id.editText8))).setText(jabber_host);
    }
    java.lang.String jabber_user = settings.getString("jabber_user", null);
    if (jabber_user != null) {
        ((android.widget.EditText) (findViewById(R.id.editText6))).setText(jabber_user);
    }
    java.lang.String jabber_pass = settings.getString("jabber_pass", null);
    if (jabber_pass != null) {
        ((android.widget.EditText) (findViewById(R.id.editText7))).setText(jabber_pass);
    }
    int jabber_port = 5222;
    if (settings.contains("jabber_port")) {
        age = settings.getInt("jabber_port", jabber_port);
    }
    ((android.widget.EditText) (findViewById(R.id.editText9))).setText("" + jabber_port);
    ((android.widget.TimePicker) (findViewById(R.id.timePicker))).setIs24HourView(true);
    ((android.widget.TimePicker) (findViewById(R.id.timePicker3))).setIs24HourView(true);
    ((android.widget.TimePicker) (findViewById(R.id.timePicker4))).setIs24HourView(true);
    reportHelper = new de.elbosso.serveravailabilityalarm.ReportHelper(this);
    java.lang.Thread.setDefaultUncaughtExceptionHandler(reportHelper);
}