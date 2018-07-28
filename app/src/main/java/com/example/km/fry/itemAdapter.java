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
            //  LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.list_item, viewGroup, false);
        }


        TextView textView = (TextView) view.findViewById(R.id.textView);
        TextView textView2 = (TextView) view.findViewById(R.id.textView2);
        TextView textView3 = (TextView) view.findViewById(R.id.textView3);

        // Data Set에서 position에 위치한 데이터 참조 획득
        ItemInfo itemInfo = arrayList.get(i);

        //아이템 내 각 위젝에 데이터 반영
        //icon.setImageDrawable(animal.getIcon());

        textView.setText(itemInfo.getDegree());
        textView2.setText(itemInfo.getHumidity());
        textView3.setText(itemInfo.getHour());

        return view;
    }
}
