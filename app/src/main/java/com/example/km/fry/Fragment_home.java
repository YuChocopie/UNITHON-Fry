package com.example.km.fry;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

/**
 * Created by xowns on 2018-07-29.
 */

public class Fragment_home extends android.support.v4.app.Fragment {

    public Fragment_home()
    {
// required
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        RelativeLayout layout = (RelativeLayout)inflater.inflate(R.layout.fragment_home, container, false);

        return layout;
    }
}
