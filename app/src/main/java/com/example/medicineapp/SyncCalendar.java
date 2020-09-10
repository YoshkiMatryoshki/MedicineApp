package com.example.medicineapp;

import java.util.Calendar;
import java.util.Date;

public class SyncCalendar {
    private static final int SYNC_HOUR = 14;
    private static final int SYNC_ADD_DAYS = 1;

    private long syncStart;
    private long syncEnd;

    private Date syncStartDate;
    private Date syncEndDate;

    public SyncCalendar(){
        Calendar syncCalendar = Calendar.getInstance();
        syncCalendar.setTimeInMillis(System.currentTimeMillis());
        syncCalendar.set(Calendar.HOUR_OF_DAY, SYNC_HOUR);

        syncStart = syncCalendar.getTimeInMillis();
        syncStartDate = syncCalendar.getTime();
        syncCalendar.add(Calendar.DAY_OF_MONTH, SYNC_ADD_DAYS);

        syncEnd = syncCalendar.getTimeInMillis();
        syncEndDate = syncCalendar.getTime();
    }

    public long getSyncStart(){
        return syncStart;
    }
    public  long getSyncEnd(){
        return syncEnd;
    }
    public static long getSyncEndByDate(Date start){
        Calendar syncCalendar = Calendar.getInstance();
        syncCalendar.setTime(start);
        syncCalendar.add(Calendar.DAY_OF_MONTH, SYNC_ADD_DAYS);
        syncCalendar.set(Calendar.HOUR_OF_DAY,SYNC_HOUR+1);

        return syncCalendar.getTimeInMillis();
    }
    public static long getSyncEndByDate(long startInMillis) {
        Calendar syncCalendar = Calendar.getInstance();
        syncCalendar.setTimeInMillis(startInMillis);
        syncCalendar.add(Calendar.DAY_OF_MONTH, SYNC_ADD_DAYS);
        syncCalendar.set(Calendar.HOUR_OF_DAY, SYNC_HOUR + 1);

        return syncCalendar.getTimeInMillis();
    }
    public Date getSyncStartDate(){
        return syncStartDate;
    }
    public Date getSyncEndDate(){
        return syncEndDate;
    }


}

