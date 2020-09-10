package com.example.medicineapp.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.Date;
import java.util.List;

@Dao
public interface MedicineTakeInfoDAO {
    @Query("SELECT * FROM takeinfo")
    List<MedicineTakeInfo> getAllInfo();

    @Insert
    void insertInfo(MedicineTakeInfo medicineTakeInfo);

    @Query("DELETE FROM takeinfo")
    void deletaALLInfo();

    @Query("SELECT takeinfo.id, medName, medDose, takeDay FROM courses, takeinfo "
            + "WHERE courses.id == takeinfo.medicineId AND takeinfo.isTaken == 0 "
            + "AND takeinfo.takeDay <= :end "
            + "ORDER BY takeDay DESC")
    List<MedicineTakeToUser> getUserDataRepresentation(Long end);

    @Query("UPDATE takeinfo SET isTaken = :isTaken WHERE id = :id")
    void updateTakeInfo(int isTaken, int id);

    @Query("DELETE FROM takeinfo WHERE medicineId==:id")
    void deleteAllCourse(int id);

    //Get all records within 1 day from start
    @Query("SELECT takeinfo.id, medName, medDose, takeDay FROM courses, takeinfo "
            + "WHERE courses.id == takeinfo.medicineId AND takeinfo.isTaken == 0 "
            + "AND takeinfo.takeDay BETWEEN :start AND :end "
            + "ORDER BY takeDay DESC")
    List<MedicineTakeToUser> getAllInBetweenRecords(Long start, Long end);
    ////Get all records within 2 dates by main course  id

    @Query("SELECT takeinfo.id, medName, medDose, takeDay FROM courses, takeinfo "
            + "WHERE courses.id == takeinfo.medicineId AND courses.medName == :courseName "
            + "AND takeinfo.takeDay BETWEEN :start AND :end "
            + "ORDER BY takeDay DESC")
    List<MedicineTakeToUser> getAllInBetweenRecordsByID(Long start, Long end, String courseName);



    @Query("SELECT courses.id, medName, medDose, takeDay FROM courses,takeinfo"
            + " WHERE courses.id == takeinfo.medicineId"
            + " ORDER BY takeDay DESC")
    List<MedicineTakeToUser> TEST_getALL();


}
