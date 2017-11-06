package com.projects.mypillapp.Alarm;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import com.projects.mypillapp.Model.DBHelper;
import com.projects.mypillapp.Model.PillModel;

import java.util.Calendar;
import java.util.List;

/**
 * Created by นนทรักษ์ on 16/11/2558.
 */
public class AlarmPillManager extends BroadcastReceiver{
    public static final String ID = "id";

    @Override
    public void onReceive(Context context, Intent intent) {
        setAlarms(context);
    }

    public static void setAlarms(Context context) {
        MyReceiverHour.cancelAlarms(context);
        DBHelper dbHelper = new DBHelper(context);
        List<PillModel> alarms = dbHelper.getPillAll();
        if (alarms != null){
            for (PillModel alarm : alarms){
                if (alarm.eat .equals("ก่อนอาหาร")){
                    MyReceiverBefore.setAlarms(context);
                    Log.d("MainActivity1", alarm.eat);
                    break;
                }

            }
            for (PillModel alarm : alarms){
                if (alarm.eat .equals( "หลังอาหาร")){
                    MyReceiverAfter.setAlarms(context);
                    Log.d("MainActivity1", alarm.eat);
                    break;
                }
            }
            MyReceiverHour.setAlarms(context);
        }
    }

}
