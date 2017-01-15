package com.guenwoo.expedometer;

import java.util.List;
import java.util.Locale;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by SPECIAL-GW on 2017-01-15.
 *  GPS 관련 참고 사이트
 *  -- http://leesang0204.tistory.com/8
 *  -- http://ilililililililililili.blogspot.kr/2013/07/android-gps_18.html
 *  Permission 관련 참고 사이트
 *  -- https://developer.android.com/training/permissions/requesting.html
 */

public class GPStoAddress extends AppCompatActivity {


    Activity mActivity;

    public final int MY_PERMISSION_ACCESS_COURSE_LOCATIO = 1;
    public GPStoAddress(Activity aActivity)
    {
        mActivity = aActivity;
    }

    LocationManager LocationMg = null;
    String provider = null;

    private double latitude = 0;
    private double longitude = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void getMyLocation() {
        if (LocationMg == null) {
            LocationMg = (LocationManager)mActivity.getSystemService(Context.LOCATION_SERVICE);
        }

        long minTime = 10000;
        float minDistance = 0;

        MyLocationListener listener = new MyLocationListener();

        // 권한 설정 여부 확인
        if (  Build.VERSION.SDK_INT >= 23 && ContextCompat.checkSelfPermission( mActivity, android.Manifest.permission.ACCESS_COARSE_LOCATION ) != PackageManager.PERMISSION_GRANTED )
        {
            ActivityCompat.requestPermissions( mActivity, new String[] {  android.Manifest.permission.ACCESS_COARSE_LOCATION  },MY_PERMISSION_ACCESS_COURSE_LOCATIO  );
        }
        else
        {
            LocationMg.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, minTime, minDistance, listener);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSION_ACCESS_COURSE_LOCATIO:
            {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    getMyLocation();
                }
                return;
            }
        }
    }

    class MyLocationListener implements LocationListener {

        @Override
        public void onLocationChanged(Location location)
        {
            // 위도 , 경도 체크
            latitude = location.getLatitude();
            longitude = location.getLongitude();
        }

        @Override
        public void onProviderDisabled(String provider) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }
    }

    public String getAddress()
    {
        String address = null;

        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        List<Address> list = null;

        try {
            list = geocoder.getFromLocation(latitude, longitude, 1);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        if(list == null)
            return null;

        if(list.size() > 0){
            Address addr = list.get(0);
            address = addr.getCountryName() + " "
                    + addr.getLocality() + " "
                    + addr.getThoroughfare() + " "
                    + addr.getFeatureName();
        }

        return address;
    }
}
