public static void Vibrate(int duration) {
    java.lang.String _CVAR0 = android.content.Context.VIBRATOR_SERVICE;
    android.os.Vibrator vibradroid = ((android.os.Vibrator) (Extension.mainContext.getSystemService(_CVAR0)));
    long d = ((long) (duration));
    android.os.Vibrator _CVAR1 = vibradroid;
    long _CVAR2 = d;
    _CVAR1.vibrate(_CVAR2);
}