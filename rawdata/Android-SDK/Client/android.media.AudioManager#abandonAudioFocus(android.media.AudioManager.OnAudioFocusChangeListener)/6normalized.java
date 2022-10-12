@java.lang.Override
public void onAudioFocusChange(int focusChange) {
    if (audioChange != null) {
        audioChange.onAudioFocusChange(audioManager, focusChange);
    }
    if (focusChange == android.media.AudioManager.AUDIOFOCUS_LOSS) {
        android.media.AudioManager _CVAR0 = audioManager;
        com.example.han.referralproject.new_music.MusicService.1 _CVAR1 = this;
        _CVAR0.abandonAudioFocus(_CVAR1);
        // Stop playback
    }
}