/**
 * Vibrates the phone for a given time
 *
 * @param time
 * 		in milliseconds
 */
public static void vibrate(long time) {
    if (!com.stickycoding.rokon.device.Vibrator.enabled) {
        return;
    }
    com.stickycoding.rokon.device.Vibrator.prepareVibrator();
    android.os.Vibrator _CVAR0 = com.stickycoding.rokon.device.Vibrator.vibrator;
    long _CVAR1 = time;
    _CVAR0.vibrate(_CVAR1);
}