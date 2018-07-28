package com.example.km.fry.Location;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ViewFlipper;

import com.example.km.fry.R;

import java.util.ArrayList;
import java.util.List;

public class CuponBox extends AppCompatActivity implements ViewFlipperAction.ViewFlipperCallback {

  ViewFlipper flipper;
  List<ImageView> indexes;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_cupon_box);

    flipper = (ViewFlipper)findViewById(R.id.flipper);
    ImageView index0 = (ImageView)findViewById(R.id.imgIndex0);
    ImageView index1 = (ImageView)findViewById(R.id.imgIndex1);
    ImageView index2 = (ImageView)findViewById(R.id.imgIndex2);

    indexes = new ArrayList<>();
    indexes.add(index0);
    indexes.add(index1);
    indexes.add(index2);

    LayoutInflater inflater = (LayoutInflater)getSystemService(LAYOUT_INFLATER_SERVICE);
    View view1 = inflater.inflate(R.layout.viewflipper1, flipper, false);
    View view2 = inflater.inflate(R.layout.viewflipper2, flipper, false);
    View view3 = inflater.inflate(R.layout.viewflipper3, flipper, false);
    flipper.addView(view1);
    flipper.addView(view2);
    flipper.addView(view3);

    flipper.setOnTouchListener(new ViewFlipperAction(this, flipper));
  }

  @Override
  public void onFlipperActionCallback(int position) {
    Log.d("ddd", ""+position);
    for(int i=0; i<indexes.size(); i++){
      ImageView index = indexes.get(i);
      if(i == position){
        index.setImageResource(R.drawable.angry);
      }
      else{
        index.setImageResource(R.drawable.uv);
      }
    }
  }
}