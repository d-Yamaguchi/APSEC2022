package com.zxj.music.fusion.bean;
import android.media.*;
import android.os.*;
import android.widget.*;
import com.zxj.music.fusion.util.*;
import java.io.*;
import java.util.*;
public class MusicPlayer extends android.media.MediaPlayer implements android.media.MediaPlayer.OnErrorListener , android.media.MediaPlayer.OnBufferingUpdateListener , android.media.MediaPlayer.OnPreparedListener , android.widget.SeekBar.OnSeekBarChangeListener , android.media.AudioManager.OnAudioFocusChangeListener {
    private java.util.Timer timer;

    private java.util.TimerTask task;

    private int var_update_period = 1000;

    private boolean var_timer_enabled = false;

    private android.widget.SeekBar seekbar;

    private com.zxj.music.fusion.bean.MusicPlayer.OnAudioFocusChangeListener listener;

    private com.zxj.music.fusion.bean.MusicPlayer.OnProgressUpdateListener onProgressUpdateListener;

    public interface OnAudioFocusChangeListener {
        void onGainFocus();

        void onLoseFocus();
    }

    public void setOnAudioFocusChangeListener(com.zxj.music.fusion.bean.MusicPlayer.OnAudioFocusChangeListener listenr) {
        this.listener = listenr;
    }

    public interface OnProgressUpdateListener {
        public void onProgress(com.zxj.music.fusion.bean.MusicPlayer player, int progress);
    }

    public MusicPlayer(android.widget.SeekBar seekbar) {
        this.seekbar = seekbar;
        this.setAudioStreamType(android.media.AudioManager.STREAM_MUSIC);
        this.setOnBufferingUpdateListener(this);
        this.setOnPreparedListener(this);
        com.zxj.music.fusion.bean.MusicPlayer.audioManager = com.zxj.music.fusion.bean.MusicPlayer.audioManager = ((android.media.AudioManager) (App.app_context.getSystemService(App.AUDIO_SERVICE)));
        this.seekbar.setOnSeekBarChangeListener(this);
    }

    @java.lang.Override
    public void onProgressChanged(android.widget.SeekBar p1, int p2, boolean p3) {
        if (p1.isPressed()) {
            this.seekTo(p2 - (p2 % 10));
        }
    }

    @java.lang.Override
    public void onStartTrackingTouch(android.widget.SeekBar p1) {
    }

    @java.lang.Override
    public void onStopTrackingTouch(android.widget.SeekBar p1) {
    }

    public void play(java.lang.String url) {
        reset();
        seekbar.setSecondaryProgress(0);
        seekbar.setProgress(0);
        try {
            this.setDataSource(url);
        } catch (java.lang.IllegalStateException e) {
        } catch (java.lang.SecurityException e) {
        } catch (java.lang.IllegalArgumentException e) {
        } catch (java.io.IOException e) {
        }
        prepareAsync();
    }

    public void setOnProgressUpdateListener(com.zxj.music.fusion.bean.MusicPlayer.OnProgressUpdateListener listener) {
        this.onProgressUpdateListener = listener;
    }

    public void enableUpdateProgress() {
        timer = new java.util.Timer();
        task = new java.util.TimerTask() {
            @java.lang.Override
            public void run() {
                if (!seekbar.isPressed()) {
                    if (isPlaying()) {
                        seekbar.setProgress(getCurrentPosition());
                    }
                }
                onProgressUpdateListener.onProgress(MusicPlayer.this, getCurrentPosition());
            }
        };
        timer.schedule(task, 0, var_update_period);
        var_timer_enabled = true;
    }

    public void disableUpdateProgress() {
        var_timer_enabled = false;
        if (task != null) {
            task.cancel();
        }
        if (timer != null) {
            timer.cancel();
        }
    }

    @java.lang.Override
    public void onBufferingUpdate(android.media.MediaPlayer p1, int p2) {
        seekbar.setSecondaryProgress(seekbar.getMax() * p2);
    }

    @java.lang.Override
    public boolean onError(android.media.MediaPlayer p1, int p2, int p3) {
        seekbar.setEnabled(false);
        disableUpdateProgress();
        UiUtils.toast(App.app_context.getString(R.string.err_play));
        return false;
    }

    private static android.media.AudioManager audioManager;

    private boolean requestAudioFocus() {
        AudioFocusRequest request = audioFocusRequestOreo.getAudioFocusRequest();
        int result = com.zxj.music.fusion.bean.MusicPlayer.audioManager.requestAudioFocus(request);
        if (result == com.zxj.music.fusion.bean.MusicPlayer.audioManager.AUDIOFOCUS_REQUEST_GRANTED) {
            return true;
        }
        return false;
    }

    @java.lang.Override
    public void onPrepared(android.media.MediaPlayer p1) {
        seekbar.setMax(p1.getDuration());
        requestAudioFocus();
        this.start();
        enableUpdateProgress();
    }

    public void auto() {
        if (isPlaying()) {
            pause();
        } else {
            this.start();
        }
    }

    @java.lang.Override
    public void onAudioFocusChange(int p1) {
        if ((p1 == android.media.AudioManager.AUDIOFOCUS_GAIN_TRANSIENT) || (p1 == android.media.AudioManager.AUDIOFOCUS_GAIN)) {
            enableUpdateProgress();
            if (!isPlaying()) {
                this.start();
                this.setVolume(1, 1);
                if (listener != null) {
                    listener.onGainFocus();
                }
            }
        } else if ((p1 == android.media.AudioManager.AUDIOFOCUS_LOSS_TRANSIENT) || (p1 == android.media.AudioManager.AUDIOFOCUS_LOSS)) {
            disableUpdateProgress();
            if (isPlaying()) {
                pause();
            }
            if (!isPlaying()) {
                if (listener != null) {
                    listener.onLoseFocus();
                }
            } else if (p1 == android.media.AudioManager.AUDIOFOCUS_GAIN_TRANSIENT_MAY_DUCK) {
                this.setVolume(0.2F, 0.2F);
            }
        }
    }

    public void release() {
        disableUpdateProgress();
        super.release();
        com.zxj.music.fusion.bean.MusicPlayer.audioManager.abandonAudioFocus(this);
    }
}