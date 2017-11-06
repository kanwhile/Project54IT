package com.projects.mypillapp.activity;


import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputFilter;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.fourmob.datetimepicker.date.DatePickerDialog;
import com.projects.mypillapp.Alarm.AlarmEventManager;
import com.projects.mypillapp.Model.DBHelper;
import com.projects.mypillapp.Model.Doctors;
import com.projects.mypillapp.Model.EventModel;
import com.projects.mypillapp.R;
import com.sleepbot.datetimepicker.time.RadialPickerLayout;
import com.sleepbot.datetimepicker.time.TimePickerDialog;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

public class NewEvent extends AppCompatActivity implements View.OnClickListener {

    private String textTargetUri,textDetail,textLocation;//นำค่าไปเก็บฐานข้อมูล
    private ImageView setImg;
    private EditText eventName;
    private TextView eventLocation,eventDetail,nameDoctor;
    private DatePickerDialog mDatePicker;
    private TimePickerDialog mTimePicker;
    private TextView txtTime,txtDate;
    private Calendar mCalendar;
    private static int pYear,pMonth,pDay;
    private static int pHour,pMin;
    private DBHelper dbHelper;
    private EventModel addEvent;
    private static final int REQUEST_CAMERA = 1;
    private static final int SELECT_FILE = 2;
    private int idDoctor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_new_event);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        dbHelper = new DBHelper(this);
        FloatingActionButton selectImg = (FloatingActionButton)findViewById(R.id.set_img);
        selectImg.setOnClickListener(this);

        eventName = (EditText)findViewById(R.id.event_name);
        eventLocation = (TextView)findViewById(R.id.event_location);
        eventDetail = (TextView)findViewById(R.id.event_detail);
        txtDate = (TextView)findViewById(R.id.date_event);
        txtTime = (TextView)findViewById(R.id.time_event);
        nameDoctor = (TextView)findViewById(R.id.event_doctor);
        LinearLayout sTime = (LinearLayout) findViewById(R.id.time);
        LinearLayout sDate = (LinearLayout) findViewById(R.id.date);
        setImg = (ImageView)findViewById(R.id.img_event);
        int id = getIntent().getExtras().getInt("id");
        if (id == -1){
            addEvent = new EventModel();
        }else {
            addEvent = dbHelper.getEventAll(id);
            eventName.setText(addEvent.name_event);
            eventLocation.setText(addEvent.location);
            eventDetail.setText(addEvent.detail_event);
            idDoctor = addEvent.id_doctor;
            if (idDoctor > 0){
                Doctors doctors = dbHelper.getDoctorId(addEvent.id_doctor);
                nameDoctor.setText("" + doctors.name);
            }
            pHour = addEvent.hour;
            pMin = addEvent.minute;
            pDay = addEvent.day;
            pMonth = addEvent.month;
            pYear = addEvent.year;
            textTargetUri = addEvent.img_event;
            txtTime.setText(String.format("%02d : %02d",addEvent.hour,addEvent.minute));
            mCalendar = Calendar.getInstance();
            DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.LONG);
            mCalendar.set(pYear+543,pMonth,pDay);
            Date date = mCalendar.getTime();
            String textDate = dateFormat.format(date);
            txtDate.setText(textDate);

            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.RGB_565;
            Bitmap bitmap = BitmapFactory.decodeFile(addEvent.img_event, options);
            setImg.setImageBitmap(bitmap);



        }

        mCalendar = Calendar.getInstance();
        mDatePicker = DatePickerDialog.newInstance(onDateSetListener,
                mCalendar.get(Calendar.YEAR),
                mCalendar.get(Calendar.MONTH),
                mCalendar.get(Calendar.DAY_OF_MONTH),
                false);
        mTimePicker = TimePickerDialog.newInstance(onTimeSetListener,
                mCalendar.get(Calendar.HOUR_OF_DAY),
                mCalendar.get(Calendar.MINUTE),
                true,
                false);

        sTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTimePicker.show(getSupportFragmentManager(), "timePicker");
            }
        });
        sDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDatePicker.setYearRange(2014, 2020);
                mDatePicker.show(getSupportFragmentManager(), "datePicker");
            }
        });



    }
    public void onClick(View view) {
        final CharSequence[] items = { "กล้องถ่ายรูป", "คลังภาพ",
                "ยกเลิก" };

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("เพิ่มรูปภาพ !");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("กล้องถ่ายรูป")) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    File f = new File(android.os.Environment
                            .getExternalStorageDirectory()+"/DCIM/.imgPillApp", "temp.jpg");
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
                    startActivityForResult(intent, REQUEST_CAMERA);
                } else if (items[item].equals("คลังภาพ")) {
                    Intent intent = new Intent(
                            Intent.ACTION_PICK,
                            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    intent.setType("image/*");
                    startActivityForResult(
                            Intent.createChooser(intent, "Select File"),
                            SELECT_FILE);
                } else if (items[item].equals("ยกเลิก")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_CAMERA) {
                insetImageCamera();
            } else if (requestCode == SELECT_FILE) {
                Uri selectedImageUri = data.getData();
                textTargetUri = getPath(selectedImageUri, this);
                Bitmap bm;
                BitmapFactory.Options btmapOptions = new BitmapFactory.Options();
                bm = BitmapFactory.decodeFile(textTargetUri, btmapOptions);
                setImg.setImageBitmap(bm);
            }
        }
    }

    private void insetImageCamera() {
        File f =new File(Environment.getExternalStorageDirectory()
                , "DCIM/.imgPillApp");
        for (File temp : f.listFiles()) {
            if (temp.getName().equals("temp.jpg")) {
                f = temp;
                break;
            }
        }
        try {
            Bitmap bm;
            BitmapFactory.Options btmapOptions = new BitmapFactory.Options();

            bm = BitmapFactory.decodeFile(f.getAbsolutePath(),
                    btmapOptions);

            setImg.setImageBitmap(bm);
            f.delete();
            String path = android.os.Environment
                    .getExternalStorageDirectory()+"/DCIM";
            OutputStream fOut = null;
            File file = new File(path, String.valueOf(System
                    .currentTimeMillis()) + ".jpg");
            try {
                fOut = new FileOutputStream(file);
                bm.compress(Bitmap.CompressFormat.JPEG, 85, fOut);
                fOut.flush();
                fOut.close();
                textTargetUri = file.toString();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public String getPath(Uri uri, Activity activity) {
        String[] projection = { MediaStore.MediaColumns.DATA };
        Cursor cursor = activity
                .managedQuery(uri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }


    private TimePickerDialog.OnTimeSetListener onTimeSetListener =
            new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(RadialPickerLayout radialPickerLayout, int hourOfDay, int minute) {
                    txtTime.setText(String.format("%02d : %02d",hourOfDay,minute));
                    pHour = hourOfDay;
                    pMin = minute;
                }
            };

    private DatePickerDialog.OnDateSetListener onDateSetListener =
            new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePickerDialog datePickerDialog, int year, int month, int day) {

                    pDay=day;
                    pMonth = month;
                    pYear = year;
                    DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.LONG);
                    mCalendar.set(year+543, month, day);
                    Date date = mCalendar.getTime();
                    String textDate = dateFormat.format(date);

                    txtDate.setText(textDate);
                }
            };


    public void setEventLocation (View view){
        final AlertDialog.Builder builder =
                new AlertDialog.Builder(NewEvent.this,R.style.MyAlertDialogStyle);
        LayoutInflater inflater = getLayoutInflater();
        View v = inflater.inflate(R.layout.dialog_custom_context, null);
        builder.setView(v);

        final EditText editText = (EditText) v.findViewById(R.id.editText);

        TextView textView = (TextView)v.findViewById(R.id.titel_name);
        textView.setText(R.string.event_location);
        final ImageView imageView = (ImageView) v.findViewById(R.id.btn_save);
        editText.setText(textLocation);
        final AlertDialog mDialog = builder.show();
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textLocation = editText.getText().toString();
                eventLocation.setText(textLocation);
                mDialog.dismiss();
            }
        });
    }

    public void setEventDetail (View view){
        final AlertDialog.Builder builder =
                new AlertDialog.Builder(NewEvent.this);
        LayoutInflater inflater = getLayoutInflater();

        View v = inflater.inflate(R.layout.dialog_custom_context, null);
        builder.setView(v);

        final EditText editText = (EditText) v.findViewById(R.id.editText);
        TextView textView = (TextView)v.findViewById(R.id.titel_name);
        textView.setText(R.string.event_detail);
        final ImageView imageView = (ImageView) v.findViewById(R.id.btn_save);
        editText.setText(textDetail);
        final AlertDialog mDialog = builder.show();
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textDetail = editText.getText().toString();
                eventDetail.setText(textDetail);
                mDialog.dismiss();
            }
        });
    }
    private boolean validateName() {
        if (eventName.getText().toString().trim().isEmpty()) {
            Snackbar.make(findViewById(R.id.coordinatorLayout),"กรุณากรอก"+getString(R.string.event_name)+"!", Snackbar.LENGTH_LONG).show();
            return false;
        }

        return true;
    }

    private boolean validateLocation() {
        if (eventLocation.getText().toString().trim().isEmpty()) {
            Snackbar.make(findViewById(R.id.coordinatorLayout),"กรุณากรอก"+getString(R.string.event_location)+"!",Snackbar.LENGTH_LONG).show();
            return false;
        }

        return true;
    }
    private boolean Time() {
        if (txtTime.getText().toString().contains(getString(R.string.event_time))) {
            Snackbar.make(findViewById(R.id.coordinatorLayout),"กรุณา"+getString(R.string.event_time)+"!",Snackbar.LENGTH_LONG).show();
            return false;
        }

        return true;
    }
    private boolean Date() {
        if (txtDate.getText().toString().contains(getString(R.string.event_day))) {
            Snackbar.make(findViewById(R.id.coordinatorLayout), "กรุณา" + getString(R.string.event_day) + "!", Snackbar.LENGTH_LONG).show();
            return false;
        }

        return true;
    }


    private void submitForm() {
        if (!validateName()) {
            return;
        }
        if (!validateLocation()) {
            return;
        }
        if (!Time()){
            return;
        }
        if (!Date()){
            return;
        }
        updateModelFromEvent();
        AlarmEventManager.cancelAlarms(this);
        if (addEvent.id < 0) {
            dbHelper.createEvent(addEvent);

        } else {
            dbHelper.updateEvent(addEvent);
        }

        AlarmEventManager.setAlarms(this);
        startActivity(new Intent(this,ListEvent.class));
        finish();
    }
    private void updateModelFromEvent(){
        addEvent.name_event = eventName.getText().toString();
        addEvent.location = eventLocation.getText().toString();
        addEvent.hour = pHour;
        addEvent.minute = pMin;
        addEvent.day = pDay;
        addEvent.month = pMonth;
        addEvent.year = pYear;
        addEvent.detail_event = eventDetail.getText().toString();
        addEvent.img_event = textTargetUri;
        addEvent.id_doctor = idDoctor;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_newevent, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home: {
                overridePendingTransition(android.R.anim.slide_in_left,android.R.anim.slide_out_right);
                finish();
                break;
            }

            case R.id.action_save_pill:
                submitForm();

        }
        return super.onOptionsItemSelected(item);
    }

    public void setSelectDoctor (View view) {
        final AlertDialog.Builder showDoctor =
                new AlertDialog.Builder(NewEvent.this);
        LayoutInflater inflater = getLayoutInflater();

        View v = inflater.inflate(R.layout.dialog_custom_listdoctor, null);
        showDoctor.setView(v);
        showDoctor.setPositiveButton("ยกเลิก", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Check username password
            }
        });
        final AlertDialog mDialog = showDoctor.show();
        final ListView listView = (ListView)v.findViewById(R.id.list_disease);
        final ImageView imageView = (ImageView)v.findViewById(R.id.btn_new);
        final doctorListAdater mAdapter = new doctorListAdater(this, dbHelper.getDoctorAll());
        listView.setAdapter(mAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                idDoctor = (int)mAdapter.getItemId(position);
                Doctors doctors = dbHelper.getDoctorId(idDoctor);
                nameDoctor.setText(doctors.name);
                mDialog.dismiss();
            }
        });
        showDoctor.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        showDoctor.setView(listView);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NewEvent.this, NewDoctorActivity.class);
                intent.putExtra("id", -1);
                startActivity(intent);
                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
            }
        });


    }

}