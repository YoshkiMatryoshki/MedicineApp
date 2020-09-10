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



    @Query("SELECT courses.id, medName, medDose, takeDay FROM courses,takeinfo"
            + " WHERE courses.id == takeinfo.medicineId"
            + " ORDER BY takeDay DESC")
    List<MedicineTakeToUser> getALLTEST();
}
