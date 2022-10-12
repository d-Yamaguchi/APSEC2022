@java.lang.Override
public void onClick(android.view.View view) {
    java.util.Calendar cal = java.util.Calendar.getInstance();
    android.widget.TimePicker _CVAR1 = timepicker;
    android.widget.TimePicker _CVAR3 = timepicker;
    java.util.Calendar _CVAR0 = cal;
    java.lang.Integer _CVAR2 = _CVAR1.getCurrentHour();
    java.lang.Integer _CVAR4 = _CVAR3.getCurrentMinute();
    int _CVAR5 = 00;
    _CVAR0.set(_CVAR2, _CVAR4, _CVAR5);
    android.widget.TimePicker _CVAR7 = timepicker;
    java.lang.Integer _CVAR8 = _CVAR7.getCurrentHour();
    java.lang.String _CVAR9 = "Alarm set at:" + _CVAR8;
    java.lang.String _CVAR10 = _CVAR9 + ":";
    com.example.alarm_app.MainActivity _CVAR6 = this;
    java.lang.String _CVAR11 = _CVAR10 + timepicker.getCurrentMinute();
    int _CVAR12 = android.widget.Toast.LENGTH_LONG;
    android.widget.Toast _CVAR13 = android.widget.Toast.makeText(_CVAR6, _CVAR11, _CVAR12);
    _CVAR13.show();
    android.content.Intent intent = new android.content.Intent(this, com.example.alarm_app.Mybroadcastreceiver.class);
    android.app.PendingIntent pi = android.app.PendingIntent.getBroadcast(this, 1234, intent, android.app.PendingIntent.FLAG_ONE_SHOT);
    android.app.AlarmManager amp = ((android.app.AlarmManager) (getSystemService(android.content.Context.ALARM_SERVICE)));
    amp.set(android.app.AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pi);
    android.widget.TimePicker _CVAR14 = timepicker;
    java.lang.Integer _CVAR15 = _CVAR14.getCurrentHour();
    java.lang.String _CVAR16 = "Alarm Set: " + _CVAR15;
    java.lang.String _CVAR17 = _CVAR16 + ":";
    java.lang.String currentTime = _CVAR17 + timepicker.getCurrentMinute();
    timetve.setText(currentTime);
}