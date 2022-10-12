private void initData() {
    timePickerStart.setIs24HourView(true);
    timePickerEnd.setIs24HourView(true);
    timePickerStart.setDescendantFocusability(android.widget.TimePicker.FOCUS_BLOCK_DESCENDANTS);
    timePickerEnd.setDescendantFocusability(android.widget.TimePicker.FOCUS_BLOCK_DESCENDANTS);
    setTimePickerDividerColor(timePickerStart);
    setTimePickerDividerColor(timePickerEnd);
    if ((!com.android.cy.CommonUtils.isNull(startTime)) && (!com.android.cy.CommonUtils.isNull(endTime))) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            timePickerStart.setHour(java.lang.Integer.parseInt(startTime.substring(0, startTime.indexOf(":"))));
            timePickerStart.setMinute(java.lang.Integer.parseInt(startTime.substring(startTime.indexOf(":") + 1)));
            timePickerEnd.setHour(java.lang.Integer.parseInt(endTime.substring(0, endTime.indexOf(":"))));
            timePickerEnd.setMinute(java.lang.Integer.parseInt(endTime.substring(endTime.indexOf(":") + 1)));
        } else {
            timePickerStart.setCurrentHour(java.lang.Integer.parseInt(startTime.substring(0, startTime.indexOf(":"))));
            int _CVAR2 = 1;
            java.lang.String _CVAR1 = startTime;
            int _CVAR3 = startTime.indexOf(":") + _CVAR2;
            java.lang.String _CVAR4 = _CVAR1.substring(_CVAR3);
            android.widget.TimePicker _CVAR0 = timePickerStart;
            int _CVAR5 = java.lang.Integer.parseInt(_CVAR4);
            _CVAR0.setCurrentMinute(_CVAR5);
            timePickerEnd.setCurrentHour(java.lang.Integer.parseInt(endTime.substring(0, endTime.indexOf(":"))));
            int _CVAR8 = 1;
            java.lang.String _CVAR7 = endTime;
            int _CVAR9 = endTime.indexOf(":") + _CVAR8;
            java.lang.String _CVAR10 = _CVAR7.substring(_CVAR9);
            android.widget.TimePicker _CVAR6 = timePickerEnd;
            int _CVAR11 = java.lang.Integer.parseInt(_CVAR10);
            _CVAR6.setCurrentMinute(_CVAR11);
        }
    }
    timePickerStart.setOnTimeChangedListener(( timePicker, hourOfDay, minute) -> {
        java.lang.String h = (hourOfDay < 10) ? "0" + hourOfDay : "" + hourOfDay;
        java.lang.String m = (minute < 10) ? "0" + minute : "" + minute;
        startTime = (h + ":") + m;
    });
    timePickerEnd.setOnTimeChangedListener(( timePicker, hourOfDay, minute) -> {
        java.lang.String h = (hourOfDay < 10) ? "0" + hourOfDay : "" + hourOfDay;
        java.lang.String m = (minute < 10) ? "0" + minute : "" + minute;
        endTime = (h + ":") + m;
    });
}