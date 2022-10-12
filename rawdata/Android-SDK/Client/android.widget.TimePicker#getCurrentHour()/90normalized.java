public void sendWeekDayTimes(android.view.View view) {
    void _CVAR0 = R.id.timePicker;
    android.widget.TimePicker bedTimeWeekday = ((android.widget.TimePicker) (findViewById(_CVAR0)));
    android.widget.TimePicker _CVAR1 = bedTimeWeekday;
    int bedtimeH = _CVAR1.getCurrentHour();
    int bedtimeMin = bedTimeWeekday.getCurrentMinute();
    void _CVAR2 = R.id.timePicker2;
    android.widget.TimePicker awakeWeekday = ((android.widget.TimePicker) (findViewById(_CVAR2)));
    android.widget.TimePicker _CVAR3 = awakeWeekday;
    int awakeH = _CVAR3.getCurrentHour();
    int awakeMin = awakeWeekday.getCurrentMinute();
    // final String weekday = bedtimeH + " " + bedtimeMin + " " + awakeH + " " + awakeMin;
    final java.lang.String weekday = (((((java.lang.String.format("%02d", bedtimeH) + " ") + java.lang.String.format("%02d", bedtimeMin)) + " ") + java.lang.String.format("%02d", awakeH)) + " ") + java.lang.String.format("%02d", awakeMin);
    setContentView(R.layout.activity_sleep_weekend);
    void _CVAR4 = R.id.moveToWeek;
    android.widget.Button moveToMain = ((android.widget.Button) (findViewById(_CVAR4)));
    android.widget.Button _CVAR5 = moveToMain;
    android.view.View.OnClickListener _CVAR6 = new android.view.View.OnClickListener() {
        @java.lang.Override
        public void onClick(android.view.View v) {
            android.content.Intent intent = new android.content.Intent(getBaseContext(), com.example.carlos.exercisebuddy.MainActivity.class);
            android.widget.TimePicker bedTimeWeekend = ((android.widget.TimePicker) (findViewById(R.id.timePicker)));
            android.widget.TimePicker awakeWeekend = ((android.widget.TimePicker) (findViewById(R.id.timePicker2)));
            int weekendbedtimeH = bedTimeWeekend.getCurrentHour();
            int weekendbedtimeMin = bedTimeWeekend.getCurrentMinute();
            int weekendawakeH = awakeWeekend.getCurrentHour();
            int weekendawakeMin = awakeWeekend.getCurrentMinute();
            java.lang.String.format("%02d", weekendbedtimeH);
            // String weekend = weekendbedtimeH + " " + weekendbedtimeMin + " " + weekendawakeH + " " + weekendawakeMin;
            java.lang.String weekend = (((((java.lang.String.format("%02d", weekendbedtimeH) + " ") + java.lang.String.format("%02d", weekendbedtimeMin)) + " ") + java.lang.String.format("%02d", weekendawakeH)) + " ") + java.lang.String.format("%02d", weekendawakeMin);
            java.lang.String times = (weekday + " ") + weekend;
            android.util.Log.i("playing", times);
            intent.putExtra(com.example.carlos.exercisebuddy.SleepActivity.ACTIVITY_TIMES, times);
            startActivity(intent);
        }
    };
    _CVAR5.setOnClickListener(_CVAR6);
}