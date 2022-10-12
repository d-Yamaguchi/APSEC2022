@java.lang.Override
protected void onCreate(android.os.Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.datetime_dialog);
    timePicker = findViewById(R.id.timePicker);
    datePicker = findViewById(R.id.datePicker);
    calendar.set(java.util.Calendar.YEAR, datePicker.getYear());
    calendar.set(java.util.Calendar.MONTH, datePicker.getMonth());
    calendar.set(java.util.Calendar.DAY_OF_MONTH, datePicker.getDayOfMonth());
    // editText=(EditText)findViewById(R.id.et_date);
    java.util.Calendar calendar = java.util.Calendar.getInstance();
    android.widget.TimePicker _CVAR2 = timePicker;
    java.util.Calendar _CVAR0 = calendar;
    int _CVAR1 = java.util.Calendar.HOUR_OF_DAY;
    java.lang.Integer _CVAR3 = _CVAR2.getCurrentHour();
    _CVAR0.set(_CVAR1, _CVAR3);
    calendar.set(java.util.Calendar.MINUTE, timePicker.getCurrentMinute());
    calendar.set(java.util.Calendar.SECOND, 0);
    void _CVAR4 = R.id.bt_datesave;
    android.widget.Button bt_datesave = ((android.widget.Button) (findViewById(_CVAR4)));
    android.widget.Button _CVAR5 = bt_datesave;
    android.view.View.OnClickListener _CVAR6 = new android.view.View.OnClickListener() {
        @java.lang.Override
        public void onClick(android.view.View view) {
            java.lang.String str = (((((((datePicker.getYear() + "-") + (datePicker.getMonth() + 1)) + "-") + datePicker.getDayOfMonth()) + " ") + timePicker.getCurrentHour()) + ":") + timePicker.getCurrentMinute();
            date = (((datePicker.getYear() + "-") + (datePicker.getMonth() + 1)) + "-") + datePicker.getDayOfMonth();
            time = (timePicker.getCurrentHour() + ":") + timePicker.getCurrentMinute();
            org.androidtown.healthcareguide.Fragment.InputdiabetsFragment.setD(date);
            org.androidtown.healthcareguide.Fragment.InputdiabetsFragment.setT(time);
            tv_date.setText(str);
            dismiss();
        }
    };
    _CVAR5.setOnClickListener(_CVAR6);
}