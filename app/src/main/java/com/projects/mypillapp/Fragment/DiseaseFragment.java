package com.projects.mypillapp.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.projects.mypillapp.Model.DBHelper;
import com.projects.mypillapp.R;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class DiseaseFragment extends Fragment {

    DBHelper dbHelper ;
    public DiseaseFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_disease, container, false);
        dbHelper = new DBHelper(getActivity());
        ListView listView = (ListView)view.findViewById(R.id.list_disease);
        List<String> lables = dbHelper.getAllLabels();
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_selectable_list_item, lables);
        listView.setAdapter(dataAdapter);
        return view;
    }

}
