@java.lang.Override
public void onClick(android.view.View v) {
    alarmTitle = title.getText().toString().trim();
    if (alarmTitle.isEmpty()) {
        title.setError("Pls Enter Title!!");
    } else {
        calendar = java.util.Calendar.getInstance();
        calendar.set(java.util.Calendar.HOUR_OF_DAY, timePicker.getCurrentHour());
        android.widget.TimePicker _CVAR2 = timePicker;
        java.util.Calendar _CVAR0 = calendar;
        int _CVAR1 = java.util.Calendar.MINUTE;
        java.lang.Integer _CVAR3 = _CVAR2.getCurrentMinute();
        _CVAR0.set(_CVAR1, _CVAR3);
        calendar.set(java.util.Calendar.SECOND, 0);
        calendar.set(java.util.Calendar.MILLISECOND, 0);
        android.widget.TimePicker _CVAR6 = timePicker;
        java.util.concurrent.TimeUnit _CVAR5 = java.util.concurrent.TimeUnit.MINUTES;
        java.lang.Integer _CVAR7 = _CVAR6.getCurrentMinute();
        long _CVAR8 = _CVAR5.toSeconds(_CVAR7);
        java.util.concurrent.TimeUnit _CVAR4 = java.util.concurrent.TimeUnit.SECONDS;
        long _CVAR9 = java.util.concurrent.TimeUnit.HOURS.toSeconds(timePicker.getCurrentHour()) + _CVAR8;
        long milliseconds = _CVAR4.toMillis(_CVAR9);
        android.util.Log.v("millisecond", (calendar.getTimeInMillis() + "M") + milliseconds);
        long ab = calendar.getTimeInMillis();
        int seconds = ((int) (milliseconds / 1000)) % 60;
        int minutes = ((int) ((milliseconds / (1000 * 60)) % 60));
        int hours = ((int) ((milliseconds / ((1000 * 60) * 60)) % 24));
        android.util.Log.v("sec", "" + seconds);
        android.util.Log.v("min", "" + minutes);
        android.util.Log.v("hours", "" + hours);
        if (update) {
            values.put("Name", alarmTitle);
            values.put("Hour", timePicker.getCurrentHour());
            android.content.ContentValues values = new android.content.ContentValues();
            android.widget.TimePicker _CVAR12 = timePicker;
            android.content.ContentValues _CVAR10 = values;
            java.lang.String _CVAR11 = "Minute";
            java.lang.Integer _CVAR13 = _CVAR12.getCurrentMinute();
            _CVAR10.put(_CVAR11, _CVAR13);
            database.update("Alarm", values, "Column_Id=" + position, null);
            android.util.Log.v("update", "" + position);
        } else {
            values.put("Name", alarmTitle);
            values.put("Hour", timePicker.getCurrentHour());
            android.content.ContentValues values = new android.content.ContentValues();
            android.widget.TimePicker _CVAR16 = timePicker;
            android.content.ContentValues _CVAR14 = values;
            java.lang.String _CVAR15 = "Minute";
            java.lang.Integer _CVAR17 = _CVAR16.getCurrentMinute();
            _CVAR14.put(_CVAR15, _CVAR17);
            values.put("Status", "true");
            values.put("Time_Status", "true");
            values.put("Title_Status", "true");
            database.insert("Alarm", null, values);
        }
        android.content.Intent j = new android.content.Intent(getBaseContext(), com.example.nitinsharma.medicalalarmremainder.AlarmReceiver.class);
        pendingIntent = android.app.PendingIntent.getBroadcast(getBaseContext(), 1, j, android.app.PendingIntent.FLAG_UPDATE_CURRENT);
        if (android.os.Build.VERSION.SDK_INT >= 23) {
            alarmManager.setExactAndAllowWhileIdle(android.app.AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        } else if (android.os.Build.VERSION.SDK_INT >= 19) {
            alarmManager.setExact(android.app.AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        } else {
            alarmManager.set(android.app.AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        }
        if (update) {
            o.putExtra("Title", alarmTitle);
            o.putExtra("Hour", timePicker.getCurrentHour());
            android.content.Intent o = new android.content.Intent();
            android.widget.TimePicker _CVAR20 = timePicker;
            android.content.Intent _CVAR18 = o;
            java.lang.String _CVAR19 = "Min";
            java.lang.Integer _CVAR21 = _CVAR20.getCurrentMinute();
            _CVAR18.putExtra(_CVAR19, _CVAR21);
            setResult(101, o);
            finish();
        } else {
            f.putExtra("Title", alarmTitle);
            f.putExtra("Hour", timePicker.getCurrentHour());
            android.content.Intent f = new android.content.Intent();
            android.widget.TimePicker _CVAR24 = timePicker;
            android.content.Intent _CVAR22 = f;
            java.lang.String _CVAR23 = "Min";
            java.lang.Integer _CVAR25 = _CVAR24.getCurrentMinute();
            _CVAR22.putExtra(_CVAR23, _CVAR25);
            setResult(com.example.nitinsharma.medicalalarmremainder.RESULT_OK, f);
            finish();
        }
    }
}