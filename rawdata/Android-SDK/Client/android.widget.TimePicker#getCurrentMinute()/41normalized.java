public void SetAlarm(android.view.View view) {
    java.util.Calendar calendar = java.util.Calendar.getInstance();
    if (android.os.Build.VERSION.SDK_INT >= 23) {
        logFiles.Senddata(com.lnt.chthp00109.servicesapp.Sceduletask.TAG + "-->Api level  >= 23!");
        calendar.set(calendar.get(java.util.Calendar.YEAR), calendar.get(java.util.Calendar.MONTH), calendar.get(java.util.Calendar.DAY_OF_MONTH), timePicker.getHour(), timePicker.getMinute(), 0);
        amorpm = (timePicker.getHour() >= 12) ? "PM" : "AM";
        times = (((java.lang.String.valueOf(TimES = (timePicker.getHour() > 12) ? timePicker.getHour() - 12 : timePicker.getHour()) + ":") + java.lang.String.valueOf(timePicker.getMinute() < 10 ? java.lang.String.valueOf("0" + timePicker.getMinute()) : timePicker.getMinute())) + " ") + amorpm;
    } else {
        logFiles.Senddata(com.lnt.chthp00109.servicesapp.Sceduletask.TAG + "-->:API LESS THAN 23!");
        java.util.Calendar _CVAR1 = calendar;
        int _CVAR2 = java.util.Calendar.YEAR;
        java.util.Calendar _CVAR4 = calendar;
        int _CVAR5 = java.util.Calendar.MONTH;
        java.util.Calendar _CVAR7 = calendar;
        int _CVAR8 = java.util.Calendar.DAY_OF_MONTH;
        android.widget.TimePicker _CVAR10 = timePicker;
        android.widget.TimePicker _CVAR12 = timePicker;
        java.util.Calendar _CVAR0 = calendar;
        int _CVAR3 = _CVAR1.get(_CVAR2);
        int _CVAR6 = _CVAR4.get(_CVAR5);
        int _CVAR9 = _CVAR7.get(_CVAR8);
        java.lang.Integer _CVAR11 = _CVAR10.getCurrentHour();
        java.lang.Integer _CVAR13 = _CVAR12.getCurrentMinute();
        int _CVAR14 = 0;
        _CVAR0.set(_CVAR3, _CVAR6, _CVAR9, _CVAR11, _CVAR13, _CVAR14);
        amorpm = (timePicker.getCurrentHour() >= 12) ? "PM" : "AM";
        java.lang.Object _CVAR15 = (timePicker.getCurrentMinute() < 10) ? java.lang.String.valueOf("0" + timePicker.getCurrentMinute()) : timePicker.getCurrentMinute();
        java.lang.String _CVAR16 = java.lang.String.valueOf(_CVAR15);
        java.lang.String _CVAR17 = java.lang.String.valueOf(TimES = (timePicker.getCurrentHour() > 12) ? timePicker.getCurrentHour() - 12 : timePicker.getCurrentHour()) + _CVAR16;
        java.lang.String _CVAR18 = _CVAR17 + " ";
        java.lang.String _CVAR19 = _CVAR18 + amorpm;
        times = _CVAR19;
    }
    setTask(calendar.getTimeInMillis(), times);
}