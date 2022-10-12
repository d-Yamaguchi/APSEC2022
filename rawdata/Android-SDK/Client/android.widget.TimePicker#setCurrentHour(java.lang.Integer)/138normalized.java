/* (non-Javadoc)

@see android.preference.DialogPreference#onCreateDialogView()
 */
@java.lang.Override
protected android.view.View onCreateDialogView() {
    tp.setOnTimeChangedListener(this);
    // Added by Panthro to avoid AM - PM controls
    tp.setIs24HourView(true);
    int m = getMinute();
    android.content.Context _CVAR0 = getContext();
    android.widget.TimePicker tp = new android.widget.TimePicker(_CVAR0);
    int h = getHour();
    if ((h >= 0) && (m >= 0)) {
        android.widget.TimePicker _CVAR1 = tp;
        int _CVAR2 = h;
        _CVAR1.setCurrentHour(_CVAR2);
        tp.setCurrentMinute(m);
    }
    return tp;
}