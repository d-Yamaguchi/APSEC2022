@android.support.annotation.RequiresApi(api = android.os.Build.VERSION_CODES.N)
@java.lang.Override
public void onClick(android.view.View view) {
    final android.icu.util.Calendar calendar = android.icu.util.Calendar.getInstance();
    try {
        android.widget.DatePicker _CVAR1 = datePicker;
        android.widget.DatePicker _CVAR3 = datePicker;
        android.widget.DatePicker _CVAR5 = datePicker;
        android.widget.TimePicker _CVAR7 = timePicker;
        android.widget.TimePicker _CVAR9 = timePicker;
        android.icu.util.Calendar _CVAR0 = calendar;
        int _CVAR2 = _CVAR1.getYear();
        int _CVAR4 = _CVAR3.getMonth();
        int _CVAR6 = _CVAR5.getDayOfMonth();
        java.lang.Integer _CVAR8 = _CVAR7.getCurrentHour();
        int _CVAR10 = _CVAR9.getMinute();
        // calendar.set(Calendar.HOUR_OF_DAY, timePicker.getCurrentHour());
        // calendar.set(Calendar.MINUTE, timePicker.getCurrentMinute());
        // calendar.set(Calendar.DATE, datePicker.getDayOfMonth());
        // calendar.set(Calendar.MONTH, datePicker.getMonth());
        // calendar.set(Calendar.YEAR, datePicker.getYear());
        // int Year = getData.getIntExtra("year", 2017);
        // int Month = getData.getIntExtra("month", 13);
        // int Day = getData.getIntExtra("day", 3);
        // int Hourr = getData.getIntExtra("hour", 3);
        // int Minutee = getData.getIntExtra("minute", 5);
        // calendar.set(Year, Month, Day, Hourr, Minutee);
        _CVAR0.set(_CVAR2, _CVAR4, _CVAR6, _CVAR8, _CVAR10);
        android.widget.TimePicker _CVAR11 = timePicker;
        // calendar.set(DateDialog.datePicker2.getYear(),DateDialog.datePicker2.getMonth(), DateDialog.datePicker2.getDayOfMonth(), TimeDialog.timePicker2.getCurrentHour(), TimeDialog.timePicker2.getCurrentMinute());
        int hour = _CVAR11.getCurrentHour();
        int minute = timePicker.getCurrentMinute();
        java.lang.String Hour = java.lang.String.valueOf(hour);
        java.lang.String Minute = java.lang.String.valueOf(minute);
        if (minute < 10) {
            Minute = "0" + Minute;
        }
        int date = datePicker.getDayOfMonth();
        int month = datePicker.getMonth();
        android.widget.Toast.makeText(this, (java.lang.String.valueOf(date) + " ") + java.lang.String.valueOf(month), android.widget.Toast.LENGTH_SHORT).show();
        android.content.Intent intent1 = getIntent();
        com.example.thjen.reminderdemo.Alarm.position = intent1.getExtras().getInt("position");
        intent.putExtra("extra", "on");
        pendingIntent = android.app.PendingIntent.getBroadcast(this, com.example.thjen.reminderdemo.Alarm.position, intent, android.app.PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.set(android.app.AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        tv.setText((("Time Current: " + Hour) + ":") + Minute);
    } catch (java.lang.Exception e) {
    }
}