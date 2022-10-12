/* (non-Javadoc)

@see android.preference.DialogPreference#onCreateDialogView()
 */
@java.lang.Override
protected android.view.View onCreateDialogView() {
    tp = new android.widget.TimePicker(getContext());
    int m = getMinute();
    // tp.setOnTimeChangedListener(this);
    int h = getHour();
    if ((h >= 0) && (m >= 0)) {
        android.widget.TimePicker _CVAR0 = tp;
        int _CVAR1 = h;
        _CVAR0.setCurrentHour(_CVAR1);
        tp.setCurrentMinute(m);
    }
    return tp;
}