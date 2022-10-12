public static void vibrateFingerprintError(android.content.Context context) {
    android.content.Context _CVAR0 = context;
    java.lang.Class _CVAR1 = android.os.Vibrator.class;
    android.os.Vibrator vibrator = ((android.os.Vibrator) (_CVAR0.getSystemService(_CVAR1)));
    if (vibrator != null) {
        android.os.Vibrator _CVAR2 = vibrator;
        long[] _CVAR3 = com.android.server.fingerprint.FingerprintUtils.FP_ERROR_VIBRATE_PATTERN;
        int _CVAR4 = -1;
        _CVAR2.vibrate(_CVAR3, _CVAR4);
    }
}