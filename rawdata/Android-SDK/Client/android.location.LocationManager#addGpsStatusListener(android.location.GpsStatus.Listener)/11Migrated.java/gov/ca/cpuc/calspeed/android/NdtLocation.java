/* Original work: Copyright 2009 Google Inc. All Rights Reserved.

Modified work: The original source code (AndroidNdt.java) comes from the NDT Android app
that is available from http://code.google.com/p/ndt/.
It's modified for the CalSPEED Android app by California 
State University Monterey Bay (CSUMB) on April 29, 2013.
 */
package gov.ca.cpuc.calspeed.android;
import gov.ca.cpuc.calspeed.android.CalspeedFragment.LatLong;
import gov.ca.cpuc.calspeed.android.CalspeedFragment.NetworkLatLong;
/**
 * Handle the location related functions and listeners.
 */
public class NdtLocation implements android.location.LocationListener {
    /**
     * Location variable, publicly accessible to provide access to geographic data.
     */
    public android.location.Location location;

    public android.location.Location networkLocation;

    public android.location.LocationManager locationManager;

    private android.location.Criteria criteria;

    public java.lang.String bestProvider;

    public java.lang.Boolean gpsEnabled;

    public java.lang.Boolean networkEnabled;

    private android.content.Context context;

    private gov.ca.cpuc.calspeed.android.AndroidUiServices uiServices;

    public android.location.Location NetworkLastKnownLocation;

    public android.location.Location GPSLastKnownLocation;

    public gov.ca.cpuc.calspeed.android.CalspeedFragment.NetworkLatLong networkLatLong;

    public gov.ca.cpuc.calspeed.android.CalspeedFragment.LatLong latLongptr;

    /**
     * Passes context to this class to initialize members.
     *
     * @param context
     * 		context which is currently running
     */
    public NdtLocation(android.content.Context context, gov.ca.cpuc.calspeed.android.AndroidUiServices uiServices, gov.ca.cpuc.calspeed.android.CalspeedFragment.NetworkLatLong networkLatLong, gov.ca.cpuc.calspeed.android.CalspeedFragment.LatLong latLongptr) {
        this.context = context;
        this.uiServices = uiServices;
        this.networkLatLong = networkLatLong;
        this.latLongptr = latLongptr;
        locationManager = ((android.location.LocationManager) (context.getSystemService(android.content.Context.LOCATION_SERVICE)));
        java.util.Iterator<java.lang.String> providers = locationManager.getAllProviders().iterator();
        location = null;
        networkLocation = new android.location.Location(android.location.LocationManager.NETWORK_PROVIDER);
        networkLocation.setLatitude(0.0);
        networkLocation.setLongitude(0.0);
        while (providers.hasNext()) {
            android.util.Log.v("debug", providers.next());
        } 
        criteria = new android.location.Criteria();
        criteria.setAccuracy(android.location.Criteria.ACCURACY_FINE);
        criteria.setCostAllowed(true);
        criteria.setPowerRequirement(android.location.Criteria.NO_REQUIREMENT);
        bestProvider = locationManager.getBestProvider(criteria, true);
        android.util.Log.v("debug", "Best provider is:" + bestProvider);
        addGPSStatusListener();
        addNetworkListener();
    }

    public void addGPSStatusListener() {
        if (locationManager.isProviderEnabled(android.location.LocationManager.GPS_PROVIDER)) {
            locationManager.registerGnssStatusCallback(callback);
            gpsEnabled = true;
        } else {
            gpsEnabled = false;
        }
    }

    public void removeGPSStatusListener() {
        if (locationManager.isProviderEnabled(android.location.LocationManager.GPS_PROVIDER)) {
            locationManager.removeGpsStatusListener(onGpsStatusChange);
            gpsEnabled = false;
        }
    }

    public void addNetworkListener() {
        if (locationManager.isProviderEnabled(android.location.LocationManager.NETWORK_PROVIDER)) {
            locationManager.requestLocationUpdates(android.location.LocationManager.NETWORK_PROVIDER, 2000, 0, NetworkLocationListener);
            networkEnabled = true;
        } else {
            networkEnabled = false;
        }
    }

    public void stopNetworkListenerUpdates() {
        if (locationManager.isProviderEnabled(android.location.LocationManager.NETWORK_PROVIDER)) {
            locationManager.removeUpdates(NetworkLocationListener);
            networkEnabled = false;
        }
    }

