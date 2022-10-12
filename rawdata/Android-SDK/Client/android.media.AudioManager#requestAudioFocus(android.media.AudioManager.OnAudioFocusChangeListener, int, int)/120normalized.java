// 获取音频焦点
public boolean requestAudioFocus() {
    android.media.AudioManager _CVAR0 = audiomanager;
    android.media.AudioManager.OnAudioFocusChangeListener _CVAR1 = afChangeListener;
    int _CVAR2 = android.media.AudioManager.STREAM_MUSIC;
    int _CVAR3 = android.media.AudioManager.AUDIOFOCUS_GAIN;
    int result = _CVAR0.requestAudioFocus(_CVAR1, _CVAR2, _CVAR3);
    return result == android.media.AudioManager.AUDIOFOCUS_REQUEST_GRANTED;
}