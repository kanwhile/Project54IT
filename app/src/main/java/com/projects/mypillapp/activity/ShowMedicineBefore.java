package com.projects.mypillapp.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.PowerManager;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.projects.mypillapp.Alarm.MyReceiverAfter;
import com.projects.mypillapp.Alarm.MyReceiverBefore;
import com.projects.mypillapp.Alarm.MyServiceBefore;
import com.projects.mypillapp.Model.DBHelper;
import com.projects.mypillapp.Model.PillModel;
import com.projects.mypillapp.Model.ShowPillAdapter;
import com.projects.mypillapp.R;

import java.util.Calendar;
import java.util.Date;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class ShowMedicineBefore extends AppCompatActivity {

    public final String TAG = this.getClass().getSimpleName();

    private static final int WAKELOCK_TIMEOUT = 3 * 1000;



    Handler handler,handlerplay;
    Runnable runnable;
    long delay_time;
    long time = 10*60*1000;

    private PowerManager.WakeLock mWakeLock;
    private MediaPlayer mPlayer;
    Vibrator v;

    DBHelper dbHelper = new DBHelper(this);
    String eat = "ก่อนอาหาร";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_medicine);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //กำหนดการสั่น
        v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        long[] pattern = { 0, 200, 500 , 200 };
        v.vibrate(pattern, 0);
        //Play alarm tone
        String tone ="content://media/internal/audio/media/135";
        mPlayer = new MediaPlayer();
        try {
            if (tone != null && !tone.equals("")) {
                Uri toneUri = Uri.parse(tone);
                if (toneUri != null) {
                    mPlayer.setDataSource(this, toneUri);
                    mPlayer.setAudioStreamType(AudioManager.STREAM_ALARM);
                    mPlayer.setLooping(true);
                    mPlayer.prepare();
                    mPlayer.start();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //เปิดหน้าจอลง
        handler = new Handler();
        runnable = new Runnable() {
            public void run() {
                finish();
                v.cancel();
                mPlayer.stop();
            }
        };


        Runnable releaseWakelock = new Runnable() {

            @Override
            public void run() {
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);

                if (mWakeLock != null && mWakeLock.isHeld()) {
                    mWakeLock.release();
                }
            }
        };
        new Handler().postDelayed(releaseWakelock, WAKELOCK_TIMEOUT);

        //หยุดเสียงเพลง
        handlerplay = new Handler();
        handlerplay.postDelayed(new Runnable() {
            @Override
            public void run() {
                v.cancel();
                mPlayer.stop();
            }
        },5000);
        final RecyclerView recyclerView =(RecyclerView)findViewById(R.id.recyle_view_showpill);
        final String id = getIntent().getStringExtra(MyReceiverBefore.ID);
        switch (Integer.parseInt(id)) {
            case 1:
                RecyclerViewBreak(recyclerView);
                getSupportActionBar().setTitle("ยาที่ต้องรับประทานตอนเช้า");
                break;
            case 2:
                RecyclerViewLunch(recyclerView);
                getSupportActionBar().setTitle("ที่ต้องรับประทานตอนกลางวัน");
                break;
            case 3:
                RecyclerViewDinner(recyclerView);
                getSupportActionBar().setTitle("ยาที่ต้องรับประทานตอนเย็น");
                break;
            case 4:
                RecyclerViewNight(recyclerView);
                getSupportActionBar().setTitle("ยาที่ต้องรับประทานก่อนนอน");
                break;
        }
    }


    private void RecyclerViewBreak(RecyclerView recyclerView) {
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ShowPillAdapter mAdapter = new ShowPillAdapter(dbHelper.getPillBreakfast(eat),this);
        recyclerView.setAdapter(mAdapter);

    }
    private void RecyclerViewLunch(RecyclerView recyclerView) {
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ShowPillAdapter mAdapter = new ShowPillAdapter(dbHelper.getPillLUNCH(eat),this);
        recyclerView.setAdapter(mAdapter);

    }
    private void RecyclerViewDinner(RecyclerView recyclerView) {
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ShowPillAdapter mAdapter = new ShowPillAdapter(dbHelper.getPillDINNER(eat),this);
        recyclerView.setAdapter(mAdapter);

    }
    private void RecyclerViewNight(RecyclerView recyclerView) {
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ShowPillAdapter mAdapter = new ShowPillAdapter(dbHelper.getPillNIGHT(null),this);
        recyclerView.setAdapter(mAdapter);

    }
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
    @Override
    public void onBackPressed() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(ShowMedicineBefore.this);

        alertDialog.setTitle("Confirm Exit...");
        alertDialog.setMessage("คุณต้องการออกจากโปรแกรมหรือไม่ ?");
        alertDialog.setIcon(android.R.drawable.ic_dialog_info);

        alertDialog.setPositiveButton("ใช่",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //คลิกใช่ ออกจากโปรแกรม
                        finish();
                        ShowMedicineBefore.super.onBackPressed();
                    }
                });

        alertDialog.setNegativeButton("ไม่",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //คลิกไม่ cancel dialog
                        dialog.cancel();
                    }
                });

        alertDialog.show();

    }
    public void onResume() {

        // Set the window to keep screen on
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);

        // Acquire wakelock
        PowerManager pm = (PowerManager) getApplicationContext().getSystemService(Context.POWER_SERVICE);
        if (mWakeLock == null) {
            mWakeLock = pm.newWakeLock((PowerManager.FULL_WAKE_LOCK | PowerManager.SCREEN_BRIGHT_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP), TAG);
        }

        if (!mWakeLock.isHeld()) {
            mWakeLock.acquire();
            Log.i(TAG, "Wakelock aquired!!");
        }


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


}
