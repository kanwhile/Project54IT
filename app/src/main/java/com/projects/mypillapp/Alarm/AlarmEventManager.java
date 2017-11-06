package com.projects.mypillapp.Alarm;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.projects.mypillapp.Model.DBHelper;
import com.projects.mypillapp.Model.EventModel;

import java.util.Calendar;
import java.util.List;

/**
 * Created by นนทรักษ์ on 2/11/2558.
 */
public class AlarmEventManager extends BroadcastReceiver {
    public static final String ID = "id";
    public static final String NAME = "name";
    public static final String LOCATION = "location";
    public static final String DETAIL = "detail";
    public static final String IMG_EVENT = "img";

    @Override
    public void onReceive(Context context, Intent intent) {
        setAlarms(context);
        Log.d("MainActivity1", "onReceive");
    }
    public static void setAlarms(Context context) {
        cancelAlarms(context);

        DBHelper dbHelper = new DBHelper(context);

        List<EventModel> alarms =  dbHelper.getEventAll();
        Log.d("MainActivity1", "AlarmModel");
        for (EventModel alarm : alarms) {
            if (alarm.id > 0) {

                PendingIntent pIntent = createPendingIntent(context, alarm);

                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.DAY_OF_MONTH,alarm.day);
                calendar.set(Calendar.HOUR_OF_DAY, alarm.hour);
                calendar.set(Calendar.MINUTE, alarm.minute);
                calendar.set(Calendar.SECOND, 00);
                //Find next time to set
                final int nowDay = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
                final int nowMonth = Calendar.getInstance().get(Calendar.MONTH);
                final int nowYear = Calendar.getInstance().get(Calendar.YEAR);
                final int nowHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
                final int nowMinute = Calendar.getInstance().get(Calendar.MINUTE);
                if (nowDay == alarm.day){
                    Log.d("MainActivity1", "day " + alarm.day);
                    if (nowMonth == alarm.month){
                        Log.d("MainActivity1", " month  " +alarm.month);
                        if (nowYear == alarm.year){
                            Log.d("MainActivity1", "  year "+alarm.year );
                            if (!(alarm.day == nowDay && alarm.hour < nowHour)&& !(alarm.day == nowDay && alarm.hour == nowHour && alarm.minute <= nowMinute)){
                                setAlarm(context, calendar, pIntent);
                                Log.d("MainActivity1", "  setAlarm " );
                            }
                        }
                    }
                }
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

        List<EventModel> alarms =  dbHelper.getEventAll();
        Log.d("MainActivity1", "cancelAlarms");
        if (alarms != null) {
            for (EventModel alarm : alarms) {
                if (alarm.id>=1) {
                    PendingIntent pIntent = createPendingIntent(context, alarm);
                    Log.d("MainActivity1", "AlarmManager");
                    AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
                    alarmManager.cancel(pIntent);
                }
            }
        }
    }

    private static PendingIntent createPendingIntent(Context context, EventModel model) {
        Intent intent = new Intent(context, AlarmServiceEvent.class);
        intent.putExtra(ID, model.id);
        intent.putExtra(NAME, model.name_event);
        intent.putExtra(LOCATION, model.location);
        intent.putExtra(DETAIL, model.detail_event);
        intent.putExtra(IMG_EVENT,model.img_event);
        return PendingIntent.getService(context, (int) model.id, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }
}
