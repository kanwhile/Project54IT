package com.projects.mypillapp.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;


import com.bumptech.glide.Glide;
import com.projects.mypillapp.Model.DBHelper;
import com.projects.mypillapp.Model.Doctors;
import com.projects.mypillapp.R;

public class NewDoctorActivity extends AppCompatActivity implements View.OnClickListener {
    private int[] img = {

            R.drawable.doctor1,
            R.drawable.doctor2,
            R.drawable.doctor3,
    };
    private ImageView imageView;
    private EditText name,phone,email,address;
    private int imgSet,pill;
    private Doctors doctors;
    private DBHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_doctor);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        dbHelper = new DBHelper(this);
        imageView = (ImageView)findViewById(R.id.img_doctor);
        imageView.setOnClickListener(this);
        name = (EditText)findViewById(R.id.txt_name_docter);
        phone = (EditText)findViewById(R.id.txt_phone);
        phone.addTextChangedListener(new PhoneNumberFormattingTextWatcher());

        email = (EditText)findViewById(R.id.txt_email);
        address = (EditText)findViewById(R.id.txt_address);

        int id = getIntent().getExtras().getInt("id");
        pill = getIntent().getExtras().getInt("pill");
        if (id == -1){
            doctors = new Doctors();
        }else {
            doctors = dbHelper.getDoctorId(id);
            name.setText(doctors.name);
            phone.setText(doctors.phone);
            email.setText(doctors.email);
            address.setText(doctors.address);
            Glide.with(this)
                    .load(img[doctors.image]).into(imageView);
        }

    }



    @Override
    public void onClick(View v) {
        GridView gridView = new GridView(this);

        final AlertDialog.Builder builder =
                new AlertDialog.Builder(this);
        builder.setTitle("เลือกรูปภาพ ");
        gridView.setAdapter(new ImageAdapter(this));
        gridView.setNumColumns(3);
        builder.setView(gridView);
        final AlertDialog alertDialog = builder.show();
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Glide.with(getApplicationContext())
                        .load(img[position]).into(imageView);
                alertDialog.dismiss();
                imgSet = position;
            }
        });

    }
    private boolean validateName() {
        if (name.getText().toString().trim().isEmpty()) {
            Snackbar.make(findViewById(R.id.coordinatorLayout),"กรุณากรอก ชื่อแพทย์!",Snackbar.LENGTH_LONG).show();
            return false;
        }

        return true;
    }



    private void submitForm() {
        if (!validateName()) {
            return;
        }

        updateModelFromDoctor();
        if (doctors.id < 0) {
            dbHelper.createDoctor(doctors);

        } else {
            dbHelper.updatedoctor(doctors);
        }
        if (pill == -1){
            Intent returnIntent = new Intent(getApplicationContext(),NewDoctorActivity.class);
            returnIntent.putExtra("name_doctor",doctors.name);
            setResult(RESULT_OK,returnIntent);
            finish();
        }else {
            startActivity(new Intent(this,ListDoctor.class));
            finish();
        }

    }
    private void updateModelFromDoctor(){
        doctors.name = name.getText().toString();
        doctors.phone = phone.getText().toString();
        doctors.email = email.getText().toString();
        doctors.address = address.getText().toString();
        doctors.image = imgSet;
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
                this.overridePendingTransition(android.R.anim.slide_in_left,android.R.anim.slide_out_right);
                finish();
                break;
            }

            case R.id.action_save_pill:
                submitForm();

        }
        return super.onOptionsItemSelected(item);
    }

    public class ImageAdapter extends BaseAdapter {
        private Context context;

        public ImageAdapter(Context c)
        {
            // TODO Auto-generated method stub
            context = c;
        }

        public int getCount() {
            // TODO Auto-generated method stub
            return img.length;
        }

        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub
            ImageView imageView;
            //imageView
            if (convertView == null) {
                imageView = new ImageView(context);
                imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                imageView.setPadding(10,10,10,10);
                imageView.setLayoutParams(new GridView.LayoutParams(150, 150));
            } else {
                imageView = (ImageView) convertView;
            }
            imageView.setImageResource(img[position]);
            return imageView;
        }
    }
}
