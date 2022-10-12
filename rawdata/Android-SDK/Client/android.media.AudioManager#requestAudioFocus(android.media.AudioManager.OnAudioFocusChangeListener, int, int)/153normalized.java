@java.lang.Override
public void onItemClick(android.widget.AdapterView<?> parent, android.view.View view, int position, long id) {
    com.example.android.ugonabo.Word currentWord = words.get(position);
    int sd = currentWord.getSoundResourceId();
    releaseMediaPlayer();
    android.media.AudioManager _CVAR0 = audioManager;
    android.media.AudioManager.OnAudioFocusChangeListener _CVAR1 = MonAudioFocusChange;
    int _CVAR2 = android.media.AudioManager.STREAM_MUSIC;
    int _CVAR3 = android.media.AudioManager.AUDIOFOCUS_GAIN_TRANSIENT;
    // int result = audioManager.requestAudioFocus(xxy, STREAM)
    int result = _CVAR0.requestAudioFocus(_CVAR1, _CVAR2, _CVAR3);
    if (result == android.media.AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
        // When we recieve audio focus
        mp = android.media.MediaPlayer.create(getActivity(), sd);
        mp.start();
        mp.setOnCompletionListener(completionListener);
    }
}