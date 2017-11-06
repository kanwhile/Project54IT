package com.projects.mypillapp.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.projects.mypillapp.Alarm.AlarmEventManager;
import com.projects.mypillapp.MainActivity;
import com.projects.mypillapp.Model.DBHelper;
import com.projects.mypillapp.Model.Doctors;
import com.projects.mypillapp.Model.EventModel;
import com.projects.mypillapp.R;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;


public class EventDetail extends AppCompatActivity {

    private DBHelper dbHelper = new DBHelper(this);
    EventModel eventDetail;
    private Context mContext;
    Doctors doctors;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mContext = this;

        TextView nameEvent = (TextView)findViewById(R.id.txt_name_event);
        TextView location = (TextView)findViewById(R.id.txt_location_event);
        TextView detail = (TextView)findViewById(R.id.txt_detail_event);
        TextView timeEvent = (TextView)findViewById(R.id.txt_time_event);
        TextView dayEvent = (TextView)findViewById(R.id.txt_day_event);
        TextView nameDoctor = (TextView)findViewById(R.id.txt_doctor_event);
        ImageView imgEvent = (ImageView)findViewById(R.id.img_event);



        int id = getIntent().getExtras().getInt("id");
        eventDetail = dbHelper.getEventAll(id);
        Glide.with(mContext)
                .load(eventDetail.img_event).into(imgEvent);

        nameEvent.setText(eventDetail.name_event);
        location.setText(eventDetail.location);
        detail.setText(eventDetail.detail_event);

        if (eventDetail.id_doctor > 0){
            doctors = dbHelper.getDoctorId(eventDetail.id_doctor);
            nameDoctor.setText("" + doctors.name);
        }
        timeEvent.setText(String.format("%02d : %02d ", eventDetail.hour, eventDetail.minute));
        Calendar mCalendar = Calendar.getInstance();
        DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.LONG);
        mCalendar.set(eventDetail.year+543, eventDetail.month, eventDetail.day);
        Date date = mCalendar.getTime();
        String textDate = dateFormat.format(date);
        dayEvent.setText(textDate);
    }

    public void deleteEvent (int id) {
        final int eventId = id;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("ยืนยันการลบ")
                .setTitle("คุณต้องการลบข้อมูล ?")
                .setCancelable(true)
                .setNegativeButton("ยกเลิก", null)
                .setPositiveButton("ตกลง", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Cancel Alarms
                        AlarmEventManager.cancelAlarms(mContext);
                        //Delete alarm from DB by id
                        dbHelper.deleteEvent(eventDetail.id);
                        Intent intent = new Intent(EventDetail.this, ListEvent.class);
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

            case R.id.delete:
                deleteEvent(eventDetail.id);
            break;
            case R.id.edit:
                Intent intent = new Intent(EventDetail.this, NewEvent.class);
                intent.putExtra("id", eventDetail.id);
                startActivity(intent);
                finish();
                overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
            break;
        }
        return super.onOptionsItemSelected(item);
    }
}
