package com.example.medicineapp.notificationstuff;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.util.Log;

import com.example.medicineapp.database.MedicineTakeToUser;

import java.text.SimpleDateFormat;
import java.util.List;

public class MyNotificationManager {
    public static void CreateAlarm(List<MedicineTakeToUser> records, Context context){
        for (MedicineTakeToUser record : records){
            Intent newIntent = new Intent(context, NotificationReciever.class);
            newIntent.putExtra("id",record.id);
            newIntent.putExtra("medName", record.medName);
            newIntent.putExtra("Dose", record.medDose);

            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
            String takeDay =  sdf.format(record.takeDay);
            newIntent.putExtra("takeTime",takeDay);

            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, record.id, newIntent, 0);
            AlarmManager manager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);

            manager.set(AlarmManager.RTC_WAKEUP, record.takeDay.getTime(), pendingIntent);
            Log.i("AlarmsNNotifications", String.format("Created alarm for %s, id: %d",record.medName,record.id));
        }
    }
    //Disable all alarms of deleted course
    public static void DisableAlarms(int id, Context context){
        Intent newIntent = new Intent(context, NotificationReciever.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context,id,newIntent,0);
        AlarmManager manager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        if (manager != null){
            manager.cancel(pendingIntent);
        }
    }
}
