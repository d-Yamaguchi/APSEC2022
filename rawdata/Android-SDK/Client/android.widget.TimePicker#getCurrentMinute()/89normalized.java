public void init(android.widget.DatePicker datePicker, android.widget.TimePicker timePicker) {
    datePicker.init(calendar.get(java.util.Calendar.YEAR), calendar.get(java.util.Calendar.MONDAY), calendar.get(java.util.Calendar.DAY_OF_MONTH), this);
    datePicker.setCalendarViewShown(false);
    timePicker.setIs24HourView(true);
    timePicker.setCurrentHour(calendar.get(java.util.Calendar.HOUR_OF_DAY));
    timePicker.setCurrentMinute(calendar.get(java.util.Calendar.MINUTE));
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
    java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd  HH:mm");
    com.greenhouse.widget.CustomDatePicker.Init_Start_time = sdf.format(calendar.getTime());
    android.util.Log.i("init start_time", "init start time" + com.greenhouse.widget.CustomDatePicker.Init_Start_time);
}