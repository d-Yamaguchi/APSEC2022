package com.sygic.aura.feature.sound;
public class SoundManager {
    private static java.lang.reflect.Method mAM_abondonAudioFocus;

    private static java.lang.reflect.Method mAM_requestAudioFocus;

    private android.media.AudioManager.OnAudioFocusChangeListener m_AFChangeListener;

    private android.media.AudioManager m_AudioManager;

    /* renamed from: com.sygic.aura.feature.sound.SoundManager.1 */
    class C12331 implements android.media.AudioManager.OnAudioFocusChangeListener {
        C12331() {
        }

        public void onAudioFocusChange(int i) {
        }
    }

    static {
        initCompatibility();
    }

    private static void initCompatibility() {
        try {
            com.sygic.aura.feature.sound.SoundManager.mAM_requestAudioFocus = android.media.AudioManager.class.getMethod("requestAudioFocus", new java.lang.Class[]{ android.media.AudioManager.OnAudioFocusChangeListener.class, java.lang.Integer.TYPE, java.lang.Integer.TYPE });
            com.sygic.aura.feature.sound.SoundManager.mAM_abondonAudioFocus = android.media.AudioManager.class.getMethod("abandonAudioFocus", new java.lang.Class[]{ android.media.AudioManager.OnAudioFocusChangeListener.class });
        } catch (java.lang.SecurityException e) {
            e.printStackTrace();
        } catch (java.lang.NoSuchMethodException e2) {
            e2.printStackTrace();
        }
    }

    public SoundManager(android.media.AudioManager audioManager) {
        this.m_AFChangeListener = new com.sygic.aura.feature.sound.SoundManager.C12331();
        this.m_AudioManager = audioManager;
    }

    public int requestAudioFocus(int streamType) {
        if ((this.m_AudioManager == null) || (com.sygic.aura.feature.sound.SoundManager.mAM_requestAudioFocus == null)) {
            return 0;
        }
        AudioFocusRequest request = audioFocusRequestOreo.getAudioFocusRequest();
        return this.m_AudioManager.requestAudioFocus(request);
    }

    public int abandonAudioFocus() {
        if ((this.m_AudioManager == null) || (com.sygic.aura.feature.sound.SoundManager.mAM_abondonAudioFocus == null)) {
            return 0;
        }
        return this.m_AudioManager.abandonAudioFocus(this.m_AFChangeListener);
    }
}