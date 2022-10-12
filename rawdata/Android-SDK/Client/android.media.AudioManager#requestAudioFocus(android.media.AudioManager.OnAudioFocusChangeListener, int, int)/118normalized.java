public static int requestAudioFocus() {
    // 
    try {
        net.oschina.app.v2.activity.chat.recoder.AudioFocusChangeManager.c = new android.media.AudioManager.OnAudioFocusChangeListener() {
            @java.lang.Override
            public void onAudioFocusChange(int focusChange) {
                if (((focusChange != net.oschina.app.v2.activity.chat.recoder.AudioFocusChangeManager.d) && (focusChange != android.media.AudioManager.AUDIOFOCUS_GAIN)) && (focusChange == android.media.AudioManager.AUDIOFOCUS_LOSS)) {
                    net.oschina.app.v2.activity.chat.recoder.AudioFocusChangeManager.audioManger.abandonAudioFocus(((android.media.AudioManager.OnAudioFocusChangeListener) (net.oschina.app.v2.activity.chat.recoder.AudioFocusChangeManager.c)));
                }
            }
        };// b();

        android.media.AudioManager _CVAR0 = net.oschina.app.v2.activity.chat.recoder.AudioFocusChangeManager.audioManger;
        java.lang.Object _CVAR1 = ((android.media.AudioManager.OnAudioFocusChangeListener) (net.oschina.app.v2.activity.chat.recoder.AudioFocusChangeManager.c));
        int _CVAR2 = 3;
        int _CVAR3 = 1;
        int _CVAR4 = _CVAR0.requestAudioFocus(_CVAR1, _CVAR2, _CVAR3);
        return _CVAR4;
    } catch (java.lang.Throwable e) {
        e.printStackTrace();
    }
    return 0;
}