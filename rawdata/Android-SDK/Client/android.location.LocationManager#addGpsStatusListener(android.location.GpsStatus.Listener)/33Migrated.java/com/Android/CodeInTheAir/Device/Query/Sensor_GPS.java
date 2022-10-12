package com.Android.CodeInTheAir.Device.Query;
import com.Android.CodeInTheAir.Global.AppContext;
import com.Android.CodeInTheAir.Types.LocationInfo;
import com.Android.CodeInTheAir.Types.SatelliteInfo;
public class Sensor_GPS {
    private static android.location.LocationManager locationManager;

    private static final int QUICK_PERIOD = 1000;

    public static void init() {
        com.Android.CodeInTheAir.Device.Query.Sensor_GPS.locationManager = ((android.location.LocationManager) (AppContext.context.getSystemService(android.content.Context.LOCATION_SERVICE)));
    }

    public static boolean isEnabled() {
        return com.Android.CodeInTheAir.Device.Query.Sensor_GPS.locationManager.isProviderEnabled(android.location.LocationManager.GPS_PROVIDER);
    }

    public static com.Android.CodeInTheAir.Types.LocationInfo getLocation() {
        return com.Android.CodeInTheAir.Types.LocationInfo.toLocationInfo(com.Android.CodeInTheAir.Device.Query.Sensor_GPS.getLastKnownLocation());
    }

    public static double getLat() {
        android.location.Location location = com.Android.CodeInTheAir.Device.Query.Sensor_GPS.getLastKnownLocation();
        if (location != null) {
            return location.getLatitude();
        }
        return 0;
    }

    public static double getLng() {
        android.location.Location location = com.Android.CodeInTheAir.Device.Query.Sensor_GPS.getLastKnownLocation();
        if (location != null) {
            return location.getLongitude();
        }
        return 0;
    }

    public static double getAcc() {
        android.location.Location location = com.Android.CodeInTheAir.Device.Query.Sensor_GPS.getLastKnownLocation();
        if (location != null) {
            return location.getAccuracy();
        }
        return 0;
    }

    public static double getAlt() {
        android.location.Location location = com.Android.CodeInTheAir.Device.Query.Sensor_GPS.getLastKnownLocation();
        if (location != null) {
            return location.getAltitude();
        }
        return 0;
    }

    public static double getSpeed() {
        android.location.Location location = com.Android.CodeInTheAir.Device.Query.Sensor_GPS.getLastKnownLocation();
        if (location != null) {
            return location.getSpeed();
        }
        return 0;
    }

    public static double getDir() {
        android.location.Location location = com.Android.CodeInTheAir.Device.Query.Sensor_GPS.getLastKnownLocation();
        if (location != null) {
            return location.getBearing();
        }
        return 0;
    }

    public static long getTime() {
        android.location.Location location = com.Android.CodeInTheAir.Device.Query.Sensor_GPS.getLastKnownLocation();
        if (location != null) {
            return location.getTime();
        }
        return 0;
    }

    public static java.util.List<com.Android.CodeInTheAir.Types.SatelliteInfo> getSatellites() {
        java.util.List<com.Android.CodeInTheAir.Types.SatelliteInfo> satellites = new java.util.ArrayList<com.Android.CodeInTheAir.Types.SatelliteInfo>();
        android.location.GpsStatus gpsStatus = com.Android.CodeInTheAir.Device.Query.Sensor_GPS.locationManager.getGpsStatus(null);
        if (gpsStatus != null) {
            java.util.Iterator<android.location.GpsSatellite> i = gpsStatus.getSatellites().iterator();
            while (i.hasNext()) {
                android.location.GpsSatellite s = i.next();
                com.Android.CodeInTheAir.Types.SatelliteInfo si = new com.Android.CodeInTheAir.Types.SatelliteInfo(s.getAzimuth(), s.getElevation(), s.getPrn(), s.getSnr(), s.hasAlmanac(), s.hasEphemeris(), s.usedInFix());
                satellites.add(si);
            } 
        }
        return satellites;
    }

    public static int getTimeToFix() {
        android.location.GpsStatus gpsStatus = com.Android.CodeInTheAir.Device.Query.Sensor_GPS.locationManager.getGpsStatus(null);
        if (gpsStatus != null) {
            return gpsStatus.getTimeToFirstFix();
        }
        return -1;
    }

    public static android.location.GpsStatus getGPSStatus() {
        return com.Android.CodeInTheAir.Device.Query.Sensor_GPS.locationManager.getGpsStatus(null);
    }

    /* Private methods */
    private static android.location.Location getLastKnownLocation() {
        android.location.Location location = com.Android.CodeInTheAir.Device.Query.Sensor_GPS.locationManager.getLastKnownLocation(android.location.LocationManager.GPS_PROVIDER);
        return location;
    }

    /* Listener and Events */
    /* Location listener */
    public static void addLocationListener(android.location.LocationListener locationListener) {
        com.Android.CodeInTheAir.Device.Query.Sensor_GPS.locationManager.requestLocationUpdates(android.location.LocationManager.GPS_PROVIDER, com.Android.CodeInTheAir.Device.Query.Sensor_GPS.QUICK_PERIOD, 0, locationListener);
    }

    public static void addLocationListener(android.location.LocationListener locationListener, int period, float distance) {
        com.Android.CodeInTheAir.Device.Query.Sensor_GPS.locationManager.requestLocationUpdates(android.location.LocationManager.GPS_PROVIDER, period, distance, locationListener);
    }

    public static void removeLocationListener(android.location.LocationListener locationListener) {
        com.Android.CodeInTheAir.Device.Query.Sensor_GPS.locationManager.removeUpdates(locationListener);
    }

    /* State listener */
    public static void addGpsStatusListener(android.location.GpsStatus.Listener listener) {
        locationManager.registerGnssStatusCallback(callback);
    }

    public static void removeGpsStatusListener(android.location.GpsStatus.Listener listener) {
        com.Android.CodeInTheAir.Device.Query.Sensor_GPS.locationManager.removeGpsStatusListener(listener);
    }
}