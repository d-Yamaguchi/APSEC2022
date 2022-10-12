@java.lang.Override
public void onClick(android.view.View view) {
    java.lang.String minits = null;
    java.lang.String strdateTime;
    int _CVAR0 = 10;
    boolean _CVAR1 = timePicker.getCurrentMinute() < _CVAR0;
    if () {
        android.widget.TimePicker _CVAR2 = timePicker;
        java.lang.Integer _CVAR3 = _CVAR2.getCurrentMinute();
        java.lang.String _CVAR4 = "0" + _CVAR3;
        minits = _CVAR4;
        strdateTime = (((((((datePicker.getDayOfMonth() + "-") + (datePicker.getMonth() + 1)) + "-") + datePicker.getYear()) + " ") + timePicker.getCurrentHour()) + ":") + minits;
    } else {
        android.widget.TimePicker _CVAR5 = timePicker;
        java.lang.Integer _CVAR6 = _CVAR5.getCurrentMinute();
        java.lang.String _CVAR7 = (((((((datePicker.getDayOfMonth() + "-") + (datePicker.getMonth() + 1)) + "-") + datePicker.getYear()) + " ") + timePicker.getCurrentHour()) + ":") + _CVAR6;
        strdateTime = _CVAR7;
    }
    java.text.SimpleDateFormat format = new java.text.SimpleDateFormat("dd-MM-yyyy HH:mm");
    java.util.Date date1 = null;
    java.util.Date date2 = null;
    try {
        date1 = format.parse(format.format(new java.util.Date()));
        date2 = format.parse(strdateTime);
        long diff = date2.getTime() - date1.getTime();
        long diffHours = diff / ((60 * 60) * 1000);
        // long diffHours = diff / (300);
        if (diffHours >= 1) {
            android.widget.TimePicker _CVAR8 = timePicker;
            java.lang.Integer _CVAR9 = _CVAR8.getCurrentMinute();
            java.lang.String strdateTime1 = (((((((datePicker.getYear() + "-") + (datePicker.getMonth() + 1)) + "-") + datePicker.getDayOfMonth()) + " ") + timePicker.getCurrentHour()) + ":") + _CVAR9;
            setResult(com.calljack.im028.calljack.ActivityClasses.RESULT_OK, getIntent().putExtra(ConstantValues.scheduleDateTime, strdateTime).putExtra(ConstantValues.scheduleDateTime1, strdateTime1).putExtra(ConstantValues.scheduleDateTime, strdateTime));
            // setResult(RESULT_OK, getIntent().putExtra(ConstantValues.scheduleDateTime1, strdateTime));
            finish();
        } else {
            com.calljack.im028.calljack.Utility.ConstantClasses.ConstantFunctions.toast(this, "Please select after one hour from current time");
        }
    } catch (java.text.ParseException e) {
        e.printStackTrace();
    }
}