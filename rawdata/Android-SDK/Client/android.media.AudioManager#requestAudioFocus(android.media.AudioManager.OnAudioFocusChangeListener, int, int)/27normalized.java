@java.lang.Override
public void onStart(android.content.Intent intent, int startid) {
    android.widget.Toast.makeText(this, "My Service Start", android.widget.Toast.LENGTH_LONG).show();
    android.util.Log.i(deepin.ctk.media.MainService.TAG, "onStart");
    android.media.AudioManager _CVAR0 = audioManager;
    deepin.ctk.media.MainService.MyOnAudioFocusChangeListener _CVAR1 = mListener;
    int _CVAR2 = android.media.AudioManager.STREAM_VOICE_CALL;
    int _CVAR3 = android.media.AudioManager.AUDIOFOCUS_GAIN_TRANSIENT;
    // Request audio focus for playback
    int result = _CVAR0.requestAudioFocus(_CVAR1, _CVAR2, _CVAR3);
    if (result == android.media.AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
        android.util.Log.i(deepin.ctk.media.MainService.TAG, "requestAudioFocus successfully.");
        audioManager.setMode(android.media.AudioManager.STREAM_VOICE_CALL);
        // Start playback.
        player.start();
    } else {
        android.util.Log.e(deepin.ctk.media.MainService.TAG, "requestAudioFocus failed.");
    }
}