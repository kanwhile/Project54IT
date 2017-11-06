package com.projects.mypillapp.Model;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.projects.mypillapp.R;
import com.projects.mypillapp.activity.PillDetail;

import java.util.List;

/**
 * Created by นนทรักษ์ on 4/11/2558.
 */
public class PillAdapter extends RecyclerView.Adapter<PillAdapter.ViewHolder>{

    private Context mContext;
    private List<PillModel> items ;
    private int rowLayout;


    public PillAdapter(List<PillModel> items, int rowLayout, Context context) {
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
        PillModel pill = items.get(position);
        holder.txtName.setText("ชื่อยา : "+pill.name_pill);
        holder.txtFor.setText("ทานครั้งล่ะ : " + pill.for_pill);
        holder.txtEat.setText("ทาน : " + pill.eat);
        Glide.with(mContext)
                .load(pill.pillImg).into(holder.imgPill);
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

        public TextView txtName,txtFor,txtEat;
        public ImageView imgPill;
        private LinearLayout container;
        public ViewHolder(View itemView) {
            super(itemView);
            txtName = (TextView)itemView.findViewById(R.id.pill_name);
            txtFor = (TextView)itemView.findViewById(R.id.pill_for);
            txtEat = (TextView)itemView.findViewById(R.id.pill_eat);
            imgPill = (ImageView)itemView.findViewById(R.id.img_pill);
            container = (LinearLayout)itemView.findViewById(R.id.linear_pill_list);

        }

    }


    private View.OnClickListener onClickListener(final int position) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(),PillDetail.class);
                intent.putExtra("id", items.get(position).id);
                v.getContext().startActivity(intent);
            }
        };
    }

}
