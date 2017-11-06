package com.projects.mypillapp.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.telephony.PhoneStateListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.projects.mypillapp.Model.DBHelper;
import com.projects.mypillapp.Model.PillAdapter;
import com.projects.mypillapp.R;
import com.projects.mypillapp.activity.DetailDocter;
import com.projects.mypillapp.activity.NewPillActivity;


/**
 * A simple {@link Fragment} subclass.
 */
public class PillFragment extends Fragment {
    private DBHelper dbHelper;
    private RecyclerView rv;

    public PillFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pill_list, container, false);
        dbHelper = new DBHelper(getActivity().getApplicationContext());
        dbHelper = new DBHelper(getActivity().getApplicationContext());
        rv = (RecyclerView) view.findViewById(R.id.view3);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        rv.setItemAnimator(new DefaultItemAnimator());
        int id = DetailDocter.id;
        PillAdapter mAdapter = new PillAdapter(dbHelper.getPillDoctor(id),R.layout.listview_row_pill,getActivity());
        rv.setAdapter(mAdapter);


/*
        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity().getApplicationContext(), NewPillActivity.class);
                intent.putExtra("id", -1);
                startActivity(intent);
            }
        });
        */
        return view;
    }

}
