@java.lang.Override
public void onItemClick(android.widget.AdapterView<?> adapterView, android.view.View view, int position, long l) {
    mediaPlayer = android.media.MediaPlayer.create(context, words[position].getSoundId());
    android.media.AudioManager _CVAR0 = audioManager;
    android.media.AudioManager.OnAudioFocusChangeListener _CVAR1 = onAudioFocusChangeListener;
    int _CVAR2 = android.media.AudioManager.STREAM_MUSIC;
    int _CVAR3 = android.media.AudioManager.AUDIOFOCUS_GAIN_TRANSIENT;
    // get audio focus
    int result = _CVAR0.requestAudioFocus(_CVAR1, _CVAR2, _CVAR3);
    if (result == android.media.AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
        mediaPlayer.start();
        mediaPlayer.setOnCompletionListener(onCompletionListener);
    } else {
        android.util.Log.e("AudioFocus", "AudioFocus is not granted, cant play sound");
    }
}