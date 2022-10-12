package com.openandid.core;
public class PersistenceService extends android.app.Service {
    public boolean is_foreground = false;

    public static int NOTE_ID = 70503;

    public PersistenceService() {
        // TODO Auto-generated constructor stub
    }

    @java.lang.Override
    public void onCreate() {
        show_foreground();
    }

    private void show_foreground() {
        startForeground(com.openandid.core.PersistenceService.NOTE_ID, get_notification("Service Started."));
    }

    @java.lang.Override
    public void onDestroy() {
        stop();
    }

    @java.lang.Override
    public android.os.IBinder onBind(android.content.Intent arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    public void show_message(java.lang.String message) {
        android.app.Notification n = get_notification(message);
        android.app.NotificationManager nm = ((android.app.NotificationManager) (getSystemService(android.content.Context.NOTIFICATION_SERVICE)));
        nm.notify(com.openandid.core.PersistenceService.NOTE_ID, n);
    }

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
        android.content.Intent kill = new android.content.Intent(this, com.openandid.core.NotificationReceiver.class);
        android.app.PendingIntent kill_intent = android.app.PendingIntent.getService(this, 0, kill, 0);
        if (sharedPref.getBoolean("bmt.allowkill", false)) {
            Action action = new android.app.Notification.Action.Builder(Icon.createWithResource(mContext, NOTE_ID), title, intent).build();
            mBuilder.addAction(action);
        }
        android.app.Notification.Builder builder = new android.app.Notification.Builder(getApplicationContext());
        android.content.Intent crash = new android.content.Intent(this, com.openandid.core.NotificationReceiver.class);
        android.app.PendingIntent crash_intent = android.app.PendingIntent.getService(this, 0, crash, 0);
        if (sharedPref.getBoolean("acra.verbose", false)) {
            builder.addAction(android.R.drawable.ic_delete, "Send Error Report", crash_intent);
        }
        // builder.addAction(R.drawable.error_icon, "Kill BMT", kill_intent);
        android.content.Intent notificationIntent = new android.content.Intent(this, com.openandid.core.PersistenceService.class);
        android.app.PendingIntent pendingIntent = android.app.PendingIntent.getActivity(this, 0, notificationIntent, 0);
        builder.setContentIntent(pendingIntent);
        android.app.Notification n = builder.build();
        return n;
    }

    private void stop() {
        if (is_foreground) {
            is_foreground = false;
            stopForeground(true);
        }
    }
}