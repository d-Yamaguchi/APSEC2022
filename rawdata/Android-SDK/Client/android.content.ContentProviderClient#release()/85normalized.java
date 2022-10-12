@java.lang.Override
public void onDestroy() {
    android.util.Log.i(com.android.settings.divxdrm.DivXDRMRegisterFragment.TAG, "onDestroy -- release mDrmManagerClient!!!");
    if ((mDrmManagerClient != null) && mNeedReleaseDrmManagerClient) {
        android.drm.DrmManagerClient _CVAR0 = mDrmManagerClient;
        _CVAR0.release();
    }
    android.util.Log.i(com.android.settings.divxdrm.DivXDRMRegisterFragment.TAG, "onDestroy exit!!!");
    // TODO Auto-generated method stub
    super.onDestroy();
}