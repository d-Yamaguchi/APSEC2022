package com.puzhenweilibrary.utilsfive.extensions;
import static com.puzhenweilibrary.utilsfive.ContextHelper.getContext;
import static com.puzhenweilibrary.utilsfive.extensions.MathExtensions.clamp;
/**
 * Created by <a href="https://about.me/janrabe">Jan Rabe</a>.
 */
public class VibrationExtensions {
    /**
     * A Vibrator for interacting with the vibrator hardware.
     */
    public static android.os.Vibrator getVibrator() {
        return ((android.os.Vibrator) (com.puzhenweilibrary.utilsfive.ContextHelper.getContext().getSystemService(android.content.Context.VIBRATOR_SERVICE)));
    }

    /**
     * {@link Vibrator#hasVibrator()}
     */
    public static boolean hasVibrator() {
        return com.puzhenweilibrary.utilsfive.extensions.VibrationExtensions.getVibrator().hasVibrator();
    }

    /**
     * {@link Vibrator#vibrate(long)}
     */
    public static void vibrate(long milliseconds) {
        Vibrator vibrator = ((android.os.Vibrator) (context.getSystemService(android.content.Context.VIBRATOR_SERVICE)));
        int amplitude = com.ankhrom.coinmarketcap.common.AppVibrator.AMPLITUDE;
        VibrationEffect effect = VibrationEffect.createOneShot(milliseconds, amplitude);
        vibrator.vibrate(effect);
    }

    /**
     * {@link Vibrator#vibrate(long, AudioAttributes)}
     */
    public static void vibrate(long milliseconds, android.media.AudioAttributes attributes) {
        com.puzhenweilibrary.utilsfive.extensions.VibrationExtensions.getVibrator().vibrate(milliseconds, attributes);
    }

    /**
     * {@link Vibrator#vibrate(long[], int)}
     */
    public static void vibrate(long[] pattern, int repeat) {
        com.puzhenweilibrary.utilsfive.extensions.VibrationExtensions.getVibrator().vibrate(pattern, repeat);
    }

    /**
     * {@link Vibrator#vibrate(long[], int)}
     */
    public static void vibrate(long[] pattern) {
        com.puzhenweilibrary.utilsfive.extensions.VibrationExtensions.getVibrator().vibrate(pattern, -1);
    }

    /**
     * {@link Vibrator#vibrate(long[], int, AudioAttributes)}
     */
    public static void vibrate(long[] pattern, int repeat, android.media.AudioAttributes attributes) {
        com.puzhenweilibrary.utilsfive.extensions.VibrationExtensions.getVibrator().vibrate(pattern, repeat, attributes);
    }

    /**
     * {@link Vibrator#cancel()}
     */
    public static void cancel() {
        com.puzhenweilibrary.utilsfive.extensions.VibrationExtensions.getVibrator().cancel();
    }

    /**
     * <a href="http://stackoverflow.com/a/20821575">Pseudo Pulse-width modulation</a>.
     *
     * @param intensity
     * 		Intensity value [0,1]
     * @param duration
     * 		Duration in milliseconds.
     * @return Vibration Pattern.
     */
    public static long[] createVibratorPattern(float intensity, long duration) {
        intensity = com.puzhenweilibrary.utilsfive.extensions.MathExtensions.clamp(intensity, 0, 1);
        float dutyCycle = java.lang.Math.abs((intensity * 2.0F) - 1.0F);
        long hWidth = ((long) (dutyCycle * (duration - 1))) + 1;
        long lWidth = (dutyCycle == 1.0F) ? 0 : 1;
        int pulseCount = ((int) (2.0F * (((float) (duration)) / ((float) (hWidth + lWidth)))));
        long[] pattern = new long[pulseCount];
        for (int i = 0; i < pulseCount; i++) {
            pattern[i] = (intensity < 0.5F) ? (i % 2) == 0 ? hWidth : lWidth : (i % 2) == 0 ? lWidth : hWidth;
        }
        return pattern;
    }

    public enum Vibration {

        PRESET_01(0.1F),
        PRESET_02(0.2F),
        PRESET_03(0.3F),
        PRESET_04(0.4F),
        PRESET_05(0.5F),
        PRESET_06(0.6F),
        PRESET_07(0.7F),
        PRESET_08(0.8F),
        PRESET_09(0.9F),
        PRESET_10(1.0F);
        private final float intensity;

        Vibration(float intensity) {
            this.intensity = intensity;
        }

        /**
         *
         *
         * @param duration
         * 		In Millis.
         */
        public void vibrate(long duration) {
            com.puzhenweilibrary.utilsfive.extensions.VibrationExtensions.vibrate(com.puzhenweilibrary.utilsfive.extensions.VibrationExtensions.createVibratorPattern(intensity, duration));
        }
    }
}