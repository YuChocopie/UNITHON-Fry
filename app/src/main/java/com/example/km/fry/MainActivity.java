package com.example.km.fry;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.km.fry.Location.location;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    String regionString;    // 처음 지역명 가져온 것
    String[] region;        // 지역 묶음 0: 대한민국, 1: 시 ...

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        location.requestSingleUpdate(this.getApplicationContext(),
                new location.LocationCallback() {
                    @Override
                    public void onNewLocationAvailable(location.GPSCoordinates location) {

                        float lat = location.latitude;
                        float lng = location.longitude;

                        regionString = HomeActivity.getAddress(MainActivity.this, lat, lng);
                        splitString();

                        Intent intent = new Intent(getApplication(), HomeActivity.class);

                        intent.putExtra("MyLat", lat);
                        intent.putExtra("MyLng", lng);
                        startActivity(intent);
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
            for (int i = 1; i <= urls.length; ++i)
            {
                URL url = new URL(urls[i]);

                RegionAsyncTask regionTask = new RegionAsyncTask();

                regionTask.execute(url);

                regionMap = regionTask.get();

                if (i == 3)
                {
                    // 동코드: region[3]
                    Log.d("region code: ", regionMap.get(region[i]) + "");
                    break;
                }

                urls[i + 1] += String.valueOf(regionMap.get(region[i]));
                urls[i + 1] += jsonText;
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
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
