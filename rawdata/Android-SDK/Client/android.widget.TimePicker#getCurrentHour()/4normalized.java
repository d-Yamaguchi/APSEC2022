@java.lang.Override
public void onClick(android.view.View v) {
    android.widget.TimePicker _CVAR0 = timePicker;
    android.widget.TimePicker _CVAR2 = timePicker;
    android.widget.DatePicker _CVAR4 = datePicker;
    android.widget.DatePicker _CVAR6 = datePicker;
    java.lang.Integer _CVAR1 = _CVAR0.getCurrentMinute();
    java.lang.Integer _CVAR3 = _CVAR2.getCurrentHour();
    int _CVAR5 = _CVAR4.getDayOfMonth();
    int _CVAR7 = _CVAR6.getMonth();
    java.lang.String _CVAR8 = "back";
    saveClicked(_CVAR1, _CVAR3, _CVAR5, _CVAR7, _CVAR8);
    saveClicked(minute, hour, day, month, "back");
}