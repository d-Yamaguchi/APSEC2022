private void setTimeInView(java.lang.String startTime, java.lang.String finishTime) {
    android.widget.TimePicker startTimeView;
    android.widget.TimePicker finishTimeView;
    java.lang.String[] splitTime;
    int hour;
    int min;
    if ((startTime != null) && (!startTime.equals(""))) {
        splitTime = startTime.split(":");
        startTimeView = ((android.widget.TimePicker) (findViewById(R.id.start_time_picker)));
        hour = java.lang.Integer.parseInt(splitTime[0]);
        min = java.lang.Integer.parseInt(splitTime[1]);
        android.widget.TimePicker _CVAR0 = startTimeView;
        int _CVAR1 = hour;
        _CVAR0.setCurrentHour(_CVAR1);
        startTimeView.setCurrentMinute(min);
    }
    if ((finishTime != null) && (!finishTime.equals(""))) {
        splitTime = finishTime.split(":");
        finishTimeView = ((android.widget.TimePicker) (findViewById(R.id.finish_time_picker)));
        hour = java.lang.Integer.parseInt(splitTime[0]);
        min = java.lang.Integer.parseInt(splitTime[1]);
        android.widget.TimePicker _CVAR2 = finishTimeView;
        int _CVAR3 = hour;
        _CVAR2.setCurrentHour(_CVAR3);
        finishTimeView.setCurrentMinute(min);
    }
}