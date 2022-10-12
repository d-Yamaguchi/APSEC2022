private void initData() {
    timePickerStart.setIs24HourView(true);
    timePickerEnd.setIs24HourView(true);
    timePickerStart.setDescendantFocusability(android.widget.TimePicker.FOCUS_BLOCK_DESCENDANTS);
    timePickerEnd.setDescendantFocusability(android.widget.TimePicker.FOCUS_BLOCK_DESCENDANTS);
    setTimePickerDividerColor(timePickerStart);
    setTimePickerDividerColor(timePickerEnd);
    if (com.picky.timerangeselector.EmptyUtils.isNotEmpty(startTime) && com.picky.timerangeselector.EmptyUtils.isNotEmpty(endTime)) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            timePickerStart.setHour(java.lang.Integer.parseInt(startTime.substring(0, startTime.indexOf(":"))));
            timePickerStart.setMinute(java.lang.Integer.parseInt(startTime.substring(startTime.indexOf(":") + 1)));
            timePickerEnd.setHour(java.lang.Integer.parseInt(endTime.substring(0, endTime.indexOf(":"))));
            timePickerEnd.setMinute(java.lang.Integer.parseInt(endTime.substring(endTime.indexOf(":") + 1)));
        } else {
            java.lang.String _CVAR3 = startTime;
            java.lang.String _CVAR4 = ":";
            java.lang.String _CVAR1 = startTime;
            int _CVAR2 = 0;
            int _CVAR5 = _CVAR3.indexOf(_CVAR4);
            java.lang.String _CVAR6 = _CVAR1.substring(_CVAR2, _CVAR5);
            android.widget.TimePicker _CVAR0 = timePickerStart;
            int _CVAR7 = java.lang.Integer.parseInt(_CVAR6);
            _CVAR0.setCurrentHour(_CVAR7);
            timePickerStart.setCurrentMinute(java.lang.Integer.parseInt(startTime.substring(startTime.indexOf(":") + 1)));
            java.lang.String _CVAR11 = endTime;
            java.lang.String _CVAR12 = ":";
            java.lang.String _CVAR9 = endTime;
            int _CVAR10 = 0;
            int _CVAR13 = _CVAR11.indexOf(_CVAR12);
            java.lang.String _CVAR14 = _CVAR9.substring(_CVAR10, _CVAR13);
            android.widget.TimePicker _CVAR8 = timePickerEnd;
            int _CVAR15 = java.lang.Integer.parseInt(_CVAR14);
            _CVAR8.setCurrentHour(_CVAR15);
            timePickerEnd.setCurrentMinute(java.lang.Integer.parseInt(endTime.substring(endTime.indexOf(":") + 1)));
        }
    }
    setMin();
    setMax();
    timePickerStart.setOnTimeChangedListener(( timePicker, hourOfDay, minute) -> {
        if (java.lang.Integer.parseInt(minHour) < hourOfDay) {
            minStart.setMinValue(0);
        } else {
            minStart.setMinValue(java.lang.Integer.parseInt(minMinute));
        }
        java.lang.String h = (hourOfDay < 10) ? "0" + hourOfDay : "" + hourOfDay;
        java.lang.String m = (minute < 10) ? "0" + minute : "" + minute;
        startTime = (h + ":") + m;
    });
    timePickerEnd.setOnTimeChangedListener(( timePicker, hourOfDay, minute) -> {
        if (java.lang.Integer.parseInt(maxHour) > hourOfDay) {
            minEnd.setMaxValue(59);
        } else {
            minEnd.setMaxValue(java.lang.Integer.parseInt(maxMinutes));
        }
        java.lang.String h = (hourOfDay < 10) ? "0" + hourOfDay : "" + hourOfDay;
        java.lang.String m = (minute < 10) ? "0" + minute : "" + minute;
        endTime = (h + ":") + m;
    });
}