package ch.supsi.ist.camre.paths;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
public class GeolocationWorker extends android.app.Fragment implements com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks , com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener , android.location.GpsStatus.Listener {
    private static final int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;

    private android.location.LocationManager locationManager;

    private com.google.android.gms.common.api.GoogleApiClient mGoogleApiClient;

    // private ActivityRecognitionClient mActivityRecognitionClient;
    // Define an object that holds accuracy and frequency parameters
    com.google.android.gms.location.LocationRequest mLocationRequest;

    // Milliseconds per second
    private static final int MILLISECONDS_PER_SECOND = 1000;

    // Update frequency in seconds
    public static final int UPDATE_INTERVAL_IN_SECONDS = 4;

    // The fastest update frequency, in seconds
    private static final int FASTEST_INTERVAL_IN_SECONDS = 2;

    // Update frequency in milliseconds
    private static final long UPDATE_INTERVAL = ch.supsi.ist.camre.paths.GeolocationWorker.MILLISECONDS_PER_SECOND * ch.supsi.ist.camre.paths.GeolocationWorker.UPDATE_INTERVAL_IN_SECONDS;

    // A fast frequency ceiling in milliseconds
    private static final long FASTEST_INTERVAL = ch.supsi.ist.camre.paths.GeolocationWorker.MILLISECONDS_PER_SECOND * ch.supsi.ist.camre.paths.GeolocationWorker.FASTEST_INTERVAL_IN_SECONDS;

    private java.util.ArrayList<com.google.android.gms.location.LocationListener> locationListeners = new java.util.ArrayList<com.google.android.gms.location.LocationListener>();

    private java.util.ArrayList<android.location.GpsStatus.Listener> gpsListeners = new java.util.ArrayList<android.location.GpsStatus.Listener>();

    public GeolocationWorker() {
    }

    @java.lang.Override
    public android.view.View onCreateView(android.view.LayoutInflater inflater, android.view.ViewGroup container, android.os.Bundle savedInstanceState) {
        java.lang.System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> GeolocationWorker: onCreateView");
        android.view.View view = inflater.inflate(R.layout.fragment_geolocation, container, false);
        return view;
    }

    @java.lang.Override
    public void onCreate(android.os.Bundle savedInstanceState) {
        java.lang.System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> GeolocationWorker: onCreate");
        super.onCreate(savedInstanceState);
        mGoogleApiClient = new com.google.android.gms.common.api.GoogleApiClient.Builder(getActivity()).addApi(LocationServices.API).addConnectionCallbacks(this).addOnConnectionFailedListener(this).build();
        /* >> http://blog.lemberg.co.uk/fused-location-provider
        Priority	    Typical location update interval	Battery drain per hour (%)	Accuracy
        *************  ********************************    **************************  ***********
        HIGH_ACCURACY	5 seconds	                        7.25%	                    ~10 meters
        BALANCED_POWER	20 seconds	                        0.6%	                    ~40 meters
        NO_POWER	    N/A	                                small	                    ~1 mile
         */
        // Create the LocationRequest object
        // Use high accuracy
        mLocationRequest = // .setNumUpdates(1)
        com.google.android.gms.location.LocationRequest.create().setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY).setInterval(ch.supsi.ist.camre.paths.GeolocationWorker.UPDATE_INTERVAL).setFastestInterval(ch.supsi.ist.camre.paths.GeolocationWorker.FASTEST_INTERVAL);
        locationManager = ((android.location.LocationManager) (getActivity().getSystemService(WalkerActivityTest.LOCATION_SERVICE)));
        /* mActivityRecognitionClient =
        new ActivityRecognitionClient(getActivity(), new GooglePlayServicesClient.ConnectionCallbacks() {
        @Override
        public void onConnected(Bundle bundle) {

        }

        @Override
        public void onDisconnected() {

        }
        }, this);
         */
        setRetainInstance(true);
    }

    public void connectGPS() {
        // Connect the client.
        mGoogleApiClient.connect();
    }

    public void disconnectGps() {
        mGoogleApiClient.disconnect();
    }

    @java.lang.Override
    public void onStart() {
        java.lang.System.out.println("LocationWorker: onStart");
        super.onStart();
        java.lang.System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> GeolocationWorker: onStart");
        mGoogleApiClient.connect();
    }

    @java.lang.Override
    public void onStop() {
        java.lang.System.out.println("LocationWorker: onStop");
        super.onStop();
        java.lang.System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> GeolocationWorker: onStop");
        mGoogleApiClient.disconnect();
    }

    public void addFusionLocationListener(com.google.android.gms.location.LocationListener listener) {
        if (!locationListeners.contains(listener)) {
            locationListeners.add(listener);
            if ((mGoogleApiClient != null) && mGoogleApiClient.isConnected()) {
                LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, listener);
            }
        }
    }

    public void addGpsStatusListener(android.location.GpsStatus.Listener listener) {
        if (!gpsListeners.contains(listener)) {
            gpsListeners.add(listener);
            if ((mGoogleApiClient != null) && mGoogleApiClient.isConnected()) {
                locationManager.registerGnssStatusCallback(callback);
            }
        }
    }

    public void removeFusionLocationListener(com.google.android.gms.location.LocationListener listener) {
        if (locationListeners.contains(listener)) {
            locationListeners.remove(listener);
            if ((mGoogleApiClient != null) && mGoogleApiClient.isConnected()) {
                LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, listener);
            }
        }
    }

    @java.lang.Override
    public void onConnected(android.os.Bundle bundle) {
        java.lang.System.out.println("LocationWorker: onConnected");
        if (locationListeners.size() > 0) {
            for (com.google.android.gms.location.LocationListener listener : locationListeners) {
                LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, listener);
            }
        }
        if (gpsListeners.size() > 0) {
            for (android.location.GpsStatus.Listener listener : gpsListeners) {
                locationManager.addGpsStatusListener(listener);
            }
        }
        locationManager.addGpsStatusListener(this);
    }

    @java.lang.Override
    public void onGpsStatusChanged(int event) {
        switch (event) {
            case android.location.GpsStatus.GPS_EVENT_STARTED :
                java.lang.System.out.println(" >>> GpsStatus.GPS_EVENT_STARTED");
                break;
            case android.location.GpsStatus.GPS_EVENT_STOPPED :
                java.lang.System.out.println(" >>> GpsStatus.GPS_EVENT_STOPPED");
                break;
            case android.location.GpsStatus.GPS_EVENT_FIRST_FIX :
                java.lang.System.out.println(" >>> GpsStatus.GPS_EVENT_FIRST_FIX");
                break;
            case android.location.GpsStatus.GPS_EVENT_SATELLITE_STATUS :
                java.lang.System.out.println(" >>> GpsStatus.GPS_EVENT_SATELLITE_STATUS");
                break;
        }
        android.location.GpsStatus st = locationManager.getGpsStatus(null);
        android.location.Location gpsLocation = locationManager.getLastKnownLocation(android.location.LocationManager.GPS_PROVIDER);
        android.location.Location netLocation = locationManager.getLastKnownLocation(android.location.LocationManager.NETWORK_PROVIDER);
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        if (gpsLocation != null) {
            java.lang.System.out.println("  > Time gps: " + sdf.format(new java.util.Date(gpsLocation.getTime())));
        }
        if (netLocation != null) {
            java.lang.System.out.println("  > Time net: " + sdf.format(new java.util.Date(netLocation.getTime())));
        }
        /* System.out.println("GPS Coordinates: " +
        Double.toString(gpsLocation.getLatitude()) + "," +
        Double.toString(gpsLocation.getLongitude()));
        System.out.println("  > Provider: " + gpsLocation.getProvider());
        System.out.println("  > Accuracy ("+gpsLocation.hasAccuracy()+"): " + gpsLocation.getAccuracy());
        System.out.println("  > Altitude ("+gpsLocation.hasAltitude()+"): " + gpsLocation.getAltitude());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        System.out.println("  > Time: " + sdf.format(new Date(gpsLocation.getTime())));

        System.out.println("NET Coordinates: " +
        Double.toString(netLocation.getLatitude()) + "," +
        Double.toString(netLocation.getLongitude()));
        System.out.println("  > Provider: " + netLocation.getProvider());
        System.out.println("  > Accuracy ("+netLocation.hasAccuracy()+"): " + netLocation.getAccuracy());
        System.out.println("  > Altitude ("+netLocation.hasAltitude()+"): " + netLocation.getAltitude());
        System.out.println("  > Time: " + sdf.format(new Date(netLocation.getTime())));

        System.out.println("**************************************************************************");
        System.out.println("DISTANCE: " + gpsLocation.distanceTo(netLocation)+ "m");
        System.out.println("**************************************************************************");
         */
    }

    @java.lang.Override
    public void onConnectionSuspended(int i) {
        java.lang.System.out.println("GeolocationWorker: onConnectionSuspended");
    }

    @java.lang.Override
    public void onConnectionFailed(com.google.android.gms.common.ConnectionResult connectionResult) {
        /* Google Play services can resolve some errors it detects.
        If the error has a resolution, try sending an Intent to
        start a Google Play services activity that can resolve
        error.
         */
        if (connectionResult.hasResolution()) {
            try {
                // Start an Activity that tries to resolve the error
                connectionResult.startResolutionForResult(getActivity(), ch.supsi.ist.camre.paths.GeolocationWorker.CONNECTION_FAILURE_RESOLUTION_REQUEST);
                /* Thrown if Google Play services canceled the original
                PendingIntent
                 */
            } catch (android.content.IntentSender.SendIntentException e) {
                // Log the error
                e.printStackTrace();
            }
        } else {
            /* If no resolution is available, display a dialog to the
            user with the error.
             */
            // showErrorDialog(connectionResult.getErrorCode());
        }
    }

    private boolean servicesConnected() {
        // Check that Google Play services is available
        int resultCode = com.google.android.gms.common.GooglePlayServicesUtil.isGooglePlayServicesAvailable(getActivity());
        // If Google Play services is available
        if (com.google.android.gms.common.ConnectionResult.SUCCESS == resultCode) {
            // In debug mode, log the status
            android.util.Log.d(ch.supsi.ist.camre.paths.GeolocationWorker.class.getName(), "play_services_available");
            // Continue
            return true;
            // Google Play services was not available for some reason
        } else {
            // Display an error dialog
            // Dialog dialog = GooglePlayServicesUtil.getErrorDialog(resultCode, getActivity(), 0);
            // if (dialog != null) {
            // ErrorDialogFragment errorFragment = new ErrorDialogFragment();
            // errorFragment.dia(dialog);
            // errorFragment.show(getSupportFragmentManager(), LocationUtils.APPTAG);
            // }
            android.util.Log.d(ch.supsi.ist.camre.paths.GeolocationWorker.class.getName(), "play_services_un_available");
            return false;
        }
    }
}