package tw.com.tp6gl4cj86.java_tool.Tool;
/**
 * Created by tp6gl4cj86 on 2016/4/8.
 */
public class OlisAudioFocusTool {
    private static tw.com.tp6gl4cj86.java_tool.Tool.OlisAudioFocusTool.OnAudioFocusChangeListener onAudioFocusChangeListener;

    public interface OnAudioFocusChangeListener {
        void onAudioFocusGain();

        void onAudioFocusLoss();
    }

    public static void requestAudioFocus(android.app.Activity activity, tw.com.tp6gl4cj86.java_tool.Tool.OlisAudioFocusTool.OnAudioFocusChangeListener onAudioFocusChangeListener) {
        tw.com.tp6gl4cj86.java_tool.Tool.OlisAudioFocusTool.onAudioFocusChangeListener = onAudioFocusChangeListener;
        final android.media.AudioManager audioManager = ((android.media.AudioManager) (activity.getSystemService(android.content.Context.AUDIO_SERVICE)));
        if (audioManager != null) {
            AudioFocusRequest request = audioFocusRequestOreo.getAudioFocusRequest();
            audioManager.requestAudioFocus(request);
        }
    }

    public static void abandonAudioFocus(android.app.Activity activity) {
        final android.media.AudioManager audioManager = ((android.media.AudioManager) (activity.getSystemService(android.content.Context.AUDIO_SERVICE)));
        if (audioManager != null) {
            audioManager.abandonAudioFocus(tw.com.tp6gl4cj86.java_tool.Tool.OlisAudioFocusTool.listener);
        }
    }

    private static final android.media.AudioManager.OnAudioFocusChangeListener listener = new android.media.AudioManager.OnAudioFocusChangeListener() {
        @java.lang.Override
        public void onAudioFocusChange(int focusChange) {
            if ((focusChange == android.media.AudioManager.AUDIOFOCUS_LOSS_TRANSIENT) || (focusChange == android.media.AudioManager.AUDIOFOCUS_LOSS)) {
                if (tw.com.tp6gl4cj86.java_tool.Tool.OlisAudioFocusTool.onAudioFocusChangeListener != null) {
                    tw.com.tp6gl4cj86.java_tool.Tool.OlisAudioFocusTool.onAudioFocusChangeListener.onAudioFocusLoss();
                }
            } else if (focusChange == android.media.AudioManager.AUDIOFOCUS_GAIN) {
                if (tw.com.tp6gl4cj86.java_tool.Tool.OlisAudioFocusTool.onAudioFocusChangeListener != null) {
                    tw.com.tp6gl4cj86.java_tool.Tool.OlisAudioFocusTool.onAudioFocusChangeListener.onAudioFocusGain();
                }
            }
        }
    };
}