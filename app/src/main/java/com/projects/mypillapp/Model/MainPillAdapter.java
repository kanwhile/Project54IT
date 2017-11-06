package com.projects.mypillapp.Model;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.projects.mypillapp.R;

import java.util.List;

/**
 * Created by นนทรักษ์ on 9/11/2558.
 */
public class MainPillAdapter extends RecyclerView.Adapter<MainPillAdapter.ViewHolder>{

    private Context mContext;
    private List<PillModel> items ;
    PillModel pill;

    public MainPillAdapter(List<PillModel> items, Context context) {
        this.items = items;
        this.mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_pill_box, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        pill = items.get(position);
        holder.txtName.setText(pill.name_pill);
        holder.txtEat.setText(pill.eat);
        holder.txtFor.setText("ครั้งล่ะ : " + pill.for_pill);
        Glide.with(mContext)
                .load(pill.pillImg).into(holder.imgPill);
    }

    @Override
    public int getItemCount() {
        if (items != null){
            return items.size();
        }
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView txtName,txtFor,txtEat,container;
        public ImageView imgPill;
        //private View container;
        public ViewHolder(View itemView) {
            super(itemView);
            txtName = (TextView)itemView.findViewById(R.id.pill_name);
            txtFor = (TextView)itemView.findViewById(R.id.pill_for);
            imgPill = (ImageView)itemView.findViewById(R.id.img_pill);
            txtEat = (TextView)itemView.findViewById(R.id.pill_eat);
            container =(TextView) itemView.findViewById(R.id.btn_take);
        }
    }

    private void updateText(TextView view, int isOn) {
       switch (isOn){
           case 0 : view.setText("ก่อนอาหาร");
               break;
           case 1 : view.setText("หลังอาหาร");
               break;
           case 2 : view.setText("พร้อมอาหาร");
       }
    }



}
