private void requestAudioFocus() {
    android.media.AudioManager _CVAR0 = mAudioManager;
    android.media.AudioManager.OnAudioFocusChangeListener _CVAR1 = mAudioFocusChangeListener;
    int _CVAR2 = mStreamType;
    int _CVAR3 = mDurationHint;
    int result = _CVAR0.requestAudioFocus(_CVAR1, _CVAR2, _CVAR3);
    // 
    if (android.media.AudioManager.AUDIOFOCUS_REQUEST_GRANTED == result) {
        com.novelot.mediasession.MediaSessionManager.getInstance().registerMediaSession(new com.novelot.mediasession.MediaSessionImpl(getApplicationContext(), new com.novelot.mediasession.BaseMediaSessionCallback()));
        com.novelot.lib.player.framework.PlayerUtils.getInstance().play();
    }
    // 
    showFocusResult(result);
}