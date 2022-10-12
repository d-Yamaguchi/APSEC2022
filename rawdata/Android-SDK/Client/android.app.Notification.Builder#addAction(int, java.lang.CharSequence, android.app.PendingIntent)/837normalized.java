public android.app.Notification get_notification(java.lang.String message) {
    is_foreground = true;
    kill.addFlags(android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP | android.content.Intent.FLAG_ACTIVITY_NEW_TASK);
    kill.setAction("KILL");
    crash.addFlags(android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP | android.content.Intent.FLAG_ACTIVITY_NEW_TASK);
    crash.setAction("CRASH");
    builder.setContentTitle("OpenANDIDCore");
    builder.setContentText(message);
    builder.setSmallIcon(R.drawable.bmt_icon);
    // builder.setContentIntent(kill_intent);
    // builder.addAction(R.drawable.error_icon, "Stop Service", kill_intent);
    android.content.SharedPreferences sharedPref = android.preference.PreferenceManager.getDefaultSharedPreferences(this);
    com.openandid.core.PersistenceService _CVAR6 = this;
    java.lang.Class _CVAR7 = com.openandid.core.NotificationReceiver.class;
    android.content.Intent kill = new android.content.Intent(_CVAR6, _CVAR7);
    com.openandid.core.PersistenceService _CVAR4 = this;
    int _CVAR5 = 0;
    android.content.Intent _CVAR8 = kill;
    int _CVAR9 = 0;
    android.app.PendingIntent kill_intent = android.app.PendingIntent.getService(_CVAR4, _CVAR5, _CVAR8, _CVAR9);
    if (sharedPref.getBoolean("bmt.allowkill", false)) {
        android.app.Notification.Builder _CVAR1 = builder;
        int _CVAR2 = android.R.drawable.ic_input_delete;
        java.lang.String _CVAR3 = "Stop Service";
        android.app.PendingIntent _CVAR10 = kill_intent;
        _CVAR1.addAction(_CVAR2, _CVAR3, _CVAR10);
    }
    android.content.Context _CVAR0 = getApplicationContext();
    android.content.Context _CVAR11 = _CVAR0;
    android.app.Notification.Builder builder = new android.app.Notification.Builder(_CVAR11);
    com.openandid.core.PersistenceService _CVAR17 = this;
    java.lang.Class _CVAR18 = com.openandid.core.NotificationReceiver.class;
    android.content.Intent crash = new android.content.Intent(_CVAR17, _CVAR18);
    com.openandid.core.PersistenceService _CVAR15 = this;
    int _CVAR16 = 0;
    android.content.Intent _CVAR19 = crash;
    int _CVAR20 = 0;
    android.app.PendingIntent crash_intent = android.app.PendingIntent.getService(_CVAR15, _CVAR16, _CVAR19, _CVAR20);
    if (sharedPref.getBoolean("acra.verbose", false)) {
        android.app.Notification.Builder _CVAR12 = builder;
        int _CVAR13 = android.R.drawable.ic_delete;
        java.lang.String _CVAR14 = "Send Error Report";
        android.app.PendingIntent _CVAR21 = crash_intent;
        _CVAR12.addAction(_CVAR13, _CVAR14, _CVAR21);
    }
    // builder.addAction(R.drawable.error_icon, "Kill BMT", kill_intent);
    android.content.Intent notificationIntent = new android.content.Intent(this, com.openandid.core.PersistenceService.class);
    android.app.PendingIntent pendingIntent = android.app.PendingIntent.getActivity(this, 0, notificationIntent, 0);
    builder.setContentIntent(pendingIntent);
    android.app.Notification n = builder.build();
    return n;
}