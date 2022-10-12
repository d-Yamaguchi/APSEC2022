public void confirmTime(android.view.View view) {
    android.widget.TimePicker timePicker;
    android.widget.TextView timeTextField;
    int hour;
    int minute;
    java.lang.String AMorPM;
    timeTextField = ((android.widget.TextView) (findViewById(R.id.timeTextField)));
    timePicker = ((android.widget.TimePicker) (findViewById(R.id.timePicker)));
    if (android.os.Build.VERSION.SDK_INT >= 23) {
        int tempHour = timePicker.getHour();
        if (timePicker.getHour() >= 12) {
            tempHour = tempHour - 12;
        } else if ((timePicker.getHour() == 12) || (timePicker.getHour() == 0)) {
            tempHour = 12;
        }
        if (timePicker.getHour() >= 12) {
            AMorPM = "PM";
        } else {
            AMorPM = "AM";
        }
        hour = tempHour;
        minute = timePicker.getMinute();
    } else {
        android.widget.TimePicker _CVAR0 = timePicker;
        int tempHour = _CVAR0.getCurrentHour();
        int _CVAR1 = 12;
        boolean _CVAR2 = timePicker.getCurrentHour() > _CVAR1;
        if () {
            tempHour = tempHour - 12;
        } else {
            int _CVAR3 = 12;
            boolean _CVAR4 = timePicker.getCurrentHour() == _CVAR3;
            int _CVAR6 = 0;
            boolean _CVAR7 = timePicker.getCurrentHour() == _CVAR6;
            boolean _CVAR5 = _CVAR4 || _CVAR7;
            if () {
                tempHour = 12;
            }
        }
        int _CVAR8 = 12;
        boolean _CVAR9 = timePicker.getCurrentHour() >= _CVAR8;
        if () {
            AMorPM = "PM";
        } else {
            AMorPM = "AM";
        }
        hour = tempHour;
        minute = timePicker.getCurrentMinute();
    }
    timeTextField.setText(java.lang.String.format("%02d:%02d %s", hour, minute, AMorPM));
    // timeTextField.setText(hour + ":"+ minute+" "+AMorPM);
    getSupportFragmentManager().popBackStack();
}