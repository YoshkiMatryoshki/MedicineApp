package com.example.medicineapp.database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "takeinfo")
public class MedicineTakeInfo {

    @PrimaryKey(autoGenerate = true)
    public int id;

    public int medicineId;

    public Date takeDay;

    //0 - dont answered, 1 - taken, -1 - dont (left swipe)
    public int isTaken;
}
