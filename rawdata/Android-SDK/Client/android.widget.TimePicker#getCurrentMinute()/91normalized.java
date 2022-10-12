@java.lang.Override
public void onClick(android.view.View v) {
    android.widget.TimePicker _CVAR1 = alarm_timepicker;
    java.lang.Integer _CVAR2 = _CVAR1.getCurrentMinute();
     _CVAR0 = getBaseContext();
    java.lang.String _CVAR3 = (("Alarm set to" + alarm_timepicker.getCurrentHour()) + ":") + _CVAR2;
    int _CVAR4 = android.widget.Toast.LENGTH_SHORT;
    android.widget.Toast _CVAR5 = android.widget.Toast.makeText(_CVAR0, _CVAR3, _CVAR4);
    _CVAR5.show();
    cal.set(java.util.Calendar.HOUR_OF_DAY, alarm_timepicker.getCurrentHour());
    final java.util.Calendar cal = java.util.Calendar.getInstance();
    android.widget.TimePicker _CVAR8 = alarm_timepicker;
    java.util.Calendar _CVAR6 = cal;
    int _CVAR7 = java.util.Calendar.MINUTE;
    java.lang.Integer _CVAR9 = _CVAR8.getCurrentMinute();
    _CVAR6.set(_CVAR7, _CVAR9);
    intent.putExtra("extra", "alarm on");
    pendingIntent = android.app.PendingIntent.getBroadcast(this, 0, intent, android.app.PendingIntent.FLAG_UPDATE_CURRENT);
    alarmManager.set(android.app.AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pendingIntent);
}