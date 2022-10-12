/**
 * 请求音频焦点
 */
public int requestAudioFocus(com.video.player.lib.manager.VideoAudioFocusManager.OnAudioFocusListener focusListener) {
    if (null != focusListener) {
        this.mFocusListener = focusListener;
    }
    if (null != mAudioManager) {
        android.media.AudioManager _CVAR0 = mAudioManager;
        android.media.AudioManager.OnAudioFocusChangeListener _CVAR1 = onAudioFocusChangeListener;
        int _CVAR2 = android.media.AudioManager.AUDIOFOCUS_GAIN_TRANSIENT_MAY_DUCK;
        int _CVAR3 = android.media.AudioManager.AUDIOFOCUS_GAIN_TRANSIENT;
        int requestAudioFocus = _CVAR0.requestAudioFocus(_CVAR1, _CVAR2, _CVAR3);
        return requestAudioFocus;
    }
    return 1;
}