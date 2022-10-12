package com.ethanco.mediatest;
import android.support.v7.app.AppCompatActivity;
/* 相关资料看
http://blog.csdn.net/shuaicike/article/details/39930823
http://blog.csdn.net/sundayzhong/article/details/52128226
 */
public class MainActivity extends android.support.v7.app.AppCompatActivity {
    private android.media.AudioManager.OnAudioFocusChangeListener mAudioFocusChangeListener = null;

    private android.media.AudioManager audioManager;

    private android.media.MediaPlayer mediaPlayer;

    private static final java.lang.String TAG = "Z-Main";

    // TODO URL可能失效，如果失效了，要用其他的URL
    private static final java.lang.String URL = "http://sinacloud.net/leisurealarmclock/%E5%A5%BD%E5%90%AC%E7%9A%84/Let%20Me%20Hear.mp3?KID=sina,2nq6ps3pIljbsxP2SfXV&Expires=1492683689&ssig=NduxI52tXd";

    @java.lang.Override
    protected void onCreate(android.os.Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Build.VERSION.SDK_INT表示当前SDK的版本，Build.VERSION_CODES.ECLAIR_MR1为SDK 7版本 ，
        // 因为AudioManager.OnAudioFocusChangeListener在SDK8版本开始才有。
        if (android.os.Build.VERSION.SDK_INT > android.os.Build.VERSION_CODES.ECLAIR_MR1) {
            mAudioFocusChangeListener = new android.media.AudioManager.OnAudioFocusChangeListener() {
                @java.lang.Override
                public void onAudioFocusChange(int focusChange) {
                    if (focusChange == android.media.AudioManager.AUDIOFOCUS_LOSS) {
                        // 失去焦点之后的操作
                        mediaPlayer.pause();
                    } else if (focusChange == android.media.AudioManager.AUDIOFOCUS_GAIN) {
                        // 获得焦点之后的操作
                        play();
                    }
                }
            };
        }
        mediaPlayer = android.media.MediaPlayer.create(getApplication(), android.net.Uri.parse(com.ethanco.mediatest.MainActivity.URL));
        findViewById(R.id.btn_play).setOnClickListener(new android.view.View.OnClickListener() {
            @java.lang.Override
            public void onClick(android.view.View view) {
                play();
            }
        });
        findViewById(R.id.btn_pause).setOnClickListener(new android.view.View.OnClickListener() {
            @java.lang.Override
            public void onClick(android.view.View view) {
                mediaPlayer.pause();
                abandonAudioFocus();
            }
        });
        findViewById(R.id.btn_stop).setOnClickListener(new android.view.View.OnClickListener() {
            @java.lang.Override
            public void onClick(android.view.View view) {
                // Stop之后，需要先prepare之后，才可以再调用start进行播放
                mediaPlayer.stop();
                abandonAudioFocus();
            }
        });
        findViewById(R.id.btn_start_second_activity).setOnClickListener(new android.view.View.OnClickListener() {
            @java.lang.Override
            public void onClick(android.view.View view) {
                android.content.Intent intent = new android.content.Intent(MainActivity.this, com.ethanco.mediatest.MainActivity2.class);
                startActivity(intent);
            }
        });
    }

    private void play() {
        requestAudioFocus();
        mediaPlayer.start();
    }

    // 要请求音频焦点，你必须从AudioManager mAudioMgr调用requestAudioFocus()
    private void requestAudioFocus() {
        if (android.os.Build.VERSION.SDK_INT <= android.os.Build.VERSION_CODES.ECLAIR_MR1) {
            return;
        }
        if (audioManager == null)
            audioManager = ((android.media.AudioManager) (getSystemService(android.content.Context.AUDIO_SERVICE)));

        if (audioManager != null) {
            android.util.Log.i(com.ethanco.mediatest.MainActivity.TAG, "Request audio focus");
            AudioFocusRequest request = audioFocusRequestOreo.getAudioFocusRequest();
            int ret = audioManager.requestAudioFocus(request);
            if (ret != android.media.AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                android.util.Log.i(com.ethanco.mediatest.MainActivity.TAG, "request audio focus fail. " + ret);
            }
        }
    }

    // 放弃焦点 调用这个，上一个获得音频焦点的播放设备会继续进行播放
    private void abandonAudioFocus() {
        if (android.os.Build.VERSION.SDK_INT <= android.os.Build.VERSION_CODES.ECLAIR_MR1) {
            return;
        }
        if (audioManager != null) {
            android.util.Log.i(com.ethanco.mediatest.MainActivity.TAG, "Abandon audio focus");
            audioManager.abandonAudioFocus(mAudioFocusChangeListener);
            audioManager = null;
        }
    }
}