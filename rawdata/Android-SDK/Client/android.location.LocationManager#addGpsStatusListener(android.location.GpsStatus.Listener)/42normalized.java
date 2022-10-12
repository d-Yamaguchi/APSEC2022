public GPSAdmin(android.content.Context context) {
    locationManager = ((android.location.LocationManager) (context.getSystemService(android.content.Context.LOCATION_SERVICE)));
    android.location.LocationManager _CVAR0 = locationManager;
    android.location.GpsStatus.Listener _CVAR1 = listener;
    _CVAR0.addGpsStatusListener(_CVAR1);
}