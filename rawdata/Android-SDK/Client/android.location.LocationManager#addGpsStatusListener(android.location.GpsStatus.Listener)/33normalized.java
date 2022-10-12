/* State listener */
public static void addGpsStatusListener(android.location.GpsStatus.Listener listener) {
    android.location.LocationManager _CVAR0 = com.Android.CodeInTheAir.Device.Query.Sensor_GPS.locationManager;
    android.location.GpsStatus.Listener _CVAR1 = listener;
    _CVAR0.addGpsStatusListener(_CVAR1);
}