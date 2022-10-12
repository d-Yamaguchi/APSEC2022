package kr.co.schoolholic.util;
public class SoundManager {
    public static void registerBTKeyEventReceiver(android.content.Context context, final android.content.ComponentName component) {
        final android.media.AudioManager audioManager = ((android.media.AudioManager) (context.getSystemService(android.content.Context.AUDIO_SERVICE)));
        audioManager.registerMediaButtonEventReceiver(component);
    }

    public static void unregisterBTKeyEventReceiver(android.content.Context context, android.content.ComponentName component) {
        android.media.AudioManager audioManager = ((android.media.AudioManager) (context.getSystemService(android.content.Context.AUDIO_SERVICE)));
        audioManager.unregisterMediaButtonEventReceiver(component);
    }

    public static void requestAudioFocus(android.content.Context context, android.media.AudioManager.OnAudioFocusChangeListener listener) {
        android.media.AudioManager audioManager = ((android.media.AudioManager) (context.getSystemService(android.content.Context.AUDIO_SERVICE)));
        AudioFocusRequest request = audioFocusRequestOreo.getAudioFocusRequest();
        audioManager.requestAudioFocus(request);
    }

    public static void abandonAudioFocus(android.content.Context context, android.media.AudioManager.OnAudioFocusChangeListener listener) {
        android.media.AudioManager audioManager = ((android.media.AudioManager) (context.getSystemService(android.content.Context.AUDIO_SERVICE)));
        audioManager.abandonAudioFocus(listener);
    }

    public static int getCurrentvolumeLevel(android.content.Context context) {
        android.media.AudioManager audioManager = ((android.media.AudioManager) (context.getSystemService(android.content.Context.AUDIO_SERVICE)));
        return audioManager.getStreamVolume(android.media.AudioManager.STREAM_MUSIC);
    }

    public static int getMaxvloumeLevel(android.content.Context context) {
        android.media.AudioManager audioManager = ((android.media.AudioManager) (context.getSystemService(android.content.Context.AUDIO_SERVICE)));
        return audioManager.getStreamMaxVolume(android.media.AudioManager.STREAM_MUSIC);
    }

    /* 2011.08.30. Added by Kevin.Park
    For check the current mode of sound.
     */
    public static boolean isMuteMode(android.content.Context context) {
        android.telephony.TelephonyManager tm = ((android.telephony.TelephonyManager) (context.getSystemService(android.content.Context.TELEPHONY_SERVICE)));
        android.media.AudioManager am = ((android.media.AudioManager) (context.getSystemService(android.content.Context.AUDIO_SERVICE)));
        // It's for SCMS.
        if (am.isBluetoothA2dpOn() == true) {
            // return true;
        }
        // 2011.10.06. Kevin.Park.
        return (tm.getCallState() == android.telephony.TelephonyManager.CALL_STATE_OFFHOOK) || (tm.getCallState() == android.telephony.TelephonyManager.CALL_STATE_RINGING);
    }

    public static boolean isCalling(android.content.Context context) {
        android.telephony.TelephonyManager tm = ((android.telephony.TelephonyManager) (context.getSystemService(android.content.Context.TELEPHONY_SERVICE)));
        return (tm.getCallState() == android.telephony.TelephonyManager.CALL_STATE_OFFHOOK) || (tm.getCallState() == android.telephony.TelephonyManager.CALL_STATE_RINGING);
    }

    public static int getRingerMode(android.content.Context context) {
        android.media.AudioManager audioManager = ((android.media.AudioManager) (context.getSystemService(android.content.Context.AUDIO_SERVICE)));
        if (audioManager != null)
            return audioManager.getRingerMode();

        return android.media.AudioManager.RINGER_MODE_SILENT;
    }

    public static boolean isWiredHeadsetOn(android.content.Context context) {
        android.media.AudioManager audioManager = ((android.media.AudioManager) (context.getSystemService(android.content.Context.AUDIO_SERVICE)));
        if (audioManager != null)
            return audioManager.isWiredHeadsetOn();

        return false;
    }

    public static boolean setForceUse(int state) {
        // First 1 == FOR_MEDIA, second 1 == FORCE_SPEAKER. To go back to the default
        // behavior, use FORCE_NONE (0).
        java.lang.Class<?> audioSystemClass = null;
        try {
            audioSystemClass = java.lang.Class.forName("android.media.AudioSystem");
        } catch (java.lang.ClassNotFoundException e1) {
            e1.printStackTrace();
        }
        java.lang.reflect.Method setForceUse = null;
        try {
            setForceUse = audioSystemClass.getMethod("setForceUse", int.class, int.class);
        } catch (java.lang.NoSuchMethodException e) {
            e.printStackTrace();
        }
        try {
            setForceUse.invoke(null, 1, state);
            return true;
        } catch (java.lang.IllegalAccessException e) {
            e.printStackTrace();
        } catch (java.lang.IllegalArgumentException e) {
            e.printStackTrace();
        } catch (java.lang.reflect.InvocationTargetException e) {
            e.printStackTrace();
        }
        return false;
    }
}