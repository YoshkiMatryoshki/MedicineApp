package com.example.medicineapp.notificationstuff;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.medicineapp.MainActivity;
import com.example.medicineapp.R;
import com.example.medicineapp.database.MedicineTakeToUser;

import java.util.List;

public class AlarmReciever extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        //Toast.makeText(context, "LETSGOOOO", Toast.LENGTH_SHORT).show();
        //Notification
        Log.i("AlarmsNNotifications", "Sync with DB and set bunch of new alarms");
        long startSync = intent.getLongExtra("SyncTimeStart", 0);
        long endSync = intent.getLongExtra("SyncTimeEnd",0);
        long current_time = System.currentTimeMillis();



        List<MedicineTakeToUser> recordsToNotify = MainActivity.database.medicineTakeInfoDAO().getAllInBetweenRecords(current_time,endSync);
        MyNotificationManager.CreateAlarm(recordsToNotify, context.getApplicationContext());


    }
}
