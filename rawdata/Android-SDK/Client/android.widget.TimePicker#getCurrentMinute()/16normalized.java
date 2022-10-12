@java.lang.Override
public void onClick(android.view.View v) {
    calendar.set(java.util.Calendar.HOUR_OF_DAY, alarm_timepicker.getCurrentHour());
    final java.util.Calendar calendar = java.util.Calendar.getInstance();
    android.widget.TimePicker _CVAR2 = alarm_timepicker;
    java.util.Calendar _CVAR0 = calendar;
    int _CVAR1 = java.util.Calendar.MINUTE;
    java.lang.Integer _CVAR3 = _CVAR2.getCurrentMinute();
    _CVAR0.set(_CVAR1, _CVAR3);
    calendar.set(java.util.Calendar.SECOND, 0);
    int hour = alarm_timepicker.getCurrentHour();
    android.widget.TimePicker _CVAR4 = alarm_timepicker;
    int minute = _CVAR4.getCurrentMinute();
    android.widget.Toast.makeText(this, ((hour + "시 ") + minute) + "분 에 알람이 울립니다.", android.widget.Toast.LENGTH_SHORT).show();
    am.ze.wookoo.myapplication.AlarmActivity.my_intent.putExtra("state", "alarm on");
    am.ze.wookoo.myapplication.AlarmActivity.pendingIntent = android.app.PendingIntent.getBroadcast(this, 0, am.ze.wookoo.myapplication.AlarmActivity.my_intent, android.app.PendingIntent.FLAG_UPDATE_CURRENT);
    am.ze.wookoo.myapplication.AlarmActivity.alarm_manager.set(android.app.AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), am.ze.wookoo.myapplication.AlarmActivity.pendingIntent);
    android.content.Intent mIntent = new android.content.Intent(this, am.ze.wookoo.myapplication.RecordActivity.class);
    java.lang.StringBuffer sb = new java.lang.StringBuffer();
    sb.append("UPDATE user SET ALARM = ");
    java.lang.String data;
    if (alarm_timepicker.getCurrentHour() > 12) {
        int _CVAR6 = 12;
        android.widget.TimePicker _CVAR8 = alarm_timepicker;
        java.lang.String _CVAR5 = " Alarm %d : %02d PM";
        int _CVAR7 = alarm_timepicker.getCurrentHour() - _CVAR6;
        java.lang.Integer _CVAR9 = _CVAR8.getCurrentMinute();
        java.lang.String _CVAR10 = java.lang.String.format(_CVAR5, _CVAR7, _CVAR9);
        data = _CVAR10;
    } else if (alarm_timepicker.getCurrentHour() == 12) {
        android.widget.TimePicker _CVAR12 = alarm_timepicker;
        android.widget.TimePicker _CVAR14 = alarm_timepicker;
        java.lang.String _CVAR11 = " Alarm %d : %02d PM";
        java.lang.Integer _CVAR13 = _CVAR12.getCurrentHour();
        java.lang.Integer _CVAR15 = _CVAR14.getCurrentMinute();
        java.lang.String _CVAR16 = java.lang.String.format(_CVAR11, _CVAR13, _CVAR15);
        data = _CVAR16;
    } else if (alarm_timepicker.getCurrentHour() == 0) {
        android.widget.TimePicker _CVAR19 = alarm_timepicker;
        java.lang.String _CVAR17 = " Alarm %d : %02d AM";
        int _CVAR18 = 12;
        java.lang.Integer _CVAR20 = _CVAR19.getCurrentMinute();
        java.lang.String _CVAR21 = java.lang.String.format(_CVAR17, _CVAR18, _CVAR20);
        data = _CVAR21;
    } else {
        android.widget.TimePicker _CVAR23 = alarm_timepicker;
        android.widget.TimePicker _CVAR25 = alarm_timepicker;
        java.lang.String _CVAR22 = " Alarm %d : %02d AM";
        java.lang.Integer _CVAR24 = _CVAR23.getCurrentHour();
        java.lang.Integer _CVAR26 = _CVAR25.getCurrentMinute();
        java.lang.String _CVAR27 = java.lang.String.format(_CVAR22, _CVAR24, _CVAR26);
        data = _CVAR27;
    }
    startActivity(mIntent);
    finish();
}