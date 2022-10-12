@java.lang.Override
public void onItemClick(android.widget.AdapterView<?> parent, android.view.View view, int position, long id) {
    releaseMediaPlayer();
    android.media.AudioManager _CVAR0 = audioManager;
    android.media.AudioManager.OnAudioFocusChangeListener _CVAR1 = audioFocusChangeListener;
    int _CVAR2 = android.media.AudioManager.STREAM_MUSIC;
    int _CVAR3 = android.media.AudioManager.AUDIOFOCUS_GAIN_TRANSIENT;
    int requestAudioFocus = _CVAR0.requestAudioFocus(_CVAR1, _CVAR2, _CVAR3);
    if (requestAudioFocus == android.media.AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
        mediaPlayer = android.media.MediaPlayer.create(this, arrayList.get(position).getSpellSounds());
        mediaPlayer.start();
        mediaPlayer.setOnCompletionListener(onCompletionListener);
    }
}