@android.support.annotation.RequiresApi(api = android.os.Build.VERSION_CODES.M)
@java.lang.Override
public void onClick(android.view.View view) {
    if (android.os.Build.VERSION.SDK_INT > android.os.Build.VERSION_CODES.LOLLIPOP_MR1) {
        int newMinute = timePicker.getMinute() + 5;
        if (newMinute > 59) {
            timePicker.setHour(timePicker.getHour() + 1);
        }
        timePicker.setMinute(newMinute);
    } else {
        int _CVAR0 = 5;
        int newMinute = timePicker.getCurrentMinute() + _CVAR0;
        if (newMinute > 59) {
            timePicker.setCurrentHour(timePicker.getCurrentHour() + 1);
        }
        timePicker.setCurrentMinute(newMinute);
    }
}