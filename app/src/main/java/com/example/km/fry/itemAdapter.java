package com.example.km.fry;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class itemAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private ArrayList<ItemInfo> arrayList;
    private Context context;

    public itemAdapter(Context context, ArrayList<ItemInfo> arrayList) {
        this.inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.arrayList = arrayList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return this.arrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return arrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        if(view == null) {

            //view = inflater.inflate(R.layout.list_item, viewGroup, false);
           view = inflater.inflate(R.layout.item_temperature_details, viewGroup, false);
        }


        TextView tv_degree = (TextView) view.findViewById(R.id.tv_degree);
        TextView tv_humidity = (TextView) view.findViewById(R.id.tv_humidity);
        TextView tv_hour = (TextView) view.findViewById(R.id.tv_hour);
        TextView tv_unhappy = (TextView) view.findViewById(R.id.tv_unhappy);
        TextView tv_uv = (TextView) view.findViewById(R.id.tv_uv);
        TextView tv_poison = (TextView) view.findViewById(R.id.tv_poison);

        // Data Set에서 position에 위치한 데이터 참조 획득
        ItemInfo itemInfo = arrayList.get(i);

        //아이템 내 각 위젝에 데이터 반영
        //icon.setImageDrawable(animal.getIcon());

        tv_degree.setText(itemInfo.getDegree());
        tv_humidity.setText(itemInfo.getHumidity());
        tv_hour.setText(itemInfo.getHour());
        tv_unhappy.setText(itemInfo.getUnhappy());
        tv_uv.setText(itemInfo.getUv());
        tv_poison.setText(itemInfo.getPoison());

        return view;
    }
}
