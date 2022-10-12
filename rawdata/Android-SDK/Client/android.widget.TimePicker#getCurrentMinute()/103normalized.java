@java.lang.Override
public void onClick(android.view.View view) {
    editor.putInt(com.antol.batterymanager.MainActivity.SLEEP_START_HOUR, timePicker.getCurrentHour());
    java.lang.String _CVAR0 = com.antol.batterymanager.MainActivity.TAG;
    int _CVAR1 = android.app.Activity.MODE_PRIVATE;
    // Fill out existing times
    final android.content.SharedPreferences sp = getSharedPreferences(_CVAR0, _CVAR1);
    android.content.SharedPreferences _CVAR2 = sp;
    final android.content.SharedPreferences.Editor editor = _CVAR2.edit();
    android.widget.TimePicker _CVAR6 = timePicker;
    android.content.SharedPreferences.Editor _CVAR3 = editor;
    java.lang.String _CVAR4 = com.antol.batterymanager.MainActivity.SLEEP_START_MIN;
    java.lang.Integer _CVAR7 = _CVAR6.getCurrentMinute();
    _CVAR3.putInt(_CVAR4, _CVAR7);
    editor.commit();
    void _CVAR8 = R.id.sleep_start_text;
    final android.widget.TextView sleepStartTv = ((android.widget.TextView) (findViewById(_CVAR8)));
    void _CVAR5 = R.id.timePicker;
    void _CVAR10 = _CVAR5;
    final android.widget.TimePicker timePicker = ((android.widget.TimePicker) (findViewById(_CVAR10)));
    android.widget.TimePicker _CVAR11 = timePicker;
    void _CVAR13 = _CVAR10;
    final android.widget.TimePicker timePicker = ((android.widget.TimePicker) (findViewById(_CVAR13)));
    android.widget.TimePicker _CVAR14 = timePicker;
    android.widget.TextView _CVAR9 = sleepStartTv;
    java.lang.Integer _CVAR12 = _CVAR11.getCurrentHour();
    java.lang.Integer _CVAR15 = _CVAR14.getCurrentMinute();
    updateTimeTextView(_CVAR9, _CVAR12, _CVAR15);
}