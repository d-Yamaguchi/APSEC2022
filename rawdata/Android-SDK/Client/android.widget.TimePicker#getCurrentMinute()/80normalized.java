@java.lang.Override
public void onClick(android.view.View v) {
    android.content.Intent wakeUpIntent = new android.content.Intent(this, com.polozaur.testing.alarms.TaskService.class);
    pendingIntent = android.app.PendingIntent.getService(this, 0, wakeUpIntent, 0);
    android.app.AlarmManager alarmManager = ((android.app.AlarmManager) (getSystemService(android.content.Context.ALARM_SERVICE)));
    calendar.setTimeInMillis(java.lang.System.currentTimeMillis());
    calendar.clear();
    java.util.Calendar calendar = java.util.Calendar.getInstance();
    android.widget.DatePicker _CVAR1 = datePicker;
    android.widget.DatePicker _CVAR3 = datePicker;
    android.widget.DatePicker _CVAR5 = datePicker;
    android.widget.TimePicker _CVAR7 = timePicker;
    android.widget.TimePicker _CVAR9 = timePicker;
    java.util.Calendar _CVAR0 = calendar;
    int _CVAR2 = _CVAR1.getYear();
    int _CVAR4 = _CVAR3.getMonth();
    int _CVAR6 = _CVAR5.getDayOfMonth();
    java.lang.Integer _CVAR8 = _CVAR7.getCurrentHour();
    java.lang.Integer _CVAR10 = _CVAR9.getCurrentMinute();
    _CVAR0.set(_CVAR2, _CVAR4, _CVAR6, _CVAR8, _CVAR10);
    alarmManager.set(android.app.AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
    android.widget.TimePicker _CVAR12 = timePicker;
    java.lang.Integer _CVAR13 = _CVAR12.getCurrentMinute();
    com.polozaur.testing.alarms.NewTaskActivity _CVAR11 = this;
    java.lang.String _CVAR14 = (((((((("Year: " + datePicker.getYear()) + "\nMonth: ") + datePicker.getMonth()) + "\nDay: ") + datePicker.getDayOfMonth()) + "\nHour: ") + timePicker.getCurrentHour()) + "\nMinute: ") + _CVAR13;
    int _CVAR15 = android.widget.Toast.LENGTH_LONG;
    android.widget.Toast _CVAR16 = android.widget.Toast.makeText(_CVAR11, _CVAR14, _CVAR15);
    _CVAR16.show();
}