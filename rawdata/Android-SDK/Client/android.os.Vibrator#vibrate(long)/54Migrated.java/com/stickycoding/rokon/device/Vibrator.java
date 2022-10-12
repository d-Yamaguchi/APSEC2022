package com.stickycoding.rokon.device;
import com.stickycoding.rokon.Rokon;
/**
 * Vibrate.java
 * A helper class for vibrating the device
 *
 * REMEMBER TO ADD THE RIGHT PERMISSIONS TO AndroidManifest.xml *
 * <uses-permission android:name="android.permission.VIBRATE" ></uses-permission>
 *
 * Take care when using import, the name clashes with android.os.Vibrator (yeah, I know.)
 *
 * @author Richard
 */
public class Vibrator {
    protected static android.os.Vibrator vibrator;

    protected static boolean enabled = true;

    /**
     * Enables the Vibrator to work (this is on by default)
     */
    public static void enable() {
        com.stickycoding.rokon.device.Vibrator.enabled = true;
    }

    /**
     * Prevents the Vibrator from vibrating
     */
    public static void disable() {
        com.stickycoding.rokon.device.Vibrator.enabled = false;
    }

    private static void prepareVibrator() {
        if (com.stickycoding.rokon.device.Vibrator.vibrator != null)
            return;

        com.stickycoding.rokon.device.Vibrator.vibrator = ((android.os.Vibrator) (com.stickycoding.rokon.Rokon.getActivity().getSystemService(android.content.Context.VIBRATOR_SERVICE)));
    }

    /**
     * Vibrates the phone for a given time
     *
     * @param time
     * 		in milliseconds
     */
    public static void vibrate(long time) {
        if (!com.stickycoding.rokon.device.Vibrator.enabled)
            return;

        com.stickycoding.rokon.device.Vibrator.prepareVibrator();
        int amplitude = com.ankhrom.coinmarketcap.common.AppVibrator.AMPLITUDE;
        VibrationEffect effect = VibrationEffect.createOneShot(time, amplitude);
        vibrator.vibrate(effect);
    }

    /**
     * Vibrates the phone with a given pattern
     * Pass in an array of ints that are the times at which to turn on or off the vibrator. The first one is how long to wait before turning it on, and then after that it alternates. If you want to repeat, pass the index into the pattern at which to start the repeat.
     *
     * @param pattern
     * 		an array of longs of times to turn the vibrator on or off
     * @param repeat
     * 		the index into pattern at which to repeat, or -1 if you don't want to repeat
     */
    public static void vibrate(long[] pattern, int repeat) {
        if (!com.stickycoding.rokon.device.Vibrator.enabled)
            return;

        com.stickycoding.rokon.device.Vibrator.prepareVibrator();
        com.stickycoding.rokon.device.Vibrator.vibrator.vibrate(pattern, repeat);
    }
}