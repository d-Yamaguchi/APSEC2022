private void requestAudioFocus() {
    pl.orangelabs.log.Log.d(this, "requestAudioFocus");
    onAudioFocusChangeListener = ( focusChange) -> {
    };
    android.media.AudioManager _CVAR0 = audioManager;
    android.media.AudioManager.OnAudioFocusChangeListener _CVAR1 = onAudioFocusChangeListener;
    int _CVAR2 = android.media.AudioManager.STREAM_MUSIC;
    int _CVAR3 = android.media.AudioManager.AUDIOFOCUS_GAIN_TRANSIENT;
    _CVAR0.requestAudioFocus(_CVAR1, _CVAR2, _CVAR3);
}