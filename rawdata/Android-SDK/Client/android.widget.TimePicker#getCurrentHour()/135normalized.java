@java.lang.Override
public void onClick(android.view.View v) {
    android.widget.TimePicker _CVAR0 = picker1;
    int hour1 = _CVAR0.getCurrentHour();
    int minute1 = picker1.getCurrentMinute();
    android.widget.TimePicker _CVAR1 = picker2;
    int hour2 = _CVAR1.getCurrentHour();
    int minute2 = picker2.getCurrentMinute();
    android.widget.TimePicker _CVAR2 = picker3;
    int hour3 = _CVAR2.getCurrentHour();
    int minute3 = picker3.getCurrentMinute();
    time1 = (hour1 * 60) + minute1;
    time2 = (hour2 * 60) + minute2;
    time3 = (hour3 * 60) + minute3;
    android.widget.Toast.makeText(getApplicationContext(), ((((("1:(" + time1) + " mins), 2:(") + time2) + " mins), 3:(") + time3) + "mins)", android.widget.Toast.LENGTH_LONG).show();
}