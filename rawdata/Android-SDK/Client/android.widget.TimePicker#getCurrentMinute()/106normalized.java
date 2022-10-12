@java.lang.Override
public void onClick(android.view.View view) {
    android.content.Intent intent = new android.content.Intent();
    java.lang.String year = java.lang.Integer.toString(datePicker.getYear());
    java.lang.String month = java.lang.Integer.toString(datePicker.getMonth());
    if (datePicker.getMonth() < 10) {
        month = "0" + java.lang.Integer.toString(datePicker.getMonth() + 1);
    }
    java.lang.String day = java.lang.Integer.toString(datePicker.getDayOfMonth());
    if (datePicker.getDayOfMonth() < 10) {
        day = "0" + java.lang.Integer.toString(datePicker.getDayOfMonth());
    }
    java.lang.String hour = java.lang.Integer.toString(timePicker.getCurrentHour());
    if (timePicker.getCurrentHour() < 10) {
        hour = "0" + java.lang.Integer.toString(timePicker.getCurrentHour());
    }
    android.widget.TimePicker _CVAR0 = timePicker;
    java.lang.Integer _CVAR1 = _CVAR0.getCurrentMinute();
    java.lang.String minute = java.lang.Integer.toString(_CVAR1);
    int _CVAR2 = 10;
    boolean _CVAR3 = timePicker.getCurrentMinute() < _CVAR2;
    if () {
        android.widget.TimePicker _CVAR4 = timePicker;
        java.lang.Integer _CVAR5 = _CVAR4.getCurrentMinute();
        java.lang.String _CVAR6 = java.lang.Integer.toString(_CVAR5);
        java.lang.String _CVAR7 = "0" + _CVAR6;
        minute = _CVAR7;
    }
    java.lang.String second = "00";
    java.lang.String result = (((((((((year + "/") + month) + "/") + day) + " ") + hour) + ":") + minute) + ":") + second;
    intent.putExtra("DATE_TIME", result);
    setResult(android.app.Activity.RESULT_OK, intent);
    finish();
}