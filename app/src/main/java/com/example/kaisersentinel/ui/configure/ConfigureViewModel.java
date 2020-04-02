package com.example.kaisersentinel.ui.configure;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ConfigureViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public ConfigureViewModel() {
        mText = new MutableLiveData<>();
        //mText.setValue("configure fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }

}