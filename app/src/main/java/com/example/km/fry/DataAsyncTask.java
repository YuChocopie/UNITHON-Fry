package com.example.km.fry;

import android.Manifest;
import android.content.pm.PackageManager;
import android.nfc.Tag;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Adapter;
import android.widget.TextView;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

// XML 파싱
public class DataAsyncTask extends AsyncTask<String,Void,ArrayList<ItemInfo>> {
    public String dong_code = "";
    public String str_degree = null;
    public String str_humidity = null;
    public String str_hour = null;
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected ArrayList<ItemInfo> doInBackground(String... params) {
        dong_code = params[0];
        ArrayList<ItemInfo> list = new ArrayList<ItemInfo>();

        try{
            // XML 데이터를 읽어옴

            //URL url = new URL("http://www.kma.go.kr/wid/queryDFSRSS.jsp?zone=1144058500");
            String str_url = "http://www.kma.go.kr/wid/queryDFSRSS.jsp?zone="+dong_code;
            Log.d("url: ", str_url);
            URL url = new URL(str_url);
            InputStream in = url.openStream();

            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = factory.newPullParser();
            parser.setInput(in, "utf-8");

            int parserEvent = parser.getEventType();
            String tag;

            boolean check_degree = false;
            boolean check_humidity = false;
            boolean check_hour = false;
            boolean start = false;

            do{
                switch(parserEvent)
                {
                    case XmlPullParser.START_TAG:
                        tag = parser.getName();

                        if(tag.compareTo("data")==0) {

                            start = true;
                        }

                        else if(tag.compareTo("temp")==0) {
                            check_degree = true;
                        }
                        else if(tag.compareTo("reh")==0) {
                            check_humidity = true;
                        }
                        else if(tag.compareTo("hour")==0) {
                            check_hour = true;
                        }

                        break;
                    case XmlPullParser.TEXT:
                        tag = parser.getName();

                        if(start) {
                            if(check_degree) {
                                str_degree = parser.getText();
                            }
                            else if(check_humidity) {
                                str_humidity = parser.getText();
                                Log.d("list add: ", parser.getText());
                            }
                            else if(check_hour) {
                                str_hour = parser.getText();
                                Log.d("list add: ", parser.getText());
                            }
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        tag = parser.getName();
                        if(tag.compareTo("data") == 0 && start) {
                            list.add(new ItemInfo(str_degree, str_humidity, str_hour));
                            start = false;
                        }
                        else if(tag.compareTo("temp") == 0 && start) {
                            check_degree = false;
                        }
                        else if(tag.compareTo("reh") == 0 && start) {
                            check_humidity = false;
                        }
                        else if(tag.compareTo("hour") == 0 && start) {
                            check_hour  = false;
                        }

                        break;
                }
                parserEvent = parser.next(); // 다음 태그로 이동하기
            }while(parserEvent != XmlPullParser.END_DOCUMENT);


        } catch(Exception e) {
            //Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
        }
        return list;
    }
/*
    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }*/

    /*
    @Override
    protected void onPostExecute(String s) {
        //textView.setText("기온:"+str_degree);
        //textView2.setText("습도:"+str_humidity);

        super.onPostExecute(s);
    }*/
}
