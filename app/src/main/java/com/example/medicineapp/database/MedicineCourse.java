package com.example.medicineapp.database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "courses")
public class MedicineCourse {
    @PrimaryKey(autoGenerate = true)
    public int id;

    public String medName;

    public String medDose;

    public int dayCount;

    public Date startCourse;

    public Date endCourse;

}
