package com.myproject.repaircar.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import static android.content.Context.LOCATION_SERVICE;

/**
 * Created by niceinkeaw on 7/12/2558.
 */
public class GPSTracker implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {
    private Listener listener;
    private final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;
    private final static String TAG = "GPSTracker";
    protected static final int REQUEST_CHECK_SETTINGS = 0x1;

    public interface Listener {
        void onLocationChanged(Location location);

        void onLocationServiceNotAvailable();

        void onError(Throwable throwable);
    }

    private GoogleApiClient mGoogleApiClient;
    private final Context mContext;

    // flag for GPS status
    boolean isGPSEnabled = false;
    // flag for network status
    boolean isNetworkEnabled = false;

    static volatile boolean canGetLocation = false;

    Location location; // location
    static volatile double LATITUDE = 0; // LATITUDE
    static volatile double LONGITUDE = 0; // LONGITUDE


    // The minimum distance to change Updates in meters
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10; // 10 meters

    private long UPDATE_INTERVAL = 30000;  /* 60 secs */
    private long FASTEST_INTERVAL = 5000; /* 5 secs */

    // Declaring a Location Manager
    protected LocationManager mLocationManager;

    public GPSTracker(Context context) {
        this.mContext = context;
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(mContext)
                    .addApi(LocationServices.API)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .build();
        }

    }

    public void connect() {
        if (mGoogleApiClient != null) {
            mGoogleApiClient.connect();
        }
    }

    public void connect(Listener listener) {
        this.listener = listener;
        connect();
    }

    public void disconnect() {
        // Disconnecting the client invalidates it.

        if (mGoogleApiClient != null && mGoogleApiClient.isConnected()) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
            mGoogleApiClient.disconnect();
            listener = null;
        }
    }

    private boolean _isLocationEnabled() {
        int locationMode = 0;
        String locationProviders;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            try {
                locationMode = Settings.Secure.getInt(mContext.getContentResolver(), Settings.Secure.LOCATION_MODE);

            } catch (Settings.SettingNotFoundException e) {
                e.printStackTrace();
            }

            return locationMode != Settings.Secure.LOCATION_MODE_OFF;

        } else {
            locationProviders = Settings.Secure.getString(mContext.getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
            return !TextUtils.isEmpty(locationProviders);
        }

    }


    public Location getLocation() {
        try {
            mLocationManager = (LocationManager) mContext.getSystemService(LOCATION_SERVICE);

            // getting GPS status
            isGPSEnabled = mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

            // getting network status
            isNetworkEnabled = mLocationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            if (!isGPSEnabled && !isNetworkEnabled) {
                // no network provider is enabled
                Log.d("Network&GPS", "Network&GPS is not enable");
            } else {
                //this.canGetLocation = true;

                // if GPS Enabled get lat/long using GPS Services
                if (isGPSEnabled) {
                    if (location == null) {
                        //mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                        Log.d("GPS Enabled", "GPS Enabled");

                        if (mLocationManager != null) {
                            //location = getLastKnownLocation();
                            /*location = mLocationManager
                                    .getLastKnownLocation(LocationManager.GPS_PROVIDER);*/
                            if (location != null) {
                                LATITUDE = location.getLatitude();
                                LONGITUDE = location.getLongitude();
                                if (listener != null)
                                    listener.onLocationChanged(location);
                            }
                        }
                    }
                    return location;
                }

                // First get location from Network Provider
                if (isNetworkEnabled) {
                    //mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                    Log.d("Network", "Network");
                    if (mLocationManager != null) {
                        //location = mLocationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                        if (location != null) {
                            LATITUDE = location.getLatitude();
                            LONGITUDE = location.getLongitude();
                            if (listener != null)
                                listener.onLocationChanged(location);
                        }
                    }
                    return location;
                }

            }
        } catch (Exception e) {
            Toast.makeText(mContext, "Cannot get location. Please check GPS or internet enabled.", Toast.LENGTH_LONG).show();
            if(listener!=null)
                listener.onLocationServiceNotAvailable();
        } finally {
            if (location == null){
                if(listener!=null)
                    listener.onLocationServiceNotAvailable();
                Toast.makeText(mContext, "Cannot get location. Something wrong.", Toast.LENGTH_LONG).show();
            }

        }
        return location;
    }

    public static double getLatitude() {

        // return LATITUDE
        return LATITUDE;
    }

    public static double getLongitude() {

        // return LONGITUDE
        return LONGITUDE;
    }

    public static boolean canGetLocation() {
        return canGetLocation;
    }

    public void showSettingsAlert() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);

        // Setting Dialog Title
        alertDialog.setTitle("Location is settings");

        // Setting Dialog Message
        alertDialog.setMessage("Location is not enabled. Do you want to go to settings menu?");

        // Setting Icon to Dialog
        //alertDialog.setIcon(R.drawable.delete);

        // On pressing Settings button
        alertDialog.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                mContext.startActivity(intent);
            }
        });

        // on pressing cancel button
        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        // Showing Alert Message
        alertDialog.show();
    }

    public void stopUsingGPS() {
        if (mLocationManager != null) {
            //mLocationManager.removeUpdates(GPSTracker.this);
        }
    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (_isLocationEnabled()) {
            // Call Location Services
            LocationRequest locationRequest = new LocationRequest()
                    .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY|LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY)
                    .setSmallestDisplacement(MIN_DISTANCE_CHANGE_FOR_UPDATES)
                    .setInterval(UPDATE_INTERVAL)
                    .setFastestInterval(FASTEST_INTERVAL);
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, locationRequest, this);
        } else {
            if(listener!=null) listener.onError(null);
            canGetLocation = false;
            showSettingsAlert();
        }
    }


    @Override
    public void onConnectionSuspended(int i) {
        canGetLocation = false;
        if (i == CAUSE_SERVICE_DISCONNECTED) {
            Toast.makeText(mContext, "Disconnected. Please re-connect.", Toast.LENGTH_SHORT).show();
        } else if (i == CAUSE_NETWORK_LOST) {
            Toast.makeText(mContext, "Network lost. Please re-connect.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        if (connectionResult.hasResolution()) {
            if(listener!=null) listener.onError(null);
            canGetLocation = false;
            // Start an Activity that tries to resolve the error

				/*
                 * Thrown if Google Play services canceled the original
				 * PendingIntent
				 */
        } else {
            if(listener!=null) listener.onError(null);
            canGetLocation = false;
            Toast.makeText(this.mContext,
                    "Sorry. Location services not available to you : "+connectionResult.getErrorMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        canGetLocation = true;
        String provider = location.getProvider();
        double latitude = location.getLatitude();
        double longitude = location.getLongitude();
        double altitude = location.getAltitude();
        float accuracy = location.getAccuracy();
        float bearing = location.getBearing();
        float speed = location.getSpeed();
        long time = location.getTime();

        LATITUDE = latitude;
        LONGITUDE = longitude;

        if (listener != null)
            listener.onLocationChanged(location);
    }

    private void log(String message) {
        Log.d(TAG, message);
    }

}
