package com.example.medicineapp.fragments.configure;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ConfigViewModel extends ViewModel {
    private MutableLiveData<String> testText = new MutableLiveData<String>();

    public void setValue (String string){
        testText.setValue(string);
    }
    public LiveData<String> getValue(){
        return testText;
    }
}
