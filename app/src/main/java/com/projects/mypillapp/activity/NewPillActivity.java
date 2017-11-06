package com.projects.mypillapp.activity;

import android.app.Activity;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.projects.mypillapp.Alarm.AlarmPillManager;
import com.projects.mypillapp.Model.DBHelper;
import com.projects.mypillapp.Model.Doctors;
import com.projects.mypillapp.Model.PillModel;
import com.projects.mypillapp.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Calendar;
import java.util.Date;

public class NewPillActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String[] EAT =
            { "ก่อนอาหาร", "หลังอาหาร"};
    private static final String[] TYPE =
            { "เม็ด","แคปซูล"};
    private int idDoctor,timeHour,id;
    private String textTargetUri, textDetail,textLocation;
    private ImageView imageView;
    private CheckBox chBreak, chLunch, chDinner, chNight;
    private TextView txtEat,eat, location, forPill, detail,nameDoctor,typePill;
    private EditText name;
    private DBHelper dbHelper;
    private PillModel addPill;
    private static final int REQUEST_CAMERA = 1;
    private static final int SELECT_FILE = 2;
    private static final int SELECT_DOCTOR = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_pill);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        dbHelper = new DBHelper(this);
        typePill = (TextView)findViewById(R.id.type_pill);
        name = (EditText) findViewById(R.id.edit_name);
        forPill = (TextView) findViewById(R.id.for_pill);
        location = (TextView) findViewById(R.id.location_pill);
        imageView = (ImageView) findViewById(R.id.img_pill);
        eat = (TextView) findViewById(R.id.setEat);
        detail = (TextView) findViewById(R.id.pill_detail);
        txtEat =(TextView)findViewById(R.id.txt_eat);
        nameDoctor = (TextView)findViewById(R.id.txt_doctor);

        LinearLayout linearLayout = (LinearLayout)findViewById(R.id.select_eat);
        linearLayout.setOnClickListener(this);



        String name_doctor = getIntent().getExtras().getString("name_doctor");
        id = getIntent().getExtras().getInt("id");
        if (id == -1) {
            addPill = new PillModel();
        } else {
            getSupportActionBar().setTitle("แก้ไขข้อมูล");
            addPill = dbHelper.getPill(id);
            name.setText(addPill.name_pill);
            textLocation = addPill.location;
            forPill.setText("" + addPill.for_pill);
            eat.setText(addPill.eat);
            idDoctor = addPill.id_doctor;
            nameDoctor.setText(name_doctor);
            timeHour = addPill.hourly;
            textDetail = addPill.detail;
            location.setText(textLocation);
            detail.setText(textDetail);
            typePill.setText(addPill.typepill);
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.RGB_565;
            Bitmap bitmap = BitmapFactory.decodeFile(addPill.pillImg, options);
            imageView.setImageBitmap(bitmap);
            textTargetUri = addPill.pillImg;
            textAlarm();
        }
    }
    @Override
    public void onClick(View v) {
        AlertDialog.Builder builder =
                new AlertDialog.Builder(NewPillActivity.this);
        builder.setTitle("ประเภทการรับประทาน");
        builder.setItems(EAT, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String selected = EAT[which];
                eat.setText(selected);
                switch (which) {
                    case 0:
                        checkEatDay();
                        break;
                    case 1:
                        checkEatDay();
                        break;
                }

            }
        });
        builder.create();
        builder.show();
    }


    private void checkEatDay() {
        AlertDialog.Builder addDisease =
                new AlertDialog.Builder(NewPillActivity.this);
        LayoutInflater inflater = getLayoutInflater();

        View view = inflater.inflate(R.layout.dialog_custom_eat, null);
        addDisease.setView(view);
        chBreak = (CheckBox) view.findViewById(R.id.time_break);
        chLunch = (CheckBox) view.findViewById(R.id.time_lunch);
        chDinner = (CheckBox) view.findViewById(R.id.time_dinner);
        chNight = (CheckBox) view.findViewById(R.id.time_night);

        if (id != -1){
            chBreak.setChecked(addPill.breakfast);
            chLunch.setChecked(addPill.lunch);
            chDinner.setChecked(addPill.dinner);
            chNight.setChecked(addPill.night);

        }
        addDisease.setPositiveButton("บันทึก", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                StringBuffer buffer = new StringBuffer();
                buffer.append("แจ้งเตือน");
                if (chBreak.isChecked()) {
                    buffer.append("เช้า ,");
                }
                if (chLunch.isChecked()) {
                    buffer.append("เที่ยง ,");
                }
                if (chDinner.isChecked()) {
                    buffer.append("เย็น, ");
                }

                if (chNight.isChecked()) {
                    buffer.append("ก่อนนอน");
                }
                txtEat.setText(buffer.toString());
                timeHour = 0;
            }
        });

        addDisease.show();
    }

    private void textAlarm() {
        if (timeHour > 0 ){
            txtEat.setText("*แจ้งเตือนทานยา ทุกๆ " + timeHour + " ช.ม");
        }else {
            StringBuffer buffer = new StringBuffer();
            if (addPill.breakfast) {
                buffer.append("แจ้งเตือน");
                buffer.append("*เช้า");
            }
            if (addPill.lunch) {
                buffer.append(" ");
                buffer.append("*เที่ยง");
            }
            if (addPill.dinner) {
                buffer.append(" ");
                buffer.append("*เย็น");
            }

            if (addPill.night) {
                buffer.append(" ");
                buffer.append("*ก่อนนอน");
            }
            txtEat.setText(buffer.toString());
        }

    }


    public void setImage(View view) {
        final CharSequence[] items = {"กล้องถ่ายรูป", "คลังภาพ",
                "ยกเลิก"};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("เพิ่มรูปภาพ !");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("กล้องถ่ายรูป")) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    File f = new File(Environment
                            .getExternalStorageDirectory() + "/DCIM/.imgPillApp", "temp.jpg");
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
                    startActivityForResult(intent, REQUEST_CAMERA);
                } else if (items[item].equals("คลังภาพ")) {
                    Intent intent = new Intent(
                            Intent.ACTION_PICK,
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
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
                textTargetUri = getPath(selectedImageUri, NewPillActivity.this);
                Bitmap bm;
                BitmapFactory.Options btmapOptions = new BitmapFactory.Options();
                bm = BitmapFactory.decodeFile(textTargetUri, btmapOptions);
                imageView.setImageBitmap(bm);
            }else if (requestCode == SELECT_DOCTOR){
                nameDoctor.setText(data.getStringExtra("name_doctor"));
                idDoctor = dbHelper.getMaxIdDoctor();
            }
        }
    }

    private void insetImageCamera() {
        File f = new File(Environment.getExternalStorageDirectory()
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

            // bm = Bitmap.createScaledBitmap(bm, 70, 70, true);
            imageView.setImageBitmap(bm);
            f.delete();
            String path = Environment
                    .getExternalStorageDirectory() + "/DCIM";
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
        String[] projection = {MediaStore.MediaColumns.DATA};
        Cursor cursor = activity
                .managedQuery(uri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    private boolean validateName() {
        if (name.getText().toString().trim().isEmpty()) {
            Snackbar.make(findViewById(R.id.rootLayout), "กรุณากรอก" + getString(R.string.pill_name) + "!", Snackbar.LENGTH_LONG).show();
            return false;
        }
        return true;
    }
    private boolean dataFor() {
        if (forPill.getText().toString().trim().isEmpty()) {
            Snackbar.make(findViewById(R.id.rootLayout), "กรุณากรอก จำนวนยา!", Snackbar.LENGTH_LONG).show();
            return false;
        }

        return true;
    }
    private boolean selectEat() {
        if (eat.getText().toString().trim().isEmpty()) {
            Snackbar.make(findViewById(R.id.rootLayout), "กรุณาเลือกการแจ้งเตือน!", Snackbar.LENGTH_LONG).show();
            return false;
        }

        return true;
    }

    private void submitForm() {
        if (!validateName()) {
            return;
        }
        if (!dataFor()) {
            return;
        }
        if (!selectEat()){
            return;
        }
        updateModelFromPill();
        if (addPill.id < 0) {
            dbHelper.createPill(addPill);
        } else {
            dbHelper.updatePill(addPill);
        }
        Intent intent = new Intent(getApplicationContext(), ListPill.class);
        startActivity(intent);
        finish();

    }
    private void updateModelFromPill() {
        if (chBreak != null) {
            addPill.breakfast = chBreak.isChecked();
            addPill.lunch = chLunch.isChecked();
            addPill.dinner = chDinner.isChecked();
            addPill.night = chNight.isChecked();
            AlarmPillManager.setAlarms(this);
        }

        addPill.name_pill = name.getText().toString();
        addPill.location = location.getText().toString();
        addPill.for_pill = Integer.parseInt(forPill.getText().toString());
        addPill.typepill = typePill.getText().toString();
        
        addPill.eat = eat.getText().toString();
        addPill.detail = detail.getText().toString();
        addPill.pillImg = textTargetUri;
        addPill.id_doctor = idDoctor;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_newpill, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: {
                this.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                finish();
                break;
            }

            case R.id.action_save_pill:
                submitForm();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public void setSelectDoctor (View view) {
        final AlertDialog.Builder showDoctor =
                new AlertDialog.Builder(NewPillActivity.this);
        final LayoutInflater inflater = getLayoutInflater();

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
                Intent intent = new Intent(NewPillActivity.this, NewDoctorActivity.class);
                intent.putExtra("id", -1);
                intent.putExtra("pill",-1);
                startActivityForResult(intent, SELECT_DOCTOR);
                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                mDialog.dismiss();
            }
        });


    }
    public void setSelectType (View view) {
        AlertDialog.Builder builder =
                new AlertDialog.Builder(NewPillActivity.this);
        builder.setTitle("เลือกชนิดของยา");
        builder.setItems(TYPE, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String selected = TYPE[which];
                typePill.setText(selected);
            }
        });
        builder.create();
        builder.show();

    }

    public void setTextDetail(View view) {
        final AlertDialog.Builder builder =
                new AlertDialog.Builder(NewPillActivity.this);
        LayoutInflater inflater = getLayoutInflater();

        View v = inflater.inflate(R.layout.dialog_custom_context, null);
        builder.setView(v);

        final EditText editText = (EditText) v.findViewById(R.id.editText);
        TextView textView = (TextView)v.findViewById(R.id.titel_name);
        textView.setText(R.string.pill_detail);
        final ImageView imageView = (ImageView) v.findViewById(R.id.btn_save);
        editText.setText(textDetail);
        final AlertDialog mDialog = builder.show();
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textDetail = editText.getText().toString();
                detail.setText(textDetail);
                mDialog.dismiss();
            }
        });

    }
    public void setTextLocation(View view) {
        final AlertDialog.Builder builder =
                new AlertDialog.Builder(NewPillActivity.this,R.style.MyAlertDialogStyle);
        LayoutInflater inflater = getLayoutInflater();
        View v = inflater.inflate(R.layout.dialog_custom_context, null);
        builder.setView(v);

        final EditText editText = (EditText) v.findViewById(R.id.editText);
        TextView textView = (TextView)v.findViewById(R.id.titel_name);
        textView.setText(R.string.pill_location);
        final ImageView imageView = (ImageView) v.findViewById(R.id.btn_save);
        editText.setText(textLocation);
        final AlertDialog mDialog = builder.show();
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textLocation = editText.getText().toString();
                location.setText(textLocation);
                mDialog.dismiss();
            }
        });
    }

    public void setTextPill(View view) {
        final AlertDialog.Builder builder =
                new AlertDialog.Builder(NewPillActivity.this);
        LayoutInflater inflater = getLayoutInflater();

        View v = inflater.inflate(R.layout.dialog_custom_num, null);
        builder.setView(v);

        final NumberPicker picker = (NumberPicker) v.findViewById(R.id.numberPicker);
        final ImageView imageView = (ImageView) v.findViewById(R.id.btn_save);
        picker.setFocusableInTouchMode(true);
        picker.setMinValue(1);
        picker.setMaxValue(10);
        picker.setFocusable(true);
        final AlertDialog mDialog = builder.show();
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int takePill = picker.getValue();
                forPill.setText(takePill+"");
                mDialog.dismiss();
            }
        });

    }


}
