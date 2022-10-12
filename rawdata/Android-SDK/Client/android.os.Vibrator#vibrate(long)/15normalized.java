private void doVibratorOn(long millis, int uid) {
    millis = millis * android.os.SystemProperties.getInt("ro.vibrator.multiple", 1);
    synchronized(mInputDeviceVibrators) {
        try {
            mBatteryStatsService.noteVibratorOn(uid, millis);
            mCurVibUid = uid;
        } catch (android.os.RemoteException e) {
        }
        final int vibratorCount = mInputDeviceVibrators.size();
        if (vibratorCount != 0) {
            for (int i = 0; i < vibratorCount; i++) {
                mInputDeviceVibrators.get(i).vibrate(millis);
            }
        } else {
            com.android.server.VibratorService.vibratorOn(millis);
        }
    }
}