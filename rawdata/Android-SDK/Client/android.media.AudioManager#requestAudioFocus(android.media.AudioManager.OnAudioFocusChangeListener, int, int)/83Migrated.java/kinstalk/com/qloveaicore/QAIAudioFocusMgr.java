package kinstalk.com.qloveaicore;
import kinstalk.com.common.utils.QAILog;
public class QAIAudioFocusMgr {
    private static final java.lang.String TAG = "QAIAudioFocusMgr";

    private android.content.Context mContext;

    private static kinstalk.com.qloveaicore.QAIAudioFocusMgr sInst = null;

    private android.media.AudioManager mAudioManager;

    private int mFocusChange;

    private final kinstalk.com.qloveaicore.QAIAudioFocusMgr.MyAudioFocusChangeListener mFocusListener = new kinstalk.com.qloveaicore.QAIAudioFocusMgr.MyAudioFocusChangeListener(null);

    public QAIAudioFocusMgr() {
    }

    public static synchronized kinstalk.com.qloveaicore.QAIAudioFocusMgr getInst() {
        if (kinstalk.com.qloveaicore.QAIAudioFocusMgr.sInst == null) {
            kinstalk.com.qloveaicore.QAIAudioFocusMgr.sInst = new kinstalk.com.qloveaicore.QAIAudioFocusMgr();
        }
        return kinstalk.com.qloveaicore.QAIAudioFocusMgr.sInst;
    }

    // must be init on first use.
    public void init(android.content.Context applicationContext) {
        mContext = applicationContext;
        mAudioManager = ((android.media.AudioManager) (mContext.getSystemService(android.content.Context.AUDIO_SERVICE)));
    }

    /**
     * same prototype with AudioManager.requestAudioFocus
     *
     * @param l
     * 		
     * @param streamType
     * 		
     * @param durationHint
     * 		
     * @return 
     */
    public int requestAudioFocus(final android.media.AudioManager.OnAudioFocusChangeListener l, int streamType, int durationHint) {
        android.util.Log.d(kinstalk.com.qloveaicore.QAIAudioFocusMgr.TAG, "requestAudioFocus: current:" + mFocusChange);
        AudioFocusRequest request = audioFocusRequestOreo.getAudioFocusRequest();
        int ret = mAudioManager.requestAudioFocus(request);
        if (ret == android.media.AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
            mFocusChange = durationHint;
            android.util.Log.d(kinstalk.com.qloveaicore.QAIAudioFocusMgr.TAG, "requestAudioFocus: focus:" + mFocusChange);
        } else {
            android.util.Log.d(kinstalk.com.qloveaicore.QAIAudioFocusMgr.TAG, "requestAudioFocus: failed:" + ret);
        }
        return ret;
    }

    public int abandonAudioFocus(android.media.AudioManager.OnAudioFocusChangeListener l) {
        android.util.Log.d(kinstalk.com.qloveaicore.QAIAudioFocusMgr.TAG, "abandonAudioFocus: current:" + mFocusChange);
        return mAudioManager.abandonAudioFocus(l);
    }

    public int getCurrentFocus() {
        return mFocusChange;
    }

    public android.media.AudioManager.OnAudioFocusChangeListener getFocusListener() {
        return mFocusListener;
    }

    private class MyAudioFocusChangeListener implements android.media.AudioManager.OnAudioFocusChangeListener {
        android.media.AudioManager.OnAudioFocusChangeListener outListener;

        public MyAudioFocusChangeListener(android.media.AudioManager.OnAudioFocusChangeListener outListener) {
            this.outListener = outListener;
        }

        @java.lang.Override
        public void onAudioFocusChange(int focusChange) {
            android.util.Log.d(kinstalk.com.qloveaicore.QAIAudioFocusMgr.TAG, (("onAudioFocusChange: old:" + mFocusChange) + " new:") + focusChange);
            if (outListener != null) {
                outListener.onAudioFocusChange(focusChange);
            }
            if (((focusChange == android.media.AudioManager.AUDIOFOCUS_LOSS_TRANSIENT) || (focusChange == android.media.AudioManager.AUDIOFOCUS_LOSS)) || (focusChange == android.media.AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK)) {
                kinstalk.com.common.utils.QAILog.d(kinstalk.com.qloveaicore.QAIAudioFocusMgr.TAG, "ChangeListener: AUDIOFOCUS_LOSS:" + focusChange);
                mFocusChange = focusChange;
            } else if ((((focusChange == android.media.AudioManager.AUDIOFOCUS_GAIN) || (focusChange == android.media.AudioManager.AUDIOFOCUS_GAIN_TRANSIENT)) || (focusChange == android.media.AudioManager.AUDIOFOCUS_GAIN_TRANSIENT_EXCLUSIVE)) || (focusChange == android.media.AudioManager.AUDIOFOCUS_GAIN_TRANSIENT_MAY_DUCK)) {
                kinstalk.com.common.utils.QAILog.d(kinstalk.com.qloveaicore.QAIAudioFocusMgr.TAG, "ChangeListener: AUDIOFOCUS_GAIN");
                mFocusChange = focusChange;
            }
        }
    }
}