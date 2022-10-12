/**
 * addGpsStatusListener
 */
public void addGpsStatusListener(android.location.GpsStatus.Listener listener) {
    mGpsStatusListener = listener;
    android.location.LocationManager _CVAR0 = mLocationManager;
    android.location.GpsStatus.Listener _CVAR1 = mGpsStatusListener;
    _CVAR0.addGpsStatusListener(_CVAR1);
}