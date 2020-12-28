package com.example.e_debt_book.ui.marketSettings;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MarketSettingsViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public MarketSettingsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is Settings fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}