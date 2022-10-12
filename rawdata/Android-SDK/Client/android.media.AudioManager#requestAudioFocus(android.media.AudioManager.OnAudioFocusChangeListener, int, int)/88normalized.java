private static int request(android.content.Context cxt, android.media.AudioManager.OnAudioFocusChangeListener l, int streamType, int durationHint) {
    android.content.Context _CVAR0 = cxt;
    android.media.AudioManager _CVAR1 = js.lib.android.utils.AudioManagerUtil.getAudioMananger(_CVAR0);
    android.media.AudioManager.OnAudioFocusChangeListener _CVAR2 = l;
    int _CVAR3 = streamType;
    int _CVAR4 = durationHint;
    int _CVAR5 = _CVAR1.requestAudioFocus(_CVAR2, _CVAR3, _CVAR4);
    return _CVAR5;
}