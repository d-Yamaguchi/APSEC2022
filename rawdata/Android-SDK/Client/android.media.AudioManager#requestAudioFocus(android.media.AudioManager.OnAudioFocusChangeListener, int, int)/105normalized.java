// 要请求音频焦点，你必须从AudioManager mAudioMgr调用requestAudioFocus()
private void requestAudioFocus() {
    if (android.os.Build.VERSION.SDK_INT <= android.os.Build.VERSION_CODES.ECLAIR_MR1) {
        return;
    }
    if (audioManager == null) {
        audioManager = ((android.media.AudioManager) (getSystemService(android.content.Context.AUDIO_SERVICE)));
    }
    if (audioManager != null) {
        android.util.Log.i(com.ethanco.mediatest.MainActivity.TAG, "Request audio focus");
        android.media.AudioManager _CVAR0 = audioManager;
        android.media.AudioManager.OnAudioFocusChangeListener _CVAR1 = mAudioFocusChangeListener;
        int _CVAR2 = android.media.AudioManager.STREAM_MUSIC;
        int _CVAR3 = android.media.AudioManager.AUDIOFOCUS_GAIN;
        int ret = _CVAR0.requestAudioFocus(_CVAR1, _CVAR2, _CVAR3);
        if (ret != android.media.AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
            android.util.Log.i(com.ethanco.mediatest.MainActivity.TAG, "request audio focus fail. " + ret);
        }
    }
}