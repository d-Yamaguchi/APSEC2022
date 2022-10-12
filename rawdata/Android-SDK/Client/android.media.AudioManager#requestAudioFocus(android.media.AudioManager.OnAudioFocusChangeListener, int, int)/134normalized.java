/**
 * 申请获取音频焦点
 */
private boolean requestAudioFocus() {
    android.media.AudioManager _CVAR0 = audioManager;
    android.media.AudioManager.OnAudioFocusChangeListener _CVAR1 = audioFocusListener;
    int _CVAR2 = android.media.AudioManager.STREAM_MUSIC;
    int _CVAR3 = android.media.AudioManager.AUDIOFOCUS_GAIN;
    int requestResult = _CVAR0.requestAudioFocus(_CVAR1, _CVAR2, _CVAR3);
    // 返回是否授权
    return requestResult == android.media.AudioManager.AUDIOFOCUS_REQUEST_GRANTED;
}