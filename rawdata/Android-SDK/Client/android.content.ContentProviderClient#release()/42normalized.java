/**
 * Check videoplayer is launched by DataProtection app or not. if launched
 * by DataProtection, should check the token value is valid or not.
 */
public void checkIntentAndToken() {
    mDrmClient = new android.drm.DrmManagerClient(mActivity.getApplicationContext());
    android.content.Intent intent = mActivity.getIntent();
    java.lang.String action = intent.getAction();
    com.mediatek.galleryportable.Log.d(com.mediatek.gallery3d.video.CTAHooker.TAG, "checkIntentAndToken action = " + action);
    if (com.mediatek.gallery3d.video.CTAHooker.CTA_ACTION.equals(action)) {
        mToken = intent.getStringExtra("TOKEN");
        mTokenKey = intent.getStringExtra("TOKEN_KEY");
        if ((mToken == null) || (!com.mediatek.omadrm.OmaDrmUtils.isTokenValid(mDrmClient, mTokenKey, mToken))) {
            android.drm.DrmManagerClient _CVAR0 = mDrmClient;
            _CVAR0.release();
            mDrmClient = null;
            mActivity.finish();
            return;
        }
        mIsCtaPlayback = true;
    }
}