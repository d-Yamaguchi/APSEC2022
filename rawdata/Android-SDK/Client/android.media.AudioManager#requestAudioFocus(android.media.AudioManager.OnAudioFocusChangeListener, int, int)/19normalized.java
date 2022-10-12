/**
 *
 *
 * @Description 获取audio焦点
 * @author zhudedian
 * @time 2018/7/9  14:03
 */
public void requestAudioFocus(android.media.AudioManager.OnAudioFocusChangeListener listener) {
    if (listener != null) {
        android.media.AudioManager _CVAR0 = audioManager;
        android.media.AudioManager.OnAudioFocusChangeListener _CVAR1 = listener;
        int _CVAR2 = android.media.AudioManager.STREAM_MUSIC;
        int _CVAR3 = android.media.AudioManager.AUDIOFOCUS_GAIN_TRANSIENT;
        _CVAR0.requestAudioFocus(_CVAR1, _CVAR2, _CVAR3);
    } else {
        android.media.AudioManager _CVAR4 = audioManager;
        android.media.AudioManager.OnAudioFocusChangeListener _CVAR5 = audioFocusListener;
        int _CVAR6 = android.media.AudioManager.STREAM_MUSIC;
        int _CVAR7 = android.media.AudioManager.AUDIOFOCUS_GAIN_TRANSIENT;
        _CVAR4.requestAudioFocus(_CVAR5, _CVAR6, _CVAR7);
    }
}