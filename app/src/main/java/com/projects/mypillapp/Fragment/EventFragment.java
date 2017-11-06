package com.projects.mypillapp.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.projects.mypillapp.Model.DBHelper;
import com.projects.mypillapp.Model.EventAdapter;
import com.projects.mypillapp.R;
import com.projects.mypillapp.activity.DetailDocter;
import com.projects.mypillapp.activity.NewEvent;


/**
 * A simple {@link Fragment} subclass.
 */
public class EventFragment extends Fragment {


    private DBHelper dbHelper;
    private RecyclerView rv;

    private EventAdapter mAdapter;
    private int id;
    public EventFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_event, container, false);
        dbHelper = new DBHelper(getActivity().getApplicationContext());

        id = DetailDocter.id;

        //lv = (ListView) view.findViewById(R.id.listView1);
        rv = (RecyclerView) view.findViewById(R.id.view3);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        rv.setItemAnimator(new DefaultItemAnimator());

        mAdapter = new EventAdapter(dbHelper.getEventDoctor(id),R.layout.listview_row_event,getActivity());
        rv.setAdapter(mAdapter);
/*
        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity().getApplicationContext(), NewEvent.class);
                intent.putExtra("id", -1);
                startActivity(intent);
            }
        });
*/
        return view;


    }

}
