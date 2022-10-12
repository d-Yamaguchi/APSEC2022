public int requestAudioFocus(int streamType) {
    if ((this.m_AudioManager == null) || (com.sygic.aura.feature.sound.SoundManager.mAM_requestAudioFocus == null)) {
        return 0;
    }
    android.media.AudioManager _CVAR0 = this.m_AudioManager;
    android.media.AudioManager.OnAudioFocusChangeListener _CVAR1 = this.m_AFChangeListener;
    int _CVAR2 = streamType;
    int _CVAR3 = 3;
    int _CVAR4 = _CVAR0.requestAudioFocus(_CVAR1, _CVAR2, _CVAR3);
    return _CVAR4;
}