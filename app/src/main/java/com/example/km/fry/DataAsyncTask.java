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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

// XML 파싱
public class DataAsyncTask extends AsyncTask<String,Void,ArrayList<ItemInfo>> {
    public String dong_code = "";
    public String str_degree = null;
    public String str_humidity = null;
    public String str_hour = null;
    public String str_unhappy = null;
    public String str_uv = null;
    public String str_poison = null;
    ArrayList<ItemInfo> list;
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected ArrayList<ItemInfo> doInBackground(String... params) {
        dong_code = params[0];
        list = new ArrayList<ItemInfo>();

        try{
            // XML 데이터를 읽어옴

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
            boolean check_unhappy = false;
            boolean check_uv = false;
            boolean check_poison = false;

            boolean start = false;
            int cnt2=0;

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
                            cnt2++;
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

                        if(start && cnt2<15) {
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
                        if(cnt2<15) {
                            if (tag.compareTo("data") == 0 && start) {

                                String str_trans_hour = "";
                                int hour = Integer.parseInt(str_hour);


                                //24:오전12시(새벽)
                                if (hour == 24 || hour < 12) {
                                    str_trans_hour = "오전 ";
                                } else {
                                    str_trans_hour = "오후 ";
                                }

                                if (hour > 12) {
                                    hour = hour - 12;
                                }

                                str_trans_hour = str_trans_hour + String.valueOf(hour);

                                list.add(new ItemInfo(str_degree.substring(0, 2) + "˚", str_humidity + "%", str_trans_hour, "", "", ""));

                                start = false;
                            } else if (tag.compareTo("temp") == 0 && start) {
                                check_degree = false;
                            } else if (tag.compareTo("reh") == 0 && start) {
                                check_humidity = false;
                            } else if (tag.compareTo("hour") == 0 && start) {
                                check_hour = false;
                            }
                        }
                        break;
                }
                parserEvent = parser.next(); // 다음 태그로 이동하기
            }while(parserEvent != XmlPullParser.END_DOCUMENT);

            // 요기서부터는 불쾌지수, uv, 식중독
            parseUnhappy();
            parseUv();
            parsePoison();
            /*
            str_unhapy = parseData("http://newsky2.kma.go.kr/iros/RetrieveLifeIndexService3/getDsplsLifeList?serviceKey=EDAdMbBt6oyqU%2Be1GHKvAlz2aKPTixIEFnbXt4YkpD1MX0xtNbbFYwx81xYdUbR0CzYPDtzT%2FChX0sz4%2BLZjzA%3D%3D&areaNo="+dong_code+"&time="+getTime,
                    "h3");*/





        } catch(Exception e) {
            //Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
        }
        return list;
    }

    // 불쾌지수
    public void parseUnhappy() {
        String ret="";
        try {
            long now = System.currentTimeMillis();
            Date date = new Date(now);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhh");
            String getTime = sdf.format(date);
            Log.d("time: ", getTime);

            String str_url = ("http://newsky2.kma.go.kr/iros/RetrieveLifeIndexService3/getDsplsLifeList?serviceKey=EDAdMbBt6oyqU%2Be1GHKvAlz2aKPTixIEFnbXt4YkpD1MX0xtNbbFYwx81xYdUbR0CzYPDtzT%2FChX0sz4%2BLZjzA%3D%3D&areaNo="+dong_code+"&time="+getTime);
            URL url = new URL(str_url);
            InputStream in = url.openStream();
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = factory.newPullParser();
            parser.setInput(in, "utf-8");
            int parserEvent = parser.getEventType();

            boolean start = false;
            String tag=null;
            int cnt=0;
            do{
                switch(parserEvent)
                {

                    case XmlPullParser.START_TAG:
                        tag = parser.getName();
                        break;
                    case XmlPullParser.TEXT:
                        if(start==true && cnt < 15 && list.get(cnt)!=null) {
                            list.get(cnt).setUnhappy(parser.getText());
                            Log.d("unhappy: ", parser.getText());
                            cnt++;
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        tag = parser.getName();
                        if(tag.compareTo("date")==0) {
                            start = true;
                        }
                        break;
                }
                parserEvent = parser.next(); // 다음 태그로 이동하기
            }while(parserEvent != XmlPullParser.END_DOCUMENT);

        }catch(Exception e) {
            //Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
        }
    }


    //uv
    public void parseUv() {
        String ret="";
        try {
            long now = System.currentTimeMillis();
            Date date = new Date(now);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhh");
            String getTime = sdf.format(date);
            Log.d("time: ", getTime);

            String str_url = ("http://newsky2.kma.go.kr/iros/RetrieveLifeIndexService3/getUltrvLifeList?serviceKey=EDAdMbBt6oyqU%2Be1GHKvAlz2aKPTixIEFnbXt4YkpD1MX0xtNbbFYwx81xYdUbR0CzYPDtzT%2FChX0sz4%2BLZjzA%3D%3D&areaNo="+dong_code+"&time="+getTime);
            URL url = new URL(str_url);
            InputStream in = url.openStream();
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = factory.newPullParser();
            parser.setInput(in, "utf-8");
            int parserEvent = parser.getEventType();

            boolean start = false;
            String tag=null;
            int cnt=0;
            do{
                switch(parserEvent)
                {

                    case XmlPullParser.START_TAG:
                        tag = parser.getName();
                        break;
                    case XmlPullParser.TEXT:
                        if(start==true && list.get(cnt)!=null) {
                            for(int i=0; i<6; i++) {
                                list.get(i+cnt).setUv(parser.getText());
                            }
                            cnt+=6;

                            Log.d("uv: ", parser.getText());
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        tag = parser.getName();
                        if(tag.compareTo("date")==0) {
                            start = true;
                        }
                        break;
                }
                parserEvent = parser.next(); // 다음 태그로 이동하기
            }while(parserEvent != XmlPullParser.END_DOCUMENT);

        }catch(Exception e) {
            //Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    //poison
    public void parsePoison() {
        String ret="";
        try {
            long now = System.currentTimeMillis();
            Date date = new Date(now);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhh");
            String getTime = sdf.format(date);
            Log.d("time: ", getTime);

            String str_url = ("http://newsky2.kma.go.kr/iros/RetrieveLifeIndexService3/getFsnLifeList?serviceKey=EDAdMbBt6oyqU%2Be1GHKvAlz2aKPTixIEFnbXt4YkpD1MX0xtNbbFYwx81xYdUbR0CzYPDtzT%2FChX0sz4%2BLZjzA%3D%3D&areaNo="+dong_code+"&time="+getTime);
            URL url = new URL(str_url);
            InputStream in = url.openStream();
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = factory.newPullParser();
            parser.setInput(in, "utf-8");
            int parserEvent = parser.getEventType();

            boolean start = false;
            String tag=null;
            int cnt=0;
            do{
                switch(parserEvent)
                {

                    case XmlPullParser.START_TAG:
                        tag = parser.getName();
                        break;
                    case XmlPullParser.TEXT:
                        if(start==true && list.get(cnt)!=null) {
                            for(int i=0; i<6; i++) {
                                list.get(i+cnt).setPoison(parser.getText());
                            }
                            cnt+=6;

                            Log.d("uv: ", parser.getText());
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        tag = parser.getName();
                        if(tag.compareTo("date")==0) {
                            start = true;
                        }
                        break;
                }
                parserEvent = parser.next(); // 다음 태그로 이동하기
            }while(parserEvent != XmlPullParser.END_DOCUMENT);

        }catch(Exception e) {
            //Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
        }
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
