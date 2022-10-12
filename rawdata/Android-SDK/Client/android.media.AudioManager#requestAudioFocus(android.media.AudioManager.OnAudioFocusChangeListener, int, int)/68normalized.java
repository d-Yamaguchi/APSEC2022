public static boolean startRing(android.content.Context c, java.lang.String filePath) {
    android.content.Context _CVAR0 = c;
    java.lang.String _CVAR1 = "audio";
    android.media.AudioManager audioManager = ((android.media.AudioManager) (_CVAR0.getSystemService(_CVAR1)));
    android.media.AudioManager _CVAR2 = audioManager;
    <nulltype> _CVAR3 = ((android.media.AudioManager.OnAudioFocusChangeListener) (null));
    int _CVAR4 = 2;
    int _CVAR5 = 2;
    _CVAR2.requestAudioFocus(_CVAR3, _CVAR4, _CVAR5);
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
                java.io.File file = new java.io.File(filePath);
                if (!file.exists()) {
                    return false;
                }
                com.telewave.lib.base.util.RingUtils.sMediaPlayer.setDataSource(filePath);
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
    return true;
}