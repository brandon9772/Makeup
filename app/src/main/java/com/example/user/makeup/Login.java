package com.example.user.makeup;

import android.app.Activity;
import android.content.Intent;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.os.Handler;
import android.os.Message;
import android.view.View.OnClickListener;

public class Login extends AppCompatActivity {
DBHandler dbHandler;
    Handler handler= new Handler(){
        @Override
        public void handleMessage(Message msg) {
            Log.d("thread", "thread end");
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        dbHandler = new DBHandler(this, DBHandler.TABLE_PROFILE,null);
        final ImageButton login = (ImageButton) findViewById(R.id.imageButton);
        login.setOnClickListener(new View.OnClickListener(){
public void onClick (View v)
{
//runnable code to run in thread, DON'T update UI in Thread
    Runnable r = new Runnable() {
        @Override
        public void run() {
            Long furturetime = System.currentTimeMillis()+10000;
            while (System.currentTimeMillis()<furturetime)
            {
                synchronized (this)
                {
                    try
                    {
                        wait(furturetime - System.currentTimeMillis());
                    }
                    catch (Exception e)
                    {

                    }
                }
            }
            handler.sendEmptyMessage(0);
        }
    }; //runnable end
//start thread
    Thread thread = new Thread(r);
    thread.start();
//thread end
    //create new empty user if non-exist
if (dbHandler.countRow()==0)
{
    dbHandler.newUser();
}
    startActivity(new Intent(Login.this, MainActivity.class));
}
        });
    }
}
