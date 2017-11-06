package com.projects.mypillapp.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.projects.mypillapp.Alarm.AlarmPillManager;
import com.projects.mypillapp.Model.DBHelper;
import com.projects.mypillapp.Model.Disease;
import com.projects.mypillapp.Model.Doctors;
import com.projects.mypillapp.Model.PillModel;
import com.projects.mypillapp.R;

public class PillDetail extends AppCompatActivity {

    private DBHelper dbHelper = new DBHelper(this);
    PillModel pillDetails;
    Disease id_disease;
    TextView doctor;
    Doctors doctors;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pill_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        dbHelper = new DBHelper(getApplicationContext());
        TextView name = (TextView) findViewById(R.id.txt_name_pill);
        TextView location = (TextView) findViewById(R.id.txt_location_pill);
        TextView forPill = (TextView) findViewById(R.id.txt_for);
        TextView Eat = (TextView)findViewById(R.id.txt_eat_pill);
        TextView type = (TextView)findViewById(R.id.txt_type);
        TextView detailPaill = (TextView)findViewById(R.id.txt_detail_pill);
        TextView hour = (TextView)findViewById(R.id.txt_hourly);
        doctor = (TextView)findViewById(R.id.txt_doctor_name);

        ImageView img = (ImageView) findViewById(R.id.img_pill);

        LinearLayout show = (LinearLayout)findViewById(R.id.linear_time);
        LinearLayout showHour = (LinearLayout)findViewById(R.id.linear_hour);

        int id = getIntent().getExtras().getInt("id");
        pillDetails = dbHelper.getPill(id);

        if (pillDetails.hourly > 0 ){
             show.removeAllViews();
             hour.setText(String.format("%02d : %02d ", pillDetails.hour , pillDetails.minute));
        }else {
            showHour.removeAllViews();
            showImg((ImageView) findViewById(R.id.img_breakfast), pillDetails.breakfast);
            showImg((ImageView) findViewById(R.id.img_lunch), pillDetails.lunch);
            showImg((ImageView) findViewById(R.id.img_dinner), pillDetails.dinner);
            showImg((ImageView) findViewById(R.id.img_night), pillDetails.night);
        }
        name.setText( pillDetails.name_pill);
        location.setText( pillDetails.location);
        forPill.setText(pillDetails.for_pill+"");
        Eat.setText(pillDetails.eat);
        type.setText(pillDetails.typepill);
        detailPaill.setText(pillDetails.detail);
        img.setImageBitmap(BitmapFactory.decodeFile(pillDetails.pillImg));
        linearShow();





    }

    private void linearShow() {
        if (pillDetails.location.equals("")){
            LinearLayout linearLayout = (LinearLayout)findViewById(R.id.linear_location);
            linearLayout.setVisibility(View.GONE);
        }
        if (pillDetails.id_doctor == 0){
            LinearLayout linearLayout = (LinearLayout)findViewById(R.id.linear_doctor);
            linearLayout.setVisibility(View.GONE);
        }else {
            doctors = dbHelper.getDoctorId(pillDetails.id_doctor);
            doctor.setText(doctors.name);
        }

    }

    // แสดงรูปภาพการแจ้งเตือน
    private void showImg(ImageView view, boolean isOn){
        if(isOn){
            view.setImageResource(R.drawable.checked);
        }
    }

    public void deletePill(int id) {
        final int alarmId = id;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("ยืนยันการลบ")
                .setTitle("คุณต้องการลบข้อมูล ?")
                .setCancelable(true)
                .setNegativeButton("ยกเลิก", null)
                .setPositiveButton("ตกลง", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        AlarmPillManager.setAlarms(getApplicationContext());
                        dbHelper.deletePill(pillDetails.id);
                        dbHelper.deletePillHistory(pillDetails.id);
                        Intent intent = new Intent(PillDetail.this, ListPill.class);
                        startActivity(intent);
                        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                        finish();
                    }

                }).show();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_detail, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home: {
                finish();
                break;
            }
            case R.id.edit:
                if (pillDetails.hourly == 0) {
                    Intent intent = new Intent(PillDetail.this, NewPillActivity.class);
                    intent.putExtra("id", pillDetails.id);
                    if (pillDetails.id_disease > 0) {
                        intent.putExtra("name_disease", id_disease.name_disease);
                    }
                    if (pillDetails.id_doctor > 0) {
                        intent.putExtra("name_doctor", doctors.name);
                    }
                    startActivity(intent);
                }else {
                    Intent intent = new Intent(PillDetail.this, NewPillHour.class);
                    intent.putExtra("id", pillDetails.id);
                    if (pillDetails.id_disease > 0) {
                        intent.putExtra("name_disease", id_disease.name_disease);
                    }
                    if (pillDetails.id_doctor > 0) {
                        intent.putExtra("name_doctor", doctors.name);
                    }
                    startActivity(intent);
                }
                finish();
                break;
            case R.id.delete:
                deletePill(pillDetails.id);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
