public static void requestAudioFocus(android.content.Context context, android.media.AudioManager.OnAudioFocusChangeListener listener) {
    android.content.Context _CVAR0 = context;
    java.lang.String _CVAR1 = android.content.Context.AUDIO_SERVICE;
    android.media.AudioManager audioManager = ((android.media.AudioManager) (_CVAR0.getSystemService(_CVAR1)));
    android.media.AudioManager _CVAR2 = audioManager;
    android.media.AudioManager.OnAudioFocusChangeListener _CVAR3 = listener;
    int _CVAR4 = android.media.AudioManager.STREAM_MUSIC;
    int _CVAR5 = android.media.AudioManager.AUDIOFOCUS_GAIN;
    _CVAR2.requestAudioFocus(_CVAR3, _CVAR4, _CVAR5);
}