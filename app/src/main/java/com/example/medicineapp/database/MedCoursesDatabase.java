package com.example.medicineapp.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;

@Database(entities = {MedicineCourse.class,MedicineTakeInfo.class}, version = 1)
@TypeConverters({DateConverters.class})
public abstract class MedCoursesDatabase extends RoomDatabase {
    public abstract  MedicineCourseDAO medicineCourseDAO();
    public abstract MedicineTakeInfoDAO medicineTakeInfoDAO();
}
