@java.lang.Override
public void onClick(android.view.View v) {
    android.widget.TimePicker _CVAR1 = tp;
    void _CVAR3 = _CVAR0;
    // final DatePicker dp = (DatePicker) findViewById(R.id.datePicker);
    final android.widget.TimePicker tp = ((android.widget.TimePicker) (findViewById(_CVAR3)));
    android.widget.TimePicker _CVAR4 = tp;
    java.lang.Integer _CVAR2 = _CVAR1.getCurrentHour();
    java.lang.Integer _CVAR5 = _CVAR4.getCurrentMinute();
    // String strDateTime = dp.getYear() + "-" + (dp.getMonth() + 1) + "-" + dp.getDayOfMonth() + " " + tp.getCurrentHour() + ":" + tp.getCurrentMinute();
    hacktober.snoozify.Alarm A = new hacktober.snoozify.Alarm(_CVAR2, _CVAR5);
    void _CVAR0 = R.id.timePicker;
    void _CVAR6 = _CVAR0;
    // final DatePicker dp = (DatePicker) findViewById(R.id.datePicker);
    final android.widget.TimePicker tp = ((android.widget.TimePicker) (findViewById(_CVAR6)));
    android.widget.TimePicker _CVAR7 = tp;
    java.lang.Integer _CVAR8 = _CVAR7.getCurrentMinute();
    java.lang.String strDateTime = (tp.getCurrentHour() + ":") + _CVAR8;
    android.widget.Toast.makeText(this, "User has selected " + strDateTime, android.widget.Toast.LENGTH_LONG).show();
    finish();// move to previous activity

}