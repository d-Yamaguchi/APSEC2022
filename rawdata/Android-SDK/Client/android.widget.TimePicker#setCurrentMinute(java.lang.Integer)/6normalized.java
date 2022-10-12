@dalvik.annotation.TestTargetNew(level = dalvik.annotation.TestLevel.COMPLETE, method = "setOnTimeChangedListener", args = { android.widget.TimePicker.OnTimeChangedListener.class })
public void testSetOnTimeChangedListener() {
    int initialHour = 13;
    mTimePicker = new android.widget.TimePicker(mContext);
    android.widget.cts.TimePickerTest.MockOnTimeChangeListener listener = new android.widget.cts.TimePickerTest.MockOnTimeChangeListener();
    mTimePicker.setOnTimeChangedListener(listener);
    mTimePicker.setCurrentHour(java.lang.Integer.valueOf(initialHour));
    int _CVAR1 = initialMinute;
    android.widget.TimePicker _CVAR0 = mTimePicker;
    java.lang.Integer _CVAR2 = java.lang.Integer.valueOf(_CVAR1);
    _CVAR0.setCurrentMinute(_CVAR2);
    assertEquals(initialHour, listener.getNotifiedHourOfDay());
    assertEquals(initialMinute, listener.getNotifiedMinute());
    // set the same hour as current
    listener.reset();
    mTimePicker.setCurrentHour(java.lang.Integer.valueOf(initialHour));
    assertFalse(listener.hasCalledOnTimeChanged());
    mTimePicker.setCurrentHour(java.lang.Integer.valueOf(initialHour + 1));
    assertTrue(listener.hasCalledOnTimeChanged());
    assertEquals(initialHour + 1, listener.getNotifiedHourOfDay());
    assertEquals(initialMinute, listener.getNotifiedMinute());
    assertSame(mTimePicker, listener.getNotifiedView());
    // set the same minute as current
    listener.reset();
    int initialMinute = 50;
    android.widget.TimePicker _CVAR3 = mTimePicker;
    int _CVAR4 = initialMinute;
    _CVAR3.setCurrentMinute(_CVAR4);
    assertFalse(listener.hasCalledOnTimeChanged());
    listener.reset();
    int _CVAR6 = 1;
    android.widget.TimePicker _CVAR5 = mTimePicker;
    int _CVAR7 = initialMinute + _CVAR6;
    _CVAR5.setCurrentMinute(_CVAR7);
    assertTrue(listener.hasCalledOnTimeChanged());
    assertEquals(initialHour + 1, listener.getNotifiedHourOfDay());
    assertEquals(initialMinute + 1, listener.getNotifiedMinute());
    assertSame(mTimePicker, listener.getNotifiedView());
    // change time picker mode
    listener.reset();
    mTimePicker.setIs24HourView(!mTimePicker.is24HourView());
    assertFalse(listener.hasCalledOnTimeChanged());
}