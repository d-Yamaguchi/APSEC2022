package com.kolserdav.clock;
import androidx.appcompat.app.AppCompatActivity;
public class Alert extends androidx.appcompat.app.AppCompatActivity {
    protected static android.media.MediaPlayer mp;

    protected static android.os.Handler handler;

    protected static android.os.Handler handler1;

    protected static android.os.Vibrator vibrator;

    protected static int countVibrate;

    protected static int currentTimeVibrate;

    protected static int currentDurationVibrate;

    @java.lang.Override
    protected void onCreate(android.os.Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alert);
        com.kolserdav.clock.Alert.mp = android.media.MediaPlayer.create(this, R.raw.sound1);
        playSound();
    }

    public Alert() {
        com.kolserdav.clock.Alert.handler = new android.os.Handler();
        com.kolserdav.clock.Alert.handler1 = new android.os.Handler();
        com.kolserdav.clock.Alert.countVibrate = 0;
        com.kolserdav.clock.Alert.currentTimeVibrate = 0;
        com.kolserdav.clock.Alert.currentDurationVibrate = 500;
    }

    public void playSound() {
        com.kolserdav.clock.Alert.vibrator = ((android.os.Vibrator) (getSystemService(com.kolserdav.clock.VIBRATOR_SERVICE)));
        vibrateHandler();
        // TODO добавить вывод потока на динамик
        com.kolserdav.clock.Alert.mp.start();
        final java.lang.Integer duration = com.kolserdav.clock.Alert.mp.getDuration();
        java.lang.Runnable task = new java.lang.Runnable() {
            @java.lang.Override
            public void run() {
                boolean playing = com.kolserdav.clock.Alert.mp.isPlaying();
                if (!playing) {
                    com.kolserdav.clock.Alert.mp.start();
                }
                com.kolserdav.clock.Alert.handler.postDelayed(this, duration + 5000);
            }
        };
        com.kolserdav.clock.Alert.handler.post(task);
    }

    public void stopSound(android.view.View v) {
        com.kolserdav.clock.Alert.handler.removeCallbacksAndMessages(null);
        com.kolserdav.clock.Alert.mp.stop();
        com.kolserdav.clock.Alert.mp.release();
        stopVibrate();
        android.content.Context context = getBaseContext();
        android.content.Intent intent = new android.content.Intent(context, com.kolserdav.clock.MainActivity.class);
        startActivity(intent);
    }

    protected void vibrateHandler() {
        java.lang.Runnable task = new java.lang.Runnable() {
            @java.lang.Override
            public void run() {
                getLongVibrate();
                if ((com.kolserdav.clock.Alert.vibrator != null) && com.kolserdav.clock.Alert.vibrator.hasVibrator()) {
                    Vibrator vibrator = ((android.os.Vibrator) (context.getSystemService(android.content.Context.VIBRATOR_SERVICE)));
                    long milliseconds = com.ankhrom.coinmarketcap.common.AppVibrator.DURATION;
                    int amplitude = com.ankhrom.coinmarketcap.common.AppVibrator.AMPLITUDE;
                    VibrationEffect effect = VibrationEffect.createOneShot(milliseconds, amplitude);
                    vibrator.vibrate(effect);
                }
                com.kolserdav.clock.Alert.handler1.postDelayed(this, com.kolserdav.clock.Alert.currentDurationVibrate);
            }
        };
        com.kolserdav.clock.Alert.handler1.post(task);
    }

    protected void stopVibrate() {
        com.kolserdav.clock.Alert.handler1.removeCallbacksAndMessages(null);
        com.kolserdav.clock.Alert.vibrator.cancel();
    }

    protected void getLongVibrate() {
        int count = com.kolserdav.clock.Alert.countVibrate;
        switch (count) {
            case 0 :
                com.kolserdav.clock.Alert.currentTimeVibrate = 200;
                com.kolserdav.clock.Alert.currentDurationVibrate = 1500;
                com.kolserdav.clock.Alert.countVibrate++;
                break;
            case 1 :
                com.kolserdav.clock.Alert.currentTimeVibrate = 201;
                com.kolserdav.clock.Alert.currentDurationVibrate = 1500;
                com.kolserdav.clock.Alert.countVibrate++;
                break;
            case 2 :
                com.kolserdav.clock.Alert.currentTimeVibrate = 300;
                com.kolserdav.clock.Alert.currentDurationVibrate = 1300;
                com.kolserdav.clock.Alert.countVibrate++;
                break;
            case 3 :
                com.kolserdav.clock.Alert.currentTimeVibrate = 302;
                com.kolserdav.clock.Alert.currentDurationVibrate = 1300;
                com.kolserdav.clock.Alert.countVibrate++;
                break;
            case 4 :
                com.kolserdav.clock.Alert.currentTimeVibrate = 400;
                com.kolserdav.clock.Alert.currentDurationVibrate = 1100;
                com.kolserdav.clock.Alert.countVibrate++;
                break;
            case 5 :
                com.kolserdav.clock.Alert.currentTimeVibrate = 500;
                com.kolserdav.clock.Alert.currentDurationVibrate = 900;
                com.kolserdav.clock.Alert.countVibrate++;
                break;
            case 6 :
                com.kolserdav.clock.Alert.currentTimeVibrate = 600;
                com.kolserdav.clock.Alert.currentDurationVibrate = 700;
                com.kolserdav.clock.Alert.countVibrate++;
                break;
            case 7 :
                com.kolserdav.clock.Alert.currentTimeVibrate = 603;
                com.kolserdav.clock.Alert.currentDurationVibrate = 700;
                com.kolserdav.clock.Alert.countVibrate++;
                break;
            case 8 :
                com.kolserdav.clock.Alert.currentTimeVibrate = 700;
                com.kolserdav.clock.Alert.currentDurationVibrate = 700;
                com.kolserdav.clock.Alert.countVibrate++;
                break;
            case 9 :
                com.kolserdav.clock.Alert.currentTimeVibrate = 700;
                com.kolserdav.clock.Alert.currentDurationVibrate = 700;
                com.kolserdav.clock.Alert.countVibrate = 0;
        }
    }
}