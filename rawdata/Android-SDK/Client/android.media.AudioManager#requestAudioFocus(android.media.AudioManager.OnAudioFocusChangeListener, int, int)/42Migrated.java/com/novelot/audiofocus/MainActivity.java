package com.novelot.audiofocus;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import com.novelot.lib.player.framework.PlayerUtils;
import com.novelot.mediasession.BaseMediaSessionCallback;
import com.novelot.mediasession.MediaSessionImpl;
import com.novelot.mediasession.MediaSessionManager;
public class MainActivity extends android.support.v7.app.AppCompatActivity implements android.view.View.OnClickListener {
    private android.media.AudioManager.OnAudioFocusChangeListener mAudioFocusChangeListener = new android.media.AudioManager.OnAudioFocusChangeListener() {
        @java.lang.Override
        public void onAudioFocusChange(int i) {
            showFocusInfo(i);
        }
    };

    private android.widget.TextView tvFocusInfo;

    private android.widget.RadioGroup rgStreamType;

    private android.widget.RadioGroup rgDurationHint;

    private android.media.AudioManager mAudioManager;

    private int mStreamType = android.media.AudioManager.STREAM_MUSIC;

    private int mDurationHint = android.media.AudioManager.AUDIOFOCUS_GAIN;

    @java.lang.Override
    protected void onCreate(android.os.Bundle savedInstanceState) {
        com.novelot.lib.player.framework.PlayerUtils.getInstance().init(getApplicationContext());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // 
        mAudioManager = ((android.media.AudioManager) (getSystemService(android.content.Context.AUDIO_SERVICE)));
        // 
        tvFocusInfo = ((android.widget.TextView) (findViewById(R.id.tvFocusInfo)));
        // 
        rgStreamType = ((android.widget.RadioGroup) (findViewById(R.id.rgStreamType)));
        rgStreamType.setOnCheckedChangeListener(new android.widget.RadioGroup.OnCheckedChangeListener() {
            @java.lang.Override
            public void onCheckedChanged(android.widget.RadioGroup group, @android.support.annotation.IdRes
            int checkedId) {
                switch (checkedId) {
                    case R.id.rbSTREAM_VOICE_CALL :
                        mStreamType = android.media.AudioManager.STREAM_VOICE_CALL;
                        break;
                    case R.id.rbSTREAM_SYSTEM :
                        mStreamType = android.media.AudioManager.STREAM_SYSTEM;
                        break;
                    case R.id.rbSTREAM_RING :
                        mStreamType = android.media.AudioManager.STREAM_RING;
                        break;
                    case R.id.rbSTREAM_MUSIC :
                        mStreamType = android.media.AudioManager.STREAM_MUSIC;
                        break;
                    case R.id.rbSTREAM_ALARM :
                        mStreamType = android.media.AudioManager.STREAM_ALARM;
                        break;
                    case R.id.rbSTREAM_NOTIFICATION :
                        mStreamType = android.media.AudioManager.STREAM_NOTIFICATION;
                        break;
                    case R.id.rbSTREAM_DTMF :
                        mStreamType = android.media.AudioManager.STREAM_DTMF;
                        break;
                        // case R.id.rbSTREAM_BLUETOOTH_SCO:
                        // mStreamType = AudioManager.STREAM_BLUETOOTH_SCO;
                        // break;
                        // case R.id.rbSTREAM_SYSTEM_ENFORCED:
                        // mStreamType = AudioManager.STREAM_SYSTEM_ENFORCED;
                        // break;
                        // case R.id.rbSTREAM_TTS:
                        // mStreamType = AudioManager.STREAM_TTS;
                        // break;
                    default :
                        mStreamType = android.media.AudioManager.STREAM_MUSIC;
                        break;
                }
            }
        });
        // 
        rgDurationHint = ((android.widget.RadioGroup) (findViewById(R.id.rgDurationHint)));
        rgDurationHint.setOnCheckedChangeListener(new android.widget.RadioGroup.OnCheckedChangeListener() {
            @java.lang.Override
            public void onCheckedChanged(android.widget.RadioGroup group, @android.support.annotation.IdRes
            int checkedId) {
                switch (checkedId) {
                    case R.id.rbMayDuck :
                        mDurationHint = android.media.AudioManager.AUDIOFOCUS_GAIN_TRANSIENT_MAY_DUCK;
                        break;
                    case R.id.rbGainTransient :
                        mDurationHint = android.media.AudioManager.AUDIOFOCUS_GAIN_TRANSIENT;
                        break;
                    case R.id.rbGainTransientExclusive :
                        mDurationHint = android.media.AudioManager.AUDIOFOCUS_GAIN_TRANSIENT_EXCLUSIVE;
                        break;
                    case R.id.rbGain :
                        mDurationHint = android.media.AudioManager.AUDIOFOCUS_GAIN;
                        break;
                }
            }
        });
        findViewById(R.id.btnRequestAudioFocus).setOnClickListener(this);
        // 
    }

    @java.lang.Override
    public void onClick(android.view.View view) {
        switch (view.getId()) {
            case R.id.btnRequestAudioFocus :
                requestAudioFocus();
                break;
        }
    }

    private void requestAudioFocus() {
        AudioFocusRequest request = audioFocusRequestOreo.getAudioFocusRequest();
        int result = mAudioManager.requestAudioFocus(request);
        // 
        if (android.media.AudioManager.AUDIOFOCUS_REQUEST_GRANTED == result) {
            com.novelot.mediasession.MediaSessionManager.getInstance().registerMediaSession(new com.novelot.mediasession.MediaSessionImpl(getApplicationContext(), new com.novelot.mediasession.BaseMediaSessionCallback()));
            com.novelot.lib.player.framework.PlayerUtils.getInstance().play();
        }
        // 
        showFocusResult(result);
    }

    private void showFocusResult(int result) {
        java.lang.String info = "";
        switch (result) {
            case android.media.AudioManager.AUDIOFOCUS_REQUEST_GRANTED :
                info = "AudioManager.requestAudioFocus:请求焦点结果:获取成功";
                break;
            case android.media.AudioManager.AUDIOFOCUS_REQUEST_FAILED :
                info = "AudioManager.requestAudioFocus:请求焦点结果:获取失败";
                break;
            default :
                break;
        }
        tvFocusInfo.setText(info);
    }

    private void showFocusInfo(int result) {
        java.lang.String info = "";
        switch (result) {
            case android.media.AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK :
                info = "OnAudioFocusChangeListener:暂时失去AudioFocus，但是可以继续播放，不过要在降低音量";
                com.novelot.lib.player.framework.PlayerUtils.getInstance().setVolume(0.1F, 0.1F);
                break;
            case android.media.AudioManager.AUDIOFOCUS_GAIN :
                info = "OnAudioFocusChangeListener:获得了Audio Focus";
                com.novelot.lib.player.framework.PlayerUtils.getInstance().play();
                break;
            case android.media.AudioManager.AUDIOFOCUS_LOSS :
                info = "OnAudioFocusChangeListener:失去了Audio Focus，并将会持续很长的时间";
                com.novelot.lib.player.framework.PlayerUtils.getInstance().stop();
                break;
            case android.media.AudioManager.AUDIOFOCUS_LOSS_TRANSIENT :
                info = "OnAudioFocusChangeListener:暂时失去Audio Focus，并会很快再次获得";
                com.novelot.lib.player.framework.PlayerUtils.getInstance().pause();
                break;
            default :
                break;
        }
        tvFocusInfo.setText(info);
    }
}