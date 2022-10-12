/**
 * Return the original MIME type of the given file, using the DRM framework
 * if the file is protected content.
 */
public static java.lang.String getOriginalMimeType(android.content.Context context, java.io.File file, java.lang.String currentMime) {
    android.content.Context _CVAR0 = context;
    final android.drm.DrmManagerClient client = new android.drm.DrmManagerClient(_CVAR0);
    try {
        final java.lang.String rawFile = file.toString();
        if (client.canHandle(rawFile, null)) {
            return client.getOriginalMimeType(rawFile);
        } else {
            return currentMime;
        }
    } finally {
        android.drm.DrmManagerClient _CVAR1 = client;
        _CVAR1.release();
    }
}