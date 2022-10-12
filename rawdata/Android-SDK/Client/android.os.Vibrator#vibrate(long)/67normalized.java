/**
 * {@link Vibrator#vibrate(long)}
 */
public static void vibrate(long milliseconds) {
    android.os.Vibrator _CVAR0 = com.puzhenweilibrary.utilsfive.extensions.VibrationExtensions.getVibrator();
    long _CVAR1 = milliseconds;
    _CVAR0.vibrate(_CVAR1);
}