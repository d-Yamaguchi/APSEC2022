void setCollecting(boolean b) {
    collecting = b;
    if (b) {
        try {
            locationManager.requestLocationUpdates(provider, com.mdu.gps.LocationService.LOCATION_INTERVAL, com.mdu.gps.LocationService.LOCATION_MIN_DISTANCE, this);
            locationManager.addGpsStatusListener(this);
        } catch (java.lang.SecurityException e) {
            com.mdu.gps.U.E(this, e);
        }
    } else {
        try {
            locationManager.removeUpdates(this);
            android.location.LocationManager _CVAR0 = locationManager;
            com.mdu.gps.LocationService _CVAR1 = this;
            _CVAR0.removeGpsStatusListener(_CVAR1);
        } catch (java.lang.SecurityException e) {
            com.mdu.gps.U.E(this, e);
        }
    }
}