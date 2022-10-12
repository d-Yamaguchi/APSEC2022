@java.lang.Override
public void onClick(android.view.View v) {
    // switch (v.getId()) {
    // case R.id.start_button:
    long difference = 0;
    int pickedTime = timePicker.getCurrentHour();
    int currentTime = new java.sql.Time(java.lang.System.currentTimeMillis()).getHours();
    if (pickedTime < currentTime) {
        difference = ((long) (currentTime - pickedTime)) * 3600000;
        android.widget.TimePicker _CVAR0 = timePicker;
        java.lang.Integer _CVAR1 = _CVAR0.getCurrentMinute();
        pickedTime = _CVAR1;
        currentTime = new java.sql.Time(java.lang.System.currentTimeMillis()).getMinutes();
        difference += ((long) (currentTime - pickedTime)) * 60000;
    } else if (pickedTime == currentTime) {
        android.widget.TimePicker _CVAR2 = timePicker;
        java.lang.Integer _CVAR3 = _CVAR2.getCurrentMinute();
        pickedTime = _CVAR3;
        currentTime = new java.sql.Time(java.lang.System.currentTimeMillis()).getMinutes();
        if (pickedTime <= currentTime) {
            difference = ((long) (currentTime - pickedTime)) * 60000;
        } else {
            difference = ((long) ((24 * 3600000) - ((pickedTime - currentTime) * 60000)));
        }
    } else {
        difference = ((long) (((24 - pickedTime) * 3600000) + (currentTime * 3600000)));
        android.widget.TimePicker _CVAR4 = timePicker;
        java.lang.Integer _CVAR5 = _CVAR4.getCurrentMinute();
        pickedTime = _CVAR5;
        currentTime = new java.sql.Time(java.lang.System.currentTimeMillis()).getMinutes();
        difference += ((long) (currentTime - pickedTime)) * 60000;
    }
    difference += new java.sql.Time(java.lang.System.currentTimeMillis()).getSeconds();
    chronometer.setBase(android.os.SystemClock.elapsedRealtime() - difference);
    chronometer.start();
    // break;
    // case R.id.stop_button:
    // chronometer.stop();
    // break;
}