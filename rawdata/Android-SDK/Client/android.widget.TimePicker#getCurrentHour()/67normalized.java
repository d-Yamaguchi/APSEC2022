@java.lang.Override
public void onClick(android.content.DialogInterface dialog, int which) {
    android.widget.TimePicker _CVAR0 = timePicker;
    android.widget.TimePicker _CVAR2 = timePicker;
    java.lang.Integer _CVAR1 = _CVAR0.getCurrentHour();
    java.lang.Integer _CVAR3 = _CVAR2.getCurrentMinute();
    application.com.car.entity.Route.setTime(_CVAR1, _CVAR3);
    if (timePicker.getCurrentMinute() < 10) {
        java.lang.String _CVAR5 = ":";
        java.lang.String _CVAR6 = timePicker.getCurrentHour() + _CVAR5;
        java.lang.String _CVAR7 = _CVAR6 + "0";
        android.widget.Button _CVAR4 = button;
        java.lang.String _CVAR8 = _CVAR7 + timePicker.getCurrentMinute();
        _CVAR4.setText(_CVAR8);
    } else {
        java.lang.String _CVAR10 = ":";
        java.lang.String _CVAR11 = timePicker.getCurrentHour() + _CVAR10;
        android.widget.Button _CVAR9 = button;
        java.lang.String _CVAR12 = _CVAR11 + timePicker.getCurrentMinute();
        _CVAR9.setText(_CVAR12);
    }
}