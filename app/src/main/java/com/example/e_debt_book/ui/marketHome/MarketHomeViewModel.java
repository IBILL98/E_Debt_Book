package com.example.e_debt_book.ui.marketHome;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MarketHomeViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public MarketHomeViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is Market home fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}