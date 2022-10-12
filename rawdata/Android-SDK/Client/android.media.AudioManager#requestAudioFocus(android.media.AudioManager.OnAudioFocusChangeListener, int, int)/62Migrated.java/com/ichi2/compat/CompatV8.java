package com.ichi2.compat;
/**
 * Implementation of {@link Compat} for SDK level 8
 */
@android.annotation.TargetApi(8)
public class CompatV8 extends com.ichi2.compat.CompatV7 implements com.ichi2.compat.Compat {
    /**
     * Listener to handle audio focus. Currently blank because we're not respecting losing focus from other apps.
     */
    private static android.media.AudioManager.OnAudioFocusChangeListener afChangeListener = new android.media.AudioManager.OnAudioFocusChangeListener() {
        public void onAudioFocusChange(int focusChange) {
        }
    };

    @java.lang.Override
    public void requestAudioFocus(android.media.AudioManager audioManager) {
        AudioFocusRequest request = audioFocusRequestOreo.getAudioFocusRequest();
        audioManager.requestAudioFocus(request);
    }

    @java.lang.Override
    public void abandonAudioFocus(android.media.AudioManager audioManager) {
        audioManager.abandonAudioFocus(com.ichi2.compat.CompatV8.afChangeListener);
    }

    @java.lang.Override
    public int parentLayoutSize() {
        return android.view.ViewGroup.LayoutParams.MATCH_PARENT;
    }
}