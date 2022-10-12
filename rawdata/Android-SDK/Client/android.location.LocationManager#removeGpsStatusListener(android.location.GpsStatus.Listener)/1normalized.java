/**
 * removeGpsStatusListener
 */
public void removeGpsStatusListener() {
    if (mGpsStatusListener != null) {
        android.location.LocationManager _CVAR0 = mLocationManager;
        android.location.GpsStatus.Listener _CVAR1 = mGpsStatusListener;
        _CVAR0.removeGpsStatusListener(_CVAR1);
    }
}