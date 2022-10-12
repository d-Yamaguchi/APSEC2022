package com.lvj.bookoneday.activity.controllers;
import com.lvj.bookoneday.R;
// 音频播放 sd卡本地，资源，或者web
public class MediaDemo extends android.app.Activity {
    // private final static String AUDIO_PATH = "http://191.8.1.101:8080/wei/12.wma";
    // private static String testWeburl = "http://www.androidbook.com/akc/filestorage/android/documentfiles/3389/play.mp3";
    // "http://streaming.radionomy.com/Radio-Mozart";
    private static final java.lang.String AUDIO_PATH = android.os.Environment.getExternalStoragePublicDirectory("tencent") + "/QQfile_recv/roar.mp3";// Environment.DIRECTORY_MUSIC


    private android.widget.TextView tv = null;

    private android.media.AudioManager audioMrg = null;

    private android.media.AudioManager.OnAudioFocusChangeListener audioListener = null;

    @java.lang.Override
    protected void onCreate(android.os.Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        __SmPLUnsupported__(0).onCreate(savedInstanceState);
        setContentView(R.layout.media_demo_activity);
        tv = ((android.widget.TextView) (findViewById(R.id.textMediaMedo)));
        audioMrg = ((android.media.AudioManager) (getSystemService(android.content.Context.AUDIO_SERVICE)));
        // 来电等进行干预
        audioListener = __SmPLUnsupported__(1);
        AudioFocusRequest request = audioFocusRequestOreo.getAudioFocusRequest();
        int result = audioMrg.requestAudioFocus(request);
        // 返回granted或者failed，根据android的reference，对于市场未知的，例如播放音乐或者视频，采用AUDIOFOCUS_GAIN。
        // 对于短时间的，例如通知音，看采用AUDIOFOCUS_GAIN_TRANSIENT；允许叠加，共同放音，用AUDIOFOCUS_GAIN_TRANSIENT_MAY_DUCK；
        // 对于不希望系统声音干扰的，例如进行memo录音、语音识别，采用AUDIOFOCUS_GAIN_TRANSIENT_EXCLUSIVE。
        if (result == android.media.AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
            debug("requestAudioFocus : granted");
        } else if (result == android.media.AudioManager.AUDIOFOCUS_REQUEST_FAILED) {
            debug("requestAudioFocus : failed");
        } else {
            debug("requestAudioFocus : " + result);
        }
    }

    public void startPlayingAudio(android.view.View v) {
        v.setEnabled(false);
        findViewById(R.id.stopPlayButton).setEnabled(true);
        android.util.Log.d("WEI", "URL:" + com.lvj.bookoneday.activity.controllers.MediaDemo.AUDIO_PATH);
        try {
            // startPlayWebAudio(AUDIO_PATH);
            startPlayRawAudio(R.raw.roar);
            // startPlayRawAndioUsingFileDescriptor(R.raw.test);
        } catch (java.lang.Exception e) {
            debug("error : " + e.toString());
            e.printStackTrace();
        }
    }

    public void pausePlayingAudio(android.view.View v) {
        if ((mediaPlayer != null) && mediaPlayer.isPlaying()) {
            playbackPosition = mediaPlayer.getCurrentPosition();// 记录当前位置，以便restart时在该位置开始播放

            mediaPlayer.pause();
            debug(("stop at " + (playbackPosition / 1000.0F)) + " secs.");
        }
    }

    public void restartPlayingAudio(android.view.View v) {
        if ((mediaPlayer != null) && (!mediaPlayer.isPlaying())) {
            debug(("restart playing at " + (playbackPosition / 1000.0F)) + " secs.");
            mediaPlayer.seekTo(playbackPosition);// 从指定位置开始播放

            mediaPlayer.start();
        }
    }

    public void stopPlayingAudio(android.view.View v) {
        findViewById(R.id.startPlayButton).setEnabled(true);
        if (mediaPlayer != null) {
            // 停止。对于stop()，如果需要start()，要先进行prepare()。但对于pause()，可以直接进行start()。
            mediaPlayer.stop();
            playbackPosition = 0;
        }
    }

