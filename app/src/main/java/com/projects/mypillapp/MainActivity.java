package com.projects.mypillapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.github.amlcurran.showcaseview.ShowcaseView;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.projects.mypillapp.Alarm.AlarmPillManager;
import com.projects.mypillapp.Fragment.MainFragment;
import com.projects.mypillapp.activity.InfoPillActivity;
import com.projects.mypillapp.activity.ListDoctor;
import com.projects.mypillapp.activity.ListEvent;
import com.projects.mypillapp.activity.ListPill;
import com.projects.mypillapp.activity.NewDoctorActivity;
import com.projects.mypillapp.activity.NewEvent;
import com.projects.mypillapp.activity.NewPillActivity;
import com.projects.mypillapp.activity.NewPillHour;
import com.projects.mypillapp.activity.SettingTime;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private FloatingActionButton newEvent, newPill, newdoctor;
    private Handler mUiHandler = new Handler();
    private ShowcaseView mShowcaseView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AlarmPillManager.setAlarms(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_main);
        setView();

        final FloatingActionMenu fab = (FloatingActionMenu) findViewById(R.id.fab);
        newEvent = (FloatingActionButton) findViewById(R.id.fab1);
        newPill = (FloatingActionButton) findViewById(R.id.fab2);
        newdoctor = (FloatingActionButton) findViewById(R.id.fab3);
        newEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), NewEvent.class);
                intent.putExtra("id", -1);
                startActivity(intent);
            }
        });
        newPill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setSelectType();
            }
        });
        newdoctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), NewDoctorActivity.class);
                intent.putExtra("id", -1);
                startActivity(intent);
            }
        });
        fab.setClosedOnTouchOutside(true);
        fab.hideMenuButton(false);
        mUiHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                fab.showMenuButton(true);
            }
        }, 140);
    }

    public void setSelectType () {
        String[] TYPE = { "ยาก่อน-หลังอาหาร","ทานทุก 4-6 ช.ม."};
        AlertDialog.Builder builder =
                new AlertDialog.Builder(MainActivity.this);
        builder.setItems(TYPE, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0){
                    Intent intent = new Intent(getApplicationContext(), NewPillActivity.class);
                    intent.putExtra("id", -1);
                    startActivity(intent);
                }else {
                    Intent intent = new Intent(getApplicationContext(), NewPillHour.class);
                    intent.putExtra("id", -1);
                    startActivity(intent);
                }
            }
        });
        builder.create();
        builder.show();

    }

    public void setView(){
        MainFragment fragment = new MainFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.fragment_my, fragment).commit();
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        switch (item.getItemId()){
            case R.id.nav_share :
                startActivity(new Intent(getApplicationContext(),InfoPillActivity.class));
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                finish();
                break;
            case R.id.nav_time :
                startActivity(new Intent(getApplicationContext(),SettingTime.class));
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                finish();
                break;
            case R.id.nav_pill :
                startActivity(new Intent(getApplicationContext(),ListPill.class));
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                finish();
                break;
            case R.id.nav_event :
                startActivity(new Intent(getApplicationContext(), ListEvent.class));
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                finish();
                break;
            case R.id.nav_disease :
                Intent intent = new Intent(getApplicationContext(), ListDoctor.class);
                startActivity(intent);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                finish();
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        drawer.closeDrawers();
        return true;
    }
    @Override
    public void onBackPressed() {
        this.moveTaskToBack(true);

    }
}
