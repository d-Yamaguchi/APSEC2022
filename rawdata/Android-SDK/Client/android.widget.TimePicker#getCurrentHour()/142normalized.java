private java.lang.String timeToString(android.widget.TimePicker tp) {
    android.widget.TimePicker _CVAR0 = tp;
    int currHour = _CVAR0.getCurrentHour();
    int currMinute = tp.getCurrentMinute();
    if (currMinute < 40) {
        currMinute += 20;
    } else {
        int diff = 60 - currMinute;
        currHour += 1;
        currMinute = 20 - diff;
    }
    java.lang.String currMinuteS = java.lang.Integer.toString(currMinute);
    if (currHour > 12) {
        currHour -= 12;
    }
    if (currMinuteS.length() < 2) {
        currMinuteS = "0" + currMinuteS;
    }
    return (java.lang.Integer.toString(currHour) + ":") + currMinuteS;
}