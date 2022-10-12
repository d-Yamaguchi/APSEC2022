@android.annotation.SuppressLint("NewApi")
private void requestAudioFocus() {
    android.media.AudioManager _CVAR0 = audioManager;
    net.tatans.coeus.network.speaker.SpeakerControlUtils _CVAR1 = this;
    int _CVAR2 = android.media.AudioManager.STREAM_MUSIC;
    int _CVAR3 = android.media.AudioManager.AUDIOFOCUS_REQUEST_GRANTED;
    /* AUDIOFOCUS_REQUEST_GRANTED   永久获取媒体焦点（播放音乐）
    AUDIOFOCUS_GAIN_TRANSIENT  暂时获取焦点 适用于短暂的音频
    AUDIOFOCUS_GAIN_TRANSIENT_MAY_DUCK Duck我们应用跟其他应用共用焦点 我们播放的时候其他音频会降低音量
     */
    int result = _CVAR0.requestAudioFocus(_CVAR1, _CVAR2, _CVAR3);
    // 获取焦点时候
    if (result != android.media.AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
        stop();
    } else {
        start();
    }
}