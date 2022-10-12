package com.telewave.lib.base.util;
/**
 *
 *
 * @author liwh
 * @date 2019/6/13
 */
public class RingUtils {
    static android.media.MediaPlayer sMediaPlayer;

    static android.os.Vibrator sVibrator;

    private static final long[] VIBRATE_PATTERN = new long[]{ 1000L, 1000L };

    public RingUtils() {
    }

    public static boolean startRing(android.content.Context c, java.lang.String filePath) {
        android.media.AudioManager audioManager = ((android.media.AudioManager) (c.getSystemService("audio")));
        AudioFocusRequest request = audioFocusRequestOreo.getAudioFocusRequest();
        audioManager.requestAudioFocus(request);
        int mode = audioManager.getRingerMode();
        __SmPLUnsupported__(0);
        return true;
    }

    public static void startRingBack(android.content.Context c, java.lang.String fileName) {
        if (com.telewave.lib.base.util.RingUtils.sMediaPlayer == null) {
            com.telewave.lib.base.util.RingUtils.sMediaPlayer = new android.media.MediaPlayer();
        } else if (com.telewave.lib.base.util.RingUtils.sMediaPlayer.isPlaying()) {
            com.telewave.lib.base.util.RingUtils.sMediaPlayer.stop();
            com.telewave.lib.base.util.RingUtils.sMediaPlayer.reset();
        }
        com.telewave.lib.base.util.RingUtils.sMediaPlayer.setLooping(true);
        com.telewave.lib.base.util.RingUtils.sMediaPlayer.setAudioStreamType(0);
        android.media.AudioManager audioManager = ((android.media.AudioManager) (c.getSystemService("audio")));
        audioManager.requestAudioFocus(((android.media.AudioManager.OnAudioFocusChangeListener) (null)), 0, 2);
        try {
            android.content.res.AssetFileDescriptor afd = c.getAssets().openFd(fileName);
            com.telewave.lib.base.util.RingUtils.sMediaPlayer.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
            afd.close();
            com.telewave.lib.base.util.RingUtils.sMediaPlayer.prepare();
            com.telewave.lib.base.util.RingUtils.sMediaPlayer.start();
        } catch (java.lang.IllegalArgumentException var4) {
            var4.printStackTrace();
        } catch (java.lang.IllegalStateException var5) {
            var5.printStackTrace();
        } catch (java.io.IOException var6) {
            var6.printStackTrace();
        }
    }

