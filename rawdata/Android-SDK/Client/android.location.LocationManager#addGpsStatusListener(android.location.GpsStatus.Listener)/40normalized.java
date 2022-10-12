@java.lang.Override
protected void onCreate(android.os.Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.wgc_speed);
    sure_num_satellite = ((android.widget.TextView) (findViewById(R.id.sure_num_satellite)));
    relation = ((android.widget.TextView) (findViewById(R.id.relation)));
    position = ((android.widget.TextView) (findViewById(R.id.position)));
    speed = ((android.widget.ImageView) (findViewById(R.id.speed)));
    locationManager = ((android.location.LocationManager) (getSystemService(android.content.Context.LOCATION_SERVICE)));
    gps = locationManager.isProviderEnabled(android.location.LocationManager.GPS_PROVIDER);
    if (gps) {
        java.lang.String currentProvider = locationManager.getProvider(android.location.LocationManager.GPS_PROVIDER).getName();
        locationManager.requestLocationUpdates(currentProvider, 2000, 1, locationListener);
        android.location.LocationManager _CVAR0 = locationManager;
        android.location.GpsStatus.Listener _CVAR1 = gpsListener;
        _CVAR0.addGpsStatusListener(_CVAR1);
    }
}