package com.projects.mypillapp.Alarm;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.projects.mypillapp.Model.DBHelper;
import com.projects.mypillapp.Model.PillModel;

import java.util.Calendar;
import java.util.List;

public class MyReceiverHour extends BroadcastReceiver {
    public static final String ID = "id";

    @Override
    public void onReceive(Context context, Intent intent) {
        setAlarms(context);
    }
    public static void setAlarms(Context context) {
        cancelAlarms(context);
        DBHelper dbHelper = new DBHelper(context);

        List<PillModel> alarms =  dbHelper.getPillAll();
        for (PillModel alarm : alarms) {
            if (alarm.id != 0 && alarm.hourly > 0) {
                PendingIntent pIntent = createPendingIntent(context, alarm);


                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.HOUR_OF_DAY, alarm.hour);
                calendar.set(Calendar.MINUTE, alarm.minute);
                calendar.set(Calendar.SECOND, 00);
                final int nowHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
                final int nowMinute = Calendar.getInstance().get(Calendar.MINUTE);
                if (!(alarm.hour < nowHour)&&!(alarm.hour == nowHour && alarm.minute <= nowMinute)){
                    setAlarm(context, calendar, pIntent);
                    Log.d("MainActivity1", alarm.name_pill);
                    Log.d("MainActivity1", calendar.toString());

                }
/*
                int h = 00;
                int m = 26;
                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.HOUR_OF_DAY, h);
                calendar.set(Calendar.MINUTE, m);
                calendar.set(Calendar.SECOND, 00);
                final int nowHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
                final int nowMinute = Calendar.getInstance().get(Calendar.MINUTE);
                if (!(h < nowHour)&&!(h == nowHour && m <= nowMinute)){
                    setAlarm(context, calendar, pIntent);
                    Log.d("MainActivity1", alarm.name_pill);
                    Log.d("MainActivity1", calendar.toString());

                }*/
            }
        }
    }

    @SuppressLint("NewApi")
    private static void setAlarm(Context context, Calendar calendar, PendingIntent pIntent) {
        Log.d("MainActivity1", "setAlarm555");
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pIntent);
        } else {
            alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pIntent);
        }
    }
    public static void cancelAlarms(Context context) {
        DBHelper dbHelper = new DBHelper(context);

        List<PillModel> alarms =  dbHelper.getPillAll();
        if (alarms != null) {
            for (PillModel alarm : alarms) {
                if (alarm.hourly > 0) {
                    PendingIntent pIntent = createPendingIntent(context, alarm);
                    AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
                    alarmManager.cancel(pIntent);
                }
            }
        }
    }

    private static PendingIntent createPendingIntent(Context context, PillModel model) {
        Intent intent = new Intent(context, MyServiceHour.class);
        intent.putExtra(ID, model.id+"");
        return PendingIntent.getService(context,(int) model.id, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }
}
