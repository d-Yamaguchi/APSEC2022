@java.lang.Override
public void requestAudioFocus(android.media.AudioManager audioManager) {
    android.media.AudioManager _CVAR0 = audioManager;
    android.media.AudioManager.OnAudioFocusChangeListener _CVAR1 = com.ichi2.compat.CompatV8.afChangeListener;
    int _CVAR2 = android.media.AudioManager.STREAM_MUSIC;
    int _CVAR3 = android.media.AudioManager.AUDIOFOCUS_GAIN_TRANSIENT_MAY_DUCK;
    _CVAR0.requestAudioFocus(_CVAR1, _CVAR2, _CVAR3);
}