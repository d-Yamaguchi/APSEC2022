public void onClick(android.view.View arg0) {
    releaseMediaPlayer();
    android.media.AudioManager _CVAR0 = mAudioManager;
    android.media.AudioManager.OnAudioFocusChangeListener _CVAR1 = audioFocusChangeListener;
    int _CVAR2 = android.media.AudioManager.STREAM_MUSIC;
    int _CVAR3 = android.media.AudioManager.AUDIOFOCUS_GAIN_TRANSIENT;
    // haetaan toisto-oikeus järjestelmältä:
    int audiostate = _CVAR0.requestAudioFocus(_CVAR1, _CVAR2, _CVAR3);
    // jos saadaan toisto-oikeus:
    if (audiostate == android.media.AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
        // mediaplayer luodaan ja mp3 ladataan
        mp = android.media.MediaPlayer.create(getActivity(), R.raw.shortpenguin);
        // toisto mp3
        mp.start();
        // kun soitto loppu, aja oma releaseMediaPlayer metodi
        mp.setOnCompletionListener(completionListener);
    }
}