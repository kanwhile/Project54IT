package com.projects.mypillapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;

import com.projects.mypillapp.activity.InfoPillActivity;
import com.projects.mypillapp.activity.NewEvent;
import com.projects.mypillapp.activity.NewPillActivity;

public class Main2Activity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        LinearLayout newPill = (LinearLayout)findViewById(R.id.new_pill);
        newPill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), NewPillActivity.class);
                intent.putExtra("id", -1);
                startActivity(intent);
            }
        });

        LinearLayout listEvent = (LinearLayout)findViewById(R.id.new_event);
        listEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), NewEvent.class);
                intent.putExtra("id", -1);
                startActivity(intent);
            }
        });
        LinearLayout infoPill = (LinearLayout)findViewById(R.id.info_pill);
        infoPill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), InfoPillActivity.class);
                startActivity(intent);
            }
        });
    }
}

