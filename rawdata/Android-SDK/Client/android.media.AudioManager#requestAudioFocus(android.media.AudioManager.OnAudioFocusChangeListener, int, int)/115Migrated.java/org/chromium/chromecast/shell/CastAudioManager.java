package org.chromium.chromecast.shell;
/**
 * Wrapper for Cast code to use a single AudioManager instance.
 * Muting and unmuting streams must be invoke on the same AudioManager instance.
 */
public class CastAudioManager {
    // TODO(sanfin): This class should encapsulate SDK-dependent implementation details of
    // android.media.AudioManager.
    private static org.chromium.chromecast.shell.CastAudioManager sInstance = null;

    public static org.chromium.chromecast.shell.CastAudioManager getAudioManager(android.content.Context context) {
        if (org.chromium.chromecast.shell.CastAudioManager.sInstance == null) {
            org.chromium.chromecast.shell.CastAudioManager.sInstance = new org.chromium.chromecast.shell.CastAudioManager(((android.media.AudioManager) (context.getApplicationContext().getSystemService(android.content.Context.AUDIO_SERVICE))));
        }
        return org.chromium.chromecast.shell.CastAudioManager.sInstance;
    }

    private final android.media.AudioManager mAudioManager;

    private CastAudioManager(android.media.AudioManager audioManager) {
        mAudioManager = audioManager;
    }

    // TODO(sanfin): Use the AudioFocusRequest version on O and above.
    @java.lang.SuppressWarnings("deprecation")
    public int requestAudioFocus(android.media.AudioManager.OnAudioFocusChangeListener l, int streamType, int durationHint) {
        AudioFocusRequest request = audioFocusRequestOreo.getAudioFocusRequest();
        return mAudioManager.requestAudioFocus(request);
    }

    // TODO(sanfin): Use the AudioFocusRequest version on O and above.
    @java.lang.SuppressWarnings("deprecation")
    public int abandonAudioFocus(android.media.AudioManager.OnAudioFocusChangeListener l) {
        return mAudioManager.abandonAudioFocus(l);
    }

    // TODO(sanfin): Do not expose this. All needed AudioManager methods can be adapted with
    // CastAudioManager.
    public android.media.AudioManager getInternal() {
        return mAudioManager;
    }
}