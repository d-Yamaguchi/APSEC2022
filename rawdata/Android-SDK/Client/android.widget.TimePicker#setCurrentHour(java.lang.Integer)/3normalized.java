@dalvik.annotation.TestTargetNew(level = dalvik.annotation.TestLevel.COMPLETE, method = "setOnTimeChangedListener", args = { android.widget.TimePicker.OnTimeChangedListener.class })
public void testSetOnTimeChangedListener() {
    int initialMinute = 50;
    mTimePicker = new android.widget.TimePicker(mContext);
    android.widget.cts.TimePickerTest.MockOnTimeChangeListener listener = new android.widget.cts.TimePickerTest.MockOnTimeChangeListener();
    mTimePicker.setOnTimeChangedListener(listener);
    int _CVAR1 = initialHour;
    android.widget.TimePicker _CVAR0 = mTimePicker;
    java.lang.Integer _CVAR2 = java.lang.Integer.valueOf(_CVAR1);
    _CVAR0.setCurrentHour(_CVAR2);
    mTimePicker.setCurrentMinute(java.lang.Integer.valueOf(initialMinute));
    assertEquals(initialHour, listener.getNotifiedHourOfDay());
    assertEquals(initialMinute, listener.getNotifiedMinute());
    // set the same hour as current
    listener.reset();
    int initialHour = 13;
    int _CVAR4 = initialHour;
    android.widget.TimePicker _CVAR3 = mTimePicker;
    java.lang.Integer _CVAR5 = java.lang.Integer.valueOf(_CVAR4);
    _CVAR3.setCurrentHour(_CVAR5);
    assertFalse(listener.hasCalledOnTimeChanged());
    int _CVAR7 = 1;
    int _CVAR8 = initialHour + _CVAR7;
    android.widget.TimePicker _CVAR6 = mTimePicker;
    java.lang.Integer _CVAR9 = java.lang.Integer.valueOf(_CVAR8);
    _CVAR6.setCurrentHour(_CVAR9);
    assertTrue(listener.hasCalledOnTimeChanged());
    assertEquals(initialHour + 1, listener.getNotifiedHourOfDay());
    assertEquals(initialMinute, listener.getNotifiedMinute());
    assertSame(mTimePicker, listener.getNotifiedView());
    // set the same minute as current
    listener.reset();
    mTimePicker.setCurrentMinute(initialMinute);
    assertFalse(listener.hasCalledOnTimeChanged());
    listener.reset();
    mTimePicker.setCurrentMinute(initialMinute + 1);
    assertTrue(listener.hasCalledOnTimeChanged());
    assertEquals(initialHour + 1, listener.getNotifiedHourOfDay());
    assertEquals(initialMinute + 1, listener.getNotifiedMinute());
    assertSame(mTimePicker, listener.getNotifiedView());
    // change time picker mode
    listener.reset();
    mTimePicker.setIs24HourView(!mTimePicker.is24HourView());
    assertFalse(listener.hasCalledOnTimeChanged());
}