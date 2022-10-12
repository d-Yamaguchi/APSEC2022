private void doVibratorOn(long millis, int amplitude, int uid, int usageHint) {
    synchronized(mInputDeviceVibrators) {
        if (amplitude == android.os.VibrationEffect.DEFAULT_AMPLITUDE) {
            amplitude = mDefaultVibrationAmplitude;
        }
        if (com.android.server.VibratorService.DEBUG) {
            android.util.Slog.d(com.android.server.VibratorService.TAG, (((("Turning vibrator on for " + millis) + " ms") + " with amplitude ") + amplitude) + ".");
        }
        noteVibratorOnLocked(uid, millis);
        final int vibratorCount = mInputDeviceVibrators.size();
        if (vibratorCount != 0) {
            final android.media.AudioAttributes attributes = new android.media.AudioAttributes.Builder().setUsage(usageHint).build();
            for (int i = 0; i < vibratorCount; i++) {
                mInputDeviceVibrators.get(i).vibrate(millis, attributes);
            }
        } else {
            // Note: ordering is important here! Many haptic drivers will reset their amplitude
            // when enabled, so we always have to enable frst, then set the amplitude.
            com.android.server.VibratorService.vibratorOn(millis);
            doVibratorSetAmplitude(amplitude);
        }
    }
}