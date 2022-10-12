@java.lang.Override
public void onItemClick(android.widget.AdapterView<?> adapterView, android.view.View view, int i, long l) {
    if ((mediaPlayer != null) && mediaPlayer.isPlaying()) {
        mediaPlayer.stop();
        mediaPlayer.release();
        mediaPlayer = null;
    }
    int result = 0;
    try {
        android.media.AudioManager _CVAR0 = audioManager;
        android.media.AudioManager.OnAudioFocusChangeListener _CVAR1 = onAudioFocusChangeListener;
        int _CVAR2 = android.media.AudioManager.STREAM_MUSIC;
        int _CVAR3 = android.media.AudioManager.AUDIOFOCUS_GAIN;
        int _CVAR4 = _CVAR0.requestAudioFocus(_CVAR1, _CVAR2, _CVAR3);
        result = _CVAR4;
    } catch (java.lang.Exception e) {
        android.util.Log.d("status", toString());
    }
    if (result == android.media.AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
        mediaPlayer = android.media.MediaPlayer.create(getActivity(), words.get(i).getMsound());
        try {
            mediaPlayer.start();
        } catch (java.lang.IllegalStateException e) {
            e.printStackTrace();
        }
        mediaPlayer.setOnCompletionListener(onCompletionListener);
    }
}