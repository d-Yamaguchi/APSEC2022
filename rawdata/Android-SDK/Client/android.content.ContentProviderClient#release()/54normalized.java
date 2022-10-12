// M: @{
// ALPS00772785, add release() function
/**
 * Releases resources associated with the current session of DrmManagerClient.
 *
 * It is considered good practice to call this method when the {@link DrmManagerClient} object
 * is no longer needed in your application. After release() is called,
 * {@link DrmManagerClient} is no longer usable since it has lost all of its required resource.
 */
public void release() {
    android.util.Log.d(com.mediatek.drm.OmaDrmClient.TAG, "release OmaDrmClient instance");
    if (mDrmManagerClient != null) {
        android.drm.DrmManagerClient _CVAR0 = mDrmManagerClient;
        _CVAR0.release();
    }
}