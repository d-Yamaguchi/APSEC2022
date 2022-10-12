/**
 * {@link Vibrator#vibrate(long, AudioAttributes)}
 */
public static void vibrate(long milliseconds, android.media.AudioAttributes attributes) {
    android.os.Vibrator _CVAR0 = com.puzhenweilibrary.utilsfive.extensions.VibrationExtensions.getVibrator();
    long _CVAR1 = milliseconds;
    android.media.AudioAttributes _CVAR2 = attributes;
    _CVAR0.vibrate(_CVAR1, _CVAR2);
}