package com.example.km.fry.Location;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import java.util.logging.Logger;

public class location {

    static Logger log = Logger.getLogger("MainActivity");
    static long MAXCHECKTIME = 3300000;//55*60*1000;

    public static interface LocationCallback {
        public void onNewLocationAvailable(GPSCoordinates location);
    }

    // calls back to calling thread, note this is for low grain: if you want higher precision, swap the
    // contents of the else and if. Also be sure to check gps permission/settings are allowed.
    // call usually takes <10ms
    public static void requestSingleUpdate(final Context context, final LocationCallback callback) {

        log.info("확인중1");

        String permission1 = "android.permission.ACCESS_COARSE_LOCATION";
        String permission2 = "android.permission.ACCESS_FINE_LOCATION";
        int res1 = context.checkCallingOrSelfPermission(permission1);
        int res2 = context.checkCallingOrSelfPermission(permission2);
        if (res1 == PackageManager.PERMISSION_GRANTED && res2 == PackageManager.PERMISSION_GRANTED) {
            log.info("확인중2");

            final LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
            boolean isNetworkEnabled = false;
            boolean isGPSEnabled = false;
            boolean isPassiceEnaled = false;
            long currentTime = System.currentTimeMillis();
            try {
                isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
            } catch (Exception e) {
                log.warning(e.toString());
            }
            try {
                isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            } catch (Exception e) {
                log.warning(e.toString());
            }
            try {
                isPassiceEnaled = locationManager.isProviderEnabled(LocationManager.PASSIVE_PROVIDER);
            } catch (Exception e) {
                log.warning(e.toString());
            }

            try {

                Location location1 = null;
                if (isNetworkEnabled)
                    location1 = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                Location location2 = null;
                if (isGPSEnabled)
                    location2 = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                Location location3 = null;
                if (isPassiceEnaled)
                    location3 = locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);

                if (location1 != null && (currentTime - location1.getTime() < MAXCHECKTIME)) {
                    callback.onNewLocationAvailable(new GPSCoordinates(location1.getLatitude(), location1.getLongitude()));
                    return;
                } else if (location2 != null && (currentTime - location2.getTime() < MAXCHECKTIME)) {
                    callback.onNewLocationAvailable(new GPSCoordinates(location2.getLatitude(), location2.getLongitude()));
                    return;
                } else if (location3 != null && (currentTime - location3.getTime() < MAXCHECKTIME)) {
                    callback.onNewLocationAvailable(new GPSCoordinates(location3.getLatitude(), location3.getLongitude()));
                    return;
                } else {
                    log.info("아무것도 발견안됨");
                }

            } catch (Exception e) {
                log.warning(e.toString());
            }

            if (isNetworkEnabled) {

                Criteria criteria = new Criteria();
                criteria.setAccuracy(Criteria.ACCURACY_COARSE);
                locationManager.requestSingleUpdate(criteria, new LocationListener() {
                    @Override
                    public void onLocationChanged(Location location) {

                        callback.onNewLocationAvailable(new GPSCoordinates(location.getLatitude(), location.getLongitude()));
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
                }, null);
            } else {


                if (isGPSEnabled) {

                    Criteria criteria = new Criteria();
                    criteria.setAccuracy(Criteria.ACCURACY_FINE);
                    locationManager.requestSingleUpdate(criteria, new LocationListener() {
                        @Override
                        public void onLocationChanged(Location location) {
                            callback.onNewLocationAvailable(new GPSCoordinates(location.getLatitude(), location.getLongitude()));
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
                    }, null);
                } else {
                    callback.onNewLocationAvailable(new GPSCoordinates(0, 0));
                }
            }
        }else{
            //권한이 없을경우 대비
            callback.onNewLocationAvailable(new GPSCoordinates(0, 0));
        }
    }


    // consider returning Location instead of this dummy wrapper class
    public static class GPSCoordinates {
        public float longitude = -1;
        public float latitude = -1;

        GPSCoordinates(float theLatitude, float theLongitude) {
            longitude = theLongitude;
            latitude = theLatitude;
        }

        GPSCoordinates(double theLatitude, double theLongitude) {
            longitude = (float) theLongitude;
            latitude = (float) theLatitude;
        }
    }
}