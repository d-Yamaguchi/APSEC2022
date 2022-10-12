@java.lang.Override
public void onTimeChanged(android.widget.TimePicker view, int hourOfDay, int minute) {
    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
        nhour = timepicker1.getHour();
        nMinute = timepicker1.getMinute();
    } else {
        nhour = timepicker1.getCurrentHour();
        android.widget.TimePicker _CVAR0 = timepicker1;
        java.lang.Integer _CVAR1 = _CVAR0.getCurrentMinute();
        nMinute = _CVAR1;
    }
    android.util.Log.e(com.daeseong.timepicker_test.TimePickerDialogEx.TAG, (("nhour:" + nhour) + " nMinute:") + nMinute);
    android.util.Log.e(com.daeseong.timepicker_test.TimePickerDialogEx.TAG, (("hourOfDay:" + hourOfDay) + " minute:") + minute);
}