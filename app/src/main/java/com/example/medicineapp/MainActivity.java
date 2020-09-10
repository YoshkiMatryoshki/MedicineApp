package com.example.medicineapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.room.Room;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.medicineapp.database.MedCoursesDatabase;
import com.example.medicineapp.database.MedicineTakeToUser;
import com.example.medicineapp.notificationstuff.AlarmReciever;
import com.example.medicineapp.notificationstuff.MyNotificationManager;
import com.google.android.material.navigation.NavigationView;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration; //for swap fragments by navBar
    private PendingIntent pendingIntent; // for alarms

    public static MedCoursesDatabase database;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);

        appBarConfiguration = new AppBarConfiguration.Builder(R.id.nav_nearest,R.id.nav_configure).
                setDrawerLayout(drawer).
                build();

        NavController navController = Navigation.findNavController(this,R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this,navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        //DataBase!
        database = Room.databaseBuilder(getApplicationContext(), MedCoursesDatabase.class, "courses")
                .allowMainThreadQueries()
                .build();

        //Sync with DB each day at 14
        SetAlarmManager();


    }

    private void SetAlarmManager() {
        //get startSync and endSync from calendar
        SyncCalendar syncCalendar = new SyncCalendar();
        long startSync = syncCalendar.getSyncStart();
        long endSync = syncCalendar.getSyncEnd();


        //Create intent and fill it with extras
        final Intent alarmIntent = new Intent(getApplicationContext(), AlarmReciever.class);
        alarmIntent.putExtra("SyncTimeStart", startSync);
        alarmIntent.putExtra("SyncTimeEnd", endSync);
        pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, alarmIntent, 0);
        AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);

        //SetAlarm
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, startSync, AlarmManager.INTERVAL_DAY, pendingIntent);
        //alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP,SystemClock.elapsedRealtime() + 5 * 1000, pendingIntent);
    }
    public void CreateNotificationsById(String courseName) {
        //Add notification for new records
        long currentTime = System.currentTimeMillis();
        List<MedicineTakeToUser> todayRecords = MainActivity.database.medicineTakeInfoDAO()
                .getAllInBetweenRecordsByID(currentTime, SyncCalendar.getSyncEndByDate(currentTime), courseName);
        MyNotificationManager.CreateAlarm(todayRecords,getApplicationContext());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);

        MenuItem cancelButton = menu.findItem(R.id.stop_alarms);
        cancelButton.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {

                AlarmManager manager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
                manager.cancel(pendingIntent);
                Toast.makeText(getApplicationContext(), "Cancelled", Toast.LENGTH_SHORT).show();
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this,R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, appBarConfiguration) || super.onSupportNavigateUp();
        //return super.onSupportNavigateUp();
    }


}