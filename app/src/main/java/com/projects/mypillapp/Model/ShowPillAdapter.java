package com.projects.mypillapp.Model;

import android.content.Context;
import android.content.Intent;
import android.graphics.LightingColorFilter;
import android.graphics.PorterDuff;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.projects.mypillapp.MainActivity;
import com.projects.mypillapp.R;

import java.util.Calendar;
import java.util.List;

/**
 * Created by นนทรักษ์ on 9/11/2558.
 */
public class ShowPillAdapter extends RecyclerView.Adapter<ShowPillAdapter.ViewHolder>{

    private Context mContext;
    private List<PillModel> items ;
    PillModel pill;

    public ShowPillAdapter(List<PillModel> items,Context context) {
        this.items = items;
        this.mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.show_pill_box, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        pill = items.get(position);
        holder.txtName.setText(pill.name_pill);
        holder.txtEat.setText(pill.eat);
        holder.txtFor.setText(pill.for_pill+"  "+pill.typepill);
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

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView txtName,txtFor,txtEat;
        public ImageView imgPill;
        private Button container;
        public ViewHolder(View itemView) {
            super(itemView);
            txtName = (TextView)itemView.findViewById(R.id.name_pill);
            txtFor = (TextView)itemView.findViewById(R.id.pill_for);
            imgPill = (ImageView)itemView.findViewById(R.id.img_pill);
            txtEat = (TextView)itemView.findViewById(R.id.pill_eat);
            container =(Button) itemView.findViewById(R.id.btn_take);
        }
    }

    private View.OnClickListener onClickListener(final int position) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                items.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, items.size());
                addPillHistory();
                if (getItemCount() == 0){
                    mContext.startActivity(new Intent(mContext,MainActivity.class));

                }
            }
        };
    }


    private void addPillHistory() {
        Pill_history newHistory = new  Pill_history();
        String date = (DateFormat.format("dd-MM-yyyy kk:mm:ss", new java.util.Date()).toString());
        newHistory.pill_id = pill.id;
        newHistory.timestamp = date;
        newHistory.dose = pill.for_pill;
        final int nowHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        if (nowHour >= 7 && 11 >= nowHour){
            newHistory.pill_time = 1+"";
        }
        if ( nowHour >= 11 && nowHour < 18 ){
            newHistory.pill_time = 2+"";
        }
        if (nowHour >= 18 && nowHour < 21){
            newHistory.pill_time = 3+"";
        }else {
            newHistory.pill_time = 4+"";
        }

        DBHelper dbHelper = new DBHelper(mContext);
        dbHelper.createPillHistory(newHistory);

    }

}
