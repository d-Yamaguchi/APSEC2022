package com.readboy.mathproblem.util;
/**
 * Created by oubin on 2017/6/19.
 */
public class AudioUtils {
    private static final java.lang.String TAG = "AudioUtils";

    private AudioUtils() {
        android.util.Log.e(com.readboy.mathproblem.util.AudioUtils.TAG, "AudioUtils: u can not create me...");
        throw new java.lang.UnsupportedOperationException();
    }

    /**
     * 申请临时焦点，暂停其他应用播放, 调用{@link #abandonAudioFocus(Context, AudioManager.OnAudioFocusChangeListener)}可恢复播放。
     *
     * @return 是否抢焦点成功，如果为{@link AudioManager#AUDIOFOCUS_GAIN}则代表抢焦点成功，
    其他则不成功。
     */
    public static int requestAudioFocusTransient(android.content.Context context, android.media.AudioManager.OnAudioFocusChangeListener listener) {
        android.util.Log.d(com.readboy.mathproblem.util.AudioUtils.TAG, "requestAudioFocusTransient: ");
        android.media.AudioManager sAudioManager = ((android.media.AudioManager) (context.getApplicationContext().getSystemService(android.content.Context.AUDIO_SERVICE)));
        if (sAudioManager != null) {
            AudioFocusRequest request = audioFocusRequestOreo.getAudioFocusRequest();
            int ret = sAudioManager.requestAudioFocus(request);
            android.util.Log.d(com.readboy.mathproblem.util.AudioUtils.TAG, "abandonAudioFocus: ret = " + ret);
            android.util.Log.e(com.readboy.mathproblem.util.AudioUtils.TAG, "requestAudioFocusTransient: ret = " + ret);
            if (ret == android.media.AudioManager.AUDIOFOCUS_REQUEST_FAILED) {
                android.util.Log.e(com.readboy.mathproblem.util.AudioUtils.TAG, "requestAudioFocus fail: ret = " + ret);
            }
            return ret;
        }
        android.util.Log.e(com.readboy.mathproblem.util.AudioUtils.TAG, "requestAudioFocus: valueAt audio service fail");
        return android.media.AudioManager.AUDIOFOCUS_REQUEST_FAILED;
    }

    /**
     * 获取播放焦点，会停止其他应用播放
     *
     * @return 是否抢焦点成功，如果为{@link AudioManager#AUDIOFOCUS_GAIN}代表抢焦点成功，反之。
     */
    public static int requestAudioFocus(android.content.Context context) {
        android.util.Log.d(com.readboy.mathproblem.util.AudioUtils.TAG, "requestAudioFocus: request audio focus");
        android.media.AudioManager sAudioManager = ((android.media.AudioManager) (context.getApplicationContext().getSystemService(android.content.Context.AUDIO_SERVICE)));
        if (sAudioManager != null) {
            int ret = sAudioManager.requestAudioFocus(null, android.media.AudioManager.STREAM_MUSIC, android.media.AudioManager.AUDIOFOCUS_GAIN);
            android.util.Log.d(com.readboy.mathproblem.util.AudioUtils.TAG, "abandonAudioFocus: ret = " + ret);
            if (ret == android.media.AudioManager.AUDIOFOCUS_REQUEST_FAILED) {
                android.util.Log.e(com.readboy.mathproblem.util.AudioUtils.TAG, "requestAudioFocus fail: ret = " + ret);
            }
            return ret;
        }
        android.util.Log.e(com.readboy.mathproblem.util.AudioUtils.TAG, "requestAudioFocus: valueAt audio service fail");
        return android.media.AudioManager.AUDIOFOCUS_REQUEST_FAILED;
    }

    public static int requestAudioFocusExclusive(android.content.Context context) {
        android.media.AudioManager audioManager = ((android.media.AudioManager) (context.getSystemService(android.content.Context.AUDIO_SERVICE)));
        if (audioManager != null) {
            int ret = audioManager.requestAudioFocus(com.readboy.mathproblem.util.AudioUtils.mAudioFocusChangeListener, android.media.AudioManager.STREAM_MUSIC, android.media.AudioManager.AUDIOFOCUS_GAIN_TRANSIENT_EXCLUSIVE);
            android.util.Log.e(com.readboy.mathproblem.util.AudioUtils.TAG, "requestAudio: ret = " + ret);
        }
        return android.media.AudioManager.AUDIOFOCUS_REQUEST_FAILED;
    }

    public static int requestAudioFocusMayDuck(android.content.Context context) {
        android.media.AudioManager audioManager = ((android.media.AudioManager) (context.getSystemService(android.content.Context.AUDIO_SERVICE)));
        if (audioManager != null) {
            int ret = audioManager.requestAudioFocus(com.readboy.mathproblem.util.AudioUtils.mAudioFocusChangeListener, android.media.AudioManager.STREAM_MUSIC, android.media.AudioManager.AUDIOFOCUS_GAIN_TRANSIENT_MAY_DUCK);
            android.util.Log.e(com.readboy.mathproblem.util.AudioUtils.TAG, "requestAudio: ret = " + ret);
            return ret;
        }
        return android.media.AudioManager.AUDIOFOCUS_REQUEST_FAILED;
    }

    /**
     * 恢复播放
     *
     * @return 是否抢焦点成功，如果为{@link AudioManager#AUDIOFOCUS_GAIN}代表抢焦点成功，反之。
     */
    public static int abandonAudioFocus(android.content.Context context, android.media.AudioManager.OnAudioFocusChangeListener listener) {
        android.util.Log.d(com.readboy.mathproblem.util.AudioUtils.TAG, "abandonAudioFocus: abandon audio focus");
        android.media.AudioManager sAudioManager = ((android.media.AudioManager) (context.getApplicationContext().getSystemService(android.content.Context.AUDIO_SERVICE)));
        if (sAudioManager != null) {
            int ret = sAudioManager.abandonAudioFocus(listener);
            android.util.Log.d(com.readboy.mathproblem.util.AudioUtils.TAG, "abandonAudioFocus: ret = " + ret);
            if (ret == android.media.AudioManager.AUDIOFOCUS_REQUEST_FAILED) {
                android.util.Log.e(com.readboy.mathproblem.util.AudioUtils.TAG, "abandonAudioFocus fail: ret = " + ret);
            }
            return ret;
        }
        android.util.Log.e(com.readboy.mathproblem.util.AudioUtils.TAG, "abandonAudioFocus: valueAt audio service fail");
        return android.media.AudioManager.AUDIOFOCUS_REQUEST_FAILED;
    }

    private static android.media.AudioManager.OnAudioFocusChangeListener mAudioFocusChangeListener = new android.media.AudioManager.OnAudioFocusChangeListener() {
        @java.lang.Override
        public void onAudioFocusChange(int focusChange) {
            android.util.Log.e(com.readboy.mathproblem.util.AudioUtils.TAG, "onAudioFocusChange: focus = " + focusChange);
            if (android.media.AudioManager.AUDIOFOCUS_LOSS == focusChange) {
                // doSomething,
            }
        }
    };
}