package com.example.medicineapp.fragments.configure;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.medicineapp.database.MedCoursePacked;
import com.example.medicineapp.database.MedicineCourse;
import com.example.medicineapp.database.MedicineTakeInfo;

public class ConfigViewModel extends ViewModel {
    private MutableLiveData<MedCoursePacked> modelData = new MutableLiveData<MedCoursePacked>();

    public void setValue (MedCoursePacked newCourse){
        modelData.setValue(newCourse);
    }
    public LiveData<MedCoursePacked> getValue(){
        return modelData;
    }

}
