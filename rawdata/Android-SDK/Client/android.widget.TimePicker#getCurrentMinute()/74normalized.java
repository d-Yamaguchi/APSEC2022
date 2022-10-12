private void setAlarmDate() {
    android.widget.TimePicker _CVAR0 = timePicker;
    android.widget.TimePicker _CVAR2 = timePicker;
    int ngaycach = 1;
    java.lang.Integer _CVAR1 = _CVAR0.getCurrentHour();
    java.lang.Integer _CVAR3 = _CVAR2.getCurrentMinute();
    com.vinh.alarmclockandroid.activities.MainActivity _CVAR4 = this;
    int _CVAR5 = ngaycach;
    com.vinh.alarmclockandroid.activities.AlarmHandle.setAlarm(_CVAR1, _CVAR3, _CVAR4, _CVAR5);
    android.widget.TimePicker _CVAR7 = timePicker;
    java.lang.Integer _CVAR8 = _CVAR7.getCurrentMinute();
    java.lang.String _CVAR6 = "abcd";
    java.lang.String _CVAR9 = (("setAlarmDate: " + timePicker.getCurrentHour()) + " : ") + _CVAR8;
    android.util.Log.d(_CVAR6, _CVAR9);
}