package com.example.km.fry;

import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.example.km.fry.Location.location;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import noman.googleplaces.Place;
import noman.googleplaces.PlacesException;
import noman.googleplaces.PlacesListener;

/**
 * Created by xowns on 2018-07-29.
 */

public class Fragment_map  extends android.support.v4.app.Fragment implements OnMapReadyCallback, PlacesListener, LocationListener {

    float lat, lng; // 마커의 위도 경도
    GoogleMap map; // 구글 맵
    Circle circle; // 현재 위치 반경
    CircleOptions circle1Km; // 현재 위치 반경 1km

    ArrayList<Marker> previous_marker = new ArrayList<>();
    String regionString;    // 처음 지역명 가져온 것
    String[] region;        // 지역 묶음 0: 대한민국, 1: 시 ...

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        location.requestSingleUpdate(this.getActivity(),
                new location.LocationCallback() {
                    @Override
                    public void onNewLocationAvailable(location.GPSCoordinates location) {

                        lat = location.latitude;
                        lng = location.longitude;

                        Log.d("lat: ", lat + "");
                        Log.d("lng: ", lng + "");

                        // default
                        if (lat == 0.0f && lng == 0.0f) {
                            lat = 37.54664f;
                            lng = 126.94988f;
                        }

                        //맵 띄우고


                        //동 코드
                        regionString = HomeActivity.getAddress(Fragment_map.this.getActivity(), lat, lng);
                        splitString();

                    }
                });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        RelativeLayout layout = (RelativeLayout)inflater.inflate(R.layout.fragment_map, container, false);

        return layout;
    }

    public void splitString()
    {
        region = new String[4];

        region = regionString.split("\\s");

        for (int i = 0; i < region.length; ++i)
        {
            Log.d("length: ", region[i]);
        }
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        map = googleMap;

        map.clear();

        LatLng My = new LatLng(lat, lng);

        String my_address = HomeActivity.getAddress(Fragment_map.this.getActivity(), lat, lng);

        //나의 위치
        MarkerOptions markerOptions_my = new MarkerOptions();
        markerOptions_my.position(My);
        markerOptions_my.title("나의 위치");
        markerOptions_my.icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.spot_me)));
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

    @Override
    public void onPlacesFailure(PlacesException e) {

    }

    @Override
    public void onPlacesStart() {

    }

    @Override
    public void onPlacesSuccess(List<Place> places) {

    }

    @Override
    public void onPlacesFinished() {

    }
}
