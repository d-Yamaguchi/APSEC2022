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
    java.lang.Integer _CVAR8 = _CVAR7.getCurrentMinute();
    com.example.alarmapp.MainActivity _CVAR6 = this;
    java.lang.String _CVAR9 = (("Alarm set at :" + timePicker.getCurrentHour()) + ":") + _CVAR8;
    int _CVAR10 = android.widget.Toast.LENGTH_SHORT;
    android.widget.Toast _CVAR11 = android.widget.Toast.makeText(_CVAR6, _CVAR9, _CVAR10);
    _CVAR11.show();
    android.content.Intent alarmIntent = new android.content.Intent(this, com.example.alarmapp.MyAlarmBroadcastReceiver.class);
    android.app.PendingIntent pendingIntent = android.app.PendingIntent.getBroadcast(this, 8092, alarmIntent, android.app.PendingIntent.FLAG_ONE_SHOT);
    android.app.AlarmManager alarmManager = ((android.app.AlarmManager) (getSystemService(com.example.alarmapp.ALARM_SERVICE)));
    alarmManager.set(android.app.AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
    android.widget.TimePicker _CVAR12 = timePicker;
    java.lang.Integer _CVAR13 = _CVAR12.getCurrentMinute();
    java.lang.String ct = (("Alarm set at :" + timePicker.getCurrentHour()) + ":") + _CVAR13;
    timetv.setText(ct);
}