/**
 * See {@link android.location.LocationManager#removeGpsStatusListener (GpsStatus.Listener)}.
 *
 * @param listener
 * 		the listener to remove
 */
public void removeGpsStatusListener(android.location.GpsStatus.Listener listener) {
    android.util.Log.i(android.location.cts.TestLocationManager.TAG, "Remove Gps Status Listener.");
    android.location.LocationManager _CVAR0 = mLocationManager;
    android.location.GpsStatus.Listener _CVAR1 = listener;
    _CVAR0.removeGpsStatusListener(_CVAR1);
}