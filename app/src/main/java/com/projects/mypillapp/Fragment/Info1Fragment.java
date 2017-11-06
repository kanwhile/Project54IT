package com.projects.mypillapp.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.projects.mypillapp.R;
import com.projects.mypillapp.activity.InfoPillActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class Info1Fragment extends Fragment {


    public Info1Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_info1, container, false);
        int position = 0;
        int[] img = {

                R.drawable.info1,
                R.drawable.info2,
                R.drawable.info3,
                R.drawable.info4,
                R.drawable.info5,
                R.drawable.info6
        };
        Bundle bundle = getArguments();

        position = bundle.getInt(InfoPillActivity.PagerAdapter.ARGS_POSITION);

        ImageView imageView = (ImageView)view.findViewById(R.id.img_info);
        Glide.with(getActivity())
                .load(img[position%img.length]).into(imageView);
        return view;
    }


}
