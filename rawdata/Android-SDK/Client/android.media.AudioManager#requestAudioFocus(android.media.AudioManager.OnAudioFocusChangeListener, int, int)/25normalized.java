@java.lang.Override
public boolean requestAudioFocus(ir.paad.audiobook.utils.audio.AudioFocusRequestCompat audioFocusRequestCompat) {
    // Save the focus request.
    mAudioFocusRequestCompat = audioFocusRequestCompat;
    // Check for possible problems...
    if (audioFocusRequestCompat.acceptsDelayedFocusGain()) {
        final java.lang.String message = "Cannot request delayed focus on API " + android.os.Build.VERSION.SDK_INT;
        // Make an exception to allow the developer to more easily find this code path.
        @java.lang.SuppressWarnings("ThrowableNotThrown")
        final java.lang.Throwable stackTrace = new java.lang.UnsupportedOperationException(message).fillInStackTrace();
        android.util.Log.w(ir.paad.audiobook.utils.audio.AudioFocusHelper.TAG, "Cannot request delayed focus", stackTrace);
    }
    android.util.Log.e("no problem", "ok");
    final android.media.AudioManager.OnAudioFocusChangeListener listener = mAudioFocusRequestCompat.getOnAudioFocusChangeListener();
    final int streamType = mAudioFocusRequestCompat.getAudioAttributesCompat().getLegacyStreamType();
    final int focusGain = mAudioFocusRequestCompat.getFocusGain();
    int _CVAR0 = android.media.AudioManager.AUDIOFOCUS_REQUEST_GRANTED;
    boolean _CVAR1 = mAudioManager.requestAudioFocus(listener, streamType, focusGain) == _CVAR0;
    return _CVAR1;
}