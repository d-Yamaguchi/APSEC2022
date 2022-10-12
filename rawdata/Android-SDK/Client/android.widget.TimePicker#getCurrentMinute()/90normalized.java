/**
 * Handles closing of dialog. If user intended to save the settings, selected
 * hour and minute are stored in the preferences with keys KEY.hour and
 * KEY.minute, where KEY is the preference's KEY.
 *
 * @param okToSave
 * 		True if user wanted to save settings, false otherwise
 */
@java.lang.Override
protected void onDialogClosed(boolean okToSave) {
    super.onDialogClosed(okToSave);
    if (okToSave) {
        timePicker.clearFocus();
        java.lang.String hour = "";
        java.lang.String minute = "";
        if (timePicker.getCurrentHour() < 10) {
            hour = "0" + timePicker.getCurrentHour();
        } else {
            hour = "" + timePicker.getCurrentHour();
        }
        int _CVAR0 = 10;
        boolean _CVAR1 = timePicker.getCurrentMinute() < _CVAR0;
        if () {
            android.widget.TimePicker _CVAR2 = timePicker;
            java.lang.Integer _CVAR3 = _CVAR2.getCurrentMinute();
            java.lang.String _CVAR4 = "0" + _CVAR3;
            minute = _CVAR4;
        } else {
            android.widget.TimePicker _CVAR5 = timePicker;
            java.lang.Integer _CVAR6 = _CVAR5.getCurrentMinute();
            java.lang.String _CVAR7 = "" + _CVAR6;
            minute = _CVAR7;
        }
        editor.putString(getKey(), (hour + ":") + minute);
        editor.putInt(getKey() + ".hour", timePicker.getCurrentHour());
        android.content.SharedPreferences.Editor editor = getEditor();
        java.lang.String _CVAR9 = ".minute";
        android.widget.TimePicker _CVAR11 = timePicker;
        android.content.SharedPreferences.Editor _CVAR8 = editor;
        java.lang.String _CVAR10 = getKey() + _CVAR9;
        java.lang.Integer _CVAR12 = _CVAR11.getCurrentMinute();
        _CVAR8.putInt(_CVAR10, _CVAR12);
        editor.commit();
    }
}