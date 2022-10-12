package com.zt.core.base;
import com.zt.core.listener.HeadsetBroadcastReceiver;
public class PlayerAudioManager {
    private android.content.Context context;

    private android.media.AudioManager audioManager;

    private com.zt.core.base.IMediaPlayer mediaPlayer;

    private com.zt.core.listener.HeadsetBroadcastReceiver headsetBroadcastReceiver;

    protected final android.media.AudioManager.OnAudioFocusChangeListener onAudioFocusChangeListener = new android.media.AudioManager.OnAudioFocusChangeListener() {
        @java.lang.Override
        public void onAudioFocusChange(int focusChange) {
            switch (focusChange) {
                case android.media.AudioManager.AUDIOFOCUS_GAIN :
                    break;
                case android.media.AudioManager.AUDIOFOCUS_LOSS :
                    if (mediaPlayer != null) {
                        mediaPlayer.pause();
                    }
                    break;
                case android.media.AudioManager.AUDIOFOCUS_LOSS_TRANSIENT :
                    if (mediaPlayer != null) {
                        mediaPlayer.pause();
                    }
                    break;
                case android.media.AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK :
                    break;
            }
        }
    };

    private final android.content.BroadcastReceiver mediaActionReceiver = new android.content.BroadcastReceiver() {
        @java.lang.Override
        public void onReceive(android.content.Context context, android.content.Intent intent) {
            if (HeadsetBroadcastReceiver.MEDIA_ACTION.equals(intent.getAction())) {
                int mediaStatus = intent.getIntExtra(HeadsetBroadcastReceiver.MEDIA_KEY, -1);
                switch (mediaStatus) {
                    case com.zt.core.listener.HeadsetBroadcastReceiver.MEDIA_PAUSE :
                        if (mediaPlayer != null) {
                            mediaPlayer.pause();
                        }
                        break;
                    case com.zt.core.listener.HeadsetBroadcastReceiver.MEDIA_PLAY :
                        if (mediaPlayer != null) {
                            mediaPlayer.play();
                        }
                        break;
                }
            }
        }
    };

    public PlayerAudioManager(android.content.Context context, com.zt.core.base.IMediaPlayer mediaPlayer) {
        this.context = context;
        this.mediaPlayer = mediaPlayer;
        audioManager = ((android.media.AudioManager) (context.getSystemService(android.content.Context.AUDIO_SERVICE)));
        headsetBroadcastReceiver = new com.zt.core.listener.HeadsetBroadcastReceiver();
        registerHeadsetReceiver();
        registerMediaActionReceiver();
    }

    private void registerMediaActionReceiver() {
        android.content.IntentFilter intentFilter = new android.content.IntentFilter(com.zt.core.listener.HeadsetBroadcastReceiver.MEDIA_ACTION);
        context.registerReceiver(mediaActionReceiver, intentFilter);
    }

    private void unregisterMediaActionReceiver() {
        if (context != null) {
            context.unregisterReceiver(mediaActionReceiver);
        }
    }

    private void registerHeadsetReceiver() {
        android.content.IntentFilter intentFilter = new android.content.IntentFilter();
        intentFilter.addAction(android.media.AudioManager.ACTION_AUDIO_BECOMING_NOISY);
        context.registerReceiver(headsetBroadcastReceiver, intentFilter);
        android.content.ComponentName componentName = new android.content.ComponentName(context.getPackageName(), com.zt.core.listener.HeadsetBroadcastReceiver.class.getName());
        audioManager.registerMediaButtonEventReceiver(componentName);
    }

    public void unregisterHeadsetReceiver() {
        android.content.ComponentName componentName = new android.content.ComponentName(context.getPackageName(), com.zt.core.listener.HeadsetBroadcastReceiver.class.getName());
        audioManager.unregisterMediaButtonEventReceiver(componentName);
        context.unregisterReceiver(headsetBroadcastReceiver);
    }

    // 获取音频焦点
    public void requestAudioFocus() {
        AudioFocusRequest request = audioFocusRequestOreo.getAudioFocusRequest();
        audioManager.requestAudioFocus(request);
    }

    // 丢弃音频焦点
    public void abandonAudioFocus() {
        audioManager.abandonAudioFocus(onAudioFocusChangeListener);
    }

    // 获取最大音量
    public int getStreamMaxVolume() {
        return audioManager.getStreamMaxVolume(android.media.AudioManager.STREAM_MUSIC);
    }

    // 获取当前音量
    public int getStreamVolume() {
        return audioManager.getStreamVolume(android.media.AudioManager.STREAM_MUSIC);
    }

    // 设置音量
    public void setStreamVolume(int value) {
        audioManager.setStreamVolume(android.media.AudioManager.STREAM_MUSIC, value, 0);
    }

    public void destroy() {
        abandonAudioFocus();
        unregisterHeadsetReceiver();
        unregisterMediaActionReceiver();
    }
}