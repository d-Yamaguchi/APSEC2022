package ch.supsi.ist.camre.paths;
import android.app.Fragment;
import ch.supsi.ist.camre.paths.data.Point;
import ch.supsi.ist.camre.paths.data.Position;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
/**
 * A simple {@link Fragment} subclass.
 */
public class WalkerActivityFooter extends android.app.Fragment implements com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks , com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener , com.google.android.gms.location.LocationListener , android.location.GpsStatus.Listener {
    private long lastFix;

    // TextView status;
    android.view.View view;

    private static final int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;

    private android.location.LocationManager locationManager;

    private com.google.android.gms.common.api.GoogleApiClient mGoogleApiClient;

    // private ActivityRecognitionClient mActivityRecognitionClient;
    // Define an object that holds accuracy and frequency parameters
    com.google.android.gms.location.LocationRequest mLocationRequest;

    // Milliseconds per second
    private static final int MILLISECONDS_PER_SECOND = 1000;

    // Update frequency in seconds
    public static final int UPDATE_INTERVAL_IN_SECONDS = 5;

    // The fastest update frequency, in seconds
    private static final int FASTEST_INTERVAL_IN_SECONDS = 1;

    // Update frequency in milliseconds
    private static final long UPDATE_INTERVAL = ch.supsi.ist.camre.paths.WalkerActivityFooter.MILLISECONDS_PER_SECOND * ch.supsi.ist.camre.paths.WalkerActivityFooter.UPDATE_INTERVAL_IN_SECONDS;

    // A fast frequency ceiling in milliseconds
    private static final long FASTEST_INTERVAL = ch.supsi.ist.camre.paths.WalkerActivityFooter.MILLISECONDS_PER_SECOND * ch.supsi.ist.camre.paths.WalkerActivityFooter.FASTEST_INTERVAL_IN_SECONDS;

    private java.util.List<com.google.android.gms.location.LocationListener> locationListeners = new java.util.ArrayList<com.google.android.gms.location.LocationListener>();

    private java.util.List<android.location.GpsStatus.Listener> gpsStatusListeners = new java.util.ArrayList<android.location.GpsStatus.Listener>();

    private java.util.List<ch.supsi.ist.camre.paths.WalkerActivityFooter.GpsListener> gpsListeners = new java.util.ArrayList<ch.supsi.ist.camre.paths.WalkerActivityFooter.GpsListener>();

    private java.util.List<ch.supsi.ist.camre.paths.WalkerActivityFooter.BestPositionListener> bestPositionListeners = new java.util.ArrayList<ch.supsi.ist.camre.paths.WalkerActivityFooter.BestPositionListener>();

    public interface GpsListener {
        public void onGpsStatusChanged(int event, android.location.Location location, android.location.GpsStatus gpsStatus);
    }

    public interface BestPositionListener {
        public static int CONNECTING = 0;

        public static int FIRSTFIX = 1;

        public static int CALCULATING = 2;

        public void onUpdates(int event, ch.supsi.ist.camre.paths.data.Position position, int accuracy);

        public void onResult(int event, android.location.Location location, android.location.GpsStatus gpsStatus);
    }

    public WalkerActivityFooter() {
        // Required empty public constructor
        lastFix = 0;
    }

