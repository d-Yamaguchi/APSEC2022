@java.lang.Override
public void onClick(android.view.View v) {
    com.parse.ParseInstallation.getCurrentInstallation().saveInBackground();
    com.parse.ParseQuery query = com.parse.ParseInstallation.getQuery();
    com.parse.PushService.setDefaultPushCallback(context, com.hu.quantumshare.AcceptActivity.class);
    // Notification for Android users
    query.whereEqualTo("deviceType", "android");
    // JSONObject data = new JSONObject();
    com.parse.ParseUser currentUser = com.parse.ParseUser.getCurrentUser();
    com.parse.ParsePush androidPush = new com.parse.ParsePush();
    if (currentUser == null) {
        android.util.Log.e("Parse error", "parse could not find the current user");
    } else {
        task.put("owner", currentUser.getUsername());
        task.put("needs", needs.getItemAtPosition(needs.getSelectedItemPosition()).toString());
        int _CVAR0 = 9;
        boolean _CVAR1 = time.getCurrentHour() > _CVAR0;
        java.lang.String _CVAR2 = "Task";
        java.lang.String _CVAR8 = _CVAR2;
        // store the task in the database
        com.parse.ParseObject task = new com.parse.ParseObject(_CVAR8);
        if () {
            java.lang.String _CVAR5 = ":";
            java.lang.String _CVAR6 = time.getCurrentHour() + _CVAR5;
            com.parse.ParseObject _CVAR3 = task;
            java.lang.String _CVAR4 = "time";
            java.lang.String _CVAR7 = _CVAR6 + time.getCurrentMinute();
            _CVAR3.put(_CVAR4, _CVAR7);
        } else {
            android.widget.TimePicker _CVAR11 = time;
            java.lang.Integer _CVAR12 = _CVAR11.getCurrentHour();
            java.lang.String _CVAR13 = "0" + _CVAR12;
            java.lang.String _CVAR14 = _CVAR13 + ":";
            com.parse.ParseObject _CVAR9 = task;
            java.lang.String _CVAR10 = "time";
            java.lang.String _CVAR15 = _CVAR14 + time.getCurrentMinute();
            _CVAR9.put(_CVAR10, _CVAR15);
        }
        /* if(time.getCurrentHour() == 0)
        {
        task.put("hour", ""+12 );
        task.put("minute", time.getCurrentMinute());
        task.put("time","AM");
        }
        else if(time.getCurrentHour() >13)
        {
        Integer num = (time.getCurrentHour()-12);
        if(num > 9)
        {
        task.put("hour", ""+ num);
        }
        else
        {
        task.put("hour", "0"+num);
        }
        task.put("minute",time.getCurrentMinute());
        task.put("time","PM");
        }
        else if(time.getCurrentHour() > 9)
        {
        task.put("hour", ""+time.getCurrentHour());
        task.put("minute", time.getCurrentMinute());
        task.put("time","AM");

        }
        else
        {
        task.put("hour", "0"+time.getCurrentHour() );
        task.put("minute", time.getCurrentMinute());
        task.put("time","AM");

        }
         */
        task.put("place", place.getText().toString());
        task.put("taker", "no one");
        task.put("instructions", instructions.getText().toString());
        task.saveInBackground();
        // stops the push notification from being sent to the sender
        query.whereNotEqualTo("username", currentUser.getUsername());
        android.widget.TimePicker _CVAR17 = time;
        java.lang.Integer _CVAR18 = _CVAR17.getCurrentHour();
         _CVAR19 = (((currentUser.getUsername() + " needs ") + task.get("needs")) + " at ") + _CVAR18;
         _CVAR20 = _CVAR19 + ":";
        com.parse.ParsePush _CVAR16 = androidPush;
         _CVAR21 = _CVAR20 + time.getCurrentMinute();
        // sending push notification
        _CVAR16.setMessage(_CVAR21);
        androidPush.setQuery(query);
        androidPush.sendInBackground();
        android.util.Log.d("Push notification", "task sent");
    }
    android.widget.Toast.makeText(context, "task sent", android.widget.Toast.LENGTH_SHORT).show();
}