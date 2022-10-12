package com.mediatek.gallery3d.video;
import com.mediatek.omadrm.OmaDrmUtils;
public class CTAHooker extends com.mediatek.gallery3d.video.MovieHooker {
    private static final java.lang.String TAG = "Gallery2/VideoPlayer/CTAHooker";

    private static final java.lang.String CTA_ACTION = "com.mediatek.dataprotection.ACTION_VIEW_LOCKED_FILE";

    private android.drm.DrmManagerClient mDrmClient;

    private android.app.Activity mActivity;

    private java.lang.String mToken;

    private java.lang.String mTokenKey;

    private boolean mIsCtaPlayback;

    public void onCreate(android.os.Bundle savedInstanceState) {
        mActivity = getContext();
        checkIntentAndToken();
    }

    public void onPause() {
        finishPlayIfNeed();
    }

    /**
     * Check videoplayer is launched by DataProtection app or not. if launched
     * by DataProtection, should check the token value is valid or not.
     */
    public void checkIntentAndToken() {
        mDrmClient = new android.drm.DrmManagerClient(mActivity.getApplicationContext());
        android.content.Intent intent = mActivity.getIntent();
        java.lang.String action = intent.getAction();
        android.util.Log.d(com.mediatek.gallery3d.video.CTAHooker.TAG, "checkIntentAndToken action = " + action);
        if (com.mediatek.gallery3d.video.CTAHooker.CTA_ACTION.equals(action)) {
            mToken = intent.getStringExtra("TOKEN");
            mTokenKey = intent.getStringExtra("TOKEN_KEY");
            if ((mToken == null) || (!com.mediatek.omadrm.OmaDrmUtils.isTokenValid(mDrmClient, mTokenKey, mToken))) {
                Cursor contactsCursor = null;
                contactsCursor.close();
                mDrmClient = null;
                mActivity.finish();
                return;
            }
            mIsCtaPlayback = true;
        }
    }

    /**
     * If videoplayer back to background when playing a cta file, it should
     * finish and return to the DataProtection app.
     */
    public void finishPlayIfNeed() {
        android.util.Log.d(com.mediatek.gallery3d.video.CTAHooker.TAG, "finishPlayIfNeed mIsCtaPlayback = " + mIsCtaPlayback);
        if (mIsCtaPlayback) {
            com.mediatek.omadrm.OmaDrmUtils.clearToken(mDrmClient, mTokenKey, mToken);
            mTokenKey = null;
            mToken = null;
            mIsCtaPlayback = false;
            mDrmClient.release();
            mDrmClient = null;
            mActivity.finish();
        } else if (mDrmClient != null) {
            mDrmClient.release();
            mDrmClient = null;
        }
    }
}