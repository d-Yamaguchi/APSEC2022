/* (non-Javadoc)
@see android.preference.DialogPreference#onBindDialogView(android.view.View)
 */
@java.lang.Override
protected void onBindDialogView(android.view.View v) {
    super.onBindDialogView(v);
    android.widget.TimePicker _CVAR0 = mPicker;
    int _CVAR1 = mLastHour;
    _CVAR0.setCurrentHour(_CVAR1);
    mPicker.setCurrentMinute(mLastMinute);
}