    public void startNetworkListenerUpdates() {
        if (locationManager.isProviderEnabled(android.location.LocationManager.NETWORK_PROVIDER)) {
            locationManager.requestLocationUpdates(android.location.LocationManager.NETWORK_PROVIDER, 2000, 0, NetworkLocationListener);
            networkEnabled = true;
        }
    }

    // Define a listener that responds to Network location updates
    android.location.LocationListener NetworkLocationListener = new android.location.LocationListener() {
        public void onLocationChanged(android.location.Location location) {
            networkLatLong.updateNetworkLatitudeLongitude(location);
            networkLocation.set(location);
        }

        public void onStatusChanged(java.lang.String provider, int status, android.os.Bundle extras) {
        }

        public void onProviderEnabled(java.lang.String provider) {
        }

        public void onProviderDisabled(java.lang.String provider) {
        }
    };

    private final android.location.GpsStatus.Listener onGpsStatusChange = new android.location.GpsStatus.Listener() {
        public void onGpsStatusChanged(int event) {
            switch (event) {
                case android.location.GpsStatus.GPS_EVENT_STARTED :
                    // Started...
                    startListen();
                    latLongptr.updateLatitudeLongitude();
                    if (Constants.DEBUG)
                        android.util.Log.v("debug", "GPS starting...\n");

                    break;
                case android.location.GpsStatus.GPS_EVENT_FIRST_FIX :
                    // First Fix...
                    if (Constants.DEBUG)
                        android.util.Log.v("debug", "GPS first fix \n");

                    latLongptr.updateLatitudeLongitude();
                    break;
                case android.location.GpsStatus.GPS_EVENT_STOPPED :
                    // Stopped...
                    stopListen();
                    location = null;
                    latLongptr.updateLatitudeLongitude();
                    if (Constants.DEBUG)
                        android.util.Log.v("debug", "GPS stopped.\n");

                    break;
                case android.location.GpsStatus.GPS_EVENT_SATELLITE_STATUS :
                    android.location.GpsStatus xGpsStatus = locationManager.getGpsStatus(null);
                    java.lang.Iterable<android.location.GpsSatellite> iSatellites = xGpsStatus.getSatellites();
                    java.util.Iterator<android.location.GpsSatellite> it = iSatellites.iterator();
                    while (it.hasNext()) {
                        android.location.GpsSatellite oSat = ((android.location.GpsSatellite) (it.next()));
                        if (Constants.DEBUG)
                            android.util.Log.v("debug", "LocationActivity - onGpsStatusChange: Satellites: " + oSat.getSnr());

                    } 
                    break;
            }
        }
    };

    @java.lang.Override
    public void onLocationChanged(android.location.Location location) {
        this.location = location;
        latLongptr.updateLatitudeLongitude();
    }

    @java.lang.Override
    public void onProviderDisabled(java.lang.String provider) {
        stopListen();
        location = null;
        latLongptr.updateLatitudeLongitude();
    }

    @java.lang.Override
    public void onProviderEnabled(java.lang.String provider) {
        startListen();
        latLongptr.updateLatitudeLongitude();
    }

    @java.lang.Override
    public void onStatusChanged(java.lang.String provider, int status, android.os.Bundle extras) {
        switch (status) {
            case android.location.LocationProvider.OUT_OF_SERVICE :
                if (Constants.DEBUG)
                    android.util.Log.v("debug", "Status Changed: Out of Service");

                stopListen();
                location = null;
                break;
            case android.location.LocationProvider.TEMPORARILY_UNAVAILABLE :
                if (Constants.DEBUG)
                    android.util.Log.v("debug", "Status Changed: Temporarily Unavailable");

                stopListen();
                location = null;
                break;
            case android.location.LocationProvider.AVAILABLE :
                if (Constants.DEBUG)
                    android.util.Log.v("debug", "Status Changed: Available");

                startListen();
                break;
        }
    }

    /**
     * Stops requesting the location update.
     */
    public void stopListen() {
        locationManager.removeUpdates(this);
    }

    /**
     * Begins to request the location update.
     */
    public void startListen() {
        locationManager.requestLocationUpdates(android.location.LocationManager.GPS_PROVIDER, 1000, 0, this);
    }
}