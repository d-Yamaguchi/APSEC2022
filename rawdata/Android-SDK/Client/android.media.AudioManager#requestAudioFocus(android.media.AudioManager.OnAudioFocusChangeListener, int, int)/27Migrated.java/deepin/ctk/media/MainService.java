package deepin.ctk.media;
import deepin.ctk.R;
public class MainService extends android.app.Service {
    private static final java.lang.String TAG = "leeTest----->";

    private android.media.MediaPlayer player;

    private android.media.AudioManager audioManager;

    private deepin.ctk.media.MainService.MyOnAudioFocusChangeListener mListener;

    @java.lang.Override
    public void onCreate() {
        android.util.Log.i(deepin.ctk.media.MainService.TAG, "onCreate");
        player = android.media.MediaPlayer.create(this, R.raw.call_ring);// 在res目录下新建raw目录，复制一个test.mp3文件到此目录下。

        player.setLooping(true);
        audioManager = ((android.media.AudioManager) (getApplicationContext().getSystemService(android.content.Context.AUDIO_SERVICE)));
        mListener = new deepin.ctk.media.MainService.MyOnAudioFocusChangeListener();
    }

    @java.lang.Override
    public android.os.IBinder onBind(android.content.Intent intent) {
        return null;
    }

    @java.lang.Override
    public void onStart(android.content.Intent intent, int startid) {
        android.widget.Toast.makeText(this, "My Service Start", android.widget.Toast.LENGTH_LONG).show();
        android.util.Log.i(deepin.ctk.media.MainService.TAG, "onStart");
        AudioFocusRequest request = audioFocusRequestOreo.getAudioFocusRequest();
        int result = audioManager.requestAudioFocus(request);
        if (result == android.media.AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
            android.util.Log.i(deepin.ctk.media.MainService.TAG, "requestAudioFocus successfully.");
            audioManager.setMode(android.media.AudioManager.STREAM_VOICE_CALL);
            // Start playback.
            player.start();
        } else {
            android.util.Log.e(deepin.ctk.media.MainService.TAG, "requestAudioFocus failed.");
        }
    }

    @java.lang.Override
    public void onDestroy() {
        android.widget.Toast.makeText(this, "My Service Stoped", android.widget.Toast.LENGTH_LONG).show();
        android.util.Log.i(deepin.ctk.media.MainService.TAG, "onDestroy");
        player.stop();
        audioManager.abandonAudioFocus(mListener);
    }

    private class MyOnAudioFocusChangeListener implements android.media.AudioManager.OnAudioFocusChangeListener {
        @java.lang.Override
        public void onAudioFocusChange(int focusChange) {
            android.util.Log.i(deepin.ctk.media.MainService.TAG, "focusChange=" + focusChange);
        }
    }
}