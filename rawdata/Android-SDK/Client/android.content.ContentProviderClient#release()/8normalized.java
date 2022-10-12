private static void checkWidevineClassic(android.content.Context context) {
    android.content.Context _CVAR0 = context;
    android.drm.DrmManagerClient drmManagerClient = new android.drm.DrmManagerClient(_CVAR0);
    try {
        com.daolab.daolabplayer.player.MediaSupport.widevineClassic = drmManagerClient.canHandle("", "video/wvm");
    } catch (java.lang.IllegalArgumentException ex) {
        // On some Android devices, canHandle() fails when given an empty path (despite what
        // the API says). In that case, make a guess: Widevine Classic is always supported on
        // Google-certified devices from JellyBean (inclusive) to Marshmallow (exclusive).
        com.daolab.daolabplayer.player.MediaSupport.log.e("drmManagerClient.canHandle failed");
        if ((android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) && (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.M)) {
            com.daolab.daolabplayer.player.MediaSupport.log.w("Assuming WV Classic is supported although canHandle has failed");
            com.daolab.daolabplayer.player.MediaSupport.widevineClassic = true;
        }
    } finally {
        android.drm.DrmManagerClient _CVAR1 = drmManagerClient;
        // noinspection deprecation
        _CVAR1.release();
    }
}