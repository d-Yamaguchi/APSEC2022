public void submit(android.view.View v) {
    mDatabase = com.google.firebase.database.FirebaseDatabase.getInstance().getReference();
    java.lang.String UID = ((com.solutions.medadhere.medadheresolutionsapp.MyApplication) (this.getApplication())).getUID();
    android.widget.TimePicker timePicker = ((android.widget.TimePicker) (findViewById(R.id.timePicker)));
    java.util.Calendar rightNow = java.util.Calendar.getInstance();
    int isecond = rightNow.get(java.util.Calendar.SECOND);
    java.lang.String time;
    if (android.os.Build.VERSION.SDK_INT >= 23) {
        time = timePicker.getHour() + ":";
        int minute = timePicker.getMinute();
        if (minute < 10) {
            time += "0" + minute;
        } else {
            time += minute;
        }
    } else {
        java.lang.String _CVAR0 = ":";
        java.lang.String _CVAR1 = timePicker.getCurrentHour() + _CVAR0;
        time = _CVAR1;
        int minute = timePicker.getCurrentMinute();
        if (minute < 10) {
            time += "0" + minute;
        } else {
            time += minute;
        }
    }
    time += ":";
    if (isecond < 10) {
        time += "0" + isecond;
    } else {
        time += isecond;
    }
    if (medicine != " ") {
        // medicine = records.get(0);
        com.solutions.medadhere.medadheresolutionsapp.TimeLog timer = new com.solutions.medadhere.medadheresolutionsapp.TimeLog();
        mDatabase.child("app").child("users").child(UID).child("medicineLog").child(date).child(time).setValue(medicine);
        // TODO: add cancel of repeating alarm within a time frame
        com.solutions.medadhere.medadheresolutionsapp.ReminderService reminder = new com.solutions.medadhere.medadheresolutionsapp.ReminderService();
        int _CVAR2 = 12;
        boolean _CVAR3 = timePicker.getCurrentHour() < _CVAR2;
        int _CVAR5 = 5;
        boolean _CVAR6 = timePicker.getCurrentHour() > _CVAR5;
        boolean _CVAR4 = _CVAR3 & _CVAR6;
        // 10AM  limit to 5AM to 12PM
        if () {
            final android.content.Intent intent = getIntent();
            android.util.Log.e("Should Cancel", "sending 4");
            android.content.Intent i = new android.content.Intent(this, com.solutions.medadhere.medadheresolutionsapp.ReminderService.class);
            i.putExtra("type", 4);
            startService(i);
        }
        int _CVAR7 = 16;
        boolean _CVAR8 = timePicker.getCurrentHour() < _CVAR7;
        int _CVAR10 = 12;
        boolean _CVAR11 = timePicker.getCurrentHour() >= _CVAR10;
        boolean _CVAR9 = _CVAR8 & _CVAR11;
        // 2PM  limit to 12PM to 4PM
        if () {
            android.content.Intent alarmIntent = new android.content.Intent(getApplicationContext(), com.solutions.medadhere.medadheresolutionsapp.MyAlarmReceiver.class);
            alarmIntent.setData(android.net.Uri.parse("custom://" + 1000002));
            alarmIntent.setAction(java.lang.String.valueOf(1000002));
            android.app.AlarmManager alarmManager = ((android.app.AlarmManager) (getSystemService(com.solutions.medadhere.medadheresolutionsapp.ALARM_SERVICE)));
            android.app.PendingIntent displayIntent = android.app.PendingIntent.getBroadcast(getApplicationContext(), 0, alarmIntent, android.app.PendingIntent.FLAG_UPDATE_CURRENT);
            alarmManager.cancel(displayIntent);
        }
        int _CVAR12 = 24;
        boolean _CVAR13 = timePicker.getCurrentHour() < _CVAR12;
        int _CVAR15 = 14;
        boolean _CVAR16 = timePicker.getCurrentHour() >= _CVAR15;
        boolean _CVAR14 = _CVAR13 & _CVAR16;
        // 8PM  limit to 4PM to 12AM
        if () {
            android.content.Intent alarmIntent = new android.content.Intent(getApplicationContext(), com.solutions.medadhere.medadheresolutionsapp.MyAlarmReceiver.class);
            alarmIntent.setData(android.net.Uri.parse("custom://" + 1000003));
            alarmIntent.setAction(java.lang.String.valueOf(1000003));
            android.app.AlarmManager alarmManager = ((android.app.AlarmManager) (getSystemService(com.solutions.medadhere.medadheresolutionsapp.ALARM_SERVICE)));
            android.app.PendingIntent displayIntent = android.app.PendingIntent.getBroadcast(getApplicationContext(), 0, alarmIntent, android.app.PendingIntent.FLAG_UPDATE_CURRENT);
            alarmManager.cancel(displayIntent);
        }
        android.support.design.widget.Snackbar.make(v, "Your medication has been logged at " + time, Snackbar.LENGTH_LONG).setAction("Action", null).show();
    } else {
        android.support.design.widget.Snackbar.make(v, "Please select a medication.", Snackbar.LENGTH_LONG).setAction("Action", null).show();
    }
}