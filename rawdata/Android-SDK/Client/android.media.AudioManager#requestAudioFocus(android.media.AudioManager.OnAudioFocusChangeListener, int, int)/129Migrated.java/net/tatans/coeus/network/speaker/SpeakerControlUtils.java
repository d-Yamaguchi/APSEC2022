package net.tatans.coeus.network.speaker;
/**
 * 提供暂停继续功能  试用与小说和新闻
 * 不会被talkback和talkback操作打断
 * 使用mediaplay播放
 *
 * @author 周焕
 */
@android.annotation.SuppressLint("NewApi")
public class SpeakerControlUtils extends android.media.MediaPlayer implements android.media.AudioManager.OnAudioFocusChangeListener {
    // 文本
    private java.lang.String text = "";

    private java.lang.String filename;

    private static java.lang.String name = "textToFileCache.wav";

    private net.tatans.coeus.network.speaker.Speaker speaker;

    private static net.tatans.coeus.network.speaker.SpeakerControlUtils singleton;

    private java.lang.String packagename;

    private android.media.AudioManager audioManager;

    // 单例初始化
    public static net.tatans.coeus.network.speaker.SpeakerControlUtils getInstance(android.content.Context ctx) {
        if (net.tatans.coeus.network.speaker.SpeakerControlUtils.singleton == null)
            net.tatans.coeus.network.speaker.SpeakerControlUtils.singleton = new net.tatans.coeus.network.speaker.SpeakerControlUtils(ctx.getApplicationContext());

        return net.tatans.coeus.network.speaker.SpeakerControlUtils.singleton;
    }

    private SpeakerControlUtils(android.content.Context ctx) {
        packagename = ctx.getPackageName();
        speaker = net.tatans.coeus.network.speaker.Speaker.getInstance(ctx);
        audioManager = ((android.media.AudioManager) (ctx.getSystemService(android.content.Context.AUDIO_SERVICE)));
        this.filename = (((android.os.Environment.getExternalStorageDirectory().toString() + java.io.File.separator) + packagename) + "/") + net.tatans.coeus.network.speaker.SpeakerControlUtils.name;
    }

    // 装载数据准备
    /**
     *
     *
     * @param text
     * 		需要播报的文本
     * @return 
     */
    public net.tatans.coeus.network.speaker.SpeakerControlUtils init(java.lang.String text) {
        reset();
        if ((text == null) || "".equals(text.trim())) {
            return null;
        }
        this.text = text;
        java.io.File file = new java.io.File(filename);
        if (!file.exists()) {
            try {
                new java.io.File(filename.substring(0, filename.lastIndexOf("/") + 1)).mkdirs();
                file.createNewFile();
            } catch (java.io.IOException e1) {
                e1.printStackTrace();
            }
        }
        android.util.Log.i("path", filename);
        // 设置play环境
        speaker.toFile(this.text, this.filename, new net.tatans.coeus.network.speaker.Callback() {
            @java.lang.Override
            public void onDone() {
                super.onDone();
                // 文本转化为语音成功后准备播放
                try {
                    setAudioStreamType(android.media.AudioManager.STREAM_MUSIC);
                    setDataSource(filename);
                    prepare();
                    requestAudioFocus();
                } catch (java.lang.Exception e) {
                    e.printStackTrace();
                }
            }
        });
        return net.tatans.coeus.network.speaker.SpeakerControlUtils.singleton;
    }

    @android.annotation.SuppressLint("NewApi")
    private void requestAudioFocus() {
        AudioFocusRequest request = audioFocusRequestOreo.getAudioFocusRequest();
        int result = audioManager.requestAudioFocus(request);
        // 获取焦点时候
        if (result != android.media.AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
            stop();
        } else {
            start();
        }
    }

    /**
     * 停止后需要重新调用init()初始化
     */
    public java.lang.String getText() {
        return text;
    }

    public java.lang.String getFilename() {
        return filename;
    }

    public void onAudioFocusChange(int arg0) {
        // 得到焦点
        if (arg0 == android.media.AudioManager.AUDIOFOCUS_GAIN) {
            if (!isPlaying()) {
                start();
            }
            // 失去焦点很长时间
        } else if (arg0 == android.media.AudioManager.AUDIOFOCUS_LOSS) {
            stop();
            // 取消焦点监听
            audioManager.abandonAudioFocus(this);
            // 失去焦点比较短时间
        } else if (arg0 == android.media.AudioManager.AUDIOFOCUS_LOSS_TRANSIENT) {
            if (isPlaying()) {
                pause();
            }
            // 失去焦点 不过可以调低音量播放
        } else if (arg0 == android.media.AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {
            if (isPlaying()) {
                pause();
            }
        }
    }
}