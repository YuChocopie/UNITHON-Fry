package com.example.km.fry;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by xowns on 2018-07-29.
 */

public class TotalActivity extends AppCompatActivity {


    ViewPager pager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_total);

        pager = (ViewPager)findViewById(R.id.pager);
        Button btn_first = (Button)findViewById(R.id.btn_first);
        Button btn_second = (Button)findViewById(R.id.btn_second);
        Button btn_third = (Button)findViewById(R.id.btn_third);

        pager.setAdapter(new pagerAdapter(getSupportFragmentManager()));
        pager.setCurrentItem(0);

        View.OnClickListener movePageListener = new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                int tag = (int)view.getTag();
                pager.setCurrentItem(tag);
            }
        };

        btn_first.setOnClickListener(movePageListener);
        btn_first.setTag(0);
        btn_second.setOnClickListener(movePageListener);
        btn_second.setTag(1);
        btn_third.setOnClickListener(movePageListener);
        btn_third.setTag(2);
    }

    private class pagerAdapter extends FragmentStatePagerAdapter
    {
        public pagerAdapter(FragmentManager fm )
        {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch(position)
            {
                case 0:
                    Fragment_home fragment1 = new Fragment_home();
                    return fragment1;
                case 1:
                    Fragment_map fragment2 = new Fragment_map();
                    return fragment2;
                case 2:
                    Fragment_storage fragment3 = new Fragment_storage();
                    return fragment3;
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            // total page count
            return 3;
        }
    }
}
