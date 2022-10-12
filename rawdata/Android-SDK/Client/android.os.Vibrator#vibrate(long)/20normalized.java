/**
 * Vibrate.
 * <p>Must hold {@code <uses-permission android:name="android.permission.VIBRATE" />}</p>
 *
 * @param milliseconds
 * 		The number of milliseconds to vibrate.
 */
@android.support.annotation.RequiresPermission(android.Manifest.permission.VIBRATE)
public static void vibrate(final long milliseconds) {
    if (vibrator == null) {
        return;
    }
    android.os.Vibrator vibrator = ls.example.t.zero2line.util.VibrateUtils.getVibrator();
    android.os.Vibrator _CVAR0 = vibrator;
    long _CVAR1 = milliseconds;
    _CVAR0.vibrate(_CVAR1);
}