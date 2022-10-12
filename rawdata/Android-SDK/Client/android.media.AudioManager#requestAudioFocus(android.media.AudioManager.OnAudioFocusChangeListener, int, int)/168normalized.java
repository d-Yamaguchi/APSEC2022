@java.lang.Override
protected void onCreate(android.os.Bundle savedInstanceState) {
    // TODO Auto-generated method stub
    super.onCreate(savedInstanceState);
    setContentView(R.layout.media_demo_activity);
    tv = ((android.widget.TextView) (findViewById(R.id.textMediaMedo)));
    audioMrg = ((android.media.AudioManager) (getSystemService(android.content.Context.AUDIO_SERVICE)));
    // 来电等进行干预
    audioListener = new android.media.AudioManager.OnAudioFocusChangeListener() {
        @java.lang.Override
        public void onAudioFocusChange(int focusChange) {
            // TODO Auto-generated method stub
            debug("onAudioFocusChange code = " + focusChange);
            if (mediaPlayer == null) {
                return;
            }
            switch (focusChange) {
                case android.media.AudioManager.AUDIOFOCUS_LOSS_TRANSIENT :
                    // 临时事情audio的focuse，例如有来电，进行音乐播放暂停操作
                    debug("AUDIOFOCUS_LOSS_TRANSIENT");
                    pausePlayingAudio(null);
                    break;
                case android.media.AudioManager.AUDIOFOCUS_GAIN :
                    // 获得audio的focuse，对暂停的音乐继续播放
                    debug("AUDIOFOCUS_GAIN");
                    restartPlayingAudio(null);
                    break;
                case android.media.AudioManager.AUDIOFOCUS_LOSS :
                    debug("AUDIOFOCUS_LOSS");
                    // resume?
                    stopPlayingAudio(null);
                    break;
                case android.media.AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK :
                    // 例如有短信的通知音，可以调低声音，无需silent
                    debug("AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK");
                    // 可以通过AudioManager.get调低声音，或简单地不做处理。
                    debug("" + audioMrg.getStreamVolume(android.media.AudioManager.AUDIOFOCUS_GAIN));
                    break;
                default :
                    break;
            }
        }
    };
    android.media.AudioManager _CVAR0 = audioMrg;
    android.media.AudioManager.OnAudioFocusChangeListener _CVAR1 = audioListener;
    int _CVAR2 = android.media.AudioManager.STREAM_MUSIC;
    int _CVAR3 = android.media.AudioManager.AUDIOFOCUS_GAIN;
    int result = _CVAR0.requestAudioFocus(_CVAR1, _CVAR2, _CVAR3);
    // 返回granted或者failed，根据android的reference，对于市场未知的，例如播放音乐或者视频，采用AUDIOFOCUS_GAIN。
    // 对于短时间的，例如通知音，看采用AUDIOFOCUS_GAIN_TRANSIENT；允许叠加，共同放音，用AUDIOFOCUS_GAIN_TRANSIENT_MAY_DUCK；
    // 对于不希望系统声音干扰的，例如进行memo录音、语音识别，采用AUDIOFOCUS_GAIN_TRANSIENT_EXCLUSIVE。
    if (result == android.media.AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
        debug("requestAudioFocus : granted");
    } else if (result == android.media.AudioManager.AUDIOFOCUS_REQUEST_FAILED) {
        debug("requestAudioFocus : failed");
    } else {
        debug("requestAudioFocus : " + result);
    }
}