package com.example.e_debt_book.ui.changePhoneMarket;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ChangePhoneViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public ChangePhoneViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is slideshow fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}