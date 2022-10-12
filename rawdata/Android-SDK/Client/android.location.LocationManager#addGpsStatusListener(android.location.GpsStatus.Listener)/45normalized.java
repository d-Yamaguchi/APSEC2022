/**
 * 初始化
 *
 * @param ctx
 * 		
 */
public Gps(android.content.Context ctx) {
    context = ctx;
    locationManager = ((android.location.LocationManager) (context.getSystemService(android.content.Context.LOCATION_SERVICE)));
    if (!locationManager.isProviderEnabled(android.location.LocationManager.GPS_PROVIDER)) {
        // DeviceCtrlTools.openGpsSwitch(ctx);
        com.movementinsome.kernel.util.DeviceCtrlTools.toggleGPS(ctx);
    }
    location = locationManager.getLastKnownLocation(getProvider());
    locationManager.requestLocationUpdates(android.location.LocationManager.GPS_PROVIDER, minTime, minDistance, locationListener);
    android.location.LocationManager _CVAR0 = locationManager;
    android.location.GpsStatus.Listener _CVAR1 = gpsListener;
    // 绑定监听状态
    _CVAR0.addGpsStatusListener(_CVAR1);
    geocoder = new android.location.Geocoder(context);
}