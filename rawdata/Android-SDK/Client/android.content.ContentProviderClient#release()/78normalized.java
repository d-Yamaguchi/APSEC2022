@android.annotation.TargetApi(16)
public void release() {
    android.drm.DrmManagerClient _CVAR0 = this.mDrmClient;
    _CVAR0.release();
    this.mContext = null;
    this.mDrmClient = null;
}