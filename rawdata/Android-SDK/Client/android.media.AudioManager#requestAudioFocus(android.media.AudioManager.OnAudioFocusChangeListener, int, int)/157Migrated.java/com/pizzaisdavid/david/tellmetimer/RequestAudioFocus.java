package com.pizzaisdavid.david.tellmetimer;
public class RequestAudioFocus extends android.speech.tts.UtteranceProgressListener {
    private android.media.AudioManager audioManager;

    android.media.AudioManager.OnAudioFocusChangeListener audioFocusChangeListener = new android.media.AudioManager.OnAudioFocusChangeListener() {
        @java.lang.Override
        public void onAudioFocusChange(int focusChange) {
            switch (focusChange) {
                case android.media.AudioManager.AUDIOFOCUS_GAIN :
                    break;
                case android.media.AudioManager.AUDIOFOCUS_LOSS :
                    audioManager.abandonAudioFocus(audioFocusChangeListener);
                    break;
            }
        }
    };

    public RequestAudioFocus(android.app.Activity activity) {
        audioManager = ((android.media.AudioManager) (activity.getSystemService(android.content.Context.AUDIO_SERVICE)));
    }

    @java.lang.Override
    public void onStart(java.lang.String utteranceId) {
        AudioFocusRequest request = audioFocusRequestOreo.getAudioFocusRequest();
        audioManager.requestAudioFocus(request);
    }

    @java.lang.Override
    public void onDone(java.lang.String utteranceId) {
        audioManager.abandonAudioFocus(audioFocusChangeListener);
    }

    @java.lang.Override
    public void onError(java.lang.String utteranceId) {
    }
}