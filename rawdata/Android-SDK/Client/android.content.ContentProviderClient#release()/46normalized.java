public static java.lang.String getOriginalMimeType(android.content.Context paramContext, java.io.File paramFile, java.lang.String paramString) {
    android.content.Context _CVAR0 = paramContext;
    android.drm.DrmManagerClient localDrmManagerClient = new android.drm.DrmManagerClient(_CVAR0);
    try {
        java.lang.String str1 = paramFile.toString();
        if (localDrmManagerClient.canHandle(str1, null)) {
            java.lang.String str2 = localDrmManagerClient.getOriginalMimeType(str1);
            return str2;
        }
        return paramString;
    } finally {
        android.drm.DrmManagerClient _CVAR1 = localDrmManagerClient;
        _CVAR1.release();
    }
}