/**
 * 申请临时焦点，暂停其他应用播放, 调用{@link #abandonAudioFocus(Context, AudioManager.OnAudioFocusChangeListener)}可恢复播放。
 *
 * @return 是否抢焦点成功，如果为{@link AudioManager#AUDIOFOCUS_GAIN}则代表抢焦点成功，
其他则不成功。
 */
public static int requestAudioFocusTransient(android.content.Context context, android.media.AudioManager.OnAudioFocusChangeListener listener) {
    android.util.Log.d(com.readboy.mathproblem.util.AudioUtils.TAG, "requestAudioFocusTransient: ");
    android.content.Context _CVAR0 = context;
    android.content.Context _CVAR1 = _CVAR0.getApplicationContext();
    java.lang.String _CVAR2 = android.content.Context.AUDIO_SERVICE;
    android.media.AudioManager sAudioManager = ((android.media.AudioManager) (_CVAR1.getSystemService(_CVAR2)));
    if (sAudioManager != null) {
        android.media.AudioManager _CVAR3 = sAudioManager;
        android.media.AudioManager.OnAudioFocusChangeListener _CVAR4 = listener;
        int _CVAR5 = android.media.AudioManager.STREAM_MUSIC;
        int _CVAR6 = android.media.AudioManager.AUDIOFOCUS_GAIN_TRANSIENT;
        int ret = _CVAR3.requestAudioFocus(_CVAR4, _CVAR5, _CVAR6);
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