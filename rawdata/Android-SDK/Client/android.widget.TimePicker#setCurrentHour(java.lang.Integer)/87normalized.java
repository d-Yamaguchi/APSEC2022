@java.lang.Override
public void setCurrentMinute(java.lang.Integer currentMinute) {
    int cleanMinute = currentMinute / com.esquel.epass.ui.QuarterTimePicker.TIME_PICKER_MINUTE_INTERVAL;
    if ((currentMinute % com.esquel.epass.ui.QuarterTimePicker.TIME_PICKER_MINUTE_INTERVAL) > 0) {
        if (cleanMinute == maxMinuteIndex()) {
            cleanMinute = 0;
            int _CVAR0 = 1;
            int _CVAR1 = getCurrentHour() + _CVAR0;
            setCurrentHour(_CVAR1);
        } else {
            cleanMinute++;
        }
    }
    super.setCurrentMinute(cleanMinute);
}