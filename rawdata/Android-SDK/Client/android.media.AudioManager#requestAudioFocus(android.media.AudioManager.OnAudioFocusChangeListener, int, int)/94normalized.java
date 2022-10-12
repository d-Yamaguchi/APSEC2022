/**
 * same prototype with AudioManager.requestAudioFocus
 *
 * @param l
 * 		
 * @param streamType
 * 		
 * @param durationHint
 * 		
 * @return 
 */
public int requestAudioFocus(final android.media.AudioManager.OnAudioFocusChangeListener l, int streamType, int durationHint) {
    android.util.Log.d(kinstalk.com.qloveaicore.QAIAudioFocusMgr.TAG, "requestAudioFocus: current:" + mFocusChange);
    android.media.AudioManager _CVAR0 = mAudioManager;
    android.media.AudioManager.OnAudioFocusChangeListener _CVAR1 = l;
    int _CVAR2 = streamType;
    int _CVAR3 = durationHint;
    int ret = _CVAR0.requestAudioFocus(_CVAR1, _CVAR2, _CVAR3);
    if (ret == android.media.AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
        mFocusChange = durationHint;
        android.util.Log.d(kinstalk.com.qloveaicore.QAIAudioFocusMgr.TAG, "requestAudioFocus: focus:" + mFocusChange);
    } else {
        android.util.Log.d(kinstalk.com.qloveaicore.QAIAudioFocusMgr.TAG, "requestAudioFocus: failed:" + ret);
    }
    return ret;
}