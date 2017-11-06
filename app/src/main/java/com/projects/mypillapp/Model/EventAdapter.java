package com.projects.mypillapp.Model;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.projects.mypillapp.activity.EventDetail;
import com.projects.mypillapp.R;


import java.util.List;

/**
 * Created by นนทรักษ์ on 4/11/2558.
 */
public class EventAdapter extends RecyclerView.Adapter<EventAdapter.ViewHolder>{

    private Context mContext;
    private List<EventModel> items ;
    private int rowLayout;

    String Months[] = {
            "ม.ค", "ก.พ", "มี.ค", "เม.ย",
            "พ.ค", "มิ.ย", "ก.ค", "ส.ค",
            "ก.ย", "ต.ค", "พ.ย", "ธ.ค"};

    public EventAdapter(List<EventModel> items, int rowLayout, Context context) {
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
        EventModel event = items.get(position);
        holder.txtName.setText(event.name_event);
        holder.txtLocation.setText("สถานที่ : "+event.location);
        holder.txtTime.setText(String.format("%02d:%02d",event.hour,event.minute));
        holder.txtDay.setText(String.format("%s %s %s", event.day, Months[event.month], event.year + 543));
        Glide.with(mContext)
                .load(event.img_event).into(holder.imgEvent);
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

        public TextView txtName,txtLocation,txtTime,txtDay,txtDetail;
        public ImageView imgEvent;
        private LinearLayout container;
        public ViewHolder(View itemView) {
            super(itemView);
            txtName = (TextView)itemView.findViewById(R.id.pill_name);
            imgEvent = (ImageView)itemView.findViewById(R.id.img_event);
            txtLocation =(TextView)itemView.findViewById(R.id.txt_location);
            txtTime = (TextView)itemView.findViewById(R.id.txt_time);
            txtDay = (TextView)itemView.findViewById(R.id.txtDay);
            container =(LinearLayout) itemView.findViewById(R.id.view_event);
        }

    }


    private View.OnClickListener onClickListener(final int position) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(),EventDetail.class);
                intent.putExtra("id", items.get(position).id);
                v.getContext().startActivity(intent);
            }
        };
    }





    private void showText(TextView view, boolean isOn){
        if(isOn){
            view.setTextColor(ContextCompat.getColor(mContext, R.color.colorPrimary));
        }else {
            view.setTextColor(Color.GRAY);
        }
    }

}
