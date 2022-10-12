@java.lang.Override
public void onClick(android.view.View v) {
    int hour1 = picker1.getCurrentHour();
    android.widget.TimePicker _CVAR0 = picker1;
    int minute1 = _CVAR0.getCurrentMinute();
    int hour2 = picker2.getCurrentHour();
    android.widget.TimePicker _CVAR1 = picker2;
    int minute2 = _CVAR1.getCurrentMinute();
    int hour3 = picker3.getCurrentHour();
    android.widget.TimePicker _CVAR2 = picker3;
    int minute3 = _CVAR2.getCurrentMinute();
    time1 = (hour1 * 60) + minute1;
    time2 = (hour2 * 60) + minute2;
    time3 = (hour3 * 60) + minute3;
    android.widget.Toast.makeText(getApplicationContext(), ((((("1:(" + time1) + " mins), 2:(") + time2) + " mins), 3:(") + time3) + "mins)", android.widget.Toast.LENGTH_LONG).show();
}