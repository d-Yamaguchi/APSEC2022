/* (non-Javadoc)

@see android.preference.DialogPreference#onCreateDialogView()
 */
@java.lang.Override
protected android.view.View onCreateDialogView() {
    android.content.SharedPreferences prefs = android.preference.PreferenceManager.getDefaultSharedPreferences(getContext());
    boolean is24Hour = prefs.getBoolean(getContext().getString(R.string.prefTwelveTwentyfour), false);
    tp.setIs24HourView(is24Hour);
    tp.setOnTimeChangedListener(this);
    android.util.Log.d("TimePicker", "Current default =" + getPersistedString(this.defaultValue));
    int m = getMinute();
    android.content.Context _CVAR0 = getContext();
    android.widget.TimePicker tp = new android.widget.TimePicker(_CVAR0);
    int h = getHour();
    if ((h >= 0) && (h < 24)) {
        android.widget.TimePicker _CVAR1 = tp;
        int _CVAR2 = h;
        _CVAR1.setCurrentHour(_CVAR2);
    }
    if ((m >= 0) && (m < 60)) {
        tp.setCurrentMinute(m);
    }
    return tp;
}