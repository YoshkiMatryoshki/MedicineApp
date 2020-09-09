package com.example.medicineapp.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface MedicineTakeInfoDAO {
    @Query("SELECT * FROM takeinfo")
    List<MedicineTakeInfo> getAllInfo();

    @Insert
    void insertInfo(MedicineTakeInfo medicineTakeInfo);

    @Query("DELETE FROM takeinfo")
    void deletaALLInfo();
}
