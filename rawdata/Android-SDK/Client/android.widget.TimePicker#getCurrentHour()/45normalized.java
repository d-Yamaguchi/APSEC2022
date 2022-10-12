@java.lang.Override
public void onValueChange(android.widget.NumberPicker picker, int oldVal, int newVal) {
    int _CVAR0 = 12;
    int _CVAR1 = timePicker.getCurrentHour() + _CVAR0;
    int hourOfDay = _CVAR1 % 24;
    timePicker.setCurrentHour(hourOfDay);
}