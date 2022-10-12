package cn.com.yqhome.instrumentapp;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
/**
 * Created by depengli on 2017/10/6.
 */
public class MusicService extends android.app.Service implements android.media.MediaPlayer.OnPreparedListener , android.media.MediaPlayer.OnCompletionListener , android.media.MediaPlayer.OnErrorListener {
    private static java.lang.String TAG = "MusicService";

    private final android.os.IBinder iBinder = new cn.com.yqhome.instrumentapp.MusicService.MusicBinder();

    private android.media.MediaPlayer mediaPlayer;

    private boolean prepared = false;

    // private final IBinder iBinder = new Mu();
    @android.support.annotation.RequiresApi(api = android.os.Build.VERSION_CODES.LOLLIPOP)
    @java.lang.Override
    public void onCreate() {
        __SmPLUnsupported__(0).onCreate();
        android.media.AudioAttributes.Builder builder = new android.media.AudioAttributes.Builder();
        builder.setContentType(android.media.AudioAttributes.CONTENT_TYPE_MUSIC);
        mediaPlayer = new android.media.MediaPlayer();
        Builder builder = new android.media.AudioAttributes.Builder();
        AudioAttributes attributes = builder.build();
        mediaPlayer.setAudioAttributes(attributes);
        // mediaPlayer.setAudioAttributes(builder.build());
        // mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        // mediaPlayer.setWakeMode(getApplicationContext(), PowerManager.FULL_WAKE_LOCK);
        mediaPlayer.setOnCompletionListener(this);
        mediaPlayer.setOnErrorListener(this);
        mediaPlayer.setOnPreparedListener(this);
    }

    class MusicBinder extends android.os.Binder {
        cn.com.yqhome.instrumentapp.MusicService getService() {
            return MusicService.this;
        }
    }

    @android.support.annotation.Nullable
    @java.lang.Override
    public android.os.IBinder onBind(android.content.Intent intent) {
        return iBinder;
    }

    @java.lang.Override
    public void onPrepared(android.media.MediaPlayer mp) {
        android.util.Log.i(cn.com.yqhome.instrumentapp.MusicService.TAG, "had prepared");
        prepared = true;
        mediaPlayer.start();
    }

    @java.lang.Override
    public void onDestroy() {
        mediaPlayer.release();
        super.onDestroy();
    }

    public void start(java.lang.String path) {
        mediaPlayer.reset();
        try {
            // mediaPlayer = MediaPlayer.create(this, Uri.parse("http://vprbbc.streamguys.net:80/vprbbc24.mp3"))
            // mediaPlayer.setDataSource(path);
            mediaPlayer.setDataSource(getApplicationContext(), android.net.Uri.parse(path));
            // mediaPlayer = MediaPlayer.create(getApplicationContext(),Uri.parse(path));
        } catch (java.lang.Exception e) {
            e.printStackTrace();
        }
        try {
            mediaPlayer.prepare();
        } catch (java.lang.Exception e) {
            e.printStackTrace();
        }
    }

    public void play(java.lang.String path) {
        if (prepared) {
            mediaPlayer.start();
        } else {
            start(path);
        }
    }

    public void pause() {
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
        }
    }

    public void seekTo(int i) {
        if (mediaPlayer.isPlaying()) {
            float precentage = ((float) (i)) / 100;
            float seek = precentage * mediaPlayer.getDuration();
            mediaPlayer.seekTo(((int) (seek)));
        }
    }

    public int getProgress() {
        if (mediaPlayer.isPlaying()) {
            return ((int) ((mediaPlayer.getCurrentPosition() / mediaPlayer.getDuration()) * 100));
        }
        return 0;
    }

    public java.lang.String getTimeShow() {
        return (mediaPlayer.getCurrentPosition() + "/") + mediaPlayer.getDuration();
    }

    @java.lang.Override
    public boolean onUnbind(android.content.Intent intent) {
        mediaPlayer.stop();
        mediaPlayer.release();
        return super.onUnbind(intent);
    }

    @java.lang.Override
    public boolean onError(android.media.MediaPlayer mp, int what, int extra) {
        mediaPlayer.reset();
        return false;
    }

    @java.lang.Override
    public void onCompletion(android.media.MediaPlayer mp) {
    }
}