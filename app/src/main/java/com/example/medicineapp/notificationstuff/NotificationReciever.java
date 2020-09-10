package com.example.medicineapp.notificationstuff;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings;

import androidx.core.app.NotificationCompat;

import com.example.medicineapp.R;

public class NotificationReciever extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        //GET INFO FROM INTENT!!!
        int testId = intent.getIntExtra("id", -1);
        String medName = intent.getStringExtra("medName");
        String medDose = intent.getStringExtra("Dose");
        String medTime = intent.getStringExtra("takeTime");


        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "10")
                .setSmallIcon(R.drawable.ic_baseline_alarm_24)
                .setContentTitle(String.format("%s",medName))
                .setContentText(String.format("%s - %s",medDose, medTime))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setSound(Settings.System.DEFAULT_NOTIFICATION_URI);
        NotificationManager notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        //NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("10", "MedicineNotifications", NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(channel);
            builder.setChannelId("10");

        }
        notificationManager.notify(testId,builder.build());
    }
}
