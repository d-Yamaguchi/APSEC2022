public boolean requestAudioFocus() {
    android.util.Log.d(com.tencent.wechat.manager.AudioWrapperManager.TAG, "requestAudioChannel");
    android.media.AudioManager _CVAR0 = audioManager;
    android.media.AudioManager.OnAudioFocusChangeListener _CVAR1 = mListener;
    int _CVAR2 = android.media.AudioManager.STREAM_MUSIC;
    int _CVAR3 = android.media.AudioManager.AUDIOFOCUS_GAIN;
    // if (curAudioFocus != AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
    int ret = _CVAR0.requestAudioFocus(_CVAR1, _CVAR2, _CVAR3);
    if (ret == android.media.AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
        com.tencent.wechat.manager.AudioWrapperManager.isPeempt = false;
        android.util.Log.d(com.tencent.wechat.manager.AudioWrapperManager.TAG, "wechat requestAudioFocus stream music gain granted");
    } else if (ret == android.media.AudioManager.AUDIOFOCUS_REQUEST_FAILED) {
        android.util.Log.d(com.tencent.wechat.manager.AudioWrapperManager.TAG, "wechat requestAudioFocus stream music gain failed");
    }
    return false;
}