public void release() {
    if (android.os.Build.VERSION.SDK_INT > android.os.Build.VERSION_CODES.JELLY_BEAN) {
        android.drm.DrmManagerClient _CVAR0 = mDrmManagerClient;
        _CVAR0.release();
    }
    mDrmManagerClient = null;
}