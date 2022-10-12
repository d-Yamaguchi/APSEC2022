@java.lang.Override
public void onClick(android.content.DialogInterface dialog, int which) {
    android.widget.TimePicker _CVAR0 = timePicker;
    android.widget.TimePicker _CVAR2 = timePicker;
    java.lang.Integer _CVAR1 = _CVAR0.getCurrentHour();
    java.lang.Integer _CVAR3 = _CVAR2.getCurrentMinute();
    application.com.car.entity.Route.setTime(_CVAR1, _CVAR3);
    int _CVAR4 = 10;
    boolean _CVAR5 = timePicker.getCurrentMinute() < _CVAR4;
    if () {
        android.widget.TimePicker _CVAR7 = timePicker;
        java.lang.Integer _CVAR8 = _CVAR7.getCurrentMinute();
        android.widget.Button _CVAR6 = button;
        java.lang.String _CVAR9 = ((timePicker.getCurrentHour() + ":") + "0") + _CVAR8;
        _CVAR6.setText(_CVAR9);
    } else {
        android.widget.TimePicker _CVAR11 = timePicker;
        java.lang.Integer _CVAR12 = _CVAR11.getCurrentMinute();
        android.widget.Button _CVAR10 = button;
        java.lang.String _CVAR13 = (timePicker.getCurrentHour() + ":") + _CVAR12;
        _CVAR10.setText(_CVAR13);
    }
}