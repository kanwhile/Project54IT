package com.projects.mypillapp.Fragment;


import android.content.Intent;
import android.os.Bundle;
import com.github.clans.fab.FloatingActionButton;

import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;


import com.github.clans.fab.FloatingActionMenu;
import com.nhaarman.supertooltips.ToolTipRelativeLayout;
import com.nhaarman.supertooltips.ToolTipView;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.projects.mypillapp.R;
import com.projects.mypillapp.activity.NewEvent;
import com.projects.mypillapp.activity.NewPillActivity;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment {
    ViewPager viewPager;
    private FloatingActionButton fab1,fab2;
    private Animation fab_open,fab_close,rotate_forward,rotate_backward;
    private Boolean isFabOpen = false;

    private Handler mUiHandler = new Handler();


    private ToolTipRelativeLayout mToolTipFrameLayout;
    private ToolTipView mOrangeToolTipView;
    public MainFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        viewPager = (ViewPager) view.findViewById(R.id.viewpager);
        viewPager.setAdapter(new PagerAdapter(getChildFragmentManager()));
        SmartTabLayout viewPagerTab = (SmartTabLayout) view.findViewById(R.id.tab);
        viewPagerTab.setViewPager(viewPager);


        return view;
    }
/*
    public void animateFAB() {

        if(isFabOpen){

            fab.startAnimation(rotate_backward);
            fab1.startAnimation(fab_close);
            fab2.startAnimation(fab_close);
            fab1.setClickable(false);
            fab2.setClickable(false);
            isFabOpen = false;
            Log.d("Raj", "close");

        } else {

            fab.startAnimation(rotate_forward);
            fab1.startAnimation(fab_open);
            fab2.startAnimation(fab_open);
            fab1.setClickable(true);
            fab2.setClickable(true);
            isFabOpen = true;
            Log.d("Raj","open");

        }
    }
*/
    static class PagerAdapter extends FragmentStatePagerAdapter {

        final int PAGE_COUNT = 4;
        private String tabTitles[] = new String[] { "เช้า", "กลางวัน","เย็น","ก่อนนอน"};

        public PagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        @Override
        public Fragment getItem(int position) {
            if (position == 0)
                return new BreakfastFragment();
            else if (position == 1)
                return new LunchFragment();
            else if (position == 2)
                return new DinnerFragment();
            else if (position == 3)
                return new NightFragment();

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
    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String text = "";

            switch (v.getId()) {
                case R.id.fab1:
                    text = fab1.getLabelText();
                    break;
                case R.id.fab2:
                    text = fab2.getLabelText();
                    break;
            }
        }
    };
}
