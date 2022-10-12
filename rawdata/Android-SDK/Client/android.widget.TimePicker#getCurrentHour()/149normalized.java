public void a_on(android.view.View view) {
    android.widget.TimePicker _CVAR0 = timePicker;
    int getHour = _CVAR0.getCurrentHour();
    int getMinute = timePicker.getCurrentMinute();
    if (java.lang.String.valueOf(spinner.getSelectedItem()).matches("التنبيه الاول")) {
        android.widget.TimePicker _CVAR3 = timePicker;
        java.util.Calendar _CVAR1 = calendar1;
        int _CVAR2 = java.util.Calendar.HOUR_OF_DAY;
        java.lang.Integer _CVAR4 = _CVAR3.getCurrentHour();
        _CVAR1.set(_CVAR2, _CVAR4);
        calendar1.set(java.util.Calendar.MINUTE, timePicker.getCurrentMinute());
        calendar1.set(java.util.Calendar.SECOND, 0);
        android.widget.Toast.makeText(this, java.lang.String.valueOf(spinner.getSelectedItem()), android.widget.Toast.LENGTH_SHORT).show();
        intent = new android.content.Intent(this, momen.alarme.Alarm_Receiver.class);
        pendingIntent1 = android.app.PendingIntent.getBroadcast(this, 0, intent, android.app.PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.set(android.app.AlarmManager.RTC_WAKEUP, calendar1.getTimeInMillis(), pendingIntent1);
    } else if (java.lang.String.valueOf(spinner.getSelectedItem()).matches("التنبيه الثانى")) {
        android.widget.TimePicker _CVAR7 = timePicker;
        java.util.Calendar _CVAR5 = calendar2;
        int _CVAR6 = java.util.Calendar.HOUR_OF_DAY;
        java.lang.Integer _CVAR8 = _CVAR7.getCurrentHour();
        _CVAR5.set(_CVAR6, _CVAR8);
        calendar2.set(java.util.Calendar.MINUTE, timePicker.getCurrentMinute());
        calendar2.set(java.util.Calendar.SECOND, 0);
        android.widget.Toast.makeText(this, java.lang.String.valueOf(spinner.getSelectedItem()), android.widget.Toast.LENGTH_SHORT).show();
        intent = new android.content.Intent(this, momen.alarme.Alarm_Receiver.class);
        pendingIntent2 = android.app.PendingIntent.getBroadcast(this, 1, intent, android.app.PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.set(android.app.AlarmManager.RTC_WAKEUP, calendar2.getTimeInMillis(), pendingIntent2);
    }
}