package com.beastbikes.android.modules.cycling.activity.util;
/**
 * Created by caoxiao on 16/4/22.
 */
public abstract class GpsStatusObserve implements android.location.GpsStatus.Listener {
    private android.location.LocationManager locationListener;

    private java.util.List<android.location.GpsSatellite> numSatelliteList = new java.util.ArrayList<>();// 卫星信号


    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(com.beastbikes.android.modules.cycling.activity.util.GpsStatusObserve.class);

    // private int count;
    private boolean hasLocation;

    public GpsStatusObserve(android.content.Context context) {
        locationListener = ((android.location.LocationManager) (context.getSystemService(android.content.Context.LOCATION_SERVICE)));
    }

    @java.lang.Override
    public void onGpsStatusChanged(int event) {
        switch (event) {
            // 第一次定位
            case android.location.GpsStatus.GPS_EVENT_FIRST_FIX :
                com.beastbikes.android.modules.cycling.activity.util.GpsStatusObserve.logger.info("第一次定位");
                break;
                // 卫星状态改变
            case android.location.GpsStatus.GPS_EVENT_SATELLITE_STATUS :
                android.location.GpsStatus status = locationListener.getGpsStatus(null);// 取当前状态

                updateGpsStatus(event, status);
                break;
                // 定位启动
            case android.location.GpsStatus.GPS_EVENT_STARTED :
                com.beastbikes.android.modules.cycling.activity.util.GpsStatusObserve.logger.info("定位启动");
                break;
                // 定位结束
            case android.location.GpsStatus.GPS_EVENT_STOPPED :
                com.beastbikes.android.modules.cycling.activity.util.GpsStatusObserve.logger.info("定位结束");
                break;
        }
    }

    private void updateGpsStatus(int event, android.location.GpsStatus status) {
        if (status == null) {
        } else if (event == android.location.GpsStatus.GPS_EVENT_SATELLITE_STATUS) {
            java.util.Iterator<android.location.GpsSatellite> it = status.getSatellites().iterator();
            numSatelliteList.clear();
            int gpsStatus = 0;// 总信噪比之和

            while (it.hasNext()) {
                android.location.GpsSatellite s = it.next();
                if (s.getSnr() > 0.0) {
                    // 信号信噪比不为0计入GPS信号
                    numSatelliteList.add(s);
                    gpsStatus += s.getSnr();
                }
            } 
            if (gpsStatus > 150) {
                this.onLocationSuccess();
                this.hasLocation = true;
            } else if (hasLocation) {
                this.onLocationFailed();
                this.hasLocation = false;
            }
        }
    }

    public abstract void onLocationSuccess();

    public abstract void onLocationFailed();

    public void addGpsStatusListener() {
        locationManager.registerGnssStatusCallback(callback);
    }

    public void removeGpsStatusListener() {
        locationListener.removeGpsStatusListener(this);
    }
}