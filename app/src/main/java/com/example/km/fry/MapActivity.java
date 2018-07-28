package com.example.km.fry;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.km.fry.Location.location;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

/**
 * Created by xowns on 2018-07-29.
 */

public class MapActivity extends AppCompatActivity{

    float lat;
    float lng;
    String regionString;    // 처음 지역명 가져온 것
    String[] region;        // 지역 묶음 0: 대한민국, 1: 시 ...
    String dongCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_map);

        location.requestSingleUpdate(this.getApplicationContext(),
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

                        regionString = HomeActivity.getAddress(MapActivity.this, lat, lng);
                        splitString();

                    }
                });

        HashMap<String, Integer> regionMap;

        String[] urls = new String[4];
        String jsonText = ".json.txt";

        // 지역 URL
        urls[1] = "http://www.kma.go.kr/DFSROOT/POINT/DATA/top.json.txt";
        urls[2] = "http://www.kma.go.kr/DFSROOT/POINT/DATA/mdl.";
        urls[3] = "http://www.kma.go.kr/DFSROOT/POINT/DATA/leaf.";

        try {

            // 현재 지역의 지역 코드를 검색
            for (int i = 1; i <= urls.length; ++i) {
                URL url = new URL(urls[i]);

                RegionAsyncTask regionTask = new RegionAsyncTask();

                regionTask.execute(url);

                regionMap = regionTask.get();

                if (i == 3) {
                    // 동코드: region[3]
                    Log.d("region code: ", regionMap.get(region[i]) + "");
                    dongCode = String.valueOf(regionMap.get(region[i]));
                    break;
                }


                urls[i + 1] += String.valueOf(regionMap.get(region[i]));
                urls[i + 1] += jsonText;

            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
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
}
