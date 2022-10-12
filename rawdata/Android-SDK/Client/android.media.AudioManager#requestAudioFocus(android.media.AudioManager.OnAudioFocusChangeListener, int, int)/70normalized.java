@java.lang.Override
public void onClick(android.view.View v) {
    if ((mediaPlayer != null) && mediaPlayer.isPlaying()) {
        mediaPlayer.pause();
        playPause.setImageResource(R.drawable.play);
    } else {
        android.media.AudioManager _CVAR0 = audioManager;
        android.media.AudioManager.OnAudioFocusChangeListener _CVAR1 = onAudioFocusChangeListener;
        int _CVAR2 = android.media.AudioManager.STREAM_MUSIC;
        int _CVAR3 = android.media.AudioManager.AUDIOFOCUS_GAIN_TRANSIENT;
        int result = _CVAR0.requestAudioFocus(_CVAR1, _CVAR2, _CVAR3);
        if (result == android.media.AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
            mediaPlayer.start();
        }
        playPause.setImageResource(R.drawable.pause);
    }
}