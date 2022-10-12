public LocationSense(android.location.LocationManager loc) {
    this.locationManager = loc;
    gpsListener = new org.morphone.sense.location.LocationSense.MyGPSListener();
    android.location.LocationManager _CVAR0 = locationManager;
    org.morphone.sense.location.LocationSense.MyGPSListener _CVAR1 = gpsListener;
    _CVAR0.addGpsStatusListener(_CVAR1);
    // Init vars
    if (locationManager.isProviderEnabled(android.location.LocationManager.GPS_PROVIDER)) {
        gpsStatus = android.location.GpsStatus.GPS_EVENT_STARTED;
        isGPSOn = true;
    } else {
        gpsStatus = android.location.GpsStatus.GPS_EVENT_STOPPED;
        isGPSOn = false;
    }
}