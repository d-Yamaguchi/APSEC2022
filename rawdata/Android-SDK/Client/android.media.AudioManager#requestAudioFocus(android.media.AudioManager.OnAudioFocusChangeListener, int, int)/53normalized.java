public int requestAudioFocus(android.media.AudioManager.OnAudioFocusChangeListener listener, int streamType, int durationHint) {
    android.media.AudioManager _CVAR0 = getAudioManager();
    android.media.AudioManager.OnAudioFocusChangeListener _CVAR1 = listener;
    int _CVAR2 = android.media.AudioManager.STREAM_MUSIC;
    int _CVAR3 = android.media.AudioManager.AUDIOFOCUS_GAIN;
    int _CVAR4 = _CVAR0.requestAudioFocus(_CVAR1, _CVAR2, _CVAR3);
    return _CVAR4;
}