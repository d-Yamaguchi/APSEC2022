public static com.micronet.obc5_gpstest.Gps get(android.content.Context appContext) {
    if (com.micronet.obc5_gpstest.Gps.gps == null) {
        android.content.Context _CVAR0 = appContext;
        android.content.Context _CVAR1 = _CVAR0.getApplicationContext();
        com.micronet.obc5_gpstest.Gps _CVAR2 = new com.micronet.obc5_gpstest.Gps(_CVAR1);
        com.micronet.obc5_gpstest.Gps.gps = _CVAR2;
    } else if (com.micronet.obc5_gpstest.Gps.gps.locationManager == null) {
        com.micronet.obc5_gpstest.Gps.gps.addLocationListener(appContext);
    }
    return com.micronet.obc5_gpstest.Gps.gps;
}