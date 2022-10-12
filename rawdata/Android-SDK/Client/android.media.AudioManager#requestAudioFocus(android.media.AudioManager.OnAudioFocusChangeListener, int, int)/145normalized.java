public static boolean a(android.content.Context context, android.media.AudioManager.OnAudioFocusChangeListener onAudioFocusChangeListener) {
    try {
        cb.a("start request music_stream focus");
        android.content.Context _CVAR0 = context;
        java.lang.String _CVAR1 = "audio";
        java.lang.Object _CVAR2 = ((android.media.AudioManager) (_CVAR0.getSystemService(_CVAR1)));
        android.media.AudioManager.OnAudioFocusChangeListener _CVAR3 = onAudioFocusChangeListener;
        int _CVAR4 = 3;
        int _CVAR5 = 2;
        _CVAR2.requestAudioFocus(_CVAR3, _CVAR4, _CVAR5);
        return true;
    } catch (java.lang.Exception e) {
        return false;
    }
}