package js.lib.android.utils;
/**
 * Audio Manager Common Methods
 *
 * @author Jun.Wang
 */
public class AudioManagerUtil {
    private static android.media.AudioManager mAudioManager;

    public static android.media.AudioManager getAudioMananger(android.content.Context cxt) {
        if (js.lib.android.utils.AudioManagerUtil.mAudioManager == null) {
            js.lib.android.utils.AudioManagerUtil.mAudioManager = ((android.media.AudioManager) (cxt.getSystemService(android.content.Context.AUDIO_SERVICE)));
        }
        return js.lib.android.utils.AudioManagerUtil.mAudioManager;
    }

    public static int abandon(android.content.Context cxt, android.media.AudioManager.OnAudioFocusChangeListener l) {
        return js.lib.android.utils.AudioManagerUtil.getAudioMananger(cxt).abandonAudioFocus(l);
    }

    public static int requestMusicGain(android.content.Context cxt, android.media.AudioManager.OnAudioFocusChangeListener l) {
        return js.lib.android.utils.AudioManagerUtil.request(cxt, l, android.media.AudioManager.STREAM_MUSIC, android.media.AudioManager.AUDIOFOCUS_GAIN);
    }

    private static int request(android.content.Context cxt, android.media.AudioManager.OnAudioFocusChangeListener l, int streamType, int durationHint) {
        AudioFocusRequest request = audioFocusRequestOreo.getAudioFocusRequest();
        return js.lib.android.utils.AudioManagerUtil.getAudioMananger(cxt).requestAudioFocus(request);
    }

    public static boolean isAudioFocusRegistered(int flag) {
        return flag == android.media.AudioManager.AUDIOFOCUS_REQUEST_GRANTED;
    }
}