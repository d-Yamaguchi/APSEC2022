/**
 * {@link Vibrator#vibrate(long, AudioAttributes)}
 */
@android.support.annotation.RequiresApi(api = android.os.Build.VERSION_CODES.LOLLIPOP)
public static void vibrate(long milliseconds, android.media.AudioAttributes attributes) {
    android.os.Vibrator _CVAR0 = com.common.android.utils.extensions.VibrationExtensions.getVibrator();
    long _CVAR1 = milliseconds;
    android.media.AudioAttributes _CVAR2 = attributes;
    _CVAR0.vibrate(_CVAR1, _CVAR2);
}