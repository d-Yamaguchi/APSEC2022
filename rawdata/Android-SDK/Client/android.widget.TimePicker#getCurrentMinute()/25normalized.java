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
    java.lang.Integer _CVAR8 = _CVAR7.getCurrentMinute();
    com.example.alarm_app.MainActivity _CVAR6 = this;
    java.lang.String _CVAR9 = (("Alarm set at:" + timepicker.getCurrentHour()) + ":") + _CVAR8;
    int _CVAR10 = android.widget.Toast.LENGTH_LONG;
    android.widget.Toast _CVAR11 = android.widget.Toast.makeText(_CVAR6, _CVAR9, _CVAR10);
    _CVAR11.show();
    android.content.Intent intent = new android.content.Intent(this, com.example.alarm_app.Mybroadcastreceiver.class);
    android.app.PendingIntent pi = android.app.PendingIntent.getBroadcast(this, 1234, intent, android.app.PendingIntent.FLAG_ONE_SHOT);
    android.app.AlarmManager amp = ((android.app.AlarmManager) (getSystemService(android.content.Context.ALARM_SERVICE)));
    amp.set(android.app.AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pi);
    android.widget.TimePicker _CVAR12 = timepicker;
    java.lang.Integer _CVAR13 = _CVAR12.getCurrentMinute();
    java.lang.String currentTime = (("Alarm Set: " + timepicker.getCurrentHour()) + ":") + _CVAR13;
    timetve.setText(currentTime);
}