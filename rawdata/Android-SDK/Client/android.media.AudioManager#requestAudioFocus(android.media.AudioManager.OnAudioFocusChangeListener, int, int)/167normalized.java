/**
 * Calls {@link AudioManager#requestAudioFocus(AudioManager.OnAudioFocusChangeListener, int, int)}
 */
public static void requestAudioFocus(android.media.AudioManager manager) {
    android.media.AudioManager _CVAR0 = manager;
    org.music.player.CompatFroyo _CVAR1 = org.music.player.CompatFroyo.sAudioFocus;
    int _CVAR2 = android.media.AudioManager.STREAM_MUSIC;
    int _CVAR3 = android.media.AudioManager.AUDIOFOCUS_GAIN;
    _CVAR0.requestAudioFocus(_CVAR1, _CVAR2, _CVAR3);
}