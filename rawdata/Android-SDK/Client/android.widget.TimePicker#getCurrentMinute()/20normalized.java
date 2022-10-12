@java.lang.Override
public void onTimeChanged(android.widget.TimePicker timePicker, int i, int i1) {
    // Have this minute variable because getCurrentMinute doesnt have "01", its only "1"
    java.lang.String minute = null;
    boolean apoveAPI23 = false;
    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
        minute = java.lang.String.valueOf(timePicker.getMinute());
        apoveAPI23 = true;
    } else {
        android.widget.TimePicker _CVAR0 = timePicker;
        java.lang.Integer _CVAR1 = _CVAR0.getCurrentMinute();
        java.lang.String _CVAR2 = java.lang.String.valueOf(_CVAR1);
        minute = _CVAR2;
    }
    if (apoveAPI23) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            if (timePicker.getMinute() < 10) {
                android.widget.TimePicker _CVAR3 = timePicker;
                java.lang.Integer _CVAR4 = _CVAR3.getCurrentMinute();
                java.lang.String _CVAR5 = java.lang.String.valueOf(_CVAR4);
                java.lang.String _CVAR6 = "0" + _CVAR5;
                minute = _CVAR6;
            }
        }
    } else {
        android.widget.TimePicker _CVAR7 = timePicker;
        java.lang.Integer _CVAR8 = _CVAR7.getCurrentMinute();
        java.lang.String _CVAR9 = java.lang.String.valueOf(_CVAR8);
        java.lang.String _CVAR10 = "0" + _CVAR9;
        minute = _CVAR10;
    }
    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
        android.util.Log.d("timePicker", (("hour = " + java.lang.String.valueOf(timePicker.getHour())) + " minute = ") + java.lang.String.valueOf(i1));
    } else {
        java.lang.String amOrPm = "PM";
        if (apoveAPI23) {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                if (timePicker.getHour() < 12) {
                    amOrPm = "AM";
                }
            }
        } else {
            if (timePicker.getCurrentHour() < 12) {
                amOrPm = "AM";
            }
            android.util.Log.d("timePicker", (((("hour = " + java.lang.String.valueOf(timePicker.getCurrentHour())) + "minute = ") + minute) + " ") + amOrPm);
        }
    }
}