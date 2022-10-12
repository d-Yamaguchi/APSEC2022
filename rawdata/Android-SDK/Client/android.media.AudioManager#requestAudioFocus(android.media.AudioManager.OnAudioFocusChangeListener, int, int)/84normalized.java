public static void requestAudioFocus(android.app.Activity activity, tw.com.tp6gl4cj86.java_tool.Tool.OlisAudioFocusTool.OnAudioFocusChangeListener onAudioFocusChangeListener) {
    tw.com.tp6gl4cj86.java_tool.Tool.OlisAudioFocusTool.onAudioFocusChangeListener = onAudioFocusChangeListener;
    android.app.Activity _CVAR0 = activity;
    java.lang.String _CVAR1 = android.content.Context.AUDIO_SERVICE;
    final android.media.AudioManager audioManager = ((android.media.AudioManager) (_CVAR0.getSystemService(_CVAR1)));
    if (audioManager != null) {
        android.media.AudioManager _CVAR2 = audioManager;
        android.media.AudioManager.OnAudioFocusChangeListener _CVAR3 = tw.com.tp6gl4cj86.java_tool.Tool.OlisAudioFocusTool.listener;
        int _CVAR4 = android.media.AudioManager.STREAM_MUSIC;
        int _CVAR5 = android.media.AudioManager.AUDIOFOCUS_GAIN;
        _CVAR2.requestAudioFocus(_CVAR3, _CVAR4, _CVAR5);
    }
}