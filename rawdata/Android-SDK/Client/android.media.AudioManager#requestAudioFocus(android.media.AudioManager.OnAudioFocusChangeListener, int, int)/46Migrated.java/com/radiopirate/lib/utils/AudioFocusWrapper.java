package com.radiopirate.lib.utils;
public class AudioFocusWrapper {
    private android.media.AudioManager.OnAudioFocusChangeListener listener = new android.media.AudioManager.OnAudioFocusChangeListener() {
        @java.lang.Override
        public void onAudioFocusChange(int focusChange) {
        }
    };

    public void requestAudioFocus(android.media.AudioManager audioManager) {
        AudioFocusRequest request = audioFocusRequestOreo.getAudioFocusRequest();
        audioManager.requestAudioFocus(request);
    }

    public void abandonAudioFocus(android.media.AudioManager audioManager) {
        audioManager.abandonAudioFocus(listener);
    }
}