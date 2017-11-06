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

public class MyReceiverAfter extends BroadcastReceiver {
    public static final String ID = "id";
    public static final String EAT = "eat";

    @Override
    public void onReceive(Context context, Intent intent) {
        setAlarms(context);
        Log.d("MainActivity1", "onReceive");
    }

    public static void setAlarms(Context context) {
        cancelAlarms(context);
        SharedPreferences sp;
        final String PREF_NAME = "TimeSetting";
        final String KEY_HOUR_BREAK = "HourBreak";
        final String KEY_MIN_BREAK = "MinBreak";
        final String KEY_HOUR_LUNCH = "HourLunch";
        final String KEY_MIN_LUNCH = "MinLunch";
        final String KEY_HOUR_DINNER = "HourDinner";
        final String KEY_MIN_DINNER = "MinDinner";
        final String KEY_HOUR_NIGHR = "HourNight";
        final String KEY_MIN_NIGHT = "MinNight";
        DBHelper dbHelper = new DBHelper(context);

        boolean alarmSet = false;
        sp = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);

        if (!alarmSet) {
            Log.d("MainActivity1", "Breakfast");
            PendingIntent pIntent = createPendingIntent(context, 1);
            int Hour = sp.getInt(KEY_HOUR_BREAK, 7);
            int Minute = sp.getInt(KEY_MIN_BREAK, 00);

            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.HOUR_OF_DAY, Hour);
            calendar.set(Calendar.MINUTE, Minute);
            calendar.set(Calendar.SECOND, 00);
            //Find next time to set
            final int nowHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
            final int nowMinute = Calendar.getInstance().get(Calendar.MINUTE);
            Log.d("MainActivity1", calendar.toString());
            List<PillModel> breakfast = dbHelper.getPillAll();
            for (PillModel alarm : breakfast) {
                if (alarm.breakfast) {
                    if (!(Hour < nowHour) && !(Hour == nowHour && Minute <= nowMinute)) {
                        Log.d("MainActivity1", "Time Breakfast");
                        setAlarm(context, calendar, pIntent);
                        alarmSet = true;
                    }
                    break;
                }

            }
        }
        if (!alarmSet) {
            Log.d("MainActivity1", "Lunch");
            PendingIntent pIntent = createPendingIntent(context, 2);
            int Hour = sp.getInt(KEY_HOUR_LUNCH, 11);
            int Minute = sp.getInt(KEY_MIN_LUNCH, 00);

            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.HOUR_OF_DAY, Hour);
            calendar.set(Calendar.MINUTE, Minute);
            calendar.set(Calendar.SECOND, 00);
            //Find next time to set

            final int nowHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
            final int nowMinute = Calendar.getInstance().get(Calendar.MINUTE);

            List<PillModel> lunch = dbHelper.getPillAll();
            for (PillModel alarmL : lunch) {
                if (alarmL.lunch) {
                    if (!(Hour < nowHour) && !(Hour == nowHour && Minute <= nowMinute)) {
                        Log.d("MainActivity1", "Time Lunch");
                        setAlarm(context, calendar, pIntent);
                        alarmSet = true;
                    }
                    break;
                }
            }
        }
        if (!alarmSet) {
            Log.d("MainActivity1", "Dinner");
            PendingIntent pIntent = createPendingIntent(context, 3);
            int Hour = sp.getInt(KEY_HOUR_DINNER, 18);
            int Minute = sp.getInt(KEY_MIN_DINNER, 00);

            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.HOUR_OF_DAY, Hour);
            calendar.set(Calendar.MINUTE, Minute);
            calendar.set(Calendar.SECOND, 00);
            //Find next time to set

            final int nowHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
            final int nowMinute = Calendar.getInstance().get(Calendar.MINUTE);

            List<PillModel> dinner = dbHelper.getPillAll();
            for (PillModel alarmD : dinner) {
                if (alarmD.dinner) {
                    if (!(Hour < nowHour) && !(Hour == nowHour && Minute <= nowMinute)) {
                        Log.d("MainActivity1", "Time DINNER");
                        setAlarm(context, calendar, pIntent);
                        alarmSet = true;
                    }
                    break;
                }
            }
        }

    }


    @SuppressLint("NewApi")
    private static void setAlarm(Context context, Calendar calendar, PendingIntent pIntent) {
        Log.d("MainActivity1", "setAlarm");
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pIntent);
        } else {
            alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pIntent);
        }
    }


    private static PendingIntent createPendingIntent(Context context, int alarm) {
        Intent intent = new Intent(context, MyServiceAfter.class);
        intent.putExtra(ID, "" + alarm);
        return PendingIntent.getService(context, alarm, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    public static void cancelAlarms(Context context) {
        for (int i = 1; i < 5; i++) {
            PendingIntent pIntent = createPendingIntent(context, i);
            Log.d("MainActivity1", "cancelAlarms" + i);
            AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            alarmManager.cancel(pIntent);
        }

    }
}