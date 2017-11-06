package com.projects.mypillapp.Alarm;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.projects.mypillapp.activity.ShowMedicneAfter;

public class MyServiceAfter extends Service {
    public static String TAG = MyServiceAfter.class.getSimpleName();

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null) {
            Intent alarmIntent = new Intent(getBaseContext(), ShowMedicneAfter.class);
            alarmIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            alarmIntent.putExtras(intent);
            getApplication().startActivity(alarmIntent);

            AlarmPillManager.setAlarms(this);
        }
        Log.d("MainActivity1", "onStartCommand");

        return super.onStartCommand(intent, flags, startId);
    }
}
