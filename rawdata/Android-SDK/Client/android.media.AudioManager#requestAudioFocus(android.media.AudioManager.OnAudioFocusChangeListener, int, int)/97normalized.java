/**
 * Request transient audio focus from the android system depending on the API used
 * for OREO or above the new AudioFocusRequest object is used
 * for older versions OnAudioFocusChangeListener is used directly.
 *
 * @return result the result of the audio focus request(used to handle audio playback on list item click).
 */
private int requestAudioFocus() {
    // the result of audio focus request.
    int result;
    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
        // in OREO or above use new AudioFocusRequest (which supersedes the older version) to prepare for requests
        result = mAudioManager.requestAudioFocus(mAudioFocusRequest);
        // } else if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.N_MR1) {
    } else {
        android.media.AudioManager _CVAR0 = mAudioManager;
        android.media.AudioManager.OnAudioFocusChangeListener _CVAR1 = mOnAudioFocusChangeListener;
        int _CVAR2 = android.media.AudioManager.STREAM_MUSIC;
        int _CVAR3 = android.media.AudioManager.AUDIOFOCUS_GAIN_TRANSIENT;
        int _CVAR4 = _CVAR0.requestAudioFocus(_CVAR1, _CVAR2, _CVAR3);
        // in other older android SDK versions use the old audio focus request method.
        result = _CVAR4;
    }
    return result;
}