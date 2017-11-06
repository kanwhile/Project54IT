package com.projects.mypillapp;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.projects.mypillapp.Model.DBHelper;
import com.projects.mypillapp.Model.PillModel;
import com.projects.mypillapp.Model.Pill_history;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MainActivity1 extends AppCompatActivity {
    DBHelper dbHelper;
    Pill_history addPillHistory;
    private TextView txtTime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main1);

        dbHelper = new DBHelper(this);
        txtTime = (TextView)findViewById(R.id.time);
        String date = (DateFormat.format("dd-MM-yyyy kk:mm:ss", new java.util.Date()).toString());
        txtTime.setText(date);

        Button btn = (Button)findViewById(R.id.btn1);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addPillHistory = new  Pill_history();
                addPillHistory.timestamp = txtTime.getText().toString();
                dbHelper.createPillHistory(addPillHistory);
            }
        });


    }

    //เปลี่ยน Font ตัวอักษร
    @Override
    protected void attachBaseContext(Context newBase){
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
    public static String getCurrentTimeStamp() {
        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");//dd/MM/yyyy
        Date now = new Date();
        String strDate = sdfDate.format(now);
        return strDate;
    }
}
