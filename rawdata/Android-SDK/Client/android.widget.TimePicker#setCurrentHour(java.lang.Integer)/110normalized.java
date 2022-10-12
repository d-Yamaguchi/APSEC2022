@android.annotation.SuppressLint("NewApi")
private void initTime(android.widget.DatePicker datePicker, android.widget.TimePicker timePicker) {
    timePicker.setIs24HourView(true);
    timePicker.setOnTimeChangedListener(onTimeChangeListener);
    lastMinute = timePicker.getCurrentMinute();
    int minute = calendar.get(java.util.Calendar.MINUTE);
    java.util.TimeZone _CVAR1 = java.util.TimeZone.getDefault();
    java.util.Calendar calendar = java.util.Calendar.getInstance(_CVAR1);
    java.util.Calendar _CVAR2 = calendar;
    int _CVAR3 = java.util.Calendar.HOUR_OF_DAY;
    int hour = _CVAR2.get(_CVAR3);
    android.widget.TimePicker _CVAR0 = timePicker;
    int _CVAR4 = hour;
    _CVAR0.setCurrentHour(_CVAR4);
    timePicker.setCurrentMinute(minute);
    calendar.set(calendar.get(java.util.Calendar.YEAR), calendar.get(java.util.Calendar.MONTH), calendar.get(java.util.Calendar.DAY_OF_MONTH), 0, 0, 0);
    datePicker.setMinDate(calendar.getTimeInMillis());
    if ((reservationinfo.time != null) && (!reservationinfo.time.equals(""))) {
        java.lang.String[] dateTime = reservationinfo.time.split(" ");
        java.lang.String[] date = dateTime[0].split("-");
        datePicker.updateDate(java.lang.Integer.valueOf(date[0]), java.lang.Integer.valueOf(date[1]) - 1, java.lang.Integer.valueOf(date[2]));
        java.lang.String[] time = dateTime[1].split(":");
        java.lang.String _CVAR6 = time[0];
        android.widget.TimePicker _CVAR5 = timePicker;
        java.lang.Integer _CVAR7 = java.lang.Integer.valueOf(_CVAR6);
        _CVAR5.setCurrentHour(_CVAR7);
        timePicker.setCurrentMinute(java.lang.Integer.valueOf(time[1]));
    }
}