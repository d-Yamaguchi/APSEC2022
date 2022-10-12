package com.example.NLSUbiPos.context;
public class GPSAdmin {
    private int GPSN;

    private double GPSSNR;

    private double maxSNR;

    private boolean GPSOK;

    android.location.LocationManager locationManager;

    android.location.LocationListener locationListener;

    public GPSAdmin(android.content.Context context) {
        locationManager = ((android.location.LocationManager) (context.getSystemService(android.content.Context.LOCATION_SERVICE)));
        locationManager.registerGnssStatusCallback(callback);
    }

    android.location.GpsStatus.Listener listener = new android.location.GpsStatus.Listener() {
        @java.lang.Override
        public void onGpsStatusChanged(int event) {
            switch (event) {
                case android.location.GpsStatus.GPS_EVENT_FIRST_FIX :
                    break;
                case android.location.GpsStatus.GPS_EVENT_SATELLITE_STATUS :
                    android.location.GpsStatus gpsStatus = locationManager.getGpsStatus(null);
                    int maxSatellites = gpsStatus.getMaxSatellites();
                    java.util.Iterator<android.location.GpsSatellite> iters = gpsStatus.getSatellites().iterator();
                    double E = 0;
                    int count = 0;
                    while (iters.hasNext() && (count <= maxSatellites)) {
                        android.location.GpsSatellite s = iters.next();
                        if (s.usedInFix()) {
                            E += s.getSnr();
                            count++;
                        }
                        if (s.getSnr() > maxSNR) {
                            maxSNR = ((int) (s.getSnr()));
                        }
                    } 
                    if (count == 0)
                        GPSSNR = 0;
                    else
                        GPSSNR = E / count;

                    GPSN = count;
                    java.lang.System.out.println((("Satelite N:" + count) + ", SNR:") + GPSSNR);
                    GPSOK = true;
                    break;
                case android.location.GpsStatus.GPS_EVENT_STARTED :
                    break;
                case android.location.GpsStatus.GPS_EVENT_STOPPED :
                    break;
            }
        }
    };

    public int getGPSN() {
        return GPSN;
    }

    public double getmaxSNR() {
        return maxSNR;
    }

    public double getSNR() {
        return GPSSNR;
    }

    public boolean checkGPSOK() {
        return GPSOK;
    }
}