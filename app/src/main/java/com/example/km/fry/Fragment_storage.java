package com.example.km.fry;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

/**
 * Created by xowns on 2018-07-29.
 */

public class Fragment_storage extends android.support.v4.app.Fragment {

    public Fragment_storage()
    {
// required
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        RelativeLayout layout = (RelativeLayout)inflater.inflate(R.layout.fragment_storage, container, false);

        return layout;
    }
}
