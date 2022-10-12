@java.lang.Override
public void setCurrentMinute(java.lang.Integer currentMinute) {
    if ((currentMinute % ru.android.cyfral.servisnik.ui.ordercard.MyTimePicker.TIME_PICKER_MINUTE_INTERVAL) > 0) {
        if (cleanMinute == maxMinuteIndex()) {
            cleanMinute = 0;
            setCurrentHour(getCurrentHour() + 1);
        } else {
            cleanMinute++;
        }
    }
    int _CVAR1 = ru.android.cyfral.servisnik.ui.ordercard.MyTimePicker.TIME_PICKER_MINUTE_INTERVAL;
    int cleanMinute = currentMinute / _CVAR1;
    android.widget.TimePicker _CVAR0 = super;
    int _CVAR2 = cleanMinute;
    _CVAR0.setCurrentMinute(_CVAR2);
}