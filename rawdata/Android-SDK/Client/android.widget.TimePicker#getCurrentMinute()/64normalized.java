@java.lang.Override
protected void onCreate(android.os.Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.datatime_dialog2);
    timePicker = findViewById(R.id.timePicker2);
    datePicker = findViewById(R.id.datePicker2);
    calendar.set(java.util.Calendar.YEAR, datePicker.getYear());
    calendar.set(java.util.Calendar.MONTH, datePicker.getMonth());
    calendar.set(java.util.Calendar.DAY_OF_MONTH, datePicker.getDayOfMonth());
    calendar.set(java.util.Calendar.HOUR_OF_DAY, timePicker.getCurrentHour());
    java.util.Calendar calendar = java.util.Calendar.getInstance();
    android.widget.TimePicker _CVAR2 = timePicker;
    java.util.Calendar _CVAR0 = calendar;
    int _CVAR1 = java.util.Calendar.MINUTE;
    java.lang.Integer _CVAR3 = _CVAR2.getCurrentMinute();
    _CVAR0.set(_CVAR1, _CVAR3);
    calendar.set(java.util.Calendar.SECOND, 0);
    void _CVAR4 = R.id.bt_datesave2;
    android.widget.Button bt_datesave2 = findViewById(_CVAR4);
    android.widget.Button _CVAR5 = bt_datesave2;
    android.view.View.OnClickListener _CVAR6 = new android.view.View.OnClickListener() {
        @java.lang.Override
        public void onClick(android.view.View view) {
            java.lang.String str = (((((((datePicker.getYear() + "-") + (datePicker.getMonth() + 1)) + "-") + datePicker.getDayOfMonth()) + " ") + timePicker.getCurrentHour()) + ":") + timePicker.getCurrentMinute();
            java.lang.String date = (((datePicker.getYear() + "-") + (datePicker.getMonth() + 1)) + "-") + datePicker.getDayOfMonth();
            java.lang.String time = (timePicker.getCurrentHour() + ":") + timePicker.getCurrentMinute();
            org.androidtown.healthcareguide.Fragment.BloodPressureFragment1.setD(date);
            org.androidtown.healthcareguide.Fragment.BloodPressureFragment1.setT(time);
            tv_dates.setText(str);
            dismiss();
        }
    };
    _CVAR5.setOnClickListener(_CVAR6);
}