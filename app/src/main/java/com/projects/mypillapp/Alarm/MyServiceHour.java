package com.projects.mypillapp.Alarm;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.projects.mypillapp.activity.ShowMedicineBefore;
import com.projects.mypillapp.activity.ShowMedicineHour;

public class MyServiceHour extends Service {
    public static String TAG = MyServiceHour.class.getSimpleName();

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null) {
            Intent alarmIntent = new Intent(getBaseContext(), ShowMedicineHour.class);
            alarmIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            alarmIntent.putExtras(intent);
            getApplication().startActivity(alarmIntent);

            MyReceiverHour.setAlarms(this);
        }
        return super.onStartCommand(intent, flags, startId);
    }
}
