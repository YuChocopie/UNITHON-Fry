package com.example.km.fry;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class TemperatureDetailActivity extends AppCompatActivity {
    ArrayList<ItemInfo> list = new ArrayList<ItemInfo>();
    itemAdapter adapter;
    ListView listView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temperature_detail);
        listView = (ListView) findViewById(R.id.listViewTemp);
        Intent intent = getIntent();

        String region = intent.getExtras().getString("region");
        int regionCode = intent.getExtras().getInt("regionCode");

        try {
            Log.d("click", "click");
            DataAsyncTask asyncTask = new DataAsyncTask();
            asyncTask.execute(String.valueOf(regionCode));
            list = asyncTask.get();
            adapter = new itemAdapter(this, list);
            adapter.notifyDataSetChanged();
            listView.setAdapter(adapter);

        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }



    }
}
