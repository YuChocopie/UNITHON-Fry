package com.example.km.fry;

import android.content.Context;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.km.fry.Location.location;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {
    private Context context = this;
    private Button btn;
    ArrayList<ItemInfo> list = new ArrayList<ItemInfo>();
    itemAdapter adapter;
    ListView listView;
    String regionString;    // 처음 지역명 가져온 것
    String[] region;        // 지역 묶음 0: 대한민국, 1: 시 ...
    String dongCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn = (Button) findViewById(R.id.button);
        listView = (ListView) findViewById(R.id.listView);
        btn.setOnClickListener(listener);

        region = new String[4];
        for(int i=0; i<4; i++) {
            region[i]="";
        }

        // 권한이 허용되어있지 않은 경우 permission 요청
        if (ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // 위치 정보 접근 요청
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 0);
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 0);
        }
        else {
            main();
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

    public void main()
    {
        location.requestSingleUpdate(this.getApplicationContext(),
                new location.LocationCallback() {
                    @Override
                    public void onNewLocationAvailable(location.GPSCoordinates location) {

                        float lat = location.latitude;
                        float lng = location.longitude;

                        Log.d("lat: ", lat + "");
                        Log.d("lng: ", lng + "");

                        // default
                        if (lat == 0.0f && lng == 0.0f)
                        {
                            lat = 37.54664f;
                            lng = 126.94988f;
                        }

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
                    dongCode = String.valueOf(regionMap.get(region[i]));
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

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch(v.getId()) {
                case R.id.button:
                    try {
                        Log.d("click", "click");
                        DataAsyncTask asyncTask = new DataAsyncTask();
                        asyncTask.execute(dongCode);
                        list = asyncTask.get();

                        adapter = new itemAdapter(context, list);
                        adapter.notifyDataSetChanged();
                        listView.setAdapter(adapter);
                        //listView.setChoiceMode(ListView.CHOICE_MODE_NONE);

                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }




                    break;

            }

        }

    };

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        if (requestCode == 0)
        {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                main();
            }
            else{
                Log.d("check: ", "check permission");
            }
        }
    }
}