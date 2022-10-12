public void addGpsStatusListener(android.location.GpsStatus.Listener listener) {
    if (!gpsListeners.contains(listener)) {
        gpsListeners.add(listener);
        if ((mGoogleApiClient != null) && mGoogleApiClient.isConnected()) {
            android.location.LocationManager _CVAR0 = locationManager;
            android.location.GpsStatus.Listener _CVAR1 = listener;
            _CVAR0.addGpsStatusListener(_CVAR1);
        }
    }
}