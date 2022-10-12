public void testAccessCurrentHour() {
    mTimePicker = new android.widget.TimePicker(mContext);
    // AM/PM mode
    mTimePicker.setIs24HourView(false);
    mTimePicker.setCurrentHour(0);
    int _CVAR0 = 0;
    android.widget.TimePicker _CVAR2 = mTimePicker;
    java.lang.Integer _CVAR1 = java.lang.Integer.valueOf(_CVAR0);
    java.lang.Integer _CVAR3 = _CVAR2.getCurrentHour();
    assertEquals(_CVAR1, _CVAR3);
    mTimePicker.setCurrentHour(12);
    int _CVAR4 = 12;
    android.widget.TimePicker _CVAR6 = mTimePicker;
    java.lang.Integer _CVAR5 = java.lang.Integer.valueOf(_CVAR4);
    java.lang.Integer _CVAR7 = _CVAR6.getCurrentHour();
    assertEquals(_CVAR5, _CVAR7);
    mTimePicker.setCurrentHour(13);
    int _CVAR8 = 13;
    android.widget.TimePicker _CVAR10 = mTimePicker;
    java.lang.Integer _CVAR9 = java.lang.Integer.valueOf(_CVAR8);
    java.lang.Integer _CVAR11 = _CVAR10.getCurrentHour();
    assertEquals(_CVAR9, _CVAR11);
    mTimePicker.setCurrentHour(23);
    int _CVAR12 = 23;
    android.widget.TimePicker _CVAR14 = mTimePicker;
    java.lang.Integer _CVAR13 = java.lang.Integer.valueOf(_CVAR12);
    java.lang.Integer _CVAR15 = _CVAR14.getCurrentHour();
    assertEquals(_CVAR13, _CVAR15);
    // for 24 hour mode
    mTimePicker.setIs24HourView(true);
    mTimePicker.setCurrentHour(0);
    int _CVAR16 = 0;
    android.widget.TimePicker _CVAR18 = mTimePicker;
    java.lang.Integer _CVAR17 = java.lang.Integer.valueOf(_CVAR16);
    java.lang.Integer _CVAR19 = _CVAR18.getCurrentHour();
    assertEquals(_CVAR17, _CVAR19);
    mTimePicker.setCurrentHour(13);
    int _CVAR20 = 13;
    android.widget.TimePicker _CVAR22 = mTimePicker;
    java.lang.Integer _CVAR21 = java.lang.Integer.valueOf(_CVAR20);
    java.lang.Integer _CVAR23 = _CVAR22.getCurrentHour();
    assertEquals(_CVAR21, _CVAR23);
    mTimePicker.setCurrentHour(23);
    int _CVAR24 = 23;
    android.widget.TimePicker _CVAR26 = mTimePicker;
    java.lang.Integer _CVAR25 = java.lang.Integer.valueOf(_CVAR24);
    java.lang.Integer _CVAR27 = _CVAR26.getCurrentHour();
    assertEquals(_CVAR25, _CVAR27);
}