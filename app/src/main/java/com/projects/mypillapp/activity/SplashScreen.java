package com.projects.mypillapp.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.projects.mypillapp.MainActivity;
import com.projects.mypillapp.Model.DBHelper;
import com.projects.mypillapp.R;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class SplashScreen extends AppCompatActivity {
    Handler handler;
    Runnable runnable;
    long delay_time;
    long time = 2000L;

    SharedPreferences sp;
    SharedPreferences.Editor editor;

    final String PREF_NAME = "TimeSetting";
    final String SET_TIME ="setTime";

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        new DBHelper(this);
        sp = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = sp.edit();
        handler = new Handler();
        runnable = new Runnable() {
            public void run() {
                Boolean timeSetting = sp.getBoolean(SET_TIME,false);
                if (timeSetting) {
                    Intent intent = new Intent(SplashScreen.this, MainActivity.class);
                    //intent.putExtra("showpill","1");
                    startActivity(intent);
                    overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
                    finish();
                }else {
                    Intent intent = new Intent(SplashScreen.this, SettingTime.class);
                    startActivity(intent);
                    finish();
                }
            }
        };

    }

    public void onResume() {
        super.onResume();
        delay_time = time;
        handler.postDelayed(runnable, delay_time);
        time = System.currentTimeMillis();
    }

    public void onPause() {
        super.onPause();
        handler.removeCallbacks(runnable);
        time = delay_time - (System.currentTimeMillis() - time);
    }
    @Override
    protected void attachBaseContext(Context newBase){
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}
