package org.morphone.sense.location;
public class LocationSense implements org.morphone.sense.location.LocationSenseInterface {
    android.location.LocationManager locationManager;

    org.morphone.sense.location.LocationSense.MyGPSListener gpsListener;

    boolean isGPSOn = false;

    int gpsStatus = -1;

    public LocationSense(android.location.LocationManager loc) {
        this.locationManager = loc;
        gpsListener = new org.morphone.sense.location.LocationSense.MyGPSListener();
        locationManager.registerGnssStatusCallback(callback);
        // Init vars
        if (locationManager.isProviderEnabled(android.location.LocationManager.GPS_PROVIDER)) {
            gpsStatus = android.location.GpsStatus.GPS_EVENT_STARTED;
            isGPSOn = true;
        } else {
            gpsStatus = android.location.GpsStatus.GPS_EVENT_STOPPED;
            isGPSOn = false;
        }
    }

    public LocationSense(android.content.Context context) {
        this.locationManager = ((android.location.LocationManager) (context.getSystemService(android.content.Context.LOCATION_SERVICE)));
        gpsListener = new org.morphone.sense.location.LocationSense.MyGPSListener();
        locationManager.addGpsStatusListener(gpsListener);
        // Init vars
        if (locationManager.isProviderEnabled(android.location.LocationManager.GPS_PROVIDER)) {
            gpsStatus = android.location.GpsStatus.GPS_EVENT_STARTED;
            isGPSOn = true;
        } else {
            gpsStatus = android.location.GpsStatus.GPS_EVENT_STOPPED;
            isGPSOn = false;
        }
    }

    @java.lang.Override
    public boolean isGPSOn() throws org.morphone.sense.location.LocationSenseException {
        /* Old implementation
        try{
        isGPSOn = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if(isGPSOn)
        return true;
        else 
        return false;

        return isGPSOn;
        } catch(Exception e){
        throw new LocationSenseException("Error while getting GPSon (" + e.getMessage() + ")");
        }
         */
        return isGPSOn;
    }

    @java.lang.Override
    public int getCurrentProviderStatus() throws org.morphone.sense.location.LocationSenseException {
        if (gpsStatus < 0)
            throw new org.morphone.sense.location.LocationSenseException("Error while getting CurrentProviderStatus");
        else
            return gpsStatus;

    }

    private class MyGPSListener implements android.location.GpsStatus.Listener {
        @java.lang.Override
        public void onGpsStatusChanged(int event) {
            gpsStatus = event;
            if (gpsStatus == android.location.GpsStatus.GPS_EVENT_STOPPED)
                isGPSOn = false;
            else
                isGPSOn = true;

        }
    }
}