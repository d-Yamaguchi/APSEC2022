@java.lang.Override
public void onClick(android.view.View v) {
    java.util.Calendar calendar = java.util.Calendar.getInstance();
    android.widget.TimePicker _CVAR1 = timePicker;
    android.widget.TimePicker _CVAR3 = timePicker;
    java.util.Calendar _CVAR0 = calendar;
    java.lang.Integer _CVAR2 = _CVAR1.getCurrentHour();
    java.lang.Integer _CVAR4 = _CVAR3.getCurrentMinute();
    int _CVAR5 = 0;
    _CVAR0.set(_CVAR2, _CVAR4, _CVAR5);
    android.widget.TimePicker _CVAR7 = timePicker;
    java.lang.Integer _CVAR8 = _CVAR7.getCurrentHour();
    java.lang.String _CVAR9 = "Alarm set at :" + _CVAR8;
    java.lang.String _CVAR10 = _CVAR9 + ":";
    com.example.alarmapp.MainActivity _CVAR6 = this;
    java.lang.String _CVAR11 = _CVAR10 + timePicker.getCurrentMinute();
    int _CVAR12 = android.widget.Toast.LENGTH_SHORT;
    android.widget.Toast _CVAR13 = android.widget.Toast.makeText(_CVAR6, _CVAR11, _CVAR12);
    _CVAR13.show();
    android.content.Intent alarmIntent = new android.content.Intent(this, com.example.alarmapp.MyAlarmBroadcastReceiver.class);
    android.app.PendingIntent pendingIntent = android.app.PendingIntent.getBroadcast(this, 8092, alarmIntent, android.app.PendingIntent.FLAG_ONE_SHOT);
    android.app.AlarmManager alarmManager = ((android.app.AlarmManager) (getSystemService(com.example.alarmapp.ALARM_SERVICE)));
    alarmManager.set(android.app.AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
    android.widget.TimePicker _CVAR14 = timePicker;
    java.lang.Integer _CVAR15 = _CVAR14.getCurrentHour();
    java.lang.String _CVAR16 = "Alarm set at :" + _CVAR15;
    java.lang.String _CVAR17 = _CVAR16 + ":";
    java.lang.String ct = _CVAR17 + timePicker.getCurrentMinute();
    timetv.setText(ct);
}