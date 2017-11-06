package com.projects.mypillapp.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.projects.mypillapp.Fragment.Info1Fragment;
import com.projects.mypillapp.Fragment.Info2Fragment;
import com.projects.mypillapp.MainActivity;
import com.projects.mypillapp.R;
import com.viewpagerindicator.CirclePageIndicator;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class InfoPillActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_pill);

        initViewPagerAndTabs();
    }
    private void initViewPagerAndTabs() {
        CirclePageIndicator mCirclePageIndicator = (CirclePageIndicator) findViewById(R.id.indicator);
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        PagerAdapter pagerAdapter = new PagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);
        mCirclePageIndicator.setViewPager(viewPager);
    }

    public static class PagerAdapter extends FragmentStatePagerAdapter {

        final int PAGE_COUNT = 6;
        public static final String ARGS_POSITION = "name";
        public PagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        @Override
        public Fragment getItem(int position) {

            Fragment fragment = new Info1Fragment();
            Bundle args = new Bundle();
            args.putInt(ARGS_POSITION, position);
            fragment.setArguments(args);

            return fragment;
        }

        @Override
        public int getCount() {
            return PAGE_COUNT;
        }

    }
    @Override
    protected void attachBaseContext(Context newBase){
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
    @Override
    public void onBackPressed() {
        startActivity(new Intent(this,MainActivity.class));
        this.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        super.onBackPressed();
    }
}