    @java.lang.Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        killMediaPlayer();
        audioMrg.abandonAudioFocus(audioListener);
        super.onDestroy();
    }

    private android.media.MediaPlayer mediaPlayer = null;

    private int playbackPosition = 0;

    private void startPlayWebAudio(java.lang.String url) throws java.lang.Exception {
        killMediaPlayer();
        mediaPlayer = new android.media.MediaPlayer();
        mediaPlayer.setAudioStreamType(android.media.AudioManager.STREAM_MUSIC);
        mediaPlayer.setDataSource(url);
        mediaPlayer.setLooping(false);
        mediaPlayer.setOnPreparedListener(new android.media.MediaPlayer.OnPreparedListener() {
            @java.lang.Override
            public void onPrepared(android.media.MediaPlayer mp) {
                debug("MediaPlayer is prepeared..");
                mp.start();// 开始播放

            }
        });
        /* 属于MediaPlayer在播放时出现的错误，出现在start()之后，可以用setOnErrorListener进行捕抓
        mediaPlayer.setOnErrorListener(new OnErrorListener() {

        @Override
        public boolean onError(MediaPlayer mp, int what, int extra) {
        debug("ERROR: " + what + "  " + extra);
        return false;
        }
        });
         */
        mediaPlayer.setOnCompletionListener(new android.media.MediaPlayer.OnCompletionListener() {
            @java.lang.Override
            public void onCompletion(android.media.MediaPlayer mp) {
                // TODO Auto-generated method stub
                android.widget.Toast.makeText(getApplicationContext(), "播放结束", android.widget.Toast.LENGTH_SHORT).show();
                debug("onCompletion");
                mp.stop();
                // 可能还需要加上release()，看需求，是结束播放，还是下一首。
                findViewById(R.id.startPlayButton).setEnabled(true);
                findViewById(R.id.stopPlayButton).setEnabled(false);
                killMediaPlayer();
            }
        });
        // 音乐播放有时需要持续性播放，我们需要为MediaPlayer申请wake lock，相关代码如下，当然，
        // 不要忘记获取相关的权限"android.permission.WAKE_LOCK"。
        // mediaPlayer.setWakeMode(this, PowerManager.PARTIAL_WAKE_LOCK);
        // 由于audio内容，来自网络，下载需要时间，如果在UI线程，使用mediaPlayer.prepare()
        // 进行准备播放，很容易造成ANR异常， 所以采用异步的方式，并通过 onPreparedListenner
        // 来获得转变成功的回调函数
        mediaPlayer.prepareAsync();// 准备过程，包括从网络下载

        debug("------After prepareAsync()-----------");
    }

    // 使用resource ID创建MediaPlayer对象的方式
    private void startPlayRawAudio(int resourceId) {
        killMediaPlayer();
        mediaPlayer = android.media.MediaPlayer.create(this, resourceId);
        mediaPlayer.setAudioStreamType(android.media.AudioManager.STREAM_MUSIC);
        mediaPlayer.setLooping(false);
        mediaPlayer.start();
    }

    // 对于本地音频，media player可以通过文件描述符file descriptor来获取，代码片段如下：
    private void startPlayRawAndioUsingFileDescriptor(int resourceId) throws java.lang.Exception {
        killMediaPlayer();
        android.content.res.AssetFileDescriptor fd = getResources().openRawResourceFd(resourceId);
        if (fd != null) {
            mediaPlayer = new android.media.MediaPlayer();
            mediaPlayer.setAudioStreamType(android.media.AudioManager.STREAM_MUSIC);
            mediaPlayer.setDataSource(fd.getFileDescriptor());
            // mediaPlayer.setDataSource(fd.getFileDescriptor(), fd.getStartOffset(), fd.getLength());
            fd.close();
            mediaPlayer.prepare();
            mediaPlayer.start();
        }
    }

    private void killMediaPlayer() {
        if (mediaPlayer != null) {
            try {
                // 应确保APP在不是用mediaPlayer的时候进行release()。对于Activity，可以在onDestory()中进行释放。
                mediaPlayer.release();
                mediaPlayer = null;
            } catch (java.lang.Exception e) {
                debug("error:" + e.toString());
                e.printStackTrace();
            }
        }
    }

    private void debug(java.lang.String info) {
        android.util.Log.i("WEI", info);
        tv.setText((info + "\n") + tv.getText());
    }
}