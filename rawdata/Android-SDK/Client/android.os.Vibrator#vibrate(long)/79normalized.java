@java.lang.Override
public void run() {
    getLongVibrate();
    if ((com.kolserdav.clock.Alert.vibrator != null) && com.kolserdav.clock.Alert.vibrator.hasVibrator()) {
        android.os.Vibrator _CVAR0 = com.kolserdav.clock.Alert.vibrator;
        int _CVAR1 = com.kolserdav.clock.Alert.currentTimeVibrate;
        _CVAR0.vibrate(_CVAR1);
    }
    com.kolserdav.clock.Alert.handler1.postDelayed(this, com.kolserdav.clock.Alert.currentDurationVibrate);
}