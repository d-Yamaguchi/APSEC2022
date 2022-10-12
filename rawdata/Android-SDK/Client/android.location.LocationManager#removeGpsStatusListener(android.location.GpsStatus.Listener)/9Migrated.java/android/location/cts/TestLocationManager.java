/* Copyright (C) 2015 Google Inc.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

     http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 */
package android.location.cts;
import android.location.LocationManager;
import junit.framework.Assert;
/**
 * A {@code LocationManager} wrapper that logs GNSS turn-on and turn-off.
 */
public class TestLocationManager {
    private static final java.lang.String TAG = "TestLocationManager";

    private android.location.LocationManager mLocationManager;

    private android.content.Context mContext;

    public TestLocationManager(android.content.Context context) {
        mContext = context;
        mLocationManager = ((android.location.LocationManager) (mContext.getSystemService(android.content.Context.LOCATION_SERVICE)));
    }

    /**
     * See {@code LocationManager#removeUpdates(LocationListener)}.
     *
     * @param listener
     * 		the listener to remove
     */
    public void removeLocationUpdates(android.location.LocationListener listener) {
        android.util.Log.i(android.location.cts.TestLocationManager.TAG, "Remove Location updates.");
        mLocationManager.removeUpdates(listener);
    }

    /**
     * See {@link android.location.LocationManager#registerGnssMeasurementsCallback
     * (GnssMeasurementsEvent.Callback callback)}
     *
     * @param callback
     * 		the listener to add
     */
    public void registerGnssMeasurementCallback(android.location.GnssMeasurementsEvent.Callback callback) {
        android.util.Log.i(android.location.cts.TestLocationManager.TAG, "Add Gnss Measurement Callback.");
        boolean measurementListenerAdded = mLocationManager.registerGnssMeasurementsCallback(callback);
        if (!measurementListenerAdded) {
            // Registration of GnssMeasurements listener has failed, this indicates a platform bug.
            android.util.Log.i(android.location.cts.TestLocationManager.TAG, TestMeasurementUtil.REGISTRATION_ERROR_MESSAGE);
            junit.framework.Assert.fail(TestMeasurementUtil.REGISTRATION_ERROR_MESSAGE);
        }
    }

    /**
     * See {@link android.location.LocationManager#registerGnssMeasurementsCallback(GnssMeasurementsEvent.Callback callback)}
     *
     * @param callback
     * 		the listener to add
     * @param handler
     * 		the handler that the callback runs at.
     */
    public void registerGnssMeasurementCallback(android.location.GnssMeasurementsEvent.Callback callback, android.os.Handler handler) {
        android.util.Log.i(android.location.cts.TestLocationManager.TAG, "Add Gnss Measurement Callback.");
        boolean measurementListenerAdded = mLocationManager.registerGnssMeasurementsCallback(callback, handler);
        if (!measurementListenerAdded) {
            // Registration of GnssMeasurements listener has failed, this indicates a platform bug.
            android.util.Log.i(android.location.cts.TestLocationManager.TAG, TestMeasurementUtil.REGISTRATION_ERROR_MESSAGE);
            junit.framework.Assert.fail(TestMeasurementUtil.REGISTRATION_ERROR_MESSAGE);
        }
    }

    /**
     * See {@link android.location.LocationManager#unregisterGnssMeasurementsCallback
     * (GnssMeasurementsEvent.Callback)}.
     *
     * @param callback
     * 		the listener to remove
     */
    public void unregisterGnssMeasurementCallback(android.location.GnssMeasurementsEvent.Callback callback) {
        android.util.Log.i(android.location.cts.TestLocationManager.TAG, "Remove Gnss Measurement Callback.");
        mLocationManager.unregisterGnssMeasurementsCallback(callback);
    }

    /**
     * See {@code LocationManager#requestLocationUpdates}.
     *
     * @param locationListener
     * 		location listener for request
     */
    public void requestLocationUpdates(android.location.LocationListener locationListener) {
        if (mLocationManager.getProvider(android.location.LocationManager.GPS_PROVIDER) != null) {
            android.util.Log.i(android.location.cts.TestLocationManager.TAG, "Request Location updates.");
            /* minTime */
            /* minDistance */
            mLocationManager.requestLocationUpdates(android.location.LocationManager.GPS_PROVIDER, 0, 0, locationListener, android.os.Looper.getMainLooper());
        }
    }

