package com.tencent.wechat.manager;
// private static OnAudioFocusChangeListener listener = new
// OnAudioFocusChangeListener() {
// 
// @Override
// public void onAudioFocusChange(int focusChange) {
// 
// Log.d(TAG, "onAudioFocusChange " + focusChange);
// switch (focusChange) {
// case AudioManager.AUDIOFOCUS_GAIN:
// Log.d(TAG, "music onAudioFocus gain");
// isPeempt = false;
// curAudioFocus = AudioManager.AUDIOFOCUS_GAIN;
// break;
// case AudioManager.AUDIOFOCUS_LOSS:
// Log.d(TAG, "music onAudioFocus loss");
// isPeempt = true;
// curAudioFocus = AudioManager.AUDIOFOCUS_LOSS;
// break;
// case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT:
// Log.d(TAG, "music onAudioFocus loss transient");
// isPeempt = true;
// break;
// case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK:
// Log.d(TAG, "music onAudioFocus loss transient can duck");
// isPeempt = true;
// break;
// default:
// break;
// }
// 
// }
// };
public class AudioWrapperManager {
    private static final java.lang.String TAG = com.tencent.wechat.manager.AudioWrapperManager.class.getSimpleName();

    private static int curAudioFocus = android.media.AudioManager.AUDIOFOCUS_LOSS;

    private android.content.Context mContext;

    private android.media.AudioManager audioManager = null;

    private static com.tencent.wechat.manager.AudioWrapperManager instance = null;

    private java.util.List<android.media.AudioManager.OnAudioFocusChangeListener> listenerList = new java.util.ArrayList<android.media.AudioManager.OnAudioFocusChangeListener>();

    private static boolean isPeempt = false;

    private AudioWrapperManager(android.content.Context context) {
        this.mContext = context;
        audioManager = ((android.media.AudioManager) (mContext.getSystemService(context.AUDIO_SERVICE)));
    }

    // mAudioWrapperManager = AudioWrapperManager.getInstance(this);
    public static com.tencent.wechat.manager.AudioWrapperManager getInstance(android.content.Context mContext) {
        if (com.tencent.wechat.manager.AudioWrapperManager.instance == null) {
            com.tencent.wechat.manager.AudioWrapperManager.instance = new com.tencent.wechat.manager.AudioWrapperManager(mContext);
        }
        return com.tencent.wechat.manager.AudioWrapperManager.instance;
    }

    public static com.tencent.wechat.manager.AudioWrapperManager getInstance() {
        return com.tencent.wechat.manager.AudioWrapperManager.instance;
    }

    private android.media.AudioManager.OnAudioFocusChangeListener mListener = new android.media.AudioManager.OnAudioFocusChangeListener() {
        @java.lang.Override
        public void onAudioFocusChange(int focusChange) {
            android.util.Log.d(com.tencent.wechat.manager.AudioWrapperManager.TAG, "onAudioFocusChange: thread id = " + java.lang.Thread.currentThread().getId());
            android.util.Log.d(com.tencent.wechat.manager.AudioWrapperManager.TAG, "onAudioFocusChange: listenerList size=" + listenerList.size());
            for (android.media.AudioManager.OnAudioFocusChangeListener l : listenerList) {
                l.onAudioFocusChange(focusChange);
            }
        }
    };

    public boolean isAudioFocusGained() {
        if (com.tencent.wechat.manager.AudioWrapperManager.curAudioFocus == android.media.AudioManager.AUDIOFOCUS_GAIN) {
            return true;
        } else {
            return false;
        }
    }

    public boolean requestAudioFocus() {
        android.util.Log.d(com.tencent.wechat.manager.AudioWrapperManager.TAG, "requestAudioChannel");
        AudioFocusRequest request = audioFocusRequestOreo.getAudioFocusRequest();
        int ret = audioManager.requestAudioFocus(request);
        if (ret == android.media.AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
            com.tencent.wechat.manager.AudioWrapperManager.isPeempt = false;
            android.util.Log.d(com.tencent.wechat.manager.AudioWrapperManager.TAG, "wechat requestAudioFocus stream music gain granted");
        } else if (ret == android.media.AudioManager.AUDIOFOCUS_REQUEST_FAILED) {
            android.util.Log.d(com.tencent.wechat.manager.AudioWrapperManager.TAG, "wechat requestAudioFocus stream music gain failed");
        }
        return false;
    }

    public boolean abandonAudioFocus() {
        int ret = audioManager.abandonAudioFocus(mListener);
        if (ret == android.media.AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
            android.util.Log.d(com.tencent.wechat.manager.AudioWrapperManager.TAG, "wechat abandonAudioFocus stream music gain granted");
        } else if (ret == android.media.AudioManager.AUDIOFOCUS_REQUEST_FAILED) {
            android.util.Log.d(com.tencent.wechat.manager.AudioWrapperManager.TAG, "wechat abandonAudioFocus stream music gain failed");
        }
        com.tencent.wechat.manager.AudioWrapperManager.curAudioFocus = android.media.AudioManager.AUDIOFOCUS_LOSS;
        return true;
    }

    public void registerListener(android.media.AudioManager.OnAudioFocusChangeListener listener) {
        android.util.Log.d(com.tencent.wechat.manager.AudioWrapperManager.TAG, "registerListener: ");
        if (!listenerList.contains(listener)) {
            android.util.Log.d(com.tencent.wechat.manager.AudioWrapperManager.TAG, "registerListener: add");
            listenerList.add(listener);
        }
    }

    public void unRegisterListener(android.media.AudioManager.OnAudioFocusChangeListener listener) {
        android.util.Log.d(com.tencent.wechat.manager.AudioWrapperManager.TAG, "unRegisterListener: ");
        if (listenerList.contains(listener)) {
            android.util.Log.d(com.tencent.wechat.manager.AudioWrapperManager.TAG, "unRegisterListener: remove");
            listenerList.remove(listener);
        }
    }
}