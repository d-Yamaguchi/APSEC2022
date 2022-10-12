public void init(android.widget.DatePicker datePicker, android.widget.TimePicker timePicker) {
    if (!((null == initDateTime) || "".equals(initDateTime))) {
        calendar = this.getCalendarByInintData(initDateTime);
    } else {
        initDateTime = (((((((calendar.get(java.util.Calendar.YEAR) + "年") + calendar.get(java.util.Calendar.MONTH)) + "月") + calendar.get(java.util.Calendar.DAY_OF_MONTH)) + "日 ") + calendar.get(java.util.Calendar.HOUR_OF_DAY)) + ":") + calendar.get(java.util.Calendar.MINUTE);
    }
    datePicker.init(calendar.get(java.util.Calendar.YEAR), calendar.get(java.util.Calendar.MONTH), calendar.get(java.util.Calendar.DAY_OF_MONTH), this);
    java.util.Calendar calendar = java.util.Calendar.getInstance();
    java.util.Calendar _CVAR1 = calendar;
    int _CVAR2 = java.util.Calendar.HOUR_OF_DAY;
    android.widget.TimePicker _CVAR0 = timePicker;
    int _CVAR3 = _CVAR1.get(_CVAR2);
    _CVAR0.setCurrentHour(_CVAR3);
    timePicker.setCurrentMinute(calendar.get(java.util.Calendar.MINUTE));
}