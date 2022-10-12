// TODO(sanfin): Use the AudioFocusRequest version on O and above.
@java.lang.SuppressWarnings("deprecation")
public int requestAudioFocus(android.media.AudioManager.OnAudioFocusChangeListener l, int streamType, int durationHint) {
    android.media.AudioManager _CVAR0 = mAudioManager;
    android.media.AudioManager.OnAudioFocusChangeListener _CVAR1 = l;
    int _CVAR2 = streamType;
    int _CVAR3 = durationHint;
    int _CVAR4 = _CVAR0.requestAudioFocus(_CVAR1, _CVAR2, _CVAR3);
    return _CVAR4;
}