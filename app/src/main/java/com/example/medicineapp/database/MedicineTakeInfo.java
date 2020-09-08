package com.example.medicineapp.database;

import java.util.Date;

public class MedicineTakeInfo {

    public int id;

    public int medicineId;

    public Date takeDay;

    //0 - dont answered, 1 - taken, 2 - dont (left swipe)
    public int isTaken;
}
