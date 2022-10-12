@java.lang.Override
public void onClick(android.view.View v) {
    void _CVAR0 = R.id.timePicker;
    // final DatePicker dp = (DatePicker) findViewById(R.id.datePicker);
    final android.widget.TimePicker tp = ((android.widget.TimePicker) (findViewById(_CVAR0)));
    android.widget.TimePicker _CVAR1 = tp;
    void _CVAR3 = _CVAR0;
    // final DatePicker dp = (DatePicker) findViewById(R.id.datePicker);
    final android.widget.TimePicker tp = ((android.widget.TimePicker) (findViewById(_CVAR3)));
    android.widget.TimePicker _CVAR4 = tp;
    java.lang.Integer _CVAR2 = _CVAR1.getCurrentHour();
    java.lang.Integer _CVAR5 = _CVAR4.getCurrentMinute();
    // String strDateTime = dp.getYear() + "-" + (dp.getMonth() + 1) + "-" + dp.getDayOfMonth() + " " + tp.getCurrentHour() + ":" + tp.getCurrentMinute();
    hacktober.snoozify.Alarm A = new hacktober.snoozify.Alarm(_CVAR2, _CVAR5);
    java.lang.String _CVAR6 = ":";
    java.lang.String _CVAR7 = tp.getCurrentHour() + _CVAR6;
    java.lang.String strDateTime = _CVAR7 + tp.getCurrentMinute();
    android.widget.Toast.makeText(this, "User has selected " + strDateTime, android.widget.Toast.LENGTH_LONG).show();
    finish();// move to previous activity

}