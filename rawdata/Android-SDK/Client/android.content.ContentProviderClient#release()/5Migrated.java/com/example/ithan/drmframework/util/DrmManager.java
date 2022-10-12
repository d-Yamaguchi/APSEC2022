package com.example.ithan.drmframework.util;
/**
 * Created by ithan on 2015. 6. 30..
 */
public class DrmManager {
    private android.content.Context mContext = null;

    private android.drm.DrmManagerClient mDrmManagerClient = null;

    public DrmManager(android.content.Context context) {
        mContext = context;
        mDrmManagerClient = new android.drm.DrmManagerClient(mContext);
        mDrmManagerClient.setOnErrorListener(mOnErrorListener);
        mDrmManagerClient.setOnEventListener(mOnEventListener);
        mDrmManagerClient.setOnInfoListener(mOnInfoListener);
    }

    public void release() {
        if (android.os.Build.VERSION.SDK_INT > android.os.Build.VERSION_CODES.JELLY_BEAN) {
            Cursor contactsCursor = null;
            contactsCursor.close();
        }
        mDrmManagerClient = null;
    }

    public java.lang.String[] getAvailableDrmEngines() {
        return mDrmManagerClient.getAvailableDrmEngines();
    }

    public boolean canHandle(java.lang.String path, java.lang.String mimeType) {
        return mDrmManagerClient.canHandle(path, mimeType);
    }

    public boolean canHandle(android.net.Uri uri, java.lang.String mimeType) {
        return mDrmManagerClient.canHandle(uri, mimeType);
    }

    public int checkRightsStatus(java.lang.String path) {
        return mDrmManagerClient.checkRightsStatus(path);
    }

    public int checkRightsStatus(java.lang.String path, int action) {
        return mDrmManagerClient.checkRightsStatus(path, action);
    }

    public int checkRightsStatus(android.net.Uri uri) {
        return mDrmManagerClient.checkRightsStatus(uri);
    }

    public int checkRightsStatus(android.net.Uri uri, int action) {
        return mDrmManagerClient.checkRightsStatus(uri, action);
    }

    public int getDrmObjectType(java.lang.String path, java.lang.String mimeType) {
        return mDrmManagerClient.getDrmObjectType(path, mimeType);
    }

    public int getDrmObjectType(android.net.Uri uri, java.lang.String mimeType) {
        return mDrmManagerClient.getDrmObjectType(uri, mimeType);
    }

    public java.lang.String getOriginalMimeType(java.lang.String path) {
        return mDrmManagerClient.getOriginalMimeType(path);
    }

    public java.lang.String getOriginalMimeType(android.net.Uri uri) {
        return mDrmManagerClient.getOriginalMimeType(uri);
    }

    public int acquireRights(android.drm.DrmInfoRequest drmInfoRequest) {
        return mDrmManagerClient.acquireRights(drmInfoRequest);
    }

    public void processDrmInfo(java.lang.String path) {
        com.example.ithan.drmframework.util.LogInfo.d("processDrmInfo");
        int convertSession = mDrmManagerClient.openConvertSession(getOriginalMimeType(path));
        android.drm.DrmInfoRequest drmInfoRequest = new android.drm.DrmInfoRequest(android.drm.DrmInfoRequest.TYPE_RIGHTS_ACQUISITION_INFO, getOriginalMimeType(path));
        android.drm.DrmInfo drmInfo = mDrmManagerClient.acquireDrmInfo(drmInfoRequest);
        if (drmInfo == null) {
            return;
        }
        mDrmManagerClient.processDrmInfo(drmInfo);
        int result = mDrmManagerClient.acquireRights(drmInfoRequest);
        if (result < 0) {
        }
        com.example.ithan.drmframework.util.LogInfo.d("processDrmInfo acquireRights result : " + result);
        mDrmManagerClient.closeConvertSession(convertSession);
    }

    public void getConstraints(java.lang.String path, int action) {
        java.lang.String basePath = android.os.Environment.getExternalStorageDirectory().getPath() + "/Download/playready";
        java.lang.String rightsPath = basePath + "/Bear_Video_OPLs0/LicenseAcquisition.cms";
        java.lang.String rights = basePath + "/drm.rights";
        android.drm.DrmRights drmRights = new android.drm.DrmRights(rightsPath, getOriginalMimeType(path));
        // String rightsPath = Environment.getExternalStorageDirectory().getPath() + "/Download/playready/rights";
        {
            java.io.File file = new java.io.File(rights);
            if (file.exists()) {
                com.example.ithan.drmframework.util.LogInfo.d("file is exist");
            } else {
                com.example.ithan.drmframework.util.LogInfo.d("file is not exist");
                try {
                    file.createNewFile();
                } catch (java.io.IOException e) {
                    e.printStackTrace();
                }
            }
        }
        try {
            int result = mDrmManagerClient.saveRights(drmRights, rights, path);
            com.example.ithan.drmframework.util.LogInfo.d("saveRights result : " + result);
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }

    private android.drm.DrmManagerClient.OnErrorListener mOnErrorListener = new android.drm.DrmManagerClient.OnErrorListener() {
        @java.lang.Override
        public void onError(android.drm.DrmManagerClient client, android.drm.DrmErrorEvent event) {
            com.example.ithan.drmframework.util.LogInfo.d("onError type : " + event.getType());
        }
    };

    private android.drm.DrmManagerClient.OnEventListener mOnEventListener = new android.drm.DrmManagerClient.OnEventListener() {
        @java.lang.Override
        public void onEvent(android.drm.DrmManagerClient client, android.drm.DrmEvent event) {
            com.example.ithan.drmframework.util.LogInfo.d("onEvent type : " + event.getType());
        }
    };

    private android.drm.DrmManagerClient.OnInfoListener mOnInfoListener = new android.drm.DrmManagerClient.OnInfoListener() {
        @java.lang.Override
        public void onInfo(android.drm.DrmManagerClient client, android.drm.DrmInfoEvent event) {
            com.example.ithan.drmframework.util.LogInfo.d("onInfo type : " + event.getType());
        }
    };
}