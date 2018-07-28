package com.example.km.fry;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.km.fry.Location.location;

public class MainActivity extends AppCompatActivity {

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

                        Intent intent = new Intent(getApplication(), HomeActivity.class);

                        intent.putExtra("MyLat", lat);
                        intent.putExtra("MyLng", lng);
                        startActivity(intent);
                    }
                });
    }
}