    @java.lang.Override
    public android.view.View onCreateView(android.view.LayoutInflater inflater, android.view.ViewGroup container, android.os.Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_walker_activity_footer, container, false);
        return view;
    }

    @java.lang.Override
    public void onCreate(android.os.Bundle savedInstanceState) {
        // System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> WalkerActivityFooter: onCreate");
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
        mLocationRequest = com.google.android.gms.location.LocationRequest.create().setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY).setInterval(ch.supsi.ist.camre.paths.WalkerActivityFooter.UPDATE_INTERVAL);
        // .setFastestInterval(FASTEST_INTERVAL);
        locationManager = ((android.location.LocationManager) (getActivity().getSystemService(WalkerActivityTest.LOCATION_SERVICE)));
    }

    public ch.supsi.ist.camre.paths.data.Position getPosition(android.location.Location location) {
        double lon = location.getLongitude();
        double lat = location.getLatitude();
        java.util.TimeZone tz = java.util.TimeZone.getTimeZone("UTC");
        java.text.DateFormat df = new java.text.SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        df.setTimeZone(tz);
        ch.supsi.ist.camre.paths.data.Position pos = new ch.supsi.ist.camre.paths.data.Position();
        pos.setAltitude(location.getAltitude());
        pos.setHeading(0.0);
        pos.setGeometry(new ch.supsi.ist.camre.paths.data.Point(lon, lat));
        pos.setTimestamp(df.format(new java.util.Date()));
        return pos;
    }

    public void connectGPS() {
        // Connect the client.
        if (!mGoogleApiClient.isConnected()) {
            mGoogleApiClient.connect();
        }
    }

    public void disconnectGps() {
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }

    public android.location.Location getLastKnownGpsLocation() {
        return locationManager.getLastKnownLocation(android.location.LocationManager.GPS_PROVIDER);
    }

    public ch.supsi.ist.camre.paths.data.Position getLastKnownGpsPosition() {
        return this.getPosition(locationManager.getLastKnownLocation(android.location.LocationManager.GPS_PROVIDER));
    }

    @java.lang.Override
    public void onStart() {
        // System.out.println("WalkerActivityFooter: onStart");
        // this.connectGPS();
        super.onStart();
    }

    @java.lang.Override
    public void onStop() {
        // System.out.println("WalkerActivityFooter: onStop");
        super.onStop();
    }

    @java.lang.Override
    public void onPause() {
        // System.out.println("WalkerActivityFooter: onStop");
        this.disconnectGps();
        super.onPause();
    }

    public void addFusionLocationListener(com.google.android.gms.location.LocationListener listener) {
        // System.out.println("WalkerActivityFooter, Adding FusionLocationListener: " + listener.getClass().getCanonicalName());
        if (!locationListeners.contains(listener)) {
            locationListeners.add(listener);
            if ((mGoogleApiClient != null) && mGoogleApiClient.isConnected()) {
                LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, listener);
            }
        }
    }

    public void addCustomGpsListener(ch.supsi.ist.camre.paths.WalkerActivityFooter.GpsListener listener) {
        // System.out.println("WalkerActivityFooter, Adding GpsListener: " + listener.getClass().getCanonicalName());
        if (!gpsListeners.contains(listener)) {
            gpsListeners.add(listener);
        }
    }

    public void addGpsStatusListener(android.location.GpsStatus.Listener listener) {
        // System.out.println("WalkerActivityFooter, Adding GpsStatus: " + listener.getClass().getCanonicalName());
        if (!gpsStatusListeners.contains(listener)) {
            gpsStatusListeners.add(listener);
            if ((mGoogleApiClient != null) && mGoogleApiClient.isConnected()) {
                locationManager.registerGnssStatusCallback(callback);
            }
        }
    }

    public void removeFusionLocationListener(com.google.android.gms.location.LocationListener listener) {
        // System.out.println("WalkerActivityFooter, Removing FusionLocationListener: " + listener.getClass().getCanonicalName());
        if (locationListeners.contains(listener)) {
            locationListeners.remove(listener);
            if ((mGoogleApiClient != null) && mGoogleApiClient.isConnected()) {
                LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, listener);
            }
        }
    }

    public void removeGpsListener(ch.supsi.ist.camre.paths.WalkerActivityFooter.GpsListener listener) {
        // System.out.println("WalkerActivityFooter, Removing GpsListener: " + listener.getClass().getCanonicalName());
        if (gpsListeners.contains(listener)) {
            gpsListeners.remove(listener);
        }
    }

    public void removeGpsStatusistener(android.location.GpsStatus.Listener listener) {
        // System.out.println("WalkerActivityFooter, Removing GpsStatus: " + listener.getClass().getCanonicalName());
        if (gpsStatusListeners.contains(listener)) {
            gpsStatusListeners.remove(listener);
            locationManager.removeGpsStatusListener(listener);
        }
    }

    @java.lang.Override
    public void onConnected(android.os.Bundle bundle) {
        // System.out.println("WalkerActivityFooter, LocationWorker: onConnected");
        if (locationListeners.size() > 0) {
            for (com.google.android.gms.location.LocationListener listener : locationListeners) {
                LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, listener);
            }
        }
        if (gpsStatusListeners.size() > 0) {
            for (android.location.GpsStatus.Listener listener : gpsStatusListeners) {
                locationManager.addGpsStatusListener(listener);
            }
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        locationManager.addGpsStatusListener(this);
    }

    @java.lang.Override
    public void onLocationChanged(android.location.Location location) {
        // System.out.println("WalkerActivityFooter: onLocationChanged");
        double lon = location.getLongitude();
        double lat = location.getLatitude();
        java.util.TimeZone tz = java.util.TimeZone.getTimeZone("UTC");
        java.text.DateFormat df = new java.text.SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        df.setTimeZone(tz);
        ch.supsi.ist.camre.paths.data.Position pos = new ch.supsi.ist.camre.paths.data.Position();
        pos.setAltitude(location.getAltitude());
        pos.setHeading(0.0);
        pos.setGeometry(new ch.supsi.ist.camre.paths.data.Point(lon, lat));
        pos.setTimestamp(df.format(new java.util.Date()));
    }

    @java.lang.Override
    public synchronized void onGpsStatusChanged(int event) {
        // System.out.println("onGpsStatusChanged..");
        android.widget.ImageView icon = ((android.widget.ImageView) (view.findViewById(R.id.gpsicon)));
        android.location.GpsStatus gpsStatus = locationManager.getGpsStatus(null);
        android.widget.TextView status = ((android.widget.TextView) (view.findViewById(R.id.walker_status_gps)));
        switch (event) {
            case android.location.GpsStatus.GPS_EVENT_STARTED :
                icon.setImageResource(R.drawable.ic_action_map_note);
                status.setText("GPS is connecting.. please wait.");
                // System.out.println("GPS  is connecting...");
                break;
            case android.location.GpsStatus.GPS_EVENT_STOPPED :
                icon.setImageResource(R.drawable.ic_action_map_end);
                status.setText("GPS disabled");
                // System.out.println("GPS disabled");
                break;
            case android.location.GpsStatus.GPS_EVENT_FIRST_FIX :
                icon.setImageResource(R.drawable.ic_action_map_start);
                status.setText("GPS connected");
                // System.out.println("GPS connected !!!!! ");
                break;
            case android.location.GpsStatus.GPS_EVENT_SATELLITE_STATUS :
                // System.out.println("GPS status received, ttff: " + gpsStatus.getTimeToFirstFix());
                int satCnt = 0;
                int satFixCnt = 0;
                java.util.Iterator<android.location.GpsSatellite> satellites = gpsStatus.getSatellites().iterator();
                while (satellites.hasNext()) {
                    android.location.GpsSatellite satellite = satellites.next();
                    satCnt++;
                    if (satellite.usedInFix()) {
                        satFixCnt++;
                    }
                } 
                if (gpsStatus.getTimeToFirstFix() > 0) {
                    android.location.Location gpsLocation = locationManager.getLastKnownLocation(android.location.LocationManager.GPS_PROVIDER);
                    // System.out.println("Listeners: " + gpsListeners.size());
                    status.setText((((((("GPS: Accuracy " + gpsLocation.getAccuracy()) + "m Altitude ") + java.lang.Math.round(gpsLocation.getAltitude())) + "m Satellites ") + satFixCnt) + "/") + satCnt);
                    if (gpsListeners.size() > 0) {
                        for (ch.supsi.ist.camre.paths.WalkerActivityFooter.GpsListener listener : gpsListeners) {
                            listener.onGpsStatusChanged(event, gpsLocation, gpsStatus);
                        }
                    }
                } else {
                    icon.setImageResource(R.drawable.ic_action_map_note);
                    status.setText((("GPS is connecting.. please wait. Satellites " + satFixCnt) + "/") + satCnt);
                    // System.out.println("GPS is connecting.. please wait. Satellites " + satFixCnt + "/" + satCnt);
                }
                break;
            default :
                // System.out.println("Event Unknown.. :[*.*]:" + event);
                status.setText("Event Unknown.. :[*.*]:" + event);
                break;
        }
    }

    @java.lang.Override
    public void onConnectionSuspended(int i) {
        // System.out.println("GeolocationWorker: onConnectionSuspended");
    }

    @java.lang.Override
    public void onConnectionFailed(com.google.android.gms.common.ConnectionResult connectionResult) {
        // System.out.println("GeolocationWorker: onConnectionFailed");
        /* Google Play services can resolve some errors it detects.
        If the error has a resolution, try sending an Intent to
        start a Google Play services activity that can resolve
        error.
         */
        if (connectionResult.hasResolution()) {
            try {
                // Start an Activity that tries to resolve the error
                connectionResult.startResolutionForResult(getActivity(), ch.supsi.ist.camre.paths.WalkerActivityFooter.CONNECTION_FAILURE_RESOLUTION_REQUEST);
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
}