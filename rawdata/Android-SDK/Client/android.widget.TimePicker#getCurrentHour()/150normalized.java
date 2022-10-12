@java.lang.Override
protected void onDialogClosed(boolean positiveResult) {
    // TODO Auto-generated method stub
    if (positiveResult) {
        java.util.HashMap<java.lang.String, java.lang.Integer> map = new java.util.HashMap<java.lang.String, java.lang.Integer>();
        android.widget.TimePicker _CVAR2 = timePicker;
        java.util.HashMap<java.lang.String, java.lang.Integer> _CVAR0 = map;
        java.lang.String _CVAR1 = "hour";
        java.lang.Integer _CVAR3 = _CVAR2.getCurrentHour();
        _CVAR0.put(_CVAR1, _CVAR3);
        map.put("minute", timePicker.getCurrentMinute());
        java.lang.String ifAm = " 上午";
        int _CVAR4 = 11;
        boolean _CVAR5 = timePicker.getCurrentHour() > _CVAR4;
        if () {
            ifAm = " 下午";
        }
        int minute = map.get("minute");
        java.lang.String mi = java.lang.String.valueOf(minute);
        if (minute < 10) {
            mi = "0" + minute;
        }
        android.widget.TimePicker _CVAR6 = timePicker;
        java.lang.Integer _CVAR7 = _CVAR6.getCurrentHour();
        java.lang.String _CVAR8 = "" + _CVAR7;
        java.lang.String _CVAR9 = _CVAR8 + ":";
        java.lang.String _CVAR10 = _CVAR9 + mi;
        java.lang.String _CVAR11 = _CVAR10 + ifAm;
        this.persistString(_CVAR11);
        this.getOnPreferenceChangeListener().onPreferenceChange(this, map);
    }
    super.onDialogClosed(positiveResult);
}