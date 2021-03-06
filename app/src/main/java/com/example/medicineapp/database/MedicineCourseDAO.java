package com.example.medicineapp.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface MedicineCourseDAO {
    @Query("SELECT * FROM courses ORDER BY endCourse DESC")
    List<MedicineCourse> getAllCourses();

    @Query("SELECT * FROM courses WHERE medName = :medName")
    List<MedicineCourse> getUniqCourse(String medName);

    @Insert
    void insertCourse(MedicineCourse medicineCourse);


    @Query("DELETE FROM courses WHERE id =:id")
    void deleteRecord(int id);

    @Query("DELETE FROM courses")
    void deleteALLCourses();
}
