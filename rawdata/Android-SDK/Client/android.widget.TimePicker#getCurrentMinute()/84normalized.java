@java.lang.Override
protected void onDialogClosed(boolean positiveResult) {
    super.onDialogClosed(positiveResult);
    editor = sharedPrefs.edit();
    if (positiveResult) {
        editor.putInt("preference_alarm_hour", timePicker.getCurrentHour());
        android.widget.TimePicker _CVAR2 = timePicker;
        android.content.SharedPreferences.Editor _CVAR0 = editor;
        java.lang.String _CVAR1 = "preference_alarm_minute";
        java.lang.Integer _CVAR3 = _CVAR2.getCurrentMinute();
        _CVAR0.putInt(_CVAR1, _CVAR3);
        int _CVAR6 = 60000;
        int _CVAR7 = timePicker.getCurrentMinute() * _CVAR6;
        android.content.SharedPreferences.Editor _CVAR4 = editor;
        java.lang.String _CVAR5 = "preference_alarm_time";
        int _CVAR8 = (timePicker.getCurrentHour() * 3600000) + _CVAR7;
        _CVAR4.putInt(_CVAR5, _CVAR8);// placeholder just so onSharedPrefChange is called? idk

        int _CVAR11 = 60000;
        int _CVAR12 = timePicker.getCurrentMinute() * _CVAR11;
        android.content.SharedPreferences.Editor _CVAR9 = editor;
        java.lang.String _CVAR10 = "preference_alarm_time";
        int _CVAR13 = (timePicker.getCurrentHour() * 3600000) + _CVAR12;
        _CVAR9.putInt(_CVAR10, _CVAR13);
        editor.apply();
    }
}