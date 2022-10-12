package com.music.player.lib.manager;
import com.music.player.lib.util.Logger;
/**
 * TinyHung@Outlook.com
 * 2018/3/10.
 * AudioFocusManager
 * 音频监听器，当音频输出焦点被其他 MediaPlayer 实例抢占，则暂停播放，重新获取到音频输出焦点，自动恢复播放
 */
public final class MusicAudioFocusManager {
    public static final java.lang.String TAG = "MusicAudioFocusManager";

    private int mVolumeWhenFocusLossTransientCanDuck;

    private android.media.AudioManager mAudioManager;

    public MusicAudioFocusManager(android.content.Context context) {
        mAudioManager = ((android.media.AudioManager) (context.getSystemService(android.content.Context.AUDIO_SERVICE)));
    }

    /**
     * 请求音频焦点
     */
    public int requestAudioFocus(com.music.player.lib.manager.MusicAudioFocusManager.OnAudioFocusListener focusListener) {
        if (null != focusListener) {
            this.mFocusListener = focusListener;
        }
        if (null != mAudioManager) {
            AudioFocusRequest request = audioFocusRequestOreo.getAudioFocusRequest();
            int requestAudioFocus = mAudioManager.requestAudioFocus(request);
            return requestAudioFocus;
        }
        return 1;
    }

    /**
     * 停止播放释放音频焦点
     */
    public void releaseAudioFocus() {
        if ((null != mAudioManager) && (null != onAudioFocusChangeListener)) {
            mAudioManager.abandonAudioFocus(onAudioFocusChangeListener);
        }
    }

    private android.media.AudioManager.OnAudioFocusChangeListener onAudioFocusChangeListener = new android.media.AudioManager.OnAudioFocusChangeListener() {
        @java.lang.Override
        public void onAudioFocusChange(int focusChange) {
            com.music.player.lib.util.Logger.d(com.music.player.lib.manager.MusicAudioFocusManager.TAG, "onAudioFocusChange:focusChange:" + focusChange);
            int volume;
            switch (focusChange) {
                // 重新获取到了焦点
                case android.media.AudioManager.AUDIOFOCUS_GAIN :
                case android.media.AudioManager.AUDIOFOCUS_GAIN_TRANSIENT :
                case android.media.AudioManager.AUDIOFOCUS_GAIN_TRANSIENT_MAY_DUCK :
                    com.music.player.lib.util.Logger.d(com.music.player.lib.manager.MusicAudioFocusManager.TAG, "重新获取到了焦点");
                    volume = mAudioManager.getStreamVolume(android.media.AudioManager.STREAM_MUSIC);
                    if ((mVolumeWhenFocusLossTransientCanDuck > 0) && (volume == (mVolumeWhenFocusLossTransientCanDuck / 2))) {
                        // 恢复音量
                        mAudioManager.setStreamVolume(android.media.AudioManager.STREAM_MUSIC, mVolumeWhenFocusLossTransientCanDuck, android.media.AudioManager.FLAG_REMOVE_SOUND_AND_VIBRATE);
                    }
                    // 恢复播放
                    if (null != mFocusListener) {
                        mFocusListener.onFocusGet();
                    }
                    break;
                    // 被其他播放器抢占
                case android.media.AudioManager.AUDIOFOCUS_LOSS :
                    com.music.player.lib.util.Logger.d(com.music.player.lib.manager.MusicAudioFocusManager.TAG, "被其他播放器抢占");
                    if (null != mFocusListener) {
                        mFocusListener.onFocusOut();
                    }
                    break;
                    // 暂时失去焦点，例如来电占用音频输出
                case android.media.AudioManager.AUDIOFOCUS_LOSS_TRANSIENT :
                    com.music.player.lib.util.Logger.d(com.music.player.lib.manager.MusicAudioFocusManager.TAG, "暂时失去焦点");
                    if (null != mFocusListener) {
                        mFocusListener.onFocusOut();
                    }
                    break;
                    // 瞬间失去焦点，例如通知占用了音频输出
                case android.media.AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK :
                    com.music.player.lib.util.Logger.d(com.music.player.lib.manager.MusicAudioFocusManager.TAG, "瞬间失去焦点");
                    if ((null != mFocusListener) && mFocusListener.isPlaying()) {
                        volume = mAudioManager.getStreamVolume(android.media.AudioManager.STREAM_MUSIC);
                        if (volume > 0) {
                            mVolumeWhenFocusLossTransientCanDuck = volume;
                            mAudioManager.setStreamVolume(android.media.AudioManager.STREAM_MUSIC, mVolumeWhenFocusLossTransientCanDuck / 2, android.media.AudioManager.FLAG_REMOVE_SOUND_AND_VIBRATE);
                        }
                    }
                    break;
            }
        }
    };

    public interface OnAudioFocusListener {
        /**
         * 恢复焦点
         */
        void onFocusGet();

        /**
         * 失去焦点
         */
        void onFocusOut();

        /**
         * 内部播放器是否正在播放
         *
         * @return 为true正在播放
         */
        boolean isPlaying();
    }

    public com.music.player.lib.manager.MusicAudioFocusManager.OnAudioFocusListener mFocusListener;

    public void onDestroy() {
        mAudioManager = null;
    }
}