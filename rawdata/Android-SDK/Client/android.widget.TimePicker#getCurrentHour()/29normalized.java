@java.lang.Override
public void onClick(android.view.View v) {
    java.lang.String workload = editTxtWorkload.getText().toString();
    if (workload.trim().length() == 0) {
        android.widget.Toast.makeText(getBaseContext(), "Error: Without workload!", android.widget.Toast.LENGTH_SHORT).show();
        return;
    } else {
        int workloadInt = java.lang.Integer.parseInt(workload);
        int _CVAR0 = 60;
        int inHour = timepickerIn.getCurrentHour() * _CVAR0;
        int inMin = timepickerIn.getCurrentMinute();
        int _CVAR1 = 60;
        int outHour = timepickerOut.getCurrentHour() * _CVAR1;
        int outMin = timepickerOut.getCurrentMinute();
        int _CVAR2 = 60;
        int in2Hour = timepickerIn2.getCurrentHour() * _CVAR2;
        int in2Min = timepickerIn2.getCurrentMinute();
        int out2Hour;
        int out2Min;
        workloadInt = workloadInt * 60;
        int workOut2 = -((((outHour + outMin) - (inHour + inMin)) - workloadInt) - (in2Hour + in2Min));
        out2Hour = workOut2 / 60;
        out2Min = workOut2 % 60;
        if ((inHour > outHour) || (outHour > in2Hour)) {
            android.widget.Toast.makeText(getBaseContext(), "Error: Problem with hours!", android.widget.Toast.LENGTH_SHORT).show();
        } else {
            timepickerOut2.setCurrentHour(out2Hour);
            timepickerOut2.setCurrentMinute(out2Min);
        }
        if (exitNotification.isChecked()) {
            // ---use the AlarmManager to trigger an alarm---
            android.app.AlarmManager alarmManager = ((android.app.AlarmManager) (getSystemService(android.content.Context.ALARM_SERVICE)));
            java.sql.Date dat = new java.sql.Date(1);
            java.util.Calendar now_calendar = java.util.Calendar.getInstance();
            now_calendar.setTime(dat);
            // ---get current date and time---
            java.util.Calendar calendar = java.util.Calendar.getInstance();
            android.widget.TimePicker _CVAR5 = timepickerOut2;
            java.util.Calendar _CVAR3 = calendar;
            int _CVAR4 = java.util.Calendar.HOUR_OF_DAY;
            java.lang.Integer _CVAR6 = _CVAR5.getCurrentHour();
            // ---sets the time for the alarm to trigger---
            _CVAR3.set(_CVAR4, _CVAR6);
            calendar.set(java.util.Calendar.MINUTE, timepickerOut2.getCurrentMinute());
            calendar.set(java.util.Calendar.SECOND, 0);
            if (calendar.before(now_calendar)) {
                calendar.add(java.util.Calendar.DATE, 1);
            }
            android.content.Intent i = new android.content.Intent(this, alberto.hugo.ezzit.DisplayNotifications.class);
            i.putExtra("NotifID", 1);
            android.app.PendingIntent displayIntent = android.app.PendingIntent.getActivity(getBaseContext(), 0, i, android.app.PendingIntent.FLAG_UPDATE_CURRENT);
            alarmManager.set(android.app.AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), displayIntent);
        }
    }
}