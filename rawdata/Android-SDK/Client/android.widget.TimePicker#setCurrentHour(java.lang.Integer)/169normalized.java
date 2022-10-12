private void LoadPreferences() {
    try {
        java.lang.String daysOfWeek = ray.droid.com.droidalarmclock.DroidPreferences.GetString(context, "DaysOfWeek");
        if (daysOfWeek.contains("1")) {
            chkDom.setChecked(true);
        }
        if (daysOfWeek.contains("2")) {
            chkSeg.setChecked(true);
        }
        if (daysOfWeek.contains("3")) {
            chkTer.setChecked(true);
        }
        if (daysOfWeek.contains("4")) {
            chkQua.setChecked(true);
        }
        if (daysOfWeek.contains("5")) {
            chkQui.setChecked(true);
        }
        if (daysOfWeek.contains("6")) {
            chkSex.setChecked(true);
        }
        if (daysOfWeek.contains("7")) {
            chkSab.setChecked(true);
        }
        int prefMinute = ray.droid.com.droidalarmclock.DroidPreferences.GetInteger(context, "timePickerMinute");
        android.content.Context _CVAR1 = context;
        java.lang.String _CVAR2 = "timePickerHour";
        int prefHour = ray.droid.com.droidalarmclock.DroidPreferences.GetInteger(_CVAR1, _CVAR2);
        android.widget.TimePicker _CVAR0 = timePicker;
        int _CVAR3 = prefHour;
        _CVAR0.setCurrentHour(_CVAR3);
        timePicker.setCurrentMinute(prefMinute);
    } catch (java.lang.Exception ex) {
        android.util.Log.d("DroidAlarmClock", "LoadPreferences - Erro: " + ex.getMessage());
    }
}