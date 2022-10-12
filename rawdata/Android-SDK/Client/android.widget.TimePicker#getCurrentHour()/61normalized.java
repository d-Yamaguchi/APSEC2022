@java.lang.Override
public void onClick(android.view.View v) {
    android.widget.TimePicker _CVAR2 = timePicker;
    java.util.Calendar _CVAR0 = calendar;
    int _CVAR1 = java.util.Calendar.HOUR_OF_DAY;
    java.lang.Integer _CVAR3 = _CVAR2.getCurrentHour();
    _CVAR0.set(_CVAR1, _CVAR3);
    calendar.set(java.util.Calendar.MINUTE, timePicker.getCurrentMinute());
    android.widget.TimePicker _CVAR5 = timePicker;
    java.io.PrintStream _CVAR4 = java.lang.System.out;
    java.lang.Integer _CVAR6 = _CVAR5.getCurrentHour();
    _CVAR4.println(_CVAR6);
    java.lang.System.out.println(timePicker.getCurrentMinute());
    android.widget.TimePicker _CVAR7 = timePicker;
    int hour = _CVAR7.getCurrentHour();
    int min = timePicker.getCurrentMinute();
    java.lang.String hour_string = java.lang.String.valueOf(hour);
    java.lang.String minute_string = java.lang.String.valueOf(min);
    if (min < 10) {
        minute_string = "0" + java.lang.String.valueOf(min);
    }
    android.widget.Toast.makeText(this, (((("You have set alarm reminder " + alarmReason.getText().toString()) + " ") + hour_string) + ":") + minute_string, android.widget.Toast.LENGTH_LONG).show();
    // its probable that the 0 below will need to be a variable that keeps changing if you want to add multiple alarms.
    pendingIntent = android.app.PendingIntent.getBroadcast(this, 0, sender, android.app.PendingIntent.FLAG_UPDATE_CURRENT);
    if (repeat.isChecked()) {
        alarmManager.setRepeating(android.app.AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), android.app.AlarmManager.INTERVAL_DAY, pendingIntent);
    } else {
        alarmManager.set(android.app.AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
    }
}