@java.lang.Override
public void onItemClick(android.widget.AdapterView<?> a, android.view.View v, int position, long id) {
    releaseMediaPlayer();
    audioManager = ((android.media.AudioManager) (getSystemService(android.content.Context.AUDIO_SERVICE)));
    android.media.AudioManager _CVAR0 = audioManager;
    android.media.AudioManager.OnAudioFocusChangeListener _CVAR1 = onAudioFocusChangeListener;
    int _CVAR2 = android.media.AudioManager.STREAM_MUSIC;
    int _CVAR3 = android.media.AudioManager.AUDIOFOCUS_GAIN_TRANSIENT;
    int audioFocus = _CVAR0.requestAudioFocus(_CVAR1, _CVAR2, _CVAR3);
    if (audioFocus == android.media.AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
        // Start playback
        mediaPlayer = android.media.MediaPlayer.create(this, words.get(position).getAudioResourceId());
        mediaPlayer.start();
        mediaPlayer.setOnCompletionListener(onCompletionListener);
    }
}