    /**
     * See {@link android.location.LocationManager#addGpsStatusListener (GpsStatus.Listener)}.
     *
     * @param listener
     * 		the GpsStatus.Listener to add
     */
    public void addGpsStatusListener(final android.location.GpsStatus.Listener listener) {
        android.util.Log.i(android.location.cts.TestLocationManager.TAG, "Add Gps Status Listener.");
        android.os.Handler mainThreadHandler = new android.os.Handler(android.os.Looper.getMainLooper());
        // Add Gps status listener to the main thread, since the Gps Status updates will go to
        // the main thread while the test thread is blocked by mGpsStatusListener.await()
        mainThreadHandler.post(new java.lang.Runnable() {
            @java.lang.Override
            public void run() {
                mLocationManager.addGpsStatusListener(listener);
            }
        });
    }

    /**
     * See {@link android.location.LocationManager#removeGpsStatusListener (GpsStatus.Listener)}.
     *
     * @param listener
     * 		the listener to remove
     */
    public void removeGpsStatusListener(android.location.GpsStatus.Listener listener) {
        android.util.Log.i(android.location.cts.TestLocationManager.TAG, "Remove Gps Status Listener.");
        mLocationManager.unregisterGnssStatusCallback(mGpsUpdate);
    }

    /**
     * See {@link android.location.LocationManager#sendExtraCommand}.
     *
     * @param command
     * 		name of the command to send to the provider.
     * @return true if the command succeeds.
     */
    public boolean sendExtraCommand(java.lang.String command) {
        android.util.Log.i(android.location.cts.TestLocationManager.TAG, "Send Extra Command = " + command);
        boolean extraCommandStatus = mLocationManager.sendExtraCommand(android.location.LocationManager.GPS_PROVIDER, command, null);
        android.util.Log.i(android.location.cts.TestLocationManager.TAG, (("Sent extra command (" + command) + ") status = ") + extraCommandStatus);
        return extraCommandStatus;
    }

    /**
     * Add a GNSS Navigation Message callback.
     *
     * @param callback
     * 		a {@link GnssNavigationMessage.Callback} object to register.
     * @return {@code true} if the listener was added successfully, {@code false} otherwise.
     */
    public boolean registerGnssNavigationMessageCallback(android.location.GnssNavigationMessage.Callback callback) {
        android.util.Log.i(android.location.cts.TestLocationManager.TAG, "Add Gnss Navigation Message Callback.");
        return mLocationManager.registerGnssNavigationMessageCallback(callback);
    }

    /**
     * Add a GNSS Navigation Message callback.
     *
     * @param callback
     * 		a {@link GnssNavigationMessage.Callback} object to register.
     * @param handler
     * 		the handler that the callback runs at.
     * @return {@code true} if the listener was added successfully, {@code false} otherwise.
     */
    public boolean registerGnssNavigationMessageCallback(android.location.GnssNavigationMessage.Callback callback, android.os.Handler handler) {
        android.util.Log.i(android.location.cts.TestLocationManager.TAG, "Add Gnss Navigation Message Callback.");
        return mLocationManager.registerGnssNavigationMessageCallback(callback, handler);
    }

    /**
     * Removes a GNSS Navigation Message callback.
     *
     * @param callback
     * 		a {@link GnssNavigationMessage.Callback} object to remove.
     */
    public void unregisterGnssNavigationMessageCallback(android.location.GnssNavigationMessage.Callback callback) {
        android.util.Log.i(android.location.cts.TestLocationManager.TAG, "Remove Gnss Navigation Message Callback.");
        mLocationManager.unregisterGnssNavigationMessageCallback(callback);
    }

    /**
     * Get LocationManager
     *
     * @return locationManager
     */
    public android.location.LocationManager getLocationManager() {
        return mLocationManager;
    }

    /**
     * Get Context
     *
     * @return context
     */
    public android.content.Context getContext() {
        return mContext;
    }
}