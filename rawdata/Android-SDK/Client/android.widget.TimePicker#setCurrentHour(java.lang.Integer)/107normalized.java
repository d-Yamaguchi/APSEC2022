@java.lang.Override
protected void onBindDialogView(android.view.View v) {
    super.onBindDialogView(v);
    android.widget.TimePicker _CVAR0 = picker;
    int _CVAR1 = lastHour;
    _CVAR0.setCurrentHour(_CVAR1);
    picker.setCurrentMinute(lastMinute);
}