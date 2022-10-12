@java.lang.Override
public void onItemClick(android.widget.AdapterView<?> parent, android.view.View view, int position, long id) {
    releaseMediaPlayer();
    // initialize audioManager
    audioManager = ((android.media.AudioManager) (getActivity().getSystemService(android.content.Context.AUDIO_SERVICE)));
    android.media.AudioManager _CVAR0 = audioManager;
    android.media.AudioManager.OnAudioFocusChangeListener _CVAR1 = onAudioFocusChangeListener;
    int _CVAR2 = android.media.AudioManager.STREAM_MUSIC;
    int _CVAR3 = android.media.AudioManager.AUDIOFOCUS_GAIN_TRANSIENT;
    // This method was deprecated in API level 26. use requestAudioFocus(android.media.AudioFocusRequest)
    int audioFocusReturn = _CVAR0.requestAudioFocus(_CVAR1, _CVAR2, _CVAR3);
    if (audioFocusReturn == android.media.AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
        mediaPlayer = android.media.MediaPlayer.create(getActivity(), myColors.get(position).getmAudio());
        mediaPlayer.start();
        mediaPlayer.setOnCompletionListener(mCompletionListener);
    }
}