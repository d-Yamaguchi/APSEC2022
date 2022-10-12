package com.manna.audio;
/**
 * 播放service
 */
public class AudioPlayService extends android.app.Service {
    private final java.lang.String TAG = com.manna.audio.AudioPlayService.class.getSimpleName();

    private com.manna.audio.AudioPlayer audioPlayer;

    // 系统音频管理工具
    private android.media.AudioManager audioManager;

    // 定时发送进度更新
    private java.util.Timer timer;

    // 标记是否播放完成
    private boolean isComplete;

    // 标记是否获得音频焦点
    private boolean isFocusGranted;

    private com.manna.audio.AudioPlayService.MediaPlayListener listener;

    public void setListener(com.manna.audio.AudioPlayService.MediaPlayListener listener) {
        this.listener = listener;
    }

    @java.lang.Override
    public void onCreate() {
        super.onCreate();
        audioManager = ((android.media.AudioManager) (getSystemService(android.content.Context.AUDIO_SERVICE)));
        isFocusGranted = requestAudioFocus();
        android.util.Log.d(TAG, "onCreate: 获取音频焦点" + isFocusGranted);
    }

    @java.lang.Override
    public android.os.IBinder onBind(android.content.Intent intent) {
        return new com.manna.audio.AudioPlayService.AudioPlayManager();
    }

    @java.lang.Override
    public void onDestroy() {
        super.onDestroy();
        if (audioPlayer != null)
            audioPlayer.stop();

        if (timer != null)
            timer.cancel();

        if (audioManager != null)
            audioManager.abandonAudioFocus(audioFocusListener);
        // 放弃音频焦点

        listener = null;
        isFocusGranted = false;
    }

    /**
     * 申请获取音频焦点
     */
    private boolean requestAudioFocus() {
        AudioFocusRequest request = audioFocusRequestOreo.getAudioFocusRequest();
        int requestResult = audioManager.requestAudioFocus(request);
        // 返回是否授权
        return requestResult == android.media.AudioManager.AUDIOFOCUS_REQUEST_GRANTED;
    }

    // 单独定义listener
    android.media.AudioManager.OnAudioFocusChangeListener audioFocusListener = new android.media.AudioManager.OnAudioFocusChangeListener() {
        public void onAudioFocusChange(int focusChange) {
            if (focusChange == android.media.AudioManager.AUDIOFOCUS_LOSS_TRANSIENT) {
                // 暂时失去音频焦点,暂停MediaPlayer
                audioPlayer.pause();
            } else if (focusChange == android.media.AudioManager.AUDIOFOCUS_GAIN) {
                // 获得音频焦点
                audioPlayer.start();
            } else if (focusChange == android.media.AudioManager.AUDIOFOCUS_LOSS) {
                // 失去音频焦点,释放MediaPlayer
                audioManager.abandonAudioFocus(audioFocusListener);
                audioPlayer.stop();
            }
        }
    };

    /**
     * 提供上层调用方法 -- AudioPlayManager
     */
    public class AudioPlayManager extends android.os.Binder implements com.manna.audio.AudioPlayInterface , com.manna.audio.AudioPlayer.AudioPlayerListener {
        public void initPlayer(java.lang.String dataSource, int sourceType, com.manna.audio.AudioPlayService.MediaPlayListener mediaPlayListener) {
            audioPlayer = new com.manna.audio.AudioPlayer(dataSource, sourceType);
            audioPlayer.setListener(this);
            setListener(mediaPlayListener);
        }

        @java.lang.Override
        public void start() {
            // 播放完成继续播放 -- 清空完成标记(MediaPlayer并未reset)
            audioPlayer.setComplete(false);
            audioPlayer.start();
            sendPlayProgress();
        }

        @java.lang.Override
        public void pause() {
            audioPlayer.pause();
            if (listener != null)
                listener.onPause(audioPlayer.getCurrentPosition());

        }

        @java.lang.Override
        public void stop() {
            audioPlayer.stop();
        }

        @java.lang.Override
        public boolean isPlaying() {
            return audioPlayer.isPlaying();
        }

        @java.lang.Override
        public boolean isComplete() {
            return audioPlayer.isComplete();
        }

        @java.lang.Override
        public void seekTo(int progress) {
            audioPlayer.seekTo(progress);
        }

        @java.lang.Override
        public int getDuration() {
            return audioPlayer.getDuration();
        }

        @java.lang.Override
        public int getCurrentPosition() {
            return audioPlayer.getCurrentPosition();
        }

        @java.lang.Override
        public void onPrepared(android.media.MediaPlayer mp) {
            // prepared
            if (listener != null)
                listener.onPrepared(mp);

        }

        @java.lang.Override
        public void onComplete(android.media.MediaPlayer mp) {
            // complete
            if (listener != null)
                listener.onComplete(mp);

        }

        @java.lang.Override
        public void onError(android.media.MediaPlayer mp, int what, int extra) {
            // error
            if (listener != null)
                listener.onError(mp, what, extra);

        }
    }

    /**
     * 开始播放后子线程定时发送进度信息
     */
    private void sendPlayProgress() {
        if (timer == null) {
            timer = new java.util.Timer();
        }
        timer.schedule(new java.util.TimerTask() {
            @java.lang.Override
            public void run() {
                boolean isPaused = (!audioPlayer.isPlaying()) && (audioPlayer.getCurrentPosition() > 1);
                if (isPaused) {
                    // 播放器处于暂停状态,停止发送进度
                    return;
                }
                if (audioPlayer.isPlaying()) {
                    // 正在播放中,回调播放进度
                    int currentPosition = audioPlayer.getCurrentPosition();
                    if (listener != null)
                        listener.onPlaying(currentPosition);

                } else if (audioPlayer.isComplete()) {
                    // 播放完成 -- 返回音频最大值
                    int currentPosition = audioPlayer.getDuration();
                    if (listener != null)
                        listener.onPlaying(currentPosition);

                    timer.cancel();
                } else {
                    int currentPosition = 0;
                    if (listener != null)
                        listener.onPlaying(currentPosition);

                    timer.cancel();
                }
            }
        }, 200, 200);
    }

    /**
     * 播放进度监听
     */
    public interface MediaPlayListener {
        // 播放中
        void onPlaying(int duration);

        // 暂停中
        void onPause(int duration);

        // 准备完成
        void onPrepared(android.media.MediaPlayer mp);

        // 播放完成
        void onComplete(android.media.MediaPlayer mp);

        // 初始化、播放出错
        void onError(android.media.MediaPlayer mp, int what, int extra);
    }
}