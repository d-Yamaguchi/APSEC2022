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
        java.lang.String _CVAR0 = "Task";
        java.lang.String _CVAR6 = _CVAR0;
        // store the task in the database
        com.parse.ParseObject task = new com.parse.ParseObject(_CVAR6);
        if (time.getCurrentHour() > 9) {
            android.widget.TimePicker _CVAR3 = time;
            java.lang.Integer _CVAR4 = _CVAR3.getCurrentMinute();
            com.parse.ParseObject _CVAR1 = task;
            java.lang.String _CVAR2 = "time";
            java.lang.String _CVAR5 = (time.getCurrentHour() + ":") + _CVAR4;
            _CVAR1.put(_CVAR2, _CVAR5);
        } else {
            android.widget.TimePicker _CVAR9 = time;
            java.lang.Integer _CVAR10 = _CVAR9.getCurrentMinute();
            com.parse.ParseObject _CVAR7 = task;
            java.lang.String _CVAR8 = "time";
            java.lang.String _CVAR11 = (("0" + time.getCurrentHour()) + ":") + _CVAR10;
            _CVAR7.put(_CVAR8, _CVAR11);
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
        android.widget.TimePicker _CVAR13 = time;
        java.lang.Integer _CVAR14 = _CVAR13.getCurrentMinute();
        com.parse.ParsePush _CVAR12 = androidPush;
         _CVAR15 = (((((currentUser.getUsername() + " needs ") + task.get("needs")) + " at ") + time.getCurrentHour()) + ":") + _CVAR14;
        // sending push notification
        _CVAR12.setMessage(_CVAR15);
        androidPush.setQuery(query);
        androidPush.sendInBackground();
        android.util.Log.d("Push notification", "task sent");
    }
    android.widget.Toast.makeText(context, "task sent", android.widget.Toast.LENGTH_SHORT).show();
}