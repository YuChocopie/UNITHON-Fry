package com.example.km.fry;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashMap;

public class RegionAsyncTask extends AsyncTask<URL, Void, HashMap<String, Integer>> {

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected HashMap<String, Integer> doInBackground(URL... urls) {
        HashMap<String, Integer> codeList = new HashMap<String, Integer>();

        try {
            StringBuffer jsonHtml = new StringBuffer();
            InputStream uis = urls[0].openConnection().getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(uis, "UTF-8"));

            String line = null;
            while ((line = br.readLine()) != null) {
                jsonHtml.append(line + "\r\n");
            }
            br.close();
            uis.close();

            JSONArray jsonArray = new JSONArray(jsonHtml.toString());

            for (int i = 0; i < jsonArray.length(); ++i)
            {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                String value = jsonObject.getString("value");
                Integer code = jsonObject.getInt("code");

                //value가 지역명
                Log.d("value: ", value);
                //code가 지역코드
                Log.d("code: ", code + "");

                codeList.put(value, code);
            }

            return codeList;

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }
}
