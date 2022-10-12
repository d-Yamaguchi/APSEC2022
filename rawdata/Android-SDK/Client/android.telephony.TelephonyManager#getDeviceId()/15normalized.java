public static java.lang.String GetDeviceId(java.lang.ref.WeakReference<android.content.Context> contextReference) {
    java.lang.ref.WeakReference<android.content.Context> _CVAR0 = contextReference;
    java.lang.String _CVAR1 = com.indtel.mcf.utils.security.DeviceId.getDeviceId(_CVAR0);
    return _CVAR1;
    // return androidId(contextReference.get());
}