package com.projects.mypillapp.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.projects.mypillapp.Alarm.AlarmEventManager;
import com.projects.mypillapp.Fragment.BreakfastFragment;
import com.projects.mypillapp.Fragment.DetailDoctorFragment;
import com.projects.mypillapp.Fragment.DinnerFragment;
import com.projects.mypillapp.Fragment.EventFragment;
import com.projects.mypillapp.Fragment.LunchFragment;
import com.projects.mypillapp.Fragment.NightFragment;
import com.projects.mypillapp.Fragment.PillFragment;
import com.projects.mypillapp.MainActivity;
import com.projects.mypillapp.Model.DBHelper;
import com.projects.mypillapp.Model.Doctors;
import com.projects.mypillapp.Model.PillModel;
import com.projects.mypillapp.R;

import java.util.List;

public class DetailDocter extends AppCompatActivity {
    ViewPager viewPager;
    private Context mContext;
    private DBHelper dbHelper= new DBHelper(this);
    Doctors doctors;
    PillModel pillModel;
    private int[] img = {

            R.drawable.doctor1,
            R.drawable.doctor2,
            R.drawable.doctor3,
    };
    public static int id;
    public static String phone;
    public static String email;
    public static String address;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_docter);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(new PagerAdapter(getSupportFragmentManager()));
        SmartTabLayout viewPagerTab = (SmartTabLayout) findViewById(R.id.tab_doctor);
        viewPagerTab.setViewPager(viewPager);
        mContext = this;
        TextView name = (TextView)findViewById(R.id.txt_name);
        ImageView imageView = (ImageView)findViewById(R.id.img_doctor);

        id = getIntent().getExtras().getInt("id");
        doctors = dbHelper.getDoctorId(id);
        Glide.with(this)
                .load(img[doctors.image]).into(imageView);
        name.setText(doctors.name);

        phone = doctors.phone;
        email = doctors.email;
        address = doctors.address;

    }

    static class PagerAdapter extends FragmentStatePagerAdapter {

        final int PAGE_COUNT = 3;
        private String tabTitles[] = new String[] { "รายละเอียด", "รายการยา","รายการนัด"};

        public PagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        @Override
        public Fragment getItem(int position) {
            if (position == 0)
                return new DetailDoctorFragment();

            else if (position == 1)
                return new PillFragment();
            else if (position == 2)
                return new EventFragment();

            return null;
        }

        @Override
        public int getCount() {
            return PAGE_COUNT;
        }
        @Override
        public CharSequence getPageTitle(int position) {
            // Generate title based on item position
            return tabTitles[position];
        }

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
                        dbHelper.deleteDoctor(doctors.id);
                        updateDoctorPill();
                        Intent intent = new Intent(getApplicationContext(), ListDoctor.class);
                        startActivity(intent);
                        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                        finish();
                    }

                }).show();
    }

    private void updateDoctorPill() {
        List<PillModel> alarms = dbHelper.getPillAll();
        if (alarms != null){
            List<PillModel> pills =  dbHelper.getDoctorPill(doctors.id);
            for (PillModel pill : pills){
                pillModel = dbHelper.getPill(pill.id);
                pillModel.id_doctor = 0;
                dbHelper.updatePill(pillModel);
            }
        }

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
                deleteEvent(doctors.id);
                break;
            case R.id.edit:
                Intent intent = new Intent(this, NewDoctorActivity.class);
                intent.putExtra("id", doctors.id);
                startActivity(intent);
                finish();
                overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}

