package com.oasisgranger.media;
import com.google.inject.Inject;
public class AudioManagement {
    private final android.content.Context context;

    @com.google.inject.Inject
    public AudioManagement(final android.content.Context context) {
        this.context = context;
    }

    public int requestAudioFocus(android.media.AudioManager.OnAudioFocusChangeListener listener, int streamType, int durationHint) {
        AudioFocusRequest request = audioFocusRequestOreo.getAudioFocusRequest();
        return getAudioManager().requestAudioFocus(request);
    }

    private android.media.AudioManager getAudioManager() {
        return ((android.media.AudioManager) (context.getSystemService(android.content.Context.AUDIO_SERVICE)));
    }
}