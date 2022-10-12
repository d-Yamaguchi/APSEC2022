public void addGPSStatusListener() {
    if (locationManager.isProviderEnabled(android.location.LocationManager.GPS_PROVIDER)) {
        android.location.LocationManager _CVAR0 = locationManager;
        android.location.GpsStatus.Listener _CVAR1 = onGpsStatusChange;
        _CVAR0.addGpsStatusListener(_CVAR1);
        gpsEnabled = true;
    } else {
        gpsEnabled = false;
    }
}