package jp.ohwada.android.gpsstatussample1;
/**
 * SatelliteManager
 */
public class SatelliteManager {
    private static final long MIN_TIME = 1000;// 1 sec


    private static final float MIN_DISTANCE = 0;

    private static final java.lang.String TAB = "\t";

    private static final java.lang.String LF = "\n";

    private android.location.LocationManager mLocationManager;

    private android.location.GpsStatus mGpsStatus;

    private android.location.LocationListener mLocationListener = null;

    private android.location.GpsStatus.Listener mGpsStatusListener = null;

    /**
     * === constructor ===
     */
    public SatelliteManager(android.content.Context context) {
        mLocationManager = ((android.location.LocationManager) (context.getSystemService(android.content.Context.LOCATION_SERVICE)));
        mGpsStatus = mLocationManager.getGpsStatus(null);
    }

    /**
     * addGpsStatusListener
     */
    public void addGpsStatusListener(android.location.GpsStatus.Listener listener) {
        mGpsStatusListener = listener;
        mLocationManager.addGpsStatusListener(mGpsStatusListener);
    }

    /**
     * removeGpsStatusListener
     */
    public void removeGpsStatusListener() {
        if (mGpsStatusListener != null) {
            mLocationManager.unregisterGnssStatusCallback(mGpsUpdate);
        }
    }

    /**
     * requestLocationUpdates
     */
    public void requestLocationUpdates() {
        mLocationListener = new jp.ohwada.android.gpsstatussample1.SatelliteManager.DummyLocationListener();
        mLocationManager.requestLocationUpdates(android.location.LocationManager.GPS_PROVIDER, jp.ohwada.android.gpsstatussample1.SatelliteManager.MIN_TIME, jp.ohwada.android.gpsstatussample1.SatelliteManager.MIN_DISTANCE, mLocationListener);
    }

    /**
     * removeUpdates
     */
    public void removeUpdates() {
        if (mLocationListener != null) {
            mLocationManager.removeUpdates(mLocationListener);
        }
    }

    /**
     * getSatellites
     */
    public java.lang.Iterable<android.location.GpsSatellite> getSatellites() {
        java.lang.Iterable<android.location.GpsSatellite> satellites = mGpsStatus.getSatellites();
        mGpsStatus = mLocationManager.getGpsStatus(mGpsStatus);
        return satellites;
    }

    /**
     * getSatelliteMsg
     */
    public java.lang.String getSatelliteMsg(java.lang.Iterable<android.location.GpsSatellite> satellites) {
        java.lang.String str = "Prn : Azimuth Elevation Snr " + jp.ohwada.android.gpsstatussample1.SatelliteManager.LF;
        for (android.location.GpsSatellite sat : satellites) {
            str += (sat.getPrn() + " : ") + jp.ohwada.android.gpsstatussample1.SatelliteManager.TAB;
            str += sat.getAzimuth() + jp.ohwada.android.gpsstatussample1.SatelliteManager.TAB;
            str += sat.getElevation() + jp.ohwada.android.gpsstatussample1.SatelliteManager.TAB;
            str += sat.getSnr() + jp.ohwada.android.gpsstatussample1.SatelliteManager.LF;
        }
        return str;
    }

    /**
     * getEvent
     */
    public java.lang.String getEvent(int event) {
        java.lang.String str = "";
        switch (event) {
            case android.location.GpsStatus.GPS_EVENT_STARTED :
                str = "STARTED";
                break;
            case android.location.GpsStatus.GPS_EVENT_STOPPED :
                str = "STOPPED";
                break;
            case android.location.GpsStatus.GPS_EVENT_FIRST_FIX :
                str = "FIRST_FIX";
                break;
            case android.location.GpsStatus.GPS_EVENT_SATELLITE_STATUS :
                str = "SATELLITE_STATUS";
                break;
        }
        return str;
    }

    /**
     * class DummyLocationListener
     */
    private class DummyLocationListener implements android.location.LocationListener {
        @java.lang.Override
        public void onLocationChanged(android.location.Location mLocation) {
            // dummy
        }

        @java.lang.Override
        public void onProviderDisabled(java.lang.String provider) {
            // dummy
        }

        @java.lang.Override
        public void onProviderEnabled(java.lang.String provider) {
            // dummy
        }

        @java.lang.Override
        public void onStatusChanged(java.lang.String provider, int status, android.os.Bundle extras) {
            // dummy
        }
    }
}