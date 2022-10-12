public boolean requestAudioFocus() {
    tw.firemaples.onscreenocr.utils.AudioFocusManager.logger.debug("requestAudioFocus()");
    if (isRequesting) {
        tw.firemaples.onscreenocr.utils.AudioFocusManager.logger.debug("Current is requesting, ignore this action");
        return true;
    }
    android.media.AudioManager _CVAR0 = audioManager;
    android.media.AudioManager.OnAudioFocusChangeListener _CVAR1 = onAudioFocusChangeListener;
    int _CVAR2 = android.media.AudioManager.STREAM_MUSIC;
    int _CVAR3 = android.media.AudioManager.AUDIOFOCUS_GAIN_TRANSIENT;
    // isOtherMusicAppPlaying = audioManager.isMusicActive();
    int result = _CVAR0.requestAudioFocus(_CVAR1, _CVAR2, _CVAR3);
    isRequesting = true;
    boolean resultBoolean = result == android.media.AudioManager.AUDIOFOCUS_REQUEST_GRANTED;
    tw.firemaples.onscreenocr.utils.AudioFocusManager.logger.debug("requestAudioFocus(), result: " + resultBoolean);
    // if (isOtherMusicAppPlaying) {
    // sendMediaPauseButton();
    // }
    return resultBoolean;
}