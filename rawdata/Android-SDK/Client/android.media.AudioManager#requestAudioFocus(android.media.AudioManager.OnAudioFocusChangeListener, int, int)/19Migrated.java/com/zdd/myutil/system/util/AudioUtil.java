package com.zdd.myutil.system.util;
/**
 * create by zhudedian on 2018/7/9.
 */
public class AudioUtil {
    private static com.zdd.myutil.system.util.AudioUtil audioUtil;

    public static void init(android.app.Activity activity) {
        com.zdd.myutil.system.util.AudioUtil.audioUtil = new com.zdd.myutil.system.util.AudioUtil(activity);
    }

    public static void closed(android.app.Activity activity) {
        com.zdd.myutil.system.util.AudioUtil.audioUtil.destroy(activity);
    }

    public static com.zdd.myutil.system.util.AudioUtil getAudioUtil() {
        if (com.zdd.myutil.system.util.AudioUtil.audioUtil == null) {
            throw new java.lang.NullPointerException("Maybe you forget called AudioUtil.init(Context context)");
        }
        return com.zdd.myutil.system.util.AudioUtil.audioUtil;
    }

    private android.media.AudioManager audioManager;

    private android.media.AudioManager.OnAudioFocusChangeListener audioFocusListener;

    public AudioUtil(android.app.Activity activity) {
        audioManager = ((android.media.AudioManager) (activity.getSystemService(android.content.Context.AUDIO_SERVICE)));
        audioFocusListener = new android.media.AudioManager.OnAudioFocusChangeListener() {
            @java.lang.Override
            public void onAudioFocusChange(int focusChange) {
            }
        };
        android.content.IntentFilter intentFilter = new android.content.IntentFilter();
        intentFilter.addAction("android.media.VOLUME_CHANGED_ACTION");
        activity.registerReceiver(volumeReceiver, intentFilter);
    }

    /**
     *
     *
     * @Description 设置音量
     * @author zhudedian
     * @time 2018/7/9  13:57
     */
    public void setStreamVolume(int streamType, int volume) {
        audioManager.setStreamVolume(streamType, volume, android.media.AudioManager.FLAG_SHOW_UI);
    }

    /**
     *
     *
     * @Description 设置是否静音
     * @author zhudedian
     * @time 2018/7/9  14:15
     */
    public void setStreamMute(int streamType, final boolean isMute) {
        audioManager.setStreamMute(streamType, isMute);
    }

    /**
     *
     *
     * @Description 获取当前音量
     * @author zhudedian
     * @time 2018/7/9  13:59
     */
    public int getStreamVolume(int streamType) {
        return audioManager.getStreamVolume(streamType);
    }

    /**
     *
     *
     * @Description 获取音量最大值
     * @author zhudedian
     * @time 2018/7/11  16:48
     */
    public int getStreamMaxVolume(int streamType) {
        return audioManager.getStreamMaxVolume(streamType);
    }

    /**
     *
     *
     * @Description 获取audio焦点
     * @author zhudedian
     * @time 2018/7/9  14:03
     */
    public void requestAudioFocus(android.media.AudioManager.OnAudioFocusChangeListener listener) {
        if (listener != null) {
            AudioFocusRequest request = audioFocusRequestOreo.getAudioFocusRequest();
            audioManager.requestAudioFocus(request);
        } else {
            audioManager.requestAudioFocus(audioFocusListener, android.media.AudioManager.STREAM_MUSIC, android.media.AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
        }
    }

    /**
     *
     *
     * @Description 释放audio焦点
     * @author zhudedian
     * @time 2018/7/9  14:05
     */
    public void abandonAudioFocus(android.media.AudioManager.OnAudioFocusChangeListener listener) {
        if (listener != null) {
            audioManager.abandonAudioFocus(listener);
        } else {
            audioManager.abandonAudioFocus(audioFocusListener);
        }
    }

    public void destroy(android.app.Activity activity) {
        activity.unregisterReceiver(volumeReceiver);
    }

    private com.zdd.myutil.system.util.AudioUtil.VolumeReceiver volumeReceiver = new com.zdd.myutil.system.util.AudioUtil.VolumeReceiver();

    private class VolumeReceiver extends android.content.BroadcastReceiver {
        @java.lang.Override
        public void onReceive(android.content.Context context, android.content.Intent intent) {
            int streamType = intent.getIntExtra("android.media.EXTRA_VOLUME_STREAM_TYPE", 0);
            int volume = intent.getIntExtra("android.media.EXTRA_VOLUME_STREAM_VALUE", 0);
            int oldVolume = intent.getIntExtra("android.media.EXTRA_PREV_VOLUME_STREAM_VALUE", 0);
            if (volumeChangeListeners != null) {
                for (int i = 0; i < volumeChangeListeners.size(); i++) {
                    volumeChangeListeners.get(i).onChange(streamType, volume, oldVolume);
                }
            }
            // if (streamType == AudioManager.STREAM_MUSIC){
            // SpeechUtil.sendEvent(Event.getSpeakerVolumeChangedEvent(Math.round(((float)volume)*100/getStreamMaxVolume(AudioManager.STREAM_MUSIC)),volume == 0));
            // }else if (streamType == AudioManager.STREAM_RING){
            // SpeechUtil.sendEvent(Event.getAlertsVolumeChangedEvent(Math.round(((float)volume)*100/getStreamMaxVolume(AudioManager.STREAM_RING))));
            // }
            android.util.Log.i("edong", (((("streamType=" + streamType) + ",volume=") + volume) + ",oldVolume=") + oldVolume);
        }
    }

    private java.util.List<com.zdd.myutil.system.util.AudioUtil.VolumeChangeListener> volumeChangeListeners;

    public void addVolumeChangeListener(com.zdd.myutil.system.util.AudioUtil.VolumeChangeListener listener) {
        if (volumeChangeListeners == null) {
            volumeChangeListeners = new java.util.ArrayList<>();
        }
        if (!volumeChangeListeners.contains(listener)) {
            volumeChangeListeners.add(listener);
        }
    }

    public void removeVolumeChangeListener(com.zdd.myutil.system.util.AudioUtil.VolumeChangeListener listener) {
        if (volumeChangeListeners != null) {
            volumeChangeListeners.remove(listener);
        }
    }

    public interface VolumeChangeListener {
        void onChange(int streamType, int volume, int oldVolume);
    }
}