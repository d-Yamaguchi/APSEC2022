@java.lang.Override
public void onClick(android.view.View v) {
    final java.util.Calendar calendar = java.util.Calendar.getInstance();
    android.widget.TimePicker _CVAR2 = alarm_timepicker;
    java.util.Calendar _CVAR0 = calendar;
    int _CVAR1 = java.util.Calendar.HOUR_OF_DAY;
    java.lang.Integer _CVAR3 = _CVAR2.getCurrentHour();
    _CVAR0.set(_CVAR1, _CVAR3);
    calendar.set(java.util.Calendar.MINUTE, alarm_timepicker.getCurrentMinute());
    calendar.set(java.util.Calendar.SECOND, 0);
    android.widget.TimePicker _CVAR4 = alarm_timepicker;
    int hour = _CVAR4.getCurrentHour();
    int minute = alarm_timepicker.getCurrentMinute();
    android.widget.Toast.makeText(this, ((hour + "시 ") + minute) + "분 에 알람이 울립니다.", android.widget.Toast.LENGTH_SHORT).show();
    am.ze.wookoo.myapplication.AlarmActivity.my_intent.putExtra("state", "alarm on");
    am.ze.wookoo.myapplication.AlarmActivity.pendingIntent = android.app.PendingIntent.getBroadcast(this, 0, am.ze.wookoo.myapplication.AlarmActivity.my_intent, android.app.PendingIntent.FLAG_UPDATE_CURRENT);
    am.ze.wookoo.myapplication.AlarmActivity.alarm_manager.set(android.app.AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), am.ze.wookoo.myapplication.AlarmActivity.pendingIntent);
    android.content.Intent mIntent = new android.content.Intent(this, am.ze.wookoo.myapplication.RecordActivity.class);
    java.lang.StringBuffer sb = new java.lang.StringBuffer();
    sb.append("UPDATE user SET ALARM = ");
    java.lang.String data;
    int _CVAR5 = 12;
    boolean _CVAR6 = alarm_timepicker.getCurrentHour() > _CVAR5;
    if () {
        int _CVAR8 = 12;
        android.widget.TimePicker _CVAR10 = alarm_timepicker;
        java.lang.String _CVAR7 = " Alarm %d : %02d PM";
        int _CVAR9 = alarm_timepicker.getCurrentHour() - _CVAR8;
        java.lang.Integer _CVAR11 = _CVAR10.getCurrentMinute();
        java.lang.String _CVAR12 = java.lang.String.format(_CVAR7, _CVAR9, _CVAR11);
        data = _CVAR12;
    } else {
        int _CVAR13 = 12;
        boolean _CVAR14 = alarm_timepicker.getCurrentHour() == _CVAR13;
        if () {
            android.widget.TimePicker _CVAR16 = alarm_timepicker;
            android.widget.TimePicker _CVAR18 = alarm_timepicker;
            java.lang.String _CVAR15 = " Alarm %d : %02d PM";
            java.lang.Integer _CVAR17 = _CVAR16.getCurrentHour();
            java.lang.Integer _CVAR19 = _CVAR18.getCurrentMinute();
            java.lang.String _CVAR20 = java.lang.String.format(_CVAR15, _CVAR17, _CVAR19);
            data = _CVAR20;
        } else {
            int _CVAR21 = 0;
            boolean _CVAR22 = alarm_timepicker.getCurrentHour() == _CVAR21;
            if () {
                data = java.lang.String.format(" Alarm %d : %02d AM", 12, alarm_timepicker.getCurrentMinute());
            } else {
                android.widget.TimePicker _CVAR24 = alarm_timepicker;
                android.widget.TimePicker _CVAR26 = alarm_timepicker;
                java.lang.String _CVAR23 = " Alarm %d : %02d AM";
                java.lang.Integer _CVAR25 = _CVAR24.getCurrentHour();
                java.lang.Integer _CVAR27 = _CVAR26.getCurrentMinute();
                java.lang.String _CVAR28 = java.lang.String.format(_CVAR23, _CVAR25, _CVAR27);
                data = _CVAR28;
            }
        }
    }
    startActivity(mIntent);
    finish();
}