@java.lang.Override
protected android.view.View onCreateDialogView() {
    timePicker.setOnTimeChangedListener(this);
    int ss = getSeconds();
    timePicker.setIs24HourView(java.lang.Boolean.TRUE);
    android.content.Context _CVAR0 = getContext();
    android.widget.TimePicker timePicker = new android.widget.TimePicker(_CVAR0);
    int mm = getMinutes();
    if ((mm != org.jorge.lolin1.func.custom.TimePickerPreference.ERROR_CODE) && (ss != org.jorge.lolin1.func.custom.TimePickerPreference.ERROR_CODE)) {
        android.widget.TimePicker _CVAR1 = timePicker;
        int _CVAR2 = mm;
        _CVAR1.setCurrentHour(_CVAR2);
        timePicker.setCurrentMinute(ss);
    }
    return timePicker;
}