package com.projects.mypillapp.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.projects.mypillapp.Model.DBHelper;
import com.projects.mypillapp.Model.MainPillAdapter;
import com.projects.mypillapp.Model.PillModel;
import com.projects.mypillapp.Model.ShowPillAdapter;
import com.projects.mypillapp.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class NightFragment extends Fragment {
    DBHelper dbHelper = new DBHelper(getActivity());

    public NightFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_night, container, false);

        RecyclerView recyclerView =(RecyclerView)view.findViewById(R.id.recyle_view_showpill);
        dbHelper = new DBHelper(getActivity().getApplicationContext());
        RecyclerViewNight(recyclerView);

        return view;
    }
    private void RecyclerViewNight(RecyclerView recyclerView) {
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        MainPillAdapter mAdapter = new MainPillAdapter(dbHelper.getPillNIGHT(null),getActivity());
        recyclerView.setAdapter(mAdapter);
    }

}
