package com.example.user.makeup;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.Toolbar;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.text.Editable;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.view.MotionEvent;
import android.util.Log;

import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity   {
    private static final String TAG = "MyActivity";
    private GestureDetectorCompat detector;
    private int numItem=20;
    private final ArrayList<String[]> btnIDArray = new ArrayList<String[]>();
    private final ArrayList<Integer> btnIDArrayTwo = new ArrayList<Integer>();
    ScrollView scrollView;
    GridLayout grib;
    EditText editSearch;
    DisplayMetrics displayMetrics = new DisplayMetrics();
    private  final TextWatcher  tw = new TextWatcher() {
        int length;
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            Log.d("search","beforeTextChanged start");
            length=charSequence.toString().length();
        }
        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                searchItem(charSequence.toString());
                deleteButton();

        }
        @Override
        public void afterTextChanged(Editable editable) {
            if (editable.toString().length()<length)
            {
                searchItem(editable.toString());
                emptyButton();
                makeButton();
                deleteButton();
            }
        }
    };
    private  final ViewTreeObserver.OnScrollChangedListener scrollReachBotton = new ViewTreeObserver.OnScrollChangedListener()
    {
        @Override
        public void onScrollChanged() {
            int scrollY = scrollView.getScrollY();
            int bottom = scrollView.getChildAt(0).getHeight();
            int diff= bottom-scrollY-scrollView.getHeight();
            if(diff<0)
            {
                Log.d("scroll","scoll reach bottom");
            }

        }
    };
    private void emptyButton()
    {
        grib.removeAllViews();
    }
    private void deleteButton() {
        LinearLayout deleteLinear;
        for (int item:btnIDArrayTwo)
        {
            deleteLinear = (LinearLayout) findViewById(item);
            grib.removeView(deleteLinear);
        }
    }
    //end of defining variable and some function
//***********************************************************************
    //
    //on create start here
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ColorDrawable colorDrawable = new ColorDrawable( Color.TRANSPARENT );
        getWindow().setBackgroundDrawable( colorDrawable );
        setContentView(R.layout.activity_main); //setcontentview End
        Log.v(TAG, "on create" );
        grib = (GridLayout) findViewById(R.id.grib);
        editSearch = (EditText) findViewById(R.id.editSearchXml);
        scrollView= (ScrollView) findViewById(R.id.scroll);
        detector = new GestureDetectorCompat(this, new MyGestureListener());
        grib.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                detector.onTouchEvent(event);
                return false;
            }
        });
        downloadBtn();//place holder for downloading from server
        makeButton();
        // search on click start
        scrollView.getViewTreeObserver().addOnScrollChangedListener(scrollReachBotton);
        editSearch.addTextChangedListener(tw); // search on click end, tw define onclick
         }
         //************************************************************
         // on create end
    //search and add id of item that DON'T match the result to btnIDArrayTwo
    private void searchItem(String s)
    {   //set search
        btnIDArrayTwo.clear();
            for (String[] item: btnIDArray)
            {
                if (!item[0].contains(s))
                {
                    Log.d("search","searchitem if start");
                    int iddd =Integer.parseInt(item[1]);
                    btnIDArrayTwo.add(iddd);
                }
            }
    }
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
            Log.d("swipe", "onFling1");
            float diffY, diffX,diffYDouble,diffXDouble;
            float event2Y= event2.getY();
            float event2X= event2.getX();
            float event1Y= event1.getY();
            float event1X= event1.getX();
            diffY = event2Y- event1Y;
            diffX = event2X - event1X;
            diffYDouble= 2*diffY;
            diffXDouble=2*diffX;
            if (Math.abs(diffX) > Math.abs(diffYDouble)) {
                if (Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                    if (diffX > 0) {
                        onSwipeRight();
                    } else {
                        onSwipeLeft();
                    }
                }
            }
            else if (Math.abs(diffY) > Math.abs(diffXDouble)) {
                if (diffY > 0) {
                    onSwipeDown();
                }
            }
            return true;
        }
        private void onSwipeLeft() {
            Log.d("swipe", "onSwipeLeft1");
            startActivity(new Intent(MainActivity.this, design.class));
            Log.d("editProfile", "Mainactivity: to profile ");
            overridePendingTransition(R.animator.right_in,R.animator.left_out);
            Log.d("swipe", "onSwipeLeft2");

        }

        private void onSwipeRight() {
            startActivity(new Intent(MainActivity.this, explore.class));
            overridePendingTransition(R.animator.left_in,R.animator.right_out);
        }
    }//end of gesture

    private void onSwipeDown() {
        startActivity(new Intent(MainActivity.this,profile.class));
        overridePendingTransition(R.animator.down_in,R.animator.up_out);
    }

    private void makeButton()//only create button whose id are NOT included in btnIDArrayTwo
    {
        for (int i=0;i<numItem;i++)
        {
                if (btnIDArrayTwo.contains(i))
                {
                }
                else
                    {
                final Button btn = new Button(this);
                        View parent = (View) findViewById(R.id.gesture);
                        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);;
                        int width =  (displayMetrics.widthPixels)/2;
                        int height = width*10/8;
                        LinearLayoutCompat.LayoutParams lp = new LinearLayoutCompat.LayoutParams(LinearLayoutCompat.LayoutParams.MATCH_PARENT,
                                                            LinearLayoutCompat.LayoutParams.MATCH_PARENT);btn.setLayoutParams(lp);
                btn.setId(i);
                btn.setBackgroundResource(R.drawable.face1);
                setButton(btn, i);// set btn
                         final int idd=i;
                btn.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View view)
                    {
                        setButtonOnClick(btn, idd);
                    }
                });//end on click
                        //set button size by linear layout, don't set size on button on it's own
                        final LinearLayout cell = new LinearLayout(this);
                        cell.setId(i);
                        cell.setPadding(30,30,30,30);
                        cell.setLayoutParams(new LinearLayout.LayoutParams(width,height,1.0f));
                        grib.addView(cell);
                cell.addView(btn);
                     }
            }
        }

    private void downloadBtn() {
        //code for download button
    }
    public void setButton(Button b, int i )
    {
        //edit button properties, SET SIZE in makeButton() via linearLayout wrapping it
        String btntxt = "button "+i;
        String idToString = Integer.toString(i);
        String[] btntxtArray= {btntxt,idToString};
        btnIDArray.add(btntxtArray);
        b.setText(btntxt);
        b.setTextSize(20);
    }
    public void setButtonOnClick(Button b, int i )
    {
        //on click for button
        Log.d("abc","asdasd");
    }
}
