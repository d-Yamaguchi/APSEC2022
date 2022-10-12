package com.android.server.fingerprint;
import android.hardware.fingerprint.Fingerprint;
import android.os.SystemProperties;
import android.os.SystemVibrator;
import com.android.internal.annotations.GuardedBy;
public class FingerprintUtils {
    private static final long[] FP_ERROR_VIBRATE_PATTERN = new long[]{ 0, 30, 100, 30 };

    private static final long[] FP_SUCCESS_VIBRATE_PATTERN = new long[]{ 0, ((long) (android.os.SystemProperties.getInt("ro.config.enroll_vibrate_time", 30))) };

    private static final boolean FRONT_FINGERPRINT_NAVIGATION = android.os.SystemProperties.getBoolean("ro.config.hw_front_fp_navi", false);

    private static final int FRONT_FP_ERROR_VIBRATE_PATTERN = 14;

    private static final long[] HW_FP_ERROR_VIBRATE_PATTERN = new long[]{ 0, 30 };

    private static com.android.server.fingerprint.FingerprintUtils sInstance;

    private static final java.lang.Object sInstanceLock = new java.lang.Object();

    @com.android.internal.annotations.GuardedBy("this")
    private final android.util.SparseArray<com.android.server.fingerprint.FingerprintsUserState> mUsers = new android.util.SparseArray();

    public static com.android.server.fingerprint.FingerprintUtils getInstance() {
        synchronized(com.android.server.fingerprint.FingerprintUtils.sInstanceLock) {
            if (com.android.server.fingerprint.FingerprintUtils.sInstance == null) {
                com.android.server.fingerprint.FingerprintUtils.sInstance = new com.android.server.fingerprint.FingerprintUtils();
            }
        }
        return com.android.server.fingerprint.FingerprintUtils.sInstance;
    }

    private FingerprintUtils() {
    }

    public java.util.List<android.hardware.fingerprint.Fingerprint> getFingerprintsForUser(android.content.Context ctx, int userId) {
        return getStateForUser(ctx, userId).getFingerprints();
    }

    public void addFingerprintForUser(android.content.Context ctx, int fingerId, int userId) {
        getStateForUser(ctx, userId).addFingerprint(fingerId, userId);
    }

    public void removeFingerprintIdForUser(android.content.Context ctx, int fingerId, int userId) {
        getStateForUser(ctx, userId).removeFingerprint(fingerId);
    }

    public void renameFingerprintForUser(android.content.Context ctx, int fingerId, int userId, java.lang.CharSequence name) {
        if (!android.text.TextUtils.isEmpty(name)) {
            getStateForUser(ctx, userId).renameFingerprint(fingerId, name);
        }
    }

    public static void vibrateFingerprintError(android.content.Context context) {
        android.os.Vibrator vibrator = ((android.os.Vibrator) (context.getSystemService(android.os.Vibrator.class)));
        if (vibrator != null) {
            VibrationEffect effect = VibrationEffect.createWaveform(FP_ERROR_VIBRATE_PATTERN, FRONT_FP_ERROR_VIBRATE_PATTERN);
            vibratorAlarm.vibrate(effect);
        }
    }

    public static void vibrateFingerprintErrorHw(android.content.Context context) {
        if (context != null) {
            if (com.android.server.fingerprint.FingerprintUtils.FRONT_FINGERPRINT_NAVIGATION) {
                android.os.Vibrator vibrator = ((android.os.Vibrator) (context.getSystemService("vibrator")));
                if (vibrator != null) {
                    android.os.SystemVibrator sysVibrator = ((android.os.SystemVibrator) (vibrator));
                    if (sysVibrator != null) {
                        sysVibrator.hwVibrate(null, 14);
                    }
                }
            } else {
                com.android.server.fingerprint.FingerprintUtils.vibrateFingerprintError(context);
            }
        }
    }

    public static void vibrateFingerprintSuccess(android.content.Context context) {
        android.os.Vibrator vibrator = ((android.os.Vibrator) (context.getSystemService(android.os.Vibrator.class)));
        if (vibrator != null) {
            vibrator.vibrate(com.android.server.fingerprint.FingerprintUtils.FP_SUCCESS_VIBRATE_PATTERN, -1);
        }
    }

    private com.android.server.fingerprint.FingerprintsUserState getStateForUser(android.content.Context ctx, int userId) {
        com.android.server.fingerprint.FingerprintsUserState state;
        synchronized(this) {
            state = ((com.android.server.fingerprint.FingerprintsUserState) (this.mUsers.get(userId)));
            if (state == null) {
                state = new com.android.server.fingerprint.FingerprintsUserState(ctx, userId);
                this.mUsers.put(userId, state);
            }
        }
        return state;
    }
}