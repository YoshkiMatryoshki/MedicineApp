package com.example.medicineapp.fragments.nearest;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class NearestViewModel extends ViewModel {
    private MutableLiveData<String> mText;

    public NearestViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("TEST");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
