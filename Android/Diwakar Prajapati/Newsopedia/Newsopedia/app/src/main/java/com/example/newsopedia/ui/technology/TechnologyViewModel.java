package com.example.newsopedia.ui.technology;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class TechnologyViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public TechnologyViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is technology fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}