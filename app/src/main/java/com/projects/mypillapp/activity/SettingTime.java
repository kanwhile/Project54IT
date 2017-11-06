package com.projects.mypillapp.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.projects.mypillapp.Alarm.AlarmPillManager;
import com.projects.mypillapp.Alarm.MyReceiverAfter;
import com.projects.mypillapp.Alarm.MyReceiverBefore;
import com.projects.mypillapp.MainActivity;
import com.projects.mypillapp.R;
import com.sleepbot.datetimepicker.time.RadialPickerLayout;
import com.sleepbot.datetimepicker.time.TimePickerDialog;

public class SettingTime extends AppCompatActivity {

    private TimePickerDialog mTimesetBreak,mTimesetLunch,mTimesetDinner,setTimeNight;
    private int bHour,bMin,lHour,lMin,dHour,dMin,nHour,nMin;
    private TextView txtBreak,txtLunch,txtDinner,txtNight;

    //สำหรับเก็บค่า sharedPreferences
    SharedPreferences sp;
    SharedPreferences.Editor editor;


    final String PREF_NAME = "TimeSetting";
    final String KEY_HOUR_BREAK = "HourBreak";
    final String KEY_MIN_BREAK = "MinBreak";
    final String KEY_HOUR_LUNCH = "HourLunch";
    final String KEY_MIN_LUNCH = "MinLunch";
    final String KEY_HOUR_DINNER = "HourDinner";
    final String KEY_MIN_DINNER = "MinDinner";
    final String KEY_HOUR_NIGHR = "HourNight";
    final String KEY_MIN_NIGHT = "MinNight";
    final String SET_TIME ="setTime";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_time);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        sp = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = sp.edit();




        txtBreak = (TextView)findViewById(R.id.time_break);
        txtLunch = (TextView)findViewById(R.id.time_lunch);
        txtDinner = (TextView)findViewById(R.id.time_dinner);
        txtNight = (TextView)findViewById(R.id.time_night);

        Boolean timeSetting = sp.getBoolean(SET_TIME,false);
        if (timeSetting){
            bHour = sp.getInt(KEY_HOUR_BREAK, 7);
            bMin = sp.getInt(KEY_MIN_BREAK, 30);
            txtBreak.setText(String.format("%02d : %02d", bHour, bMin));

            lHour = sp.getInt(KEY_HOUR_LUNCH, 11);
            lMin = sp.getInt(KEY_MIN_LUNCH, 30);
            txtLunch.setText(String.format("%02d : %02d", lHour, lMin));

            dHour = sp.getInt(KEY_HOUR_DINNER, 18);
            dMin = sp.getInt(KEY_MIN_DINNER, 30);
            txtDinner.setText(String.format("%02d : %02d", dHour, dMin));

            nHour = sp.getInt(KEY_HOUR_NIGHR, 21);
            nMin = sp.getInt(KEY_MIN_NIGHT, 30);
            txtNight.setText(String.format("%02d : %02d", nHour, nMin));

        }




        RelativeLayout setBreak = (RelativeLayout)findViewById(R.id.time_break_set);
        RelativeLayout setLunch = (RelativeLayout)findViewById(R.id.time_lunch_set);
        RelativeLayout setDinner = (RelativeLayout)findViewById(R.id.time_dinner_set);
        RelativeLayout setNight = (RelativeLayout)findViewById(R.id.time_night_set);

        setBreak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTimesetBreak.show(getSupportFragmentManager(), "timePicker");
            }
        });
        setLunch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTimesetLunch.show(getSupportFragmentManager(), "timePicker");
            }
        });
        setDinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTimesetDinner.show(getSupportFragmentManager(), "timePicker");
            }
        });
        setNight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTimeNight.show(getSupportFragmentManager(), "timePicker");
            }
        });

        mTimesetBreak = TimePickerDialog.newInstance(onTimeSetListener,
                7,
                30,
                true,
                false);

        mTimesetLunch = TimePickerDialog.newInstance(onTimeSetListenerLunch,
                11,
                00,
                true,
                false);
        mTimesetDinner = TimePickerDialog.newInstance(onTimeSetListenerDinner,
                18,
                00,
                true,
                false);
        setTimeNight = TimePickerDialog.newInstance(onTimeSetListenerNight,
                21,
                00,
                true,
                false);
    }

    private TimePickerDialog.OnTimeSetListener onTimeSetListener =
            new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(RadialPickerLayout radialPickerLayout, int hourOfDay, int minute) {

                    txtBreak.setText(String.format("%02d : %02d",hourOfDay,minute));
                    bHour = hourOfDay;
                    bMin = minute;
                }
            };
    private TimePickerDialog.OnTimeSetListener onTimeSetListenerLunch =
            new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(RadialPickerLayout radialPickerLayout, int hourOfDay, int minute) {

                    txtLunch.setText(String.format("%02d : %02d",hourOfDay,minute));
                    lHour = hourOfDay;
                    lMin = minute;
                }
            };
    private TimePickerDialog.OnTimeSetListener onTimeSetListenerDinner =
            new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(RadialPickerLayout radialPickerLayout, int hourOfDay, int minute) {

                    txtDinner.setText(String.format("%02d : %02d",hourOfDay,minute));
                    dHour = hourOfDay;
                    dMin = minute;
                }
            };
    private TimePickerDialog.OnTimeSetListener onTimeSetListenerNight =
            new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(RadialPickerLayout radialPickerLayout, int hourOfDay, int minute) {

                    txtNight.setText(String.format("%02d : %02d",hourOfDay,minute));
                    nHour = hourOfDay;
                    nMin = minute;
                }
            };

    private boolean timeBreak() {
        if (txtBreak.getText().toString().contains(getString(R.string.click_set_time) )|| ( bHour ==0 && bMin == 0)) {
            Snackbar.make(findViewById(R.id.rootLayout),  "กรุณาคลิกเลือกเวลา" + getString(R.string.time_breakfas) + "!", Snackbar.LENGTH_LONG).show();
            return false;
        }

        return true;
    }
    private boolean timeLunch() {
        if (txtLunch.getText().toString().contains(getString(R.string.click_set_time))|| ( lHour ==0 && lMin == 0) || lHour <= bHour) {
            Snackbar.make(findViewById(R.id.rootLayout),"กรุณาคลิกเลือกเวลา" + getString(R.string.time_lunch) + "!", Snackbar.LENGTH_LONG).show();
            return false;
        }

        return true;
    }
    private boolean timeDinner() {
        if (txtDinner.getText().toString().contains(getString(R.string.click_set_time))|| ( dHour ==0 && dMin == 0) || dHour <= lHour) {
            Snackbar.make(findViewById(R.id.rootLayout), "กรุณาคลิกเลือกเวลา" + getString(R.string.time_dinner) + "!", Snackbar.LENGTH_LONG).show();
            return false;
        }

        return true;
    }
    private boolean timeNight() {
        if (txtNight.getText().toString().contains(getString(R.string.click_set_time))|| ( nHour ==0 && nMin == 0) || nHour <= dHour) {
            Snackbar.make(findViewById(R.id.rootLayout), "กรุณาคลิกเลือกเวลา" + getString(R.string.time_night) + "!", Snackbar.LENGTH_LONG).show();
            return false;
        }

        return true;
    }
    private void submitForm() {
        MyReceiverAfter.cancelAlarms(this);
        MyReceiverBefore.cancelAlarms(this);
        if (!timeBreak()){
            return;
        }
        if (!timeLunch()){
            return;
        }
        if (!timeDinner()){
            return;
        }
        if (!timeNight()){
            return;
        }
        if (sp.getBoolean(SET_TIME, false)) {
            AlarmPillManager.setAlarms(this);
        }
        //เช้า
        editor.putInt(KEY_HOUR_BREAK,bHour);
        editor.putInt(KEY_MIN_BREAK,bMin);
        //กลางวัน
        editor.putInt(KEY_HOUR_LUNCH,lHour);
        editor.putInt(KEY_MIN_LUNCH,lMin);
        //เย็น
        editor.putInt(KEY_HOUR_DINNER,dHour);
        editor.putInt(KEY_MIN_DINNER,dMin);
        //ก่อนนอน
        editor.putInt(KEY_HOUR_NIGHR, nHour);
        editor.putInt(KEY_MIN_NIGHT, nMin);
        editor.putBoolean(SET_TIME, true);
        editor.commit();
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.putExtra("intent", 0);
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_newpill, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home: {
                finish();
                break;
            }

            case R.id.action_save_pill:
                submitForm();

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this,MainActivity.class));
        this.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        super.onBackPressed();
    }

}



