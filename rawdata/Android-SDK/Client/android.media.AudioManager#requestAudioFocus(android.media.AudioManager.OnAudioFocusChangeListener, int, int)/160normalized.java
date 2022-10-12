public void playSound(android.content.Context c, android.net.Uri pathToFile) {
    try {
        android.media.MediaPlayer mediaPlayer = new android.media.MediaPlayer();
        mediaPlayer.setDataSource(c, pathToFile);
        mediaPlayer.setLooping(false);
        mediaPlayer.prepare();
        android.media.AudioManager mAudioManager = ((android.media.AudioManager) (c.getSystemService(android.content.Context.AUDIO_SERVICE)));
        android.media.AudioManager.OnAudioFocusChangeListener listener = new com.kimballleavitt.swipe_soundboard.SoundPlayer.MyFocusListener(mediaPlayer, mAudioManager);
        mediaPlayer.setOnCompletionListener(new com.kimballleavitt.swipe_soundboard.SoundPlayer.MyCompletionListener(mAudioManager, listener));
        int _CVAR0 = android.media.AudioManager.AUDIOFOCUS_REQUEST_GRANTED;
        boolean _CVAR1 = mAudioManager.requestAudioFocus(listener, 1, android.media.AudioManager.AUDIOFOCUS_GAIN_TRANSIENT) == _CVAR0;
        if () {
            mediaPlayer.start();
        }
    } catch (java.io.IOException exception) {
        java.lang.System.out.println("File not found!\n" + exception.getMessage());
        exception.printStackTrace();
    }
}