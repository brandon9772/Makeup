package com.example.user.makeup;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.transition.Transition;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.view.View;
import android.widget.RelativeLayout;

public class profile extends AppCompatActivity {
    private GestureDetectorCompat detector;
    DBHandler dbHandler;
    TextView textView1r;
    TextView textView2r;

    //on create start
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ColorDrawable colorDrawable = new ColorDrawable( Color.TRANSPARENT );
        getWindow().setBackgroundDrawable( colorDrawable );
        setContentView(R.layout.activity_profile);
        RelativeLayout profileRelative = (RelativeLayout) findViewById(R.id.profileRelative);
        textView1r= (TextView)findViewById(R.id.textView1r);
        textView2r= (TextView)findViewById(R.id.textView2r);
        String test1r;
        String test2r;

        //Getting user profile from DataBase via DBHandler
        dbHandler = new DBHandler(this, DBHandler.TABLE_PROFILE,null);
        test2r = dbHandler.getAge(1);
        test1r = dbHandler.getName(1);
        textView1r.setText(test1r);
        textView2r.setText(test2r);
        //end of setting user profile
        detector = new GestureDetectorCompat(this, new MyGestureListener());
        profileRelative.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                detector.onTouchEvent(event);
                return false;
            }
        });

        final ImageButton editProfilebtn = (ImageButton) findViewById(R.id.editProfilebtn);
        editProfilebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(profile.this, editProfile.class));
            }
        });
        //edit profile btn on click end
        // Drop down menu for Clicking on profile pic
        final ImageButton profilePic = (ImageButton) findViewById(R.id.profilePicture);
        profilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupmenu = new PopupMenu(profile.this, profilePic);
                popupmenu.getMenuInflater().inflate(R.menu.popupprofilepic, popupmenu.getMenu());
                popupmenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if (item.getTitle().equals("image")) {
                            // load image from file
                        } else if (item.getTitle().equals("photo")) {
                            // take picture from carmera
                        }
                        return true;
                    }

                });
                popupmenu.show();
            }

        });
    } // oncreate end
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
    // MyGestureListener class
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
            float diffY, diffX, diffXDouble;
            float event2Y = event2.getY();
            float event2X = event2.getX();
            float event1Y = event1.getY();
            float event1X = event1.getX();
            diffY = event2Y - event1Y;
            diffX = event2X - event1X;
            diffXDouble = 2 * diffX;
            if (Math.abs(diffY) > Math.abs(diffXDouble)) {
                if (Math.abs(diffY) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                    if (diffY < 0) {
                        onSwipeUp();
                    }
                }
            } else {
            }
            return true;
        }
        // gesture end
        //edit profile btn
    }
    private void onSwipeUp()
    {

        startActivity(new Intent(profile.this, MainActivity.class));

        overridePendingTransition(R.animator.up_in,R.animator.down_out);
    }
}











