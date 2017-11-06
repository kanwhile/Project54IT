package com.projects.mypillapp.Model;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.projects.mypillapp.R;
import com.projects.mypillapp.activity.DetailDocter;

import java.util.List;

/**
 * Created by นนทรักษ์ on 4/11/2558.
 */
public class AdapterDoctor extends RecyclerView.Adapter<AdapterDoctor.ViewHolder>{

    private Context mContext;
    private List<Doctors> items ;
    private int rowLayout;

    private int[] img = {

            R.drawable.doctor1,
            R.drawable.doctor2,
            R.drawable.doctor3,
    };
    public AdapterDoctor(List<Doctors> items, int rowLayout, Context context) {
        this.items = items;
        this.rowLayout = rowLayout;
        this.mContext = context;
    }



    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Doctors doctors = items.get(position);
        holder.txtName.setText(doctors.name);
        Glide.with(mContext)
                .load(img[doctors.image]).into(holder.imgPill);
        holder.container.setOnClickListener(onClickListener(position));

    }

    @Override
    public int getItemCount() {
        if (items != null){
            return items.size();
        }
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView txtName;
        public ImageView imgPill;
        private LinearLayout container;
        public ViewHolder(View itemView) {
            super(itemView);
            txtName = (TextView)itemView.findViewById(R.id.txt_name);
            imgPill = (ImageView)itemView.findViewById(R.id.img_doctor);
            container = (LinearLayout)itemView.findViewById(R.id.view_doctor);

        }

    }


    private View.OnClickListener onClickListener(final int position) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(),DetailDocter.class);
                intent.putExtra("id", items.get(position).id);
                v.getContext().startActivity(intent);
            }
        };
    }

}
