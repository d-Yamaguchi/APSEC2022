@java.lang.Override
protected void onCreate(android.os.Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    com.example.tagweck.tagweck.MainActivity.seconds_text = ((android.widget.TextView) (findViewById(R.id.seconds_text)));
    com.example.tagweck.tagweck.MainActivity.minutes_text = ((android.widget.TextView) (findViewById(R.id.minutes_text)));
    com.example.tagweck.tagweck.MainActivity.hours_text = ((android.widget.TextView) (findViewById(R.id.hours_text)));
    com.example.tagweck.tagweck.MainActivity.weckzeit = ((android.widget.TextView) (findViewById(R.id.weckzeit)));
    com.example.tagweck.tagweck.MainActivity.timePicker = ((android.widget.TimePicker) (findViewById(R.id.timePicker)));
    com.example.tagweck.tagweck.MainActivity.alarm_toggle = ((android.widget.ToggleButton) (findViewById(R.id.alarm_toogle)));
    com.example.tagweck.tagweck.MainActivity.button_ok = ((android.widget.Button) (findViewById(R.id.button_ok)));
    com.example.tagweck.tagweck.MainActivity.timePicker.setIs24HourView(true);
    java.util.Calendar _CVAR1 = java.util.Calendar.getInstance();
    int _CVAR2 = java.util.Calendar.HOUR_OF_DAY;
    android.widget.TimePicker _CVAR0 = com.example.tagweck.tagweck.MainActivity.timePicker;
    int _CVAR3 = _CVAR1.get(_CVAR2);
    _CVAR0.setCurrentHour(_CVAR3);
    android.content.SharedPreferences prefs = getPreferences(android.content.Context.MODE_PRIVATE);
    com.example.tagweck.tagweck.MainActivity.seconds_text.setText(java.lang.Integer.toString(prefs.getInt(com.example.tagweck.tagweck.MainActivity.seconds_string, getseconds())));
    com.example.tagweck.tagweck.MainActivity.minutes_text.setText(java.lang.Integer.toString(prefs.getInt(com.example.tagweck.tagweck.MainActivity.minutes_string, getminutes())));
    com.example.tagweck.tagweck.MainActivity.hours_text.setText(java.lang.Integer.toString(prefs.getInt(com.example.tagweck.tagweck.MainActivity.hours_string, gethours())));
    com.example.tagweck.tagweck.MainActivity.hour = prefs.getInt(com.example.tagweck.tagweck.MainActivity.hour_string, 0);
    com.example.tagweck.tagweck.MainActivity.minute = prefs.getInt(com.example.tagweck.tagweck.MainActivity.minute_string, 0);
    cWeckzeit.set(java.util.Calendar.HOUR_OF_DAY, com.example.tagweck.tagweck.MainActivity.hour);
    cWeckzeit.set(java.util.Calendar.MINUTE, com.example.tagweck.tagweck.MainActivity.minute);
    com.example.tagweck.tagweck.MainActivity.weckzeit.setText((cWeckzeit.get(java.util.Calendar.HOUR_OF_DAY) + ":") + cWeckzeit.get(java.util.Calendar.MINUTE));
}