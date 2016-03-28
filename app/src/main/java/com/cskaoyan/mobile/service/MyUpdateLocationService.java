package com.cskaoyan.mobile.service;

import android.app.Service;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.cskaoyan.mobile.application.MyApplication;

/**
 * Created by Lan on 2016/3/28.
 */
public class MyUpdateLocationService extends Service {



    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public void onCreate() {
        super.onCreate();

        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        locationManager.requestLocationUpdates("gps", 0, 0, new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {

                final double longitude = location.getLongitude();
                final double latitude = location.getLatitude();
                MyApplication.setConfigValue("longitude", longitude + "");
                MyApplication.setConfigValue("latitude", latitude + "");

            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        });

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {


        return super.onStartCommand(intent, flags, startId);
    }
}
