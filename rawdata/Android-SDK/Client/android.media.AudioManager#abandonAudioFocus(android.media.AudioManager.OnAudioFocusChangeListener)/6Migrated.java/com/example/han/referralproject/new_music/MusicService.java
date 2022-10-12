package com.example.han.referralproject.new_music;
import android.support.annotation.Nullable;
/**
 * Created by gzq on 2018/1/23.
 */
public class MusicService extends android.app.Service implements android.media.MediaPlayer.OnPreparedListener , android.media.MediaPlayer.OnCompletionListener {
    private android.media.MediaPlayer mediaPlayer;

    private com.example.han.referralproject.new_music.Music music;

    private android.media.AudioManager audioManager;

    @java.lang.Override
    public void onCreate() {
        super.onCreate();
        audioManager = ((android.media.AudioManager) (getSystemService(android.content.Context.AUDIO_SERVICE)));
    }

    @android.support.annotation.Nullable
    @java.lang.Override
    public android.os.IBinder onBind(android.content.Intent intent) {
        return new com.example.han.referralproject.new_music.MusicService.MusicBind();
    }

    @java.lang.Override
    public boolean onUnbind(android.content.Intent intent) {
        release();
        return super.onUnbind(intent);
    }

    public void setMusicResourse(com.example.han.referralproject.new_music.Music music) {
        if (mediaPlayer != null) {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.stop();
            }
            mediaPlayer.release();
            mediaPlayer = null;
        }
        this.music = music;
        if ((music != null) && (!android.text.TextUtils.isEmpty(music.getPath()))) {
            mediaPlayer = getMediaPlayer(this);
            try {
                mediaPlayer.setDataSource(music.getPath());
                mediaPlayer.setOnPreparedListener(this);
                mediaPlayer.setOnCompletionListener(this);
                mediaPlayer.prepareAsync();
            } catch (java.io.IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void play() {
        // 先请求焦点
        int result = audioManager.requestAudioFocus(onAudioFocusChangeListener, android.media.AudioManager.STREAM_MUSIC, android.media.AudioManager.AUDIOFOCUS_GAIN);
        if (result == android.media.AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
            if ((mediaPlayer == null) && (music != null)) {
                setMusicResourse(music);
                return;
            }
            if ((mediaPlayer != null) && (!mediaPlayer.isPlaying())) {
                mediaPlayer.start();
            }
        }
    }

    private android.media.AudioManager.OnAudioFocusChangeListener onAudioFocusChangeListener = new android.media.AudioManager.OnAudioFocusChangeListener() {
        @java.lang.Override
        public void onAudioFocusChange(int focusChange) {
            if (audioChange != null)
                audioChange.onAudioFocusChange(audioManager, focusChange);

            if (focusChange == android.media.AudioManager.AUDIOFOCUS_LOSS) {
                Builder builder = new android.media.AudioFocusRequest.Builder(focusChange);
                AudioFocusRequest request = builder.build();
                audioManager.abandonAudioFocusRequest(request);
                // Stop playback
            }
        }
    };

    public void pause() {
        if ((mediaPlayer != null) && mediaPlayer.isPlaying()) {
            audioManager.abandonAudioFocus(onAudioFocusChangeListener);
            mediaPlayer.pause();
        }
    }

    public long getCurrentTime() {
        return mediaPlayer == null ? 0 : mediaPlayer.getCurrentPosition();
    }

    /**
     * 释放资源
     */
    public void release() {
        audioManager.abandonAudioFocus(onAudioFocusChangeListener);
        if (mediaPlayer != null) {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.stop();
            }
            mediaPlayer.release();
            mediaPlayer = null;
        }
        musicPreParedOk = null;
        audioChange = null;
        musicFinish = null;
    }

    // 音乐文件准备好了，可以开始播放
    @java.lang.Override
    public void onPrepared(android.media.MediaPlayer mp) {
        if (music == null) {
            return;
        }
        music.setDuration(mp.getDuration());
        if (musicPreParedOk != null) {
            musicPreParedOk.prepared(music);
        }
    }

    public boolean isPlaying() {
        return (mediaPlayer != null) && mediaPlayer.isPlaying();
    }

    @java.lang.Override
    public void onCompletion(android.media.MediaPlayer mp) {
        audioManager.abandonAudioFocus(onAudioFocusChangeListener);
        if (musicFinish != null) {
            musicFinish.onFinish();
        }
    }

    public class MusicBind extends android.os.Binder {
        public com.example.han.referralproject.new_music.MusicService getService() {
            return MusicService.this;
        }
    }

    private com.example.han.referralproject.new_music.MusicService.MusicPreParedOk musicPreParedOk;

    public void setOnMusicPreparedListener(com.example.han.referralproject.new_music.MusicService.MusicPreParedOk musicPreParedOk) {
        this.musicPreParedOk = musicPreParedOk;
    }

    public interface MusicPreParedOk {
        void prepared(com.example.han.referralproject.new_music.Music music);
    }

    private com.example.han.referralproject.new_music.MusicService.AudioChange audioChange;

    public void setOnAudioFocusChangeListener(com.example.han.referralproject.new_music.MusicService.AudioChange audioChange) {
        this.audioChange = audioChange;
    }

    public interface AudioChange {
        void onAudioFocusChange(android.media.AudioManager manager, int focusChange);
    }

    public void setOnCompletion(com.example.han.referralproject.new_music.MusicService.MusicFinish musicFinish) {
        this.musicFinish = musicFinish;
    }

    private com.example.han.referralproject.new_music.MusicService.MusicFinish musicFinish;

    public interface MusicFinish {
        void onFinish();
    }

    private android.media.MediaPlayer getMediaPlayer(android.content.Context context) {
        android.media.MediaPlayer mediaplayer = new android.media.MediaPlayer();
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.KITKAT) {
            return mediaplayer;
        }
        try {
            java.lang.Class<?> cMediaTimeProvider = java.lang.Class.forName("android.media.MediaTimeProvider");
            java.lang.Class<?> cSubtitleController = java.lang.Class.forName("android.media.SubtitleController");
            java.lang.Class<?> iSubtitleControllerAnchor = java.lang.Class.forName("android.media.SubtitleController$Anchor");
            java.lang.Class<?> iSubtitleControllerListener = java.lang.Class.forName("android.media.SubtitleController$Listener");
            java.lang.reflect.Constructor constructor = cSubtitleController.getConstructor(new java.lang.Class[]{ android.content.Context.class, cMediaTimeProvider, iSubtitleControllerListener });
            java.lang.Object subtitleInstance = constructor.newInstance(context, null, null);
            java.lang.reflect.Field f = cSubtitleController.getDeclaredField("mHandler");
            f.setAccessible(true);
            try {
                f.set(subtitleInstance, new android.os.Handler());
            } catch (java.lang.IllegalAccessException e) {
                return mediaplayer;
            } finally {
                f.setAccessible(false);
            }
            java.lang.reflect.Method setsubtitleanchor = mediaplayer.getClass().getMethod("setSubtitleAnchor", cSubtitleController, iSubtitleControllerAnchor);
            setsubtitleanchor.invoke(mediaplayer, subtitleInstance, null);
        } catch (java.lang.Exception e) {
        }
        return mediaplayer;
    }
}