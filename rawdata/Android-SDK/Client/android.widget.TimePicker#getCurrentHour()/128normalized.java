@java.lang.Override
public void onValueChange(android.widget.NumberPicker picker, int oldVal, int newVal) {
    android.widget.TimePicker _CVAR1 = mTimePicker;
    android.widget.TimePicker _CVAR0 = mTimePicker;
    java.lang.Integer _CVAR2 = _CVAR1.getCurrentHour();
    _CVAR0.setCurrentHour(_CVAR2);
    android.widget.TimePicker _CVAR4 = mTimePicker;
    android.widget.TimePicker _CVAR6 = mTimePicker;
    com.yuanding.schoolteacher.view.date.TimeFragment.TimeChangedListener _CVAR3 = mCallback;
    java.lang.Integer _CVAR5 = _CVAR4.getCurrentHour();
    java.lang.Integer _CVAR7 = _CVAR6.getCurrentMinute();
    /* if (picker.getValue() == 1)  // PM
    {
    if (mTimePicker.getCurrentHour() < 12)
    mTimePicker.setCurrentHour(mTimePicker.getCurrentHour() + 12);
    }
    else  // AM
    {
    if (mTimePicker.getCurrentHour() >= 12)
    mTimePicker.setCurrentHour(mTimePicker.getCurrentHour() - 12);
    }
     */
    _CVAR3.onTimeChanged(_CVAR5, _CVAR7);
}