package com.example.km.fry.Location;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ViewFlipper;

import com.example.km.fry.R;

public class ViewFlipperAction implements View.OnTouchListener {

  Context context;

  int countIndexes;
  int currentIndex;
  float downX;
  float upX;
  ViewFlipper flipper;

  ViewFlipperCallback indexCallback;

  public static interface ViewFlipperCallback{
    public void onFlipperActionCallback(int position);
  }

  public ViewFlipperAction(Context context, ViewFlipper flipper){
    this.context = context;
    this.flipper = flipper;

    if(context instanceof ViewFlipperCallback){
      indexCallback = (ViewFlipperCallback)context;
    }

    currentIndex = 0;
    downX = 0;
    upX = 0;
    countIndexes = flipper.getChildCount();

    indexCallback.onFlipperActionCallback(currentIndex);
  }

  @Override
  public boolean onTouch(View v, MotionEvent event) {
    if(event.getAction()==MotionEvent.ACTION_DOWN){
      downX = event.getX();
    }
    else if(event.getAction()==MotionEvent.ACTION_UP){
      upX = event.getX();
      if(upX < downX){
        flipper.setInAnimation(AnimationUtils.loadAnimation(context, R.anim.push_left_in));
        flipper.setOutAnimation(AnimationUtils.loadAnimation(context, R.anim.push_left_out));
        if(currentIndex < (countIndexes-1)){
          flipper.showNext();
          currentIndex++;
          indexCallback.onFlipperActionCallback(currentIndex);
        }
      }
      else if(upX > downX){
        flipper.setInAnimation(AnimationUtils.loadAnimation(context, R.anim.push_right_in));
        flipper.setOutAnimation(AnimationUtils.loadAnimation(context, R.anim.push_right_out));

        if(currentIndex > 0){
          flipper.showPrevious();

          currentIndex--;
          indexCallback.onFlipperActionCallback(currentIndex);
        }
      }
    }

    return true;
  }
}