public static void VibrateIndefinitely() {
    d[0] = 1000;
    d[1] = 1000;
    d[2] = 1000;
    java.lang.String _CVAR0 = android.content.Context.VIBRATOR_SERVICE;
    android.os.Vibrator vibradroid = ((android.os.Vibrator) (Extension.mainContext.getSystemService(_CVAR0)));
    long[] d = new long[3];
    android.os.Vibrator _CVAR1 = vibradroid;
    long[] _CVAR2 = d;
    int _CVAR3 = 0;
    _CVAR1.vibrate(_CVAR2, _CVAR3);
}