package com.projects.mypillapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.projects.mypillapp.MainActivity;
import com.projects.mypillapp.Model.AdapterDoctor;
import com.projects.mypillapp.Model.DBHelper;
import com.projects.mypillapp.R;

public class ListDoctor extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DBHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_doctor);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_disease);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), NewDoctorActivity.class);
                intent.putExtra("id", -1);
                startActivity(intent);
            }
        });


        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyle_view_doctor);
        dbHelper = new DBHelper(this);
        setupRecyclerView(recyclerView);
    }
    private void setupRecyclerView(RecyclerView recyclerView) {
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        AdapterDoctor mAdapter = new AdapterDoctor(dbHelper.getDoctorAll(),R.layout.listview_row_doctor,this);
        recyclerView.setAdapter(mAdapter);
    }
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        switch (item.getItemId()){
            case R.id.nav_main :
                startActivity(new Intent(this,MainActivity.class));
                this.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                finish();
                break;
            case R.id.nav_event :
                startActivity(new Intent(this,ListEvent.class));
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                finish();
                break;
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
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        setTitle(item.getTitle());
        drawer.closeDrawers();
        return true;
    }
    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, MainActivity.class));
        this.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        super.onBackPressed();
    }
}
