package com.example.medicineapp.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface MedicineCourseDAO {
    @Query("SELECT * FROM courses")
    List<MedicineCourse> getAllCourses();

    @Insert
    void insertCourse(MedicineCourse medicineCourse);

    @Query("DELETE FROM courses")
    void deleteALL();
}
