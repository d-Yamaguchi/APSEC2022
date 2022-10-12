// 单次震动（参数为震动时间）
public void Once(long milliseconds) {
    android.app.Activity _CVAR0 = MyActivity;
    java.lang.String _CVAR1 = android.content.Context.VIBRATOR_SERVICE;
    android.os.Vibrator MyVibrator = ((android.os.Vibrator) (_CVAR0.getSystemService(_CVAR1)));
    if (MyVibrator.hasVibrator()) {
        android.os.Vibrator _CVAR2 = MyVibrator;
        long _CVAR3 = milliseconds;
        _CVAR2.vibrate(_CVAR3);
    }
}