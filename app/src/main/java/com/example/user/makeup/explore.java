package com.example.user.makeup;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.Toast;

import android.widget.AdapterView.OnItemClickListener;
import android.util.Log;

public class explore extends Activity {
    private GestureDetectorCompat detector;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explore);
//setcontentview
        LinearLayout linerView =  findViewById(R.id.gesture);
        detector = new GestureDetectorCompat(this, new MyGestureListener());

        linerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                detector.onTouchEvent(event);
                return false;
            }
        });
    }// on create end
    // dispatch to solve first MotionEvent null?
    @Override
    public boolean dispatchTouchEvent(MotionEvent me) {
        this.detector.onTouchEvent(me);
        return super.dispatchTouchEvent(me);
    }
    //dispatch end
    //gesture on onTouchEvent
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        detector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }
    //gesture on onTouchEvent end
//gesture class start
    class MyGestureListener extends GestureDetector.SimpleOnGestureListener {

        private static final int SWIPE_THRESHOLD = 40;
        private static final int SWIPE_VELOCITY_THRESHOLD = 0;

        @Override
        public boolean onDown(MotionEvent event) {
            return true;
        }

        @Override
        public boolean onFling(MotionEvent event1, MotionEvent event2,
                               float velocityX, float velocityY) {
            float diffY, diffX,diffYDouble;
            float event2Y= event2.getY();
            float event2X= event2.getX();
            float event1Y= event1.getY();
            float event1X= event1.getX();
            diffY = event2Y- event1Y;
            diffX = event2X - event1X;
            diffYDouble= 2*diffY;
            if (Math.abs(diffX) > Math.abs(diffYDouble)) {
                if (Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                    if (diffX > 0) {
                        onSwipeRight();
                    } else {
                        onSwipeLeft();
                    }
                }
            }
            else{
            }
            return true;
        }
        private void onSwipeLeft() {
            startActivity(new Intent(explore.this, MainActivity.class));
            overridePendingTransition(R.animator.right_in,R.animator.left_out);
        }

        private void onSwipeRight() {
        //   startActivity(new Intent(explore.this, design.class));
            overridePendingTransition(R.animator.left_in,R.animator.right_out);
        }
    }
    //gesture class end
}


