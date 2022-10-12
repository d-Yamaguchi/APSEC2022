private boolean requestAudioFocus() {
    android.media.AudioManager _CVAR0 = com.zxj.music.fusion.bean.MusicPlayer.audioManager;
    com.zxj.music.fusion.bean.MusicPlayer _CVAR1 = this;
    int _CVAR2 = android.media.AudioManager.STREAM_MUSIC;
    int _CVAR3 = android.media.AudioManager.AUDIOFOCUS_GAIN;
    int result = _CVAR0.requestAudioFocus(_CVAR1, _CVAR2, _CVAR3);
    if (result == com.zxj.music.fusion.bean.MusicPlayer.audioManager.AUDIOFOCUS_REQUEST_GRANTED) {
        return true;
    }
    return false;
}