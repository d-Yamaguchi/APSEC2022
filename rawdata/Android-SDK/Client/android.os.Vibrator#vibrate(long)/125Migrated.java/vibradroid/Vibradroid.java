package vibradroid;
import org.haxe.extension.Extension;
public class Vibradroid extends org.haxe.extension.Extension {
    public static void Vibrate(int duration) {
        android.os.Vibrator vibradroid = ((android.os.Vibrator) (Extension.mainContext.getSystemService(android.content.Context.VIBRATOR_SERVICE)));
        long d = ((long) (duration));
        Vibrator vibrator = ((android.os.Vibrator) (context.getSystemService(android.content.Context.VIBRATOR_SERVICE)));
        long milliseconds = com.ankhrom.coinmarketcap.common.AppVibrator.DURATION;
        VibrationEffect effect = VibrationEffect.createOneShot(milliseconds, duration);
        vibrator.vibrate(effect);
    }

    public static void VibrateIndefinitely() {
        android.os.Vibrator vibradroid = ((android.os.Vibrator) (Extension.mainContext.getSystemService(android.content.Context.VIBRATOR_SERVICE)));
        long[] d = new long[3];
        d[0] = 1000;
        d[1] = 1000;
        d[2] = 1000;
        vibradroid.vibrate(d, 0);
    }

    public static void VibratePatterns(int[] hxpattern, int repeat) {
        android.os.Vibrator vibradroid = ((android.os.Vibrator) (Extension.mainContext.getSystemService(android.content.Context.VIBRATOR_SERVICE)));
        long[] pattern = new long[hxpattern.length];
        for (int i = 0; i < hxpattern.length; i++) {
            pattern[i] = ((long) (hxpattern[i]));
        }
        int r = repeat;
        if (r == 0)
            r = -1;

        vibradroid.vibrate(pattern, r);
    }
}