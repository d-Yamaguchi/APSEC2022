package org.music.player;
/**
 * Framework methods only in Froyo or above go here.
 */
@android.annotation.TargetApi(8)
public class CompatFroyo implements android.media.AudioManager.OnAudioFocusChangeListener {
    /**
     * Instance of the audio focus listener created by {@link #createAudioFocus()}.
     */
    private static org.music.player.CompatFroyo sAudioFocus;

    /**
     * Calls {@link AudioManager#registerMediaButtonEventReceiver(ComponentName)}.
     */
    public static void registerMediaButtonEventReceiver(android.media.AudioManager manager, android.content.ComponentName receiver) {
        manager.registerMediaButtonEventReceiver(receiver);
    }

    /**
     * Calls {@link AudioManager#unregisterMediaButtonEventReceiver(ComponentName)}.
     */
    public static void unregisterMediaButtonEventReceiver(android.media.AudioManager manager, android.content.ComponentName receiver) {
        manager.unregisterMediaButtonEventReceiver(receiver);
    }

    /**
     * Calls {@link BackupManager#dataChanged()}.
     */
    public static void dataChanged(android.content.Context context) {
        new android.app.backup.BackupManager(context).dataChanged();
    }

    /**
     * Creates an audio focus listener that calls back to {@link PlaybackService#onAudioFocusChange(int)}.
     */
    public static void createAudioFocus() {
        org.music.player.CompatFroyo.sAudioFocus = new org.music.player.CompatFroyo();
    }

    /**
     * Calls {@link AudioManager#requestAudioFocus(AudioManager.OnAudioFocusChangeListener, int, int)}
     */
    public static void requestAudioFocus(android.media.AudioManager manager) {
        AudioFocusRequest request = audioFocusRequestOreo.getAudioFocusRequest();
        manager.requestAudioFocus(request);
    }

    @java.lang.Override
    public void onAudioFocusChange(int type) {
        org.music.player.PlaybackService service = PlaybackService.sInstance;
        if (service != null) {
            service.onAudioFocusChange(type);
        }
    }

    /**
     * Calls {@link VelocityTracker#getYVelocity(int)}.
     */
    public static float getYVelocity(android.view.VelocityTracker tracker, int id) {
        return tracker.getYVelocity(id);
    }

    /**
     * Calls {@link VelocityTracker#getXVelocity(int)}.
     */
    public static float getXVelocity(android.view.VelocityTracker tracker, int id) {
        return tracker.getXVelocity(id);
    }

    /**
     * Calls {@link ViewConfiguration#getScaledPagingTouchSlop()}.
     */
    public static int getScaledPagingTouchSlop(android.view.ViewConfiguration config) {
        return config.getScaledPagingTouchSlop();
    }
}