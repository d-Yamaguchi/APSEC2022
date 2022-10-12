@java.lang.Override
protected void onCreate(android.os.Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    vibrateTestActivity = new com.android.runintest.VibrateTestActivity();
    vibrateTestActivity.isMonkeyRunning(com.android.runintest.VibrateTestActivity.TAG, "onCreate", this);
    com.android.runintest.LogRuningTest.printInfo(com.android.runintest.VibrateTestActivity.TAG, "start VibrateTestActivity", this);
    setContentView(R.layout.activity_vibrate_test);
    getWindow().addFlags(android.view.WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    mSharedPreferences = this.getSharedPreferences("runintest", android.app.Activity.MODE_PRIVATE);
    tv_vibrate = ((android.widget.TextView) (findViewById(R.id.vibrate_test_count)));
    vibrator = ((android.os.Vibrator) (getSystemService(android.content.Context.VIBRATOR_SERVICE)));
    if ((vibrator != null) && (vibrator.hasVibrator() != false)) {
        longSize = com.android.runintest.VibrateTestActivity.VIBRATE_REPEAT * 2;
        delayTime = com.android.runintest.VibrateTestActivity.DELAY_TIME + (com.android.runintest.VibrateTestActivity.VIBRATE_REPEAT * (com.android.runintest.VibrateTestActivity.VIBRATE_STOP_TIME + com.android.runintest.VibrateTestActivity.VIBRATE_RUN_TIME));
        for (int i = 0; i < longSize; i++) {
            if ((i / 2) == 0) {
                pattern[i] = com.android.runintest.VibrateTestActivity.VIBRATE_STOP_TIME;
            } else {
                pattern[i] = com.android.runintest.VibrateTestActivity.VIBRATE_RUN_TIME;
            }
        }
        tv_vibrate.setText(getResources().getString(R.string.vibrate_count) + com.android.runintest.VibrateTestActivity.VIBRATE_REPEAT);
        long[] pattern = new long[longSize];
        android.os.Vibrator _CVAR0 = vibrator;
        long[] _CVAR1 = pattern;
        int _CVAR2 = -1;
        _CVAR0.vibrate(_CVAR1, _CVAR2);
        com.android.runintest.LogRuningTest.printDebug(com.android.runintest.VibrateTestActivity.TAG, "result:Vibrator test Success", this);
    } else {
        com.android.runintest.LogRuningTest.printDebug(com.android.runintest.VibrateTestActivity.TAG, "result:Vibrator test failed", this);
        com.android.runintest.LogRuningTest.printError(com.android.runintest.VibrateTestActivity.TAG, "reason:" + "Vibrator is not existed", this);
        com.android.runintest.VibrateTestActivity.mTestSuccess = false;
    }
    mLCDHandler.sendEmptyMessageDelayed(1, delayTime);
}