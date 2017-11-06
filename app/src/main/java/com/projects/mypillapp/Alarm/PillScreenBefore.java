package com.projects.mypillapp.Alarm;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.PowerManager;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.projects.mypillapp.R;
import com.projects.mypillapp.activity.ShowMedicineBefore;

import java.util.Calendar;
import java.util.Date;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class PillScreenBefore extends AppCompatActivity {

    public final String TAG = this.getClass().getSimpleName();

    private PowerManager.WakeLock mWakeLock;
    private MediaPlayer mPlayer;
    Vibrator v;
    private static final int WAKELOCK_TIMEOUT = 3 * 1000;

    Handler handler;
    Runnable runnable;
    long delay_time;
    long time = 30 * 1000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pill_screnn_before);
        final String id = getIntent().getStringExtra(MyReceiverAfter.ID);

        //กำหนดการสั่น
        v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        long[] pattern = { 0, 200, 500 , 200 };
        v.vibrate(pattern, 0);

        Calendar cal = Calendar.getInstance(); // creates calendar
        cal.setTime(new Date()); // sets calendar time/date
        cal.getTime(); // returns new date object, one hour in the future
        TextView dismissButton = (TextView) findViewById(R.id.txt_submit);
        dismissButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                v.cancel();
                mPlayer.stop();
                Intent internt = new Intent(PillScreenBefore.this, ShowMedicineBefore.class);
                internt.putExtra("showpill", id);
                internt.putExtra("eat","หลังอาหาร");
                startActivity(internt);
                finish();
            }
        });

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
        //Ensure wakelock release
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

        handler = new Handler();

        runnable = new Runnable() {
            public void run() {
                Intent intent = new Intent(PillScreenBefore.this, ShowMedicineBefore.class);
                intent.putExtra("showpill",id);
                intent.putExtra("eat","หลังอาหาร");
                v.cancel();
                mPlayer.stop();
                finish();
            }
        };
    }

    @SuppressWarnings("deprecation")
    @Override
    protected void onResume() {
        super.onResume();

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

        delay_time = time;
        handler.postDelayed(runnable, delay_time);
        time = System.currentTimeMillis();

    }

    @Override
    protected void onPause() {
        super.onPause();

        if (mWakeLock != null && mWakeLock.isHeld()) {
            mWakeLock.release();
        }
        handler.removeCallbacks(runnable);
        time = delay_time - (System.currentTimeMillis() - time);
    }
    //เปลี่ยน Font ตัวอักษร
    @Override
    protected void attachBaseContext(Context newBase){
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

}
