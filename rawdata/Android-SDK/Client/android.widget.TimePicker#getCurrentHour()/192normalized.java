@java.lang.Override
protected void onDialogClosed(boolean positiveResult) {
    super.onDialogClosed(positiveResult);
    if (positiveResult) {
        android.widget.TimePicker _CVAR0 = timePicker;
        int h24Format = _CVAR0.getCurrentHour();
        android.widget.TimePicker _CVAR1 = timePicker;
        java.lang.Integer _CVAR2 = _CVAR1.getCurrentHour();
        lastHour = _CVAR2;
        lastMinute = timePicker.getCurrentMinute();
        java.lang.String suffix = " AM";
        isAm = true;
        int _CVAR3 = 0;
        boolean _CVAR4 = timePicker.getCurrentHour() == _CVAR3;
        if () {
            lastHour = 12;
        } else {
            int _CVAR5 = 12;
            boolean _CVAR6 = timePicker.getCurrentHour() == _CVAR5;
            if () {
                suffix = " PM";
                isAm = false;
                h24Format = 12;
            } else {
                int _CVAR7 = 12;
                boolean _CVAR8 = timePicker.getCurrentHour() > _CVAR7;
                if () {
                    suffix = " PM";
                    lastHour -= 12;
                    isAm = false;
                }
            }
        }
        java.lang.String time = ((java.lang.String.valueOf(h24Format) + ":") + java.lang.String.format("%02d", lastMinute)) + suffix;
        if (callChangeListener(time)) {
            persistString(time);
        }
    }
}