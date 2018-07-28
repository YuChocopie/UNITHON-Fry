package com.example.km.fry;

import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;


import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;

import noman.googleplaces.NRPlaces;
import noman.googleplaces.Place;
import noman.googleplaces.PlaceType;
import noman.googleplaces.PlacesException;
import noman.googleplaces.PlacesListener;

/**
 * Created by xowns on 2018-07-28.
 */

public class HomeActivity  extends AppCompatActivity implements OnMapReadyCallback, PlacesListener, LocationListener{

    float lat, lng; // 마커의 위도 경도
    GoogleMap map; // 구글 맵
    Circle circle; // 현재 위치 반경
    CircleOptions circle1Km; // 현재 위치 반경 1km

    ArrayList<Marker> previous_marker = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map);

        Intent intent = getIntent();
        lat = intent.getExtras().getFloat("MyLat");
        lng = intent.getExtras().getFloat("MyLng");

        Log.d("my_lat", String.valueOf(lat));
        Log.d("my_lng", String.valueOf(lng));

        FragmentManager fragmentManager = getFragmentManager();
        MapFragment mapFragment = (MapFragment)fragmentManager
                .findFragmentById(R.id.map);

        mapFragment.getMapAsync(this);

        showPlaceInformation(new LatLng(lat, lng)); // 현재 위도, 경도 기준으로 근처 은행들 보여줌
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        map = googleMap;

        LatLng My = new LatLng(lat, lng);

        String my_address = getAddress(this, lat, lng);

        //나의 위치
        MarkerOptions markerOptions_my = new MarkerOptions();
        markerOptions_my.position(My);
        markerOptions_my.title("나의 위치");
      //  markerOptions_my.icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.now)));
        markerOptions_my.snippet(my_address);

        googleMap.addMarker(markerOptions_my);
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(My));
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(15));

        // 반경 1KM원
        circle1Km = new CircleOptions().center(new LatLng(lat, lng)) //원점
                .radius(700)      //반지름 단위 : m
                .strokeWidth(0f)  //선너비 0f : 선없음
                .fillColor(Color.parseColor("#880000ff")); //배경색

        circle = map.addCircle(circle1Km);

        //맵을 드래그 했을때 맵을 생성
        GoogleMap.OnMarkerDragListener markerDragListener = new GoogleMap.OnMarkerDragListener() {

            @Override
            public void onMarkerDragStart(Marker marker) { // 마커 드래그를 시작할 때
                // Called when the marker drag is started

            }

            @Override
            public void onMarkerDragEnd(Marker marker) { // 마커 드래그를 끝낼 때

            }

            @Override
            public void onMarkerDrag(Marker marker) { // 마커 드래그 중

            }
        };

        googleMap.setOnMarkerDragListener(markerDragListener);

    }

    // 위도, 경도 -> 주소값
    static String getAddress(Context context, double lat, double lon) {

        Geocoder geocoder;geocoder = new Geocoder(context, Locale.getDefault());
        List<Address> addresses = null;
        try {
            addresses = geocoder.getFromLocation(lat, lon, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (addresses != null && addresses.size() != 0 ) {

            String address = "";

            String country = addresses.get(0).getCountryName();
            String city = addresses.get(0).getLocality();
            String subCity = addresses.get(0).getSubLocality();
            String subsubCity = addresses.get(0).getThoroughfare();

            if (country != null && !address.toLowerCase().contains(country.toLowerCase())) { // 나라
                if(address.equals("") || address.equals(" ")|| address.equals("  ")|| address.equals("   ")){
                    address += country;
                }else{
                    address += " " + country;
                }
            }

            if(city != null && !address.toLowerCase().contains(city.toLowerCase())){ // 지역
                if(address.equals("") || address.equals(" ")|| address.equals("  ")|| address.equals("   ")){
                    address += city;
                }else{
                    address += " " + city;
                }
            }

            //구
            if(subCity != null && !address.toLowerCase().contains(subCity.toLowerCase())){ address += " " + subCity;;}

            //동
            if(subsubCity != null && !address.toLowerCase().contains(subsubCity.toLowerCase())){ address += " " + subsubCity;;}

            return address;
        } else {
            return "";
        }
    }

    public void showPlaceInformation(LatLng location)
    {
        //   map.clear();//지도 클리어

        if (previous_marker != null)
            previous_marker.clear();//지역정보 마커 클리어

        new NRPlaces.Builder()
                .listener(HomeActivity.this)
                .key("AIzaSyC0IBPtDHrgMl-4VzCaTfo2TZmyniRVZ0Y")
                .latlng(lat, lng)//현재 위치
                .radius(500) //500 미터 내에서 검색
                .type(PlaceType.BANK )//은행
                .build()
                .execute();

        new NRPlaces.Builder()
                .listener(HomeActivity.this)
                .key("AIzaSyC0IBPtDHrgMl-4VzCaTfo2TZmyniRVZ0Y")
                .latlng(lat, lng)//현재 위치
                .radius(500) //500 미터 내에서 검색
                .type(PlaceType.ATM) // ATM
                .build()
                .execute();
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }

    @Override
    public void onPlacesFailure(PlacesException e) {

    }

    @Override
    public void onPlacesStart() {

    }

    @Override
    public void onPlacesSuccess(final List<Place> places) {

        runOnUiThread(new Runnable() {

            @Override

            public void run() {

                for (noman.googleplaces.Place place : places) {

                    LatLng latLng = new LatLng(place.getLatitude(), place.getLongitude());

                    String markerSnippet = getAddress(getApplicationContext(), latLng.latitude, latLng.longitude);

                    MarkerOptions markerOptions = new MarkerOptions();
                    markerOptions.position(latLng);
                    markerOptions.title(place.getName());
                    markerOptions.snippet(markerSnippet);
                    Marker item = map.addMarker(markerOptions);
                    previous_marker.add(item);
                }

                //중복 마커 제거
                HashSet<Marker> hashSet = new HashSet<Marker>();
                hashSet.addAll(previous_marker);
                previous_marker.clear();
                previous_marker.addAll(hashSet);

            }
        });
    }

    @Override
    public void onPlacesFinished() {

    }
}