    public static boolean startRing(android.content.Context c, int resid) {
        android.media.AudioManager audioManager = ((android.media.AudioManager) (c.getSystemService("audio")));
        audioManager.requestAudioFocus(((android.media.AudioManager.OnAudioFocusChangeListener) (null)), 2, 2);
        int mode = audioManager.getRingerMode();
        switch (mode) {
            case 0 :
            default :
                break;
            case 1 :
                if (com.telewave.lib.base.util.RingUtils.sVibrator == null) {
                    com.telewave.lib.base.util.RingUtils.sVibrator = ((android.os.Vibrator) (c.getSystemService("vibrator")));
                }
                com.telewave.lib.base.util.RingUtils.sVibrator.vibrate(com.telewave.lib.base.util.RingUtils.VIBRATE_PATTERN, 0);
                break;
            case 2 :
                if (com.telewave.lib.base.util.RingUtils.sVibrator == null) {
                    com.telewave.lib.base.util.RingUtils.sVibrator = ((android.os.Vibrator) (c.getSystemService("vibrator")));
                }
                try {
                    int value = android.provider.Settings.System.getInt(c.getContentResolver(), "vibrate_when_ringing");
                    if (value == 1) {
                        com.telewave.lib.base.util.RingUtils.sVibrator.vibrate(com.telewave.lib.base.util.RingUtils.VIBRATE_PATTERN, 0);
                    }
                } catch (android.provider.Settings.SettingNotFoundException var8) {
                    com.telewave.lib.base.util.RingUtils.sVibrator.vibrate(com.telewave.lib.base.util.RingUtils.VIBRATE_PATTERN, 0);
                }
                if (com.telewave.lib.base.util.RingUtils.sMediaPlayer == null) {
                    com.telewave.lib.base.util.RingUtils.sMediaPlayer = new android.media.MediaPlayer();
                } else if (com.telewave.lib.base.util.RingUtils.sMediaPlayer.isPlaying()) {
                    com.telewave.lib.base.util.RingUtils.sMediaPlayer.stop();
                    com.telewave.lib.base.util.RingUtils.sMediaPlayer.reset();
                }
                com.telewave.lib.base.util.RingUtils.sMediaPlayer.setLooping(true);
                com.telewave.lib.base.util.RingUtils.sMediaPlayer.setAudioStreamType(2);
                try {
                    android.content.res.AssetFileDescriptor afd = c.getResources().openRawResourceFd(resid);
                    if (afd == null) {
                        return false;
                    }
                    com.telewave.lib.base.util.RingUtils.sMediaPlayer.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
                    afd.close();
                    com.telewave.lib.base.util.RingUtils.sMediaPlayer.prepare();
                    com.telewave.lib.base.util.RingUtils.sMediaPlayer.start();
                } catch (java.lang.IllegalArgumentException var5) {
                    var5.printStackTrace();
                    return false;
                } catch (java.lang.IllegalStateException var6) {
                    var6.printStackTrace();
                    return false;
                } catch (java.io.IOException var7) {
                    var7.printStackTrace();
                    return false;
                }
        }
        return true;
    }

    public static void playAudio(android.content.Context c, int resid, boolean isLoop) {
        if (com.telewave.lib.base.util.RingUtils.sMediaPlayer == null) {
            com.telewave.lib.base.util.RingUtils.sMediaPlayer = new android.media.MediaPlayer();
        } else if (com.telewave.lib.base.util.RingUtils.sMediaPlayer.isPlaying()) {
            com.telewave.lib.base.util.RingUtils.sMediaPlayer.stop();
        }
        com.telewave.lib.base.util.RingUtils.sMediaPlayer.reset();
        com.telewave.lib.base.util.RingUtils.sMediaPlayer.setLooping(isLoop);
        com.telewave.lib.base.util.RingUtils.sMediaPlayer.setAudioStreamType(0);
        android.media.AudioManager audioManager = ((android.media.AudioManager) (c.getSystemService("audio")));
        audioManager.requestAudioFocus(((android.media.AudioManager.OnAudioFocusChangeListener) (null)), 0, 2);
        try {
            android.content.res.AssetFileDescriptor afd = c.getResources().openRawResourceFd(resid);
            com.telewave.lib.base.util.RingUtils.sMediaPlayer.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
            afd.close();
            com.telewave.lib.base.util.RingUtils.sMediaPlayer.prepare();
            com.telewave.lib.base.util.RingUtils.sMediaPlayer.start();
        } catch (java.lang.IllegalArgumentException var5) {
            var5.printStackTrace();
        } catch (java.lang.IllegalStateException var6) {
            var6.printStackTrace();
        } catch (java.io.IOException var7) {
            var7.printStackTrace();
        }
    }

    public static void stop() {
        if (com.telewave.lib.base.util.RingUtils.sMediaPlayer != null) {
            com.telewave.lib.base.util.RingUtils.sMediaPlayer.stop();
            com.telewave.lib.base.util.RingUtils.sMediaPlayer.reset();
            com.telewave.lib.base.util.RingUtils.sMediaPlayer.release();
            com.telewave.lib.base.util.RingUtils.sMediaPlayer = null;
        }
        if (com.telewave.lib.base.util.RingUtils.sVibrator != null) {
            com.telewave.lib.base.util.RingUtils.sVibrator.cancel();
            com.telewave.lib.base.util.RingUtils.sVibrator = null;
        }
    }
}