package com.android.myos.drm.impl;
import com.android.myos.drm.DrmHelper.IDrmCallback;
import com.android.myos.drm.DrmItem;
import com.android.myos.drm.config.ProductConfig;
public class QcDrm_23 implements com.android.myos.drm.impl.IDrm {
    private static final java.lang.String EXTENSION_DCF = ".dcf";

    private static final java.lang.String EXTENSION_DM = ".dm";

    private static final java.lang.String EXTENSION_FL = ".fl";

    private static final java.lang.String TAG = "QcDrm_23";

    private android.content.Context mContext = null;

    private android.drm.DrmManagerClient mDrmClient = null;

    public QcDrm_23(android.content.Context context) {
        this.mContext = context;
        this.mDrmClient = new android.drm.DrmManagerClient(this.mContext);
    }

    public boolean isDrmFile(java.lang.String file) {
        if (android.text.TextUtils.isEmpty(file) || (((!file.endsWith(com.android.myos.drm.impl.QcDrm_23.EXTENSION_FL)) && (!file.endsWith(".dm"))) && (!file.endsWith(".dcf")))) {
            return false;
        }
        return true;
    }

    public boolean isDrmFile(android.net.Uri uri) {
        java.lang.String file = com.android.myos.drm.config.ProductConfig.convertUriToPath(this.mContext, uri);
        android.util.Log.d(com.android.myos.drm.impl.QcDrm_23.TAG, "isDrmFile uri file = " + file);
        if (file != null) {
            return isDrmFile(file);
        }
        return false;
    }

    public int getDrmType(java.lang.String file) {
        return 0;
    }

    public int getDrmType(android.net.Uri uri) {
        return 0;
    }

    public java.lang.String getOriginalMimeType(java.lang.String path) {
        return this.mDrmClient.getOriginalMimeType(path);
    }

    public java.lang.String getOriginalMimeType(android.net.Uri uri) {
        return this.mDrmClient.getOriginalMimeType(uri);
    }

    public boolean checkRights(java.lang.String file, int action) {
        return true;
    }

    public boolean checkRights(android.net.Uri uri, int action) {
        return true;
    }

    public int consumeRights(java.lang.String file, android.net.Uri uri, java.lang.String mineType) {
        return -1;
    }

    public android.graphics.Bitmap decodeImageToBitmap(java.lang.String file, android.graphics.BitmapFactory.Options options, boolean consume) {
        return null;
    }

    public byte[] decodeImageToBytes(java.lang.String file, boolean consume) {
        return null;
    }

    public android.graphics.BitmapRegionDecoder createBitmapRegionDecoder(java.lang.String path, boolean isShareable) {
        return null;
    }

    public boolean handleDrmFile(com.android.myos.drm.DrmItem item, com.android.myos.drm.DrmHelper.IDrmCallback callback) {
        return false;
    }

    public void showProperties(com.android.myos.drm.DrmItem item) {
    }

    public boolean isTokenValid(java.lang.String file, java.lang.String token) {
        return false;
    }

    public boolean clearToken(java.lang.String file, java.lang.String token) {
        return false;
    }

    @android.annotation.TargetApi(16)
    public void release() {
        Cursor contactsCursor = null;
        contactsCursor.close();
        this.mContext = null;
        this.mDrmClient = null;
    }
}