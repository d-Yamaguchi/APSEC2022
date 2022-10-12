/* (non-Javadoc)

@see android.preference.DialogPreference#onCreateDialogView()
 */
@java.lang.Override
protected android.view.View onCreateDialogView() {
    tp.setOnTimeChangedListener(this);
    int h = getHour();
    android.content.Context _CVAR0 = getContext();
    android.widget.TimePicker tp = new android.widget.TimePicker(_CVAR0);
    int m = getMinute();
    if ((h >= 0) && (m >= 0)) {
        tp.setCurrentHour(h);
        android.widget.TimePicker _CVAR1 = tp;
        int _CVAR2 = m;
        _CVAR1.setCurrentMinute(_CVAR2);
    }
    return tp;
}