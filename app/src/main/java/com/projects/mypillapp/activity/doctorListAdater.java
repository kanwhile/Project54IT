package com.projects.mypillapp.activity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.projects.mypillapp.Model.Doctors;
import com.projects.mypillapp.R;

import java.util.List;

/**
 * Created by นนทรักษ์ on 11/12/2558.
 */
public class doctorListAdater extends BaseAdapter {
    private int[] img = {

            R.drawable.doctor1,
            R.drawable.doctor2,
            R.drawable.doctor3,
    };
    private Context mContext;
    private List<Doctors> mDoctor;

    public doctorListAdater(Context context, List<Doctors>doctorses) {
        mContext = context;
        mDoctor = doctorses;
    }
    public void setAlarms(List<Doctors> doctorses) {
        mDoctor = doctorses;
    }
    @Override
    public int getCount() {
        if (mDoctor != null) {
            return mDoctor.size();
        }
        return 0;
    }


    @Override
    public Object getItem(int position) {
        if (mDoctor != null) {
            return mDoctor.get(position);
        }
        return null;
    }


    @Override
    public long getItemId(int position) {
        if (mDoctor != null) {
            return mDoctor.get(position).id;
        }
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.listview_row_doctor, parent, false);
        }

        Doctors doctors = (Doctors) getItem(position);
        TextView txtTime = (TextView) convertView.findViewById(R.id.txt_name);
        ImageView imgPill = (ImageView) convertView.findViewById(R.id.img_doctor);
        txtTime.setText(doctors.name);
        Glide.with(mContext)
                .load(img[doctors.image]).into(imgPill);

        return convertView;
    }

}
