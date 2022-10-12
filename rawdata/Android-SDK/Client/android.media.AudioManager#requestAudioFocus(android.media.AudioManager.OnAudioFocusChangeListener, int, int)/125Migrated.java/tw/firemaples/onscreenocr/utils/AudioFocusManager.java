package tw.firemaples.onscreenocr.utils;
/**
 * Created by firemaples on 31/05/2017.
 */
/**
 * https://stackoverflow.com/questions/15390126/how-to-stop-other-apps-playing-music-from-my-current-activity
 * https://developer.android.com/guide/topics/media-apps/volume-and-earphones.html
 * <p>
 * https://stackoverflow.com/questions/21633495/how-to-pause-different-music-players-in-android/21633772#21633772
 */
public class AudioFocusManager {
    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(tw.firemaples.onscreenocr.utils.AudioFocusManager.class);

    protected android.content.Context context;

    protected android.media.AudioManager audioManager;

    private tw.firemaples.onscreenocr.utils.AudioFocusManager.OnAudioFocusChangedCallback callback;

    private boolean isRequesting = false;

    // private boolean isOtherMusicAppPlaying = false;
    private android.media.AudioManager.OnAudioFocusChangeListener onAudioFocusChangeListener = new android.media.AudioManager.OnAudioFocusChangeListener() {
        @java.lang.Override
        public void onAudioFocusChange(int focusChange) {
            switch (focusChange) {
                case android.media.AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK :
                    tw.firemaples.onscreenocr.utils.AudioFocusManager.logger.debug("onAudioFocusChange(), AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK");
                    if (callback != null) {
                        callback.onAudioFocusLossTransientCanDuck();
                    }
                    break;
                case android.media.AudioManager.AUDIOFOCUS_LOSS_TRANSIENT :
                    tw.firemaples.onscreenocr.utils.AudioFocusManager.logger.debug("onAudioFocusChange(), AUDIOFOCUS_LOSS_TRANSIENT");
                    if (callback != null) {
                        callback.onAudioFocusLossTransient();
                    }
                    break;
                case android.media.AudioManager.AUDIOFOCUS_LOSS :
                    tw.firemaples.onscreenocr.utils.AudioFocusManager.logger.debug("onAudioFocusChange(), AUDIOFOCUS_LOSS");
                    if (callback != null) {
                        callback.onAudioFocusLoss();
                    }
                    break;
                case android.media.AudioManager.AUDIOFOCUS_GAIN :
                    tw.firemaples.onscreenocr.utils.AudioFocusManager.logger.debug("onAudioFocusChange(), AUDIOFOCUS_GAIN");
                    if (callback != null) {
                        callback.onAudioFocusGain();
                    }
                    break;
            }
        }
    };

    public AudioFocusManager(android.content.Context context) {
        this.context = context;
        audioManager = ((android.media.AudioManager) (context.getApplicationContext().getSystemService(android.content.Context.AUDIO_SERVICE)));
    }

    public void setCallback(tw.firemaples.onscreenocr.utils.AudioFocusManager.OnAudioFocusChangedCallback callback) {
        this.callback = callback;
    }

    public boolean requestAudioFocus() {
        tw.firemaples.onscreenocr.utils.AudioFocusManager.logger.debug("requestAudioFocus()");
        if (isRequesting) {
            tw.firemaples.onscreenocr.utils.AudioFocusManager.logger.debug("Current is requesting, ignore this action");
            return true;
        }
        AudioFocusRequest request = audioFocusRequestOreo.getAudioFocusRequest();
        int result = audioManager.requestAudioFocus(request);
        isRequesting = true;
        boolean resultBoolean = result == android.media.AudioManager.AUDIOFOCUS_REQUEST_GRANTED;
        tw.firemaples.onscreenocr.utils.AudioFocusManager.logger.debug("requestAudioFocus(), result: " + resultBoolean);
        // if (isOtherMusicAppPlaying) {
        // sendMediaPauseButton();
        // }
        return resultBoolean;
    }

    public void abandonAudioFocus() {
        tw.firemaples.onscreenocr.utils.AudioFocusManager.logger.debug("abandonAudioFocus()");
        if (!isRequesting) {
            tw.firemaples.onscreenocr.utils.AudioFocusManager.logger.debug("Current is not requesting, ignore this action");
            return;
        }
        audioManager.abandonAudioFocus(onAudioFocusChangeListener);
        isRequesting = false;
        // onAudioFocusChangeListener = null;
        // if (isOtherMusicAppPlaying) {
        // isOtherMusicAppPlaying = false;
        // sendMediaPlayButton();
        // }
    }

    public interface OnAudioFocusChangedCallback {
        /**
         * Lower the volume and keep playing
         * <p>
         * See {@link AudioManager#AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK}
         */
        void onAudioFocusLossTransientCanDuck();

        /**
         * Pause playback
         * <p>
         * See {@link AudioManager#AUDIOFOCUS_LOSS_TRANSIENT}
         */
        void onAudioFocusLossTransient();

        /**
         * Stop playback and never gain the focus again
         * <p>
         * See {@link AudioManager#AUDIOFOCUS_LOSS}
         */
        void onAudioFocusLoss();

        /**
         * Raise the volume to normal or restart playback if necessary
         * <p>
         * See {@link AudioManager#AUDIOFOCUS_GAIN}
         */
        void onAudioFocusGain();
    }
}