package com.example.medicineapp.fragments.configure;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.medicineapp.database.MedicineCourse;

public class ConfigViewModel extends ViewModel {
    private MutableLiveData<MedicineCourse> modelData = new MutableLiveData<MedicineCourse>();

    public void setValue (MedicineCourse newCourse){
        modelData.setValue(newCourse);
    }
    public LiveData<MedicineCourse> getValue(){
        return modelData;
    }
}
