package com.projects.mypillapp.Alarm;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.projects.mypillapp.activity.ShowMedicineBefore;

public class MyServiceBefore extends Service {
    public static String TAG = MyServiceBefore.class.getSimpleName();

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null){
            Intent alarmIntent = new Intent(getBaseContext(), ShowMedicineBefore.class);
            alarmIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            alarmIntent.putExtras(intent);
            getApplication().startActivity(alarmIntent);

            AlarmPillManager.setAlarms(this);
        }
        Log.d("MainActivity1", "onStartCommandBefore");

        return super.onStartCommand(intent, flags, startId);
    }
}
