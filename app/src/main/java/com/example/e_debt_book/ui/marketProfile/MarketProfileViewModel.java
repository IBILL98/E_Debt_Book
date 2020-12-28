package com.example.e_debt_book.ui.marketProfile;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MarketProfileViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public MarketProfileViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is Market Edit Profile fